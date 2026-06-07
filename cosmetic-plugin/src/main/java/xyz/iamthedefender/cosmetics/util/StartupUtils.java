

package xyz.iamthedefender.cosmetics.util;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnLivingEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnPlayer;
import net.byteflux.libby.Library;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticRegistry;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.Spray;
import xyz.iamthedefender.cosmetics.api.database.DatabaseType;
import xyz.iamthedefender.cosmetics.api.handler.IWorldEditHandler;
import xyz.iamthedefender.cosmetics.api.util.Messages;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;
import xyz.iamthedefender.cosmetics.api.versionsupport.IVersionSupport;
import xyz.iamthedefender.cosmetics.category.bedbreakeffects.items.*;
import xyz.iamthedefender.cosmetics.category.deathcries.items.DeathCryItems;
import xyz.iamthedefender.cosmetics.category.deathcries.preview.DeathCryPreview;
import xyz.iamthedefender.cosmetics.category.finalkilleffects.items.*;
import xyz.iamthedefender.cosmetics.category.finalkilleffects.preview.FinalKillEffectPreview;
import xyz.iamthedefender.cosmetics.category.glyphs.items.GlyphItems;
import xyz.iamthedefender.cosmetics.category.glyphs.preview.GlyphPreview;
import xyz.iamthedefender.cosmetics.category.islandtoppers.items.IslandTopperItems;
import xyz.iamthedefender.cosmetics.category.killmessage.items.KillMessageItems;
import xyz.iamthedefender.cosmetics.category.killmessage.preview.KillMessagePreview;
import xyz.iamthedefender.cosmetics.category.projectiletrails.items.ProjectileTrailItems;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.items.ShopKeeperItems;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.preview.ShopKeeperPreview;
import xyz.iamthedefender.cosmetics.category.sprays.items.SprayItems;
import xyz.iamthedefender.cosmetics.category.sprays.preview.SprayPreview;
import xyz.iamthedefender.cosmetics.category.victorydance.items.*;
import xyz.iamthedefender.cosmetics.category.woodskin.items.*;
import xyz.iamthedefender.cosmetics.category.woodskin.items.log.*;
import xyz.iamthedefender.cosmetics.listener.CosmeticListener;
import xyz.iamthedefender.cosmetics.support.placeholders.CosmeticsPlaceholders;
import xyz.iamthedefender.cosmetics.util.lib.CosmeticsLibraryManager;
import xyz.iamthedefender.cosmetics.support.protocol.PacketEventsBridge;
import xyz.iamthedefender.cosmetics.util.version.VersionSupportRegistry;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Logger;


public class StartupUtils {

    private static final String MYSQL_CONNECTOR_VERSION = "9.7.0";
    private static final String MARIADB_CONNECTOR_VERSION = "3.5.6";
    private static final String POSTGRESQL_CONNECTOR_VERSION = "42.7.11";
    private static final String MONGODB_DRIVER_VERSION = "5.6.0";
    private static final String REDIS_DRIVER_VERSION = "7.1.0";
    private static final String SQLITE_DRIVER_VERSION = "3.53.1.0";
    private static final String HIKARI_CP_VERSION = "7.0.2";
    private static final String FASTUTIL_VERSION = "8.5.16";
    private static final String SLF4J_VERSION = "2.0.17";

    public static boolean BW2023 = Bukkit.getPluginManager().getPlugin("BedWars2023") != null ||
            Bukkit.getPluginManager().getPlugin("BWProxy2023") != null;

    /**
     * Register events and handler
     *
     * @author IamTheDefender
     */
    public static void registerEvents() {
        registerListeners(new xyz.iamthedefender.cosmetics.listener.PlayerListener(), new CosmeticListener());
    }

    public static void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, CosmeticsPlugin.getInstance());
        }
    }

    public static void convertSpraysURLs() {
        for (Spray spray : CosmeticRegistry.getByCategory(CosmeticType.SPRAYS)) {
            ConfigManager config = ConfigUtils.getSprays();
            String urlString = config.getString(CosmeticType.SPRAYS.getSectionKey() + "." + spray.getIdentifier() + ".url");
            if (urlString == null || urlString.isEmpty()) {
                DebugUtil.addMessage("Skipping Spray: " + spray.getIdentifier());
                continue;
            }
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                DebugUtil.addMessage("URL: " + urlString);
                throw new RuntimeException(e);
            }

            File file = new File(CosmeticsPlugin.getInstance().getHandler().getAddonPath() + "/" + getSprayDirectory() + "/" + spray.getIdentifier() + "." + FileUtil.getFileExtension(urlString));
            String destinationPath = file.getAbsolutePath();
            if (file.exists()) {
                DebugUtil.addMessage("Skipping existing file: " + destinationPath);
                continue;
            }

            URL sprayUrl = url;
            Run.async(() -> {
                try (InputStream in = sprayUrl.openStream();
                     OutputStream out = new BufferedOutputStream(new FileOutputStream(destinationPath))) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    DebugUtil.addMessage("Downloaded: " + destinationPath);
                } catch (IOException e) {
                    Logger.getLogger("Minecraft").warning("Failed to download spray asset: " + destinationPath + " (" + e.getMessage() + ")");
                }
            });
        }
    }

    /**
     * Get version support for the version of minecraft
     * plugin is running on.
     * <p>
     * This method should be used only by the plugin
     *
     * @return Version Support or null
     */
    public static IVersionSupport getVersionSupport() {
        return VersionSupportRegistry.resolveVersionSupport();
    }

    public static IWorldEditHandler getWorldEditHandler() {
        return VersionSupportRegistry.resolveWorldEditHandler();
    }


    /**
     * This method creates the necessary folders for the plugin to function properly.
     * It creates a folder called "Sprays" and "IslandToppers" in the plugin directory.
     * If the folders do not exist, they will be created.
     */
    public static void createFolders() {
        File spraysFolder = new File(CosmeticsPlugin.getInstance().getHandler().getAddonPath() + "/" + getSprayDirectory());
        if (!spraysFolder.exists()) {
            spraysFolder.mkdirs();
        }
        File islandToppersFolder = new File(CosmeticsPlugin.getInstance().getHandler().getAddonPath() + "/IslandToppers");
        if (!islandToppersFolder.exists()) {
            islandToppersFolder.mkdirs();
        }
        File cubeFile = new File(CosmeticsPlugin.getInstance().getHandler().getAddonPath() + "/IslandToppers/cube.schematic");
        // Save if not found
        if (cubeFile.exists()) return;
        try {
            downloadFile(new URL("https://dl.dropboxusercontent.com/s/x9rmk36qa1uwrr3/idkcube.schematic"), cubeFile.getPath());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method will download Glyphs and
     * unzip the temp.zip to get the Images
     * in the folder and remove the temp.zip.
     */
    public static void downloadGlyphs() {
        File folder = new File(CosmeticsPlugin.getInstance().getHandler().getAddonPath() + "/Glyphs");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        final String temp = CosmeticsPlugin.getInstance().getHandler().getAddonPath() + "/Glyphs/temp.zip";
        final File tempFile = new File(temp);
        if (tempFile.exists()) {
            tempFile.delete();
        }
        String[] filesInFolder = folder.list();
        if (filesInFolder != null && filesInFolder.length != 0) {
            return;
        }
        JavaPlugin plugin = CosmeticsPlugin.getInstance();
        Utility.saveFileFromInputStream(plugin.getResource("glyph/GlyphsTemp.zip"), "temp.zip", folder);
        try {
            new UnzippingUtils().unzip(tempFile.getPath(), folder.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tempFile.delete();
    }

    /**
     * Add the spray files to the plugin folder
     */
    public static void unzipSpray() {
        String sprayDir = getSprayDirectory();
        File folder = new File(CosmeticsPlugin.getInstance().getHandler().getAddonPath() + "/" + sprayDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        final String temp = CosmeticsPlugin.getInstance().getHandler().getAddonPath() + "/" + sprayDir + "/temp.zip";
        final File tempFile = new File(temp);
        if (tempFile.exists()) {
            tempFile.delete();
        }
        String[] filesInFolder = folder.list();
        if (filesInFolder != null && filesInFolder.length != 0) {
            return;
        }
        JavaPlugin plugin = CosmeticsPlugin.getInstance();
        Utility.saveFileFromInputStream(plugin.getResource("spray/Sprays.zip"), "temp.zip", folder);
        try {
            new UnzippingUtils().unzip(tempFile.getPath(), folder.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tempFile.delete();
    }

    /**
     * Download a file from a URL to a file
     *
     * @param url      URL for the download, should be a direct download
     * @param filePath Path to the file
     */
    public static void downloadFile(URL url, String filePath) {
        try {
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Migrates user-facing text defaults and legacy config overrides into language files.
     */
    public static void updateConfigs() {
        ConfigManager mainConfig = ConfigUtils.getMainConfig();
        Messages.of("title", mainConfig.getString("messages.menu.main-title", "&8Cosmetics")).saveIfMissing();
        Messages.of("menu.category-title-format", mainConfig.getString("messages.menu.category-title-format", "&8{category}")).saveIfMissing();
        Messages.of("selected", mainConfig.getString("messages.cosmetics.selected", "&aSELECTED!")).saveIfMissing();
        Messages.of("click-to-select", mainConfig.getString("messages.cosmetics.click-to-select", "&eClick to select.")).saveIfMissing();
        Messages.of("click-to-purchase", mainConfig.getString("messages.cosmetics.click-to-purchase", "&eClick to purchase.")).saveIfMissing();
        Messages.of("not-purchase-able", mainConfig.getString("messages.cosmetics.locked", "&cLOCKED.")).saveIfMissing();
        Messages.of("no-coins", mainConfig.getString("messages.cosmetics.no-coins", "&cYou don't have enough coins!")).saveIfMissing();
        Messages.of("spray-msg", mainConfig.getString("messages.cosmetics.spray-cooldown", "&cYou must wait 3 seconds between spray uses!")).saveIfMissing();
        Messages.of("final-kill-suffix", getString("messages.final-kill-suffix", "Final-Kill-Suffix", "&b&lFINAL KILL!")).saveIfMissing();
        Messages.SEARCH_RESULTS_TITLE_FORMAT.saveIfMissing();
        Messages.SEARCH_RESULTS_EMPTY_QUERY_LABEL.saveIfMissing();
        Messages.SEARCH_RESULTS_SEARCH_AGAIN_NAME.saveIfMissing();
        Messages.SEARCH_RESULTS_SEARCH_AGAIN_LORE.saveIfMissing();
        Messages.SEARCH_RESULTS_NO_RESULTS_NAME.saveIfMissing();
        Messages.SEARCH_RESULTS_NO_RESULTS_LORE.saveIfMissing();
    }

    /**
     * Checks if the dependencies are present and enabled.
     *
     * @return true if they are present, false otherwise.
     */
    public static boolean checkDependencies() {
        Logger log = Bukkit.getLogger();
        if (Bukkit.getPluginManager().getPlugin("BedWars2023") == null) {
            if (!isPluginEnabled("BedWars1058") && !CosmeticsPlugin.getInstance().getApi().isProxy() && !isPluginEnabled("BedWars")) {
                log.severe("Cosmetics addon requires BedWars1058, BedWars2023, BedWarsProxy or ScreamingBedWars to work!");
                return false;
            }
        } else {
            BW2023 = true;
        }
        if (!isPluginEnabled("Vault")) {
            log.severe("Cosmetics addon requires Vault to work properly!");
            return false;
        }
        if (!isPluginEnabled("WorldEdit") && !isPluginEnabled("FastAsyncWorldEdit")) {
            log.severe("Cosmetics addon requires WorldEdit to work!");
            return false;
        }

        if (isPluginEnabled("PlaceholderAPI")) {
            log.info("Found PlaceholderAPI, loading placeholders!");
            new CosmeticsPlaceholders().register();
            CosmeticsPlugin.setPlaceholderAPI(true);
        }
        return true;
    }

    /**
     * This method checks if a plugin is enabled.
     *
     * @param plugin The name of the plugin, for example, "BedWars1058".
     * @return true if plugin is enabled, false otherwise.
     */
    public static boolean isPluginEnabled(String plugin) {
        return Bukkit.getPluginManager().isPluginEnabled(plugin);
    }


    /**
     * This method will load all the premade cosmetics,
     * that come included.
     */
    public static void loadCosmetics() {
        new TornadoBedDestroy().register();
        new HologramBedDestroy().register();
        new BedBugsBedDestroy().register();
        new FireworksBedDestroy().register();
        new LightStrikeBedDestroy().register();
        new NoneBedDestroy().register();
        new PigMissileBedDestroy().register();
        new SquidMissileBedDestroy().register();
        new TheifBedDestroy().register();
        new RandomBedDestroy().register();

        //Items From Config
        new DeathCryItems().registerConfigItems();
        new GlyphItems().registerConfigItems();
        if (isPluginEnabled("WorldEdit") || isPluginEnabled("FastAsyncWorldEdit")) {
            new IslandTopperItems().registerItems();
        } else {
            Bukkit.getLogger().warning("Can't find worldedit! IslandTopper will not load!");
        }
        KillMessageItems.registerConfigItems();
        new ShopKeeperItems().registerItems();
        SprayItems.registerConfigItems();
        new ProjectileTrailItems().registerConfigItems();

        // Final Kill effect
        new BatCruxEffect().register();
        new BurningShoesEffect().register();
        new FireworkEffect().register();
        new HeartAuraEffect().register();
        new LightningStrikeEffect().register();
        new NoneEffect().register();
        new RektEffect().register();
        new SquidMissleEffect().register();
        new TornadoEffect().register();

        // Victory Dance
        new AnvilRainDance().register();
        new ColdSnapDance().register();
        new DragonRiderDance().register();
        new FireworksDance().register();
        new FloatingLanternDance().register();
        new HauntedDance().register();
        new NightShiftDance().register();
        new NoneDance().register();
        new RainbowDollyDance().register();
        new RainingPigsDance().register();
        new ToyStickDance().register();
        new TwerkApocalypseDance().register();
        new WitherRiderDance().register();
        new YeeHawDance().register();
        new GuardiansDance().register();
        new MeteorShowerDance().register();
//        new ChickenRiderDance().register();
//        new GhastRiderDance().register();
//        new SuperSheepDance().register();
//        new BlizzardDance().register();
//        new SwarmDance().register();
//        new ExplodingBunniesDance().register();
//        new PuppyPartyDance().register();

        // Wood Skins
        new OakPlank().register();
        new BirchPlank().register();
        new AcaciaPlank().register();
        new DarkOakPlank().register();
        new JunglePlank().register();
        new SprucePlank().register();
        new AcaciaLog().register();
        new BirchLog().register();
        new JungleLog().register();
        new OakLog().register();
        new SpruceLog().register();

        // Previews
        new ShopKeeperPreview();
        new GlyphPreview();
        new KillMessagePreview();
        new FinalKillEffectPreview();
        // new IslandTopperPreview(); - temporarily disabled
        new DeathCryPreview();
        new SprayPreview();
    }


    public static void loadLibraries() {
        CosmeticsLibraryManager libraryManager = new CosmeticsLibraryManager(CosmeticsPlugin.getInstance());
        libraryManager.addMavenCentral();
        libraryManager.addJitPack();

        libraryManager.loadLibrary(new Library.Builder()
                .groupId("it{}unimi{}dsi")
                .artifactId("fastutil")
                .version(FASTUTIL_VERSION)
                .build());
        libraryManager.loadLibrary(new Library.Builder()
                .groupId("org{}slf4j")
                .artifactId("slf4j-api")
                .version(SLF4J_VERSION)
                .build());

//        libraryManager.loadLibrary(Library.builder()
//                .groupId("de{}rapha149{}signgui")
//                .artifactId("signgui")
//                .version("2.5.4")
//                .relocate("de{}rapha149{}signgui", "xyz{}iamthedefender{}cosmetics{}support{}signgui")
//                .build());

        DatabaseType databaseType = getConfiguredDatabaseType();
        CosmeticsPlugin.getInstance().getLogger().info("Preparing runtime storage libraries for " + databaseType.name() + "...");

        switch (databaseType) {
            case MYSQL:
                loadSqlLibraries(libraryManager, new Library.Builder()
                        .groupId("com{}mysql")
                        .artifactId("mysql-connector-j")
                        .version(MYSQL_CONNECTOR_VERSION)
                        .build());
                break;
            case MARIADB:
                loadSqlLibraries(libraryManager, new Library.Builder()
                        .groupId("org{}mariadb{}jdbc")
                        .artifactId("mariadb-java-client")
                        .version(MARIADB_CONNECTOR_VERSION)
                        .build());
                break;
            case POSTGRESQL:
                loadSqlLibraries(libraryManager, new Library.Builder()
                        .groupId("org{}postgresql")
                        .artifactId("postgresql")
                        .version(POSTGRESQL_CONNECTOR_VERSION)
                        .build());
                break;
            case SQLITE:
                loadSqlLibraries(libraryManager, new Library.Builder()
                        .groupId("org{}xerial")
                        .artifactId("sqlite-jdbc")
                        .version(SQLITE_DRIVER_VERSION)
                        .build());
                break;
            case MONGODB:
                libraryManager.loadLibrary(new Library.Builder()
                        .groupId("org{}mongodb")
                        .artifactId("mongodb-driver-sync")
                        .version(MONGODB_DRIVER_VERSION)
                        .build());
                break;
            case REDIS:
                libraryManager.loadLibrary(new Library.Builder()
                        .groupId("redis{}clients")
                        .artifactId("jedis")
                        .version(REDIS_DRIVER_VERSION)
                        .build());
                break;
            default:
                break;
        }
    }

    private static void loadSqlLibraries(CosmeticsLibraryManager libraryManager, Library jdbcDriver) {
        libraryManager.loadLibrary(new Library.Builder()
                .groupId("com{}zaxxer")
                .artifactId("HikariCP")
                .version(HIKARI_CP_VERSION)
                .relocate("com{}zaxxer{}hikari", "xyz{}iamthedefender{}cosmetics{}support{}hikari")
                .build());
        libraryManager.loadLibrary(jdbcDriver);
    }

    public static Location getCosmeticLocation() {
        Location location = readLocation("preview.locations.cosmetic", "cosmetic-preview.cosmetic-location");
        location.setX(location.getBlockX() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);
        location.getChunk().load(true);
        return location;
    }

    public static Location getPlayerLocation() {
        Location location = readLocation("preview.locations.player", "cosmetic-preview.player-location");
        location.setX(location.getBlockX() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);
        location.getChunk().load(true);
        return location;
    }

    public static boolean getBoolean(String primaryPath, String legacyPath, boolean defaultValue) {
        ConfigManager config = ConfigUtils.getMainConfig();
        if (config.contains(primaryPath)) {
            return config.getBoolean(primaryPath, defaultValue);
        }
        if (legacyPath != null && config.contains(legacyPath)) {
            return config.getBoolean(legacyPath, defaultValue);
        }
        return defaultValue;
    }

    public static String getString(String primaryPath, String legacyPath, String defaultValue) {
        ConfigManager config = ConfigUtils.getMainConfig();
        if (config.contains(primaryPath)) {
            return config.getString(primaryPath, defaultValue);
        }
        if (legacyPath != null && config.contains(legacyPath)) {
            return config.getString(legacyPath, defaultValue);
        }
        return defaultValue;
    }

    public static int getInt(String primaryPath, String legacyPath, int defaultValue) {
        ConfigManager config = ConfigUtils.getMainConfig();
        if (config.contains(primaryPath)) {
            return config.getYml().getInt(primaryPath, defaultValue);
        }
        if (legacyPath != null && config.contains(legacyPath)) {
            return config.getYml().getInt(legacyPath, defaultValue);
        }
        return defaultValue;
    }

    public static boolean isFeatureEnabled(String featureKey) {
        return getBoolean("features." + featureKey + ".enabled", featureKey + ".enabled", true);
    }

    public static boolean isCosmeticEnabled(CosmeticType<?> cosmeticType) {
        return isFeatureEnabled(cosmeticType.getSectionKey());
    }

    public static DatabaseType getConfiguredDatabaseType() {
        String configured = getString("database.type", null, null);
        if (configured != null && !configured.trim().isEmpty()) {
            try {
                return DatabaseType.valueOf(configured.trim().toUpperCase());
            } catch (IllegalArgumentException ignored) {
            }
        }
        return getBoolean("database.mysql.enabled", "mysql.enable", false) ? DatabaseType.MYSQL : DatabaseType.SQLITE;
    }

    public static boolean isBackItemEnabledInCategoryMenu() {
        return getBoolean("menus.category.navigation.back.enabled", "BackItemInCosmeticsMenu", true);
    }

    public static boolean shouldShopkeeperLookClose() {
        return getBoolean("features.shopkeeper-skins.look-close", "settings.shopkeeper_skins.look_close", false);
    }

    public static boolean useIslandTopperOrder() {
        return getBoolean("features.island-toppers.use-team-order", "island-toppers.order", true);
    }

    public static String getSprayDirectory() {
        return getString("storage.sprays.directory", "Spray-Dir", "Sprays");
    }

    public static String getFinalKillSuffix() {
        return Messages.FINAL_KILL_SUFFIX.value(null);
    }

    public static boolean isDebugEnabled() {
        return getBoolean("debug.enabled", "Debug", false);
    }

    public static boolean usePacketShopkeeperEntityNpcs() {
        return getBoolean("features.packet-npcs.shopkeeper-entities", "packet-npcs.shopkeeper-entities", true);
    }

    private static Location readLocation(String primaryPath, String legacyPath) {
        ConfigManager config = ConfigUtils.getMainConfig();
        String resolved = config.contains(primaryPath + ".world") ? primaryPath : legacyPath;
        World world = Bukkit.getWorld(config.getString(resolved + ".world"));
        double x = config.getYml().getDouble(resolved + ".x");
        double y = config.getYml().getDouble(resolved + ".y");
        double z = config.getYml().getDouble(resolved + ".z");
        float yaw = (float) config.getYml().getDouble(resolved + ".yaw");
        float pitch = (float) config.getYml().getDouble(resolved + ".pitch");
        if (world == null && !Bukkit.getWorlds().isEmpty()) {
            world = Bukkit.getWorlds().get(0);
        }
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static void addEntityHideListener() {
        PacketEventsBridge.registerListener(new PacketListenerAbstract() {
            @Override
            public void onPacketSend(com.github.retrooper.packetevents.event.PacketSendEvent event) {
                int entityID;
                if (event.getPacketType() == PacketType.Play.Server.SPAWN_ENTITY) {
                    entityID = new WrapperPlayServerSpawnEntity(event).getEntityId();
                } else if (event.getPacketType() == PacketType.Play.Server.SPAWN_LIVING_ENTITY) {
                    entityID = new WrapperPlayServerSpawnLivingEntity(event).getEntityId();
                } else if (event.getPacketType() == PacketType.Play.Server.SPAWN_PLAYER) {
                    entityID = new WrapperPlayServerSpawnPlayer(event).getEntityId();
                } else {
                    return;
                }

                Player player = event.getPlayer();
                if (CosmeticsPlugin.getInstance().getEntityPlayerHashMap().containsKey(entityID)) {
                    if (!player.getUniqueId().equals(CosmeticsPlugin.getInstance().getEntityPlayerHashMap().get(entityID).getUniqueId())) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }

    public static void addPreviewInteractionListener() {
        PacketEventsBridge.registerListener(new PacketListenerAbstract() {
            @Override
            public void onPacketReceive(com.github.retrooper.packetevents.event.PacketReceiveEvent event) {
                if (event.getPacketType() != PacketType.Play.Client.INTERACT_ENTITY) {
                    return;
                }

                Player player = event.getPlayer();
                int targetId = new WrapperPlayClientInteractEntity(event).getEntityId();

                if (targetId == player.getEntityId()) {
                    event.setCancelled(true);
                    return;
                }

                boolean inPreview = CosmeticsPlugin.getInstance().getApi().getPreviewList().stream()
                        .anyMatch(preview -> preview.getActiveTasks().containsKey(player));

                if (inPreview) {
                    event.setCancelled(true);
                }
            }
        });
    }

    public static void addWindowClickListener() {
        PacketEventsBridge.registerListener(new PacketListenerAbstract() {
            @Override
            public void onPacketReceive(com.github.retrooper.packetevents.event.PacketReceiveEvent event) {
                if (event.getPacketType() != PacketType.Play.Client.CLICK_WINDOW) {
                    return;
                }

                new WrapperPlayClientClickWindow(event);
                Player player = event.getPlayer();

                boolean isClosing = CosmeticsPlugin.getInstance().getApi().getPreviewList().stream()
                        .anyMatch(preview -> preview.isProgrammaticClose(player));

                if (isClosing) {
                    event.setCancelled(true);
                    return;
                }

                boolean inPreview = CosmeticsPlugin.getInstance().getApi().getPreviewList().stream()
                        .anyMatch(preview -> preview.getActiveTasks().containsKey(player));

                if (inPreview) {
                    event.setCancelled(true);
                }
            }
        });
    }
}
