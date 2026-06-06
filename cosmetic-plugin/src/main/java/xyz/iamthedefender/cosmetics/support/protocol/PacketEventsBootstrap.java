package xyz.iamthedefender.cosmetics.support.protocol;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PacketEventsBootstrap {

    private static final String[] PACKETEVENTS_PLUGIN_NAMES = {"packetevents", "PacketEvents"};
    private static final String LATEST_RELEASE_API = "https://api.github.com/repos/retrooper/packetevents/releases/latest";
    private static final Pattern DOWNLOAD_URL_PATTERN = Pattern.compile("\"browser_download_url\":\"([^\"]*packetevents-spigot-[^\"]+\\.jar)\"");

    public static boolean ensureInstalledAndEnabled(JavaPlugin plugin) {
        Plugin loaded = findLoadedPlugin();
        if (loaded != null) {
            return ensureEnabled(loaded);
        }

        File installedJar = findInstalledJar(plugin);
        if (installedJar == null) {
            installedJar = downloadLatestRelease(plugin);
        }
        if (installedJar == null) {
            return false;
        }

        return loadAndEnable(plugin, installedJar);
    }

    public static boolean isAvailable() {
        return findLoadedPlugin() != null;
    }

    private static Plugin findLoadedPlugin() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        for (String pluginName : PACKETEVENTS_PLUGIN_NAMES) {
            Plugin plugin = pluginManager.getPlugin(pluginName);
            if (plugin != null) {
                return plugin;
            }
        }
        return null;
    }

    private static boolean ensureEnabled(Plugin plugin) {
        if (plugin.isEnabled()) {
            return true;
        }
        Bukkit.getPluginManager().enablePlugin(plugin);
        return plugin.isEnabled();
    }

    private static File findInstalledJar(JavaPlugin plugin) {
        File pluginsFolder = plugin.getDataFolder().getParentFile();
        if (pluginsFolder == null || !pluginsFolder.isDirectory()) {
            return null;
        }

        File[] files = pluginsFolder.listFiles((dir, name) -> name.startsWith("packetevents-spigot-") && name.endsWith(".jar"));
        if (files == null || files.length == 0) {
            return null;
        }
        return files[0];
    }

    private static File downloadLatestRelease(JavaPlugin plugin) {
        plugin.getLogger().info("PacketEvents was not found, downloading the latest release from GitHub...");
        try {
            ReleaseAsset releaseAsset = fetchLatestRelease();
            File pluginsFolder = Objects.requireNonNull(plugin.getDataFolder().getParentFile(), "plugins folder");
            if (!pluginsFolder.exists() && !pluginsFolder.mkdirs()) {
                plugin.getLogger().severe("Failed to create the plugins directory for PacketEvents.");
                return null;
            }

            File destination = new File(pluginsFolder, releaseAsset.fileName());
            downloadFile(releaseAsset.downloadUrl(), destination);
            plugin.getLogger().info("Downloaded PacketEvents " + releaseAsset.version() + " to " + destination.getName());
            return destination;
        } catch (IOException exception) {
            plugin.getLogger().severe("Failed to download PacketEvents: " + exception.getMessage());
            return null;
        }
    }

    private static ReleaseAsset fetchLatestRelease() throws IOException {
        HttpURLConnection connection = openConnection(new URL(LATEST_RELEASE_API));
        try (InputStream inputStream = connection.getInputStream()) {
            String body = new String(inputStream.readAllBytes());
            Matcher matcher = DOWNLOAD_URL_PATTERN.matcher(body);
            if (!matcher.find()) {
                throw new IOException("Latest PacketEvents spigot asset was not present in the GitHub release response.");
            }

            String downloadUrl = matcher.group(1);
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf('/') + 1);
            String version = fileName
                    .replace("packetevents-spigot-", "")
                    .replace(".jar", "");
            return new ReleaseAsset(version, fileName, downloadUrl);
        } finally {
            connection.disconnect();
        }
    }

    private static boolean loadAndEnable(JavaPlugin plugin, File pluginJar) {
        try {
            Plugin loadedPlugin = Bukkit.getPluginManager().loadPlugin(pluginJar);
            if (loadedPlugin == null) {
                plugin.getLogger().severe("Bukkit returned null while loading PacketEvents from " + pluginJar.getName());
                return false;
            }
            Bukkit.getPluginManager().enablePlugin(loadedPlugin);
            return loadedPlugin.isEnabled();
        } catch (Exception exception) {
            plugin.getLogger().severe("Failed to load PacketEvents from " + pluginJar.getName() + ": " + exception.getMessage());
            return false;
        }
    }

    private static void downloadFile(String downloadUrl, File destination) throws IOException {
        HttpURLConnection connection = openConnection(new URL(downloadUrl));
        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = Files.newOutputStream(destination.toPath())) {
            inputStream.transferTo(outputStream);
        } finally {
            connection.disconnect();
        }
    }

    private static HttpURLConnection openConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Accept", "application/vnd.github+json");
        connection.setRequestProperty("User-Agent", "BedWars-Cosmetics-PacketEvents-Bootstrap");
        connection.setConnectTimeout(15_000);
        connection.setReadTimeout(30_000);
        return connection;
    }

    private static final class ReleaseAsset {
        private final String version;
        private final String fileName;
        private final String downloadUrl;

        private ReleaseAsset(String version, String fileName, String downloadUrl) {
            this.version = version;
            this.fileName = fileName;
            this.downloadUrl = downloadUrl;
        }

        private String version() {
            return version;
        }

        private String fileName() {
            return fileName;
        }

        private String downloadUrl() {
            return downloadUrl;
        }
    }
}
