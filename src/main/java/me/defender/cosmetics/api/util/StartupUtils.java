

package me.defender.cosmetics.api.util;


import com.hakan.core.HCore;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.*;
import me.defender.cosmetics.api.category.finalkilleffects.items.*;
import me.defender.cosmetics.api.category.finalkilleffects.items.FireworkEffect;
import me.defender.cosmetics.api.category.projectiletrails.items.ProjectileTrailItems;
import me.defender.cosmetics.api.category.sprays.Spray;
import me.defender.cosmetics.api.category.shopkeeperskins.ShopKeeperSkin;
import me.defender.cosmetics.api.category.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.category.bedbreakeffects.items.*;
import me.defender.cosmetics.api.category.bedbreakeffects.items.FireworksBedDestroy;
import me.defender.cosmetics.api.category.bedbreakeffects.items.NoneBedDestroy;
import me.defender.cosmetics.api.category.bedbreakeffects.items.SquidMissileBedDestroy;
import me.defender.cosmetics.api.category.bedbreakeffects.items.TornadoBedDestroy;
import me.defender.cosmetics.api.category.deathcries.DeathCry;
import me.defender.cosmetics.api.category.projectiletrails.ProjectileTrail;
import me.defender.cosmetics.api.category.killmessage.KillMessage;
import me.defender.cosmetics.api.category.islandtoppers.IslandTopper;
import me.defender.cosmetics.api.category.glyphs.Glyph;
import me.defender.cosmetics.api.category.finalkilleffects.FinalKillEffect;
import me.defender.cosmetics.api.category.islandtoppers.IslandTopperHandler;
import me.defender.cosmetics.api.category.killmessage.KillMessageHandler;
import me.defender.cosmetics.api.category.projectiletrails.ProjectileHandler;
import me.defender.cosmetics.api.category.shopkeeperskins.ShopKeeperHandler;
import me.defender.cosmetics.api.category.victorydances.items.*;
import me.defender.cosmetics.api.category.woodskins.WoodSkin;
import me.defender.cosmetics.api.category.victorydances.VictoryDance;
import me.defender.cosmetics.api.category.victorydances.VictoryDanceHandler;
import me.defender.cosmetics.api.category.woodskins.WoodSkinHandler;
import me.defender.cosmetics.api.category.woodskins.items.*;
import me.defender.cosmetics.api.category.woodskins.items.log.*;
import me.defender.cosmetics.api.category.deathcries.items.DeathCryItems;
import me.defender.cosmetics.api.category.finalkilleffects.FinalKillEffectHandler;
import me.defender.cosmetics.api.category.glyphs.items.GlyphItems;
import me.defender.cosmetics.api.category.islandtoppers.items.IslandTopperItems;
import me.defender.cosmetics.api.category.killmessage.items.KillMessageItems;
import me.defender.cosmetics.api.category.shopkeeperskins.items.ShopKeeperItems;
import me.defender.cosmetics.api.category.sprays.SpraysHandler;
import me.defender.cosmetics.api.category.glyphs.GlyphHandler;
import me.defender.cosmetics.api.category.deathcries.DeathCryHandler;
import me.defender.cosmetics.api.category.bedbreakeffects.BedDestroyHandler;
import me.defender.cosmetics.api.category.sprays.items.SprayItems;
import me.defender.cosmetics.api.configuration.ConfigUtils;
import me.defender.cosmetics.listener.*;
import me.defender.cosmetics.support.placeholders.Placeholders;
import org.bukkit.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
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

    /**
     This method is used to register all events for the plugin.
     It includes registering listeners for the ShopKeeperSkin, GlyphHandler, KillMessageHandler, ProjectileHandler,
     VictoryDanceHandler, FinalKillEffectHandler, BedDestroyHandler, WoodSkins, IslandTopperHandler, PlayerLeaveListener,
     CosmeticPurchaseListener, PlayerJoinListener, DeathCryHandler and SpraysHandler classes.
     @author defender
     */
    public static void registerEvents() {
        if(!new BwcAPI().isProxy()){
            HCore.registerListeners(new ShopKeeperHandler());
            HCore.registerListeners(new GlyphHandler());
            HCore.registerListeners(new KillMessageHandler());
            HCore.registerListeners(new ProjectileHandler(Utility.plugin()));
            HCore.registerListeners(new VictoryDanceHandler());
            HCore.registerListeners(new FinalKillEffectHandler());
            HCore.registerListeners(new BedDestroyHandler());
            HCore.registerListeners(new WoodSkinHandler());
            HCore.registerListeners(new IslandTopperHandler());

        }
        HCore.registerListeners(new PlayerLeaveListener());
        HCore.registerListeners(new CosmeticPurchaseListener());
        HCore.registerListeners(new PlayerJoinListener());
        HCore.registerListeners(new PlayerInteractListener());
        HCore.registerListeners(new DeathCryHandler());
        HCore.registerListeners(new SpraysHandler());
    }


    /**
     This method creates the necessary folders for the plugin to function properly.
     It creates a folder called "Sprays" and "IslandToppers" in the plugin directory.
     If the folders do not exist, they will be created.
     */
    public static void createFolders() {
        File spraysFolder = new File(Utility.plugin().getDataFolder().getPath() + "/Sprays");
        if (!spraysFolder.exists()) {
            spraysFolder.mkdirs();
        }
        File islandToppersFolder = new File(Utility.plugin().getDataFolder().getPath() + "/IslandToppers");
        if (!islandToppersFolder.exists()) {
            islandToppersFolder.mkdirs();
        }
        File cubeFile = new File(Utility.plugin().getDataFolder().getPath() + "/IslandToppers/cube.schematic");
        // Save if not found
        if(cubeFile.exists()) return;
        try {
            Cosmetics.downloadFile(new URL("https://dl.dropboxusercontent.com/s/x9rmk36qa1uwrr3/idkcube.schematic"), cubeFile.getPath());
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
        File folder = new File(Utility.plugin().getDataFolder().getPath() + "/Glyphs");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        final String temp = Utility.plugin().getDataFolder().getPath() + "/Glyphs/temp.zip";
        final File tempFile = new File(temp);
        if (tempFile.exists()) {
            tempFile.delete();
        }
        String[] filesInFolder = folder.list();
        if(filesInFolder != null && filesInFolder.length == 0){
            return;
        }
        try {
            Cosmetics.downloadFile(new URL("https://dl.dropboxusercontent.com/s/ione3f01k1la6e8/Glyphs.zip"), temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final UnzippingUtils unzip = new UnzippingUtils();
        try {
            unzip.unzip(temp, folder.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempFile.delete();
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
        if(!isPluginEnabled("BedWars1058") && !new BwcAPI().isProxy()){
            log.severe("Cosmetics addon requires BedWars1058 or BedWarsProxy to work!");
            return false;
        }
        if(!isPluginEnabled("Vault")){
            log.severe("Cosmetics addon requires Vault to work properly!");
            return false;
        }
        if(isPluginEnabled("PlaceholderAPI")){
            log.info("Found PlaceholderAPI, loading placeholders!");
            new Placeholders(Utility.plugin()).register();
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
        if(isPluginEnabled("WorldEdit")) {
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
}