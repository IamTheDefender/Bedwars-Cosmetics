

package me.defender.cosmetics.util;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.hakan.core.HCore;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.CosmeticsAPI;
import me.defender.cosmetics.api.cosmetics.category.*;
import me.defender.cosmetics.api.handler.IHandler;
import me.defender.cosmetics.category.bedbreakeffects.items.*;
import me.defender.cosmetics.category.deathcries.items.DeathCryItems;
import me.defender.cosmetics.category.finalkilleffects.items.*;
import me.defender.cosmetics.category.glyphs.items.GlyphItems;
import me.defender.cosmetics.category.islandtoppers.items.IslandTopperItems;
import me.defender.cosmetics.category.killmessage.items.KillMessageItems;
import me.defender.cosmetics.category.projectiletrails.items.ProjectileTrailItems;
import me.defender.cosmetics.category.shopkeeperskins.items.ShopKeeperItems;
import me.defender.cosmetics.category.sprays.items.SprayItems;
import me.defender.cosmetics.category.victorydance.items.*;
import me.defender.cosmetics.category.woodskin.items.*;
import me.defender.cosmetics.category.woodskin.items.log.*;
import me.defender.cosmetics.listener.CosmeticPurchaseListener;
import me.defender.cosmetics.listener.PlayerJoinListener;
import me.defender.cosmetics.support.bedwars.handler.bedwars1058.BW1058Handler;
import me.defender.cosmetics.support.bedwars.handler.bedwars1058.BW1058ProxyHandler;
import me.defender.cosmetics.support.bedwars.handler.bedwars2023.BW2023ProxyHandler;
import me.defender.cosmetics.support.placeholders.Placeholders;
import me.defender.cosmetics.util.lib.CosmeticsLibraryManager;
import net.byteflux.libby.BukkitLibraryManager;
import net.byteflux.libby.Library;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class StartupUtils
{


    // Store all the lists
    public static List<BedDestroy> bedDestroyList;
    public static List<DeathCry> deathCryList;
    public static List<FinalKillEffect> finalKillList;
    public static List<ProjectileTrail> projectileTrailList;
    public static List<Glyph> glyphsList;
    public static List<VictoryDance> victoryDancesList;
    public static List<WoodSkin> woodSkinsList;
    public static List<Spray> sprayList;
    public static List<KillMessage> killMessageList;
    public static List<ShopKeeperSkin> shopKeeperSkinList;
    public static List<IslandTopper> islandTopperList;

    public static boolean isBw2023 = Bukkit.getPluginManager().getPlugin("BedWars2023") != null ||
            Bukkit.getPluginManager().getPlugin("BWProxy2023") != null;

    /**
     Register events and handler
      @author defender
     */
    public static void registerEvents() {
        HCore.registerListeners(new CosmeticPurchaseListener());
        HCore.registerListeners(new PlayerJoinListener());
    }


    /**
     This method creates the necessary folders for the plugin to function properly.
     It creates a folder called "Sprays" and "IslandToppers" in the plugin directory.
     If the folders do not exist, they will be created.
     */
    public static void createFolders() {
        File spraysFolder = new File(Cosmetics.getInstance().getHandler().getAddonPath() + "/" + Cosmetics.getInstance().getConfig().getString("Spray-Dir"));
        if (!spraysFolder.exists()) {
            spraysFolder.mkdirs();
        }
        File islandToppersFolder = new File(Cosmetics.getInstance().getHandler().getAddonPath() + "/IslandToppers");
        if (!islandToppersFolder.exists()) {
            islandToppersFolder.mkdirs();
        }
        File cubeFile = new File(Cosmetics.getInstance().getHandler().getAddonPath() + "/IslandToppers/cube.schematic");
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
        File folder = new File(Cosmetics.getInstance().getHandler().getAddonPath() + "/Glyphs");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        final String temp = Cosmetics.getInstance().getHandler().getAddonPath() + "/Glyphs/temp.zip";
        final File tempFile = new File(temp);
        if (tempFile.exists()) {
            tempFile.delete();
        }
        String[] filesInFolder = folder.list();
        if (filesInFolder != null && filesInFolder.length != 0) {
            return;
        }
        JavaPlugin plugin = Cosmetics.getInstance();
        Utility.saveFileFromInputStream(plugin.getResource("glyph/GlyphsTemp.zip"), "temp.zip", folder);
        try {
            new UnzippingUtils().unzip(tempFile.getPath(), folder.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tempFile.delete();
    }

    /**
     * Download a file from a URL to a file
     * @param url URL for the download, should be a direct download
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
     * This method will add messages to language file, this will only
     * add the following fields, selected, click-to-select, click-to-purchase,
     * no-coins, spray-msg, gui-title.
     */
    public static void updateConfigs(){
        Utility.saveIfNotExistsLang("cosmetics.selected", "&aSELECTED!");
        Utility.saveIfNotExistsLang("cosmetics.click-to-select", "&eClick to select.");
        Utility.saveIfNotExistsLang("cosmetics.click-to-purchase", "&eClick to purchase.");
        Utility.saveIfNotExistsLang("cosmetics.no-coins", "&cYou don't have enough coins!");
        Utility.saveIfNotExistsLang("cosmetics.spray-msg", "&cYou must wait 3 seconds between spray uses!");
        Utility.saveIfNotExistsLang("cosmetics.gui-title", "Cosmetics");
    }

    /**
     * Checks if the dependencies are present and enabled.
     * @return true if they are present, false otherwise.
     */
    public static boolean checkDependencies(){
        Logger log = Bukkit.getLogger();
        if (Bukkit.getPluginManager().getPlugin("BedWars2023") == null) {
            if (!isPluginEnabled("BedWars1058") && !Cosmetics.getInstance().getApi().isProxy()){
                log.severe("Cosmetics addon requires BedWars1058, BedWars2023, or BedWarsProxy to work!");
                return false;
            }
        } else {
            isBw2023 = true;
        }
        if (!isPluginEnabled("Vault")){
            log.severe("Cosmetics addon requires Vault to work properly!");
            return false;
        }
        if (!isPluginEnabled("WorldEdit") && !isPluginEnabled("FastAsyncWorldEdit")){
            log.severe("Cosmetics addon requires WorldEdit to work!");
            return false;
        }

        if (isPluginEnabled("PlaceholderAPI")){
            log.info("Found PlaceholderAPI, loading placeholders!");
            new Placeholders(Cosmetics.getInstance()).register();
            Cosmetics.setPlaceholderAPI(true);
        }
        return true;
    }

    /**
     * This method checks if a plugin is enabled.
     * @param plugin The name of the plugin, for example, "BedWars1058".
     * @return true if plugin is enabled, false otherwise.
     */
    public static boolean isPluginEnabled(String plugin){
        return Bukkit.getPluginManager().isPluginEnabled(plugin);
    }


    /**
     * Loads the cosmetics lists, so the cosmetics can be
     * registered. Called on Startup and reload only.
     */
    public static void loadLists(){
        bedDestroyList = new ArrayList<>();
        deathCryList = new ArrayList<>();
        finalKillList = new ArrayList<>();
        glyphsList = new ArrayList<>();
        islandTopperList = new ArrayList<>();
        projectileTrailList = new ArrayList<>();
        shopKeeperSkinList = new ArrayList<>();
        sprayList = new ArrayList<>();
        victoryDancesList = new ArrayList<>();
        woodSkinsList = new ArrayList<>();
        killMessageList = new ArrayList<>();
    }

    /**
     * This method will load all the premade cosmetics,
     * that come included.
     */
    public static void loadCosmetics(){
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
        }else{
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

        // Wood Skins
        new BirchPlank().register();
        new AcaciaPlank().register();
        new DarkOakPlank().register();
        new JunglePlank().register();
        new OakPlank().register();
        new SprucePlank().register();
        new AcaciaLog().register();
        new BirchLog().register();
        new JungleLog().register();
        new OakLog().register();
        new SpruceLog().register();
    }


    public static void loadLibraries() {
        Cosmetics.getInstance().getLogger().info("Loading libraries...");
        CosmeticsLibraryManager libraryManager = new CosmeticsLibraryManager(Cosmetics.getInstance());
        Library mysql = new Library.Builder().groupId("com{}mysql").artifactId("mysql-connector-j").version("8.2.0").build();
        Library hCore = new Library.Builder().groupId("com{}github{}hakan-krgn{}hCore").artifactId("hCore-bukkit").version("0.7.3.3").build();
        Library hikariCP = new Library.Builder().groupId("com{}zaxxer").artifactId("HikariCP").version("5.0.1").build();
        libraryManager.addMavenCentral();
        libraryManager.addJitPack();
        libraryManager.loadLibrary(mysql);
        libraryManager.loadLibrary(hCore);
        libraryManager.loadLibrary(hikariCP);

    }

    public static Location getCosmeticLocation() {
        World world = Bukkit.getWorld(Cosmetics.getInstance().getConfig().getString("cosmetic-preview.cosmetic-location.world"));
        double x = Cosmetics.getInstance().getConfig().getDouble("cosmetic-preview.cosmetic-location.x");
        double y = Cosmetics.getInstance().getConfig().getDouble("cosmetic-preview.cosmetic-location.y");
        double z = Cosmetics.getInstance().getConfig().getDouble("cosmetic-preview.cosmetic-location.z");
        float yaw = (float) Cosmetics.getInstance().getConfig().getDouble("cosmetic-preview.cosmetic-location.yaw");
        float pitch = (float) Cosmetics.getInstance().getConfig().getDouble("cosmetic-preview.cosmetic-location.pitch");

        Location location = new Location(world, x, y, z, yaw, pitch);
        location.setX(location.getBlockX() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);

        return location;
    }

    public static Location getPlayerLocation() {
        World world = Bukkit.getWorld(Cosmetics.getInstance().getConfig().getString("cosmetic-preview.player-location.world"));
        double x = Cosmetics.getInstance().getConfig().getDouble("cosmetic-preview.player-location.x");
        double y = Cosmetics.getInstance().getConfig().getDouble("cosmetic-preview.player-location.y");
        double z = Cosmetics.getInstance().getConfig().getDouble("cosmetic-preview.player-location.z");
        float yaw = (float) Cosmetics.getInstance().getConfig().getDouble("cosmetic-preview.player-location.yaw");
        float pitch = (float) Cosmetics.getInstance().getConfig().getDouble("cosmetic-preview.player-location.pitch");

        Location location = new Location(world, x, y, z, yaw, pitch);
        location.setX(location.getBlockX() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);

        return location;
    }

    public static void addEntityHideListener(){
        Cosmetics.getInstance().getProtocolManager().addPacketListener(new PacketAdapter(Cosmetics.getInstance(), PacketType.Play.Server.SPAWN_ENTITY) {
            @Override
            public void onPacketSending(PacketEvent event) {
                int entityID = event.getPacket().getIntegers().read(0);
                Player player = event.getPlayer();
                if (Cosmetics.getInstance().getEntityPlayerHashMap().containsKey(entityID)){
                    if (!player.getUniqueId().equals(Cosmetics.getInstance().getEntityPlayerHashMap().get(entityID).getUniqueId())){
                        event.setCancelled(true);
                    }
                }
            }
        });
    }
}