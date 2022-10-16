// 
// @author IamTheDefender
// 

package me.defender;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.hakan.core.HCore;
import me.defender.api.BwcAPI;
import me.defender.api.utils.UnzippingUtils;
import me.defender.listeners.onPlayerLeave;
import me.defender.listeners.onPurchase;
import me.defender.cosmetics.FinalKillEffects.FinalKillEffect;
import me.defender.cosmetics.FinalKillEffects.ItemMergeEvent;
import me.defender.cosmetics.IslandToppers.IslandToppers;
import org.bukkit.Bukkit;

import me.defender.listeners.OnJoin;
import me.defender.api.utils.util;
import me.defender.cosmetics.Glyphs.Glyphs;
import me.defender.cosmetics.KillMessage.KillMessages;
import me.defender.cosmetics.ProjectileEffects.ProjectileEffects;
import me.defender.cosmetics.ShopKeeperSkins.ShopKeeperSkin;
import me.defender.cosmetics.Sprays.OnRightClick;
import me.defender.cosmetics.VictoryDances.VictoryDances;
import me.defender.cosmetics.VictoryDances.VictoryDancesUtil;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;

public class ServerManager
{

    public static void registerEvents() {
        if(new BwcAPI().isProxy() == false){
            Bukkit.getServer().getPluginManager().registerEvents(new ShopKeeperSkin(), util.plugin());
            Bukkit.getServer().getPluginManager().registerEvents(new Glyphs(), util.plugin());
            Bukkit.getServer().getPluginManager().registerEvents(new KillMessages(), util.plugin());
            Bukkit.getServer().getPluginManager().registerEvents(new ProjectileEffects(), util.plugin());
            Bukkit.getServer().getPluginManager().registerEvents(new VictoryDances(), util.plugin());
            Bukkit.getServer().getPluginManager().registerEvents(new FinalKillEffect(), util.plugin());
            Bukkit.getServer().getPluginManager().registerEvents(new me.defender.cosmetics.BedBreakEffect.BedBreakEffect(), util.plugin());
            Bukkit.getServer().getPluginManager().registerEvents(new me.defender.cosmetics.WoodSkins.WoodSkins(), util.plugin());
            Bukkit.getServer().getPluginManager().registerEvents(new IslandToppers(), util.plugin());

        }
        Bukkit.getServer().getPluginManager().registerEvents(new onPlayerLeave(), util.plugin());
        Bukkit.getServer().getPluginManager().registerEvents(new onPurchase(), util.plugin());
        Bukkit.getServer().getPluginManager().registerEvents(new OnJoin(), util.plugin());
        Bukkit.getServer().getPluginManager().registerEvents(new me.defender.cosmetics.DeathCries.DeathCries(), util.plugin());
        Bukkit.getServer().getPluginManager().registerEvents(new OnRightClick(), util.plugin());
        Bukkit.getServer().getPluginManager().registerEvents(new VictoryDancesUtil(), util.plugin());
        Bukkit.getServer().getPluginManager().registerEvents(new ItemMergeEvent(), util.plugin());
    }


    public static void createSpraysFolder(){
        File folder = new File(System.getProperty("user.dir") + "/plugins/Bedwars1058-Cosmetics/Sprays");
        if (folder.exists()) {
            return;
        }
        folder.mkdirs();
    }

    public static void createIslandTopperFolder(){
        File folder = new File(System.getProperty("user.dir") + "/plugins/Bedwars1058-Cosmetics/IslandToppers");
        if (folder.exists()) {
            return;
        }
        folder.mkdirs();
    }

    public static void downloadHCore() {
        String folder = System.getProperty("user.dir")+ "/plugins/HCore.jar";
        if(Bukkit.getPluginManager().getPlugin("hCore") != null){
            return;
        }
        try {
            URL url = new URL("https://github.com/hakan-krgn/hCore/releases/download/0.6.2/hCore-0.6.2.jar");
            Main.downloadFile(url, folder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(new File(folder)));
        } catch (InvalidPluginException e) {
            throw new RuntimeException(e);
        } catch (InvalidDescriptionException e) {
            throw new RuntimeException(e);
        }

    }
    public static void downloadGlyphs() {
        final File folder = new File(System.getProperty("user.dir") + "/plugins/Bedwars1058-Cosmetics/Glyphs");
        if (folder.exists()) {
            return;
        }
        folder.mkdirs();
        final String temp = System.getProperty("user.dir") + "/plugins/Bedwars1058-Cosmetics/Glyphs/temp.zip";
        final String toDir = System.getProperty("user.dir") + "/plugins/Bedwars1058-Cosmetics/Glyphs";
        if (new File(temp).exists()) {
            new File(temp).delete();
            return;
        }
        try {
            Main.downloadFile(new URL("https://dl.dropboxusercontent.com/s/ione3f01k1la6e8/Glyphs.zip"), temp);
        }
        catch (final MalformedURLException e) {
            e.printStackTrace();
        }
        catch (final IOException e2) {
            e2.printStackTrace();
        }
        final UnzippingUtils unzip = new UnzippingUtils();
        try {
            unzip.unzip(temp, toDir);
        }
        catch (final IOException e3) {
            e3.printStackTrace();
        }
        new File(temp).delete();
    }


    public static void updateConfigs(){

        // BedBreakEffect
        for(String name : util.plugin().bbedata.getConfig().getConfigurationSection("BedBreakEffects").getKeys(false)){
                List<String> lore = new ArrayList<String>();
                lore.add("");
                lore.add("&7Select {name} as your Bed Break Effect");
                lore.add("");
                lore.add("&7Cost: &6{cost}");
                lore.add("");
                lore.add("{status}");
            util.saveIfNotExistsLang("BedBreakEffects." + name + ".lore", lore);
        }

        // DeathCries
        for(String name : util.plugin().dcdata.getConfig().getConfigurationSection("DeathCries").getKeys(false)){
            List<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add("&7Select {name} as your Death Cry");
            lore.add("");
            lore.add("&7Cost: &6{cost}");
            lore.add("");
            lore.add("{status}");
            util.saveIfNotExistsLang("DeathCries." + name + ".lore", lore);
        }

        // FinalKillEffects
        for(String name : util.plugin().finalkilldata.getConfig().getConfigurationSection("FinalKillEffects").getKeys(false)){
            List<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add("&7Select {name} as your Final Kill Effect");
            lore.add("");
            lore.add("&7Cost: &6{cost}");
            lore.add("");
            lore.add("{status}");
            util.saveIfNotExistsLang("FinalKillEffects." + name + ".lore", lore);
        }

        // Glyphs
        for(String name : util.plugin().glyconf.getConfig().getConfigurationSection("Glyphs").getKeys(false)){
            List<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add("&7Select {name} as your Glyph");
            lore.add("");
            lore.add("&7Cost: &6{cost}");
            lore.add("");
            lore.add("{status}");
            util.saveIfNotExistsLang("Glyphs." + name + ".lore", lore);
        }

        // KillMessages
        for(String name : util.plugin().killmessagecfg.getConfig().getConfigurationSection("KillMessages").getKeys(false)){

                List<String> lore = new ArrayList<String>();
                lore.add("");
                lore.add("&7Select {name} as your Kill Message package");
                lore.add("");
                lore.add("&7Cost: &6{cost}");
                lore.add("");
                lore.add("{status}");
                util.saveIfNotExistsLang("KillMessages." + name + ".lore", lore);

        }

        // ProjectileTrails
        for(String name : util.plugin().pedata.getConfig().getConfigurationSection("ProjectileTrails").getKeys(false)){
            List<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add("&7Select {name} as your Projectile Trail");
            lore.add("");
            lore.add("&7Cost: &6{cost}");
            lore.add("");
            lore.add("{status}");
            util.saveIfNotExistsLang("ProjectileTrails." + name + ".lore", lore);
        }

        // ShopKeeperSkins
        for(String name : util.plugin().shopkeeperdata.getConfig().getConfigurationSection("ShopKeeperSkins").getKeys(false)){
            List<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add("&7Select {name} as your Shopkeeper Skin");
            lore.add("");
            lore.add("&7Cost: &6{cost}");
            lore.add("");
            lore.add("{status}");
            util.saveIfNotExistsLang("ShopKeeperSkins." + name + ".lore", lore);
        }

        // Sprays
        for(String name : util.plugin().spraysdata.getConfig().getConfigurationSection("Sprays").getKeys(false)){
            List<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add("&7Select {name} as your Spray");
            lore.add("");
            lore.add("&7Cost: &6{cost}");
            lore.add("");
            lore.add("{status}");
            util.saveIfNotExistsLang("Sprays." + name + ".lore", lore);
        }

        // VictoryDances
        for(String name : util.plugin().vdconf.getConfig().getConfigurationSection("VictoryDances").getKeys(false)){
            List<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add("&7Select {name} as your Victory Dance");
            lore.add("");
            lore.add("&7Cost: &6{cost}");
            lore.add("");
            lore.add("{status}");
            util.saveIfNotExistsLang("VictoryDances." + name + ".lore", lore);
        }

        // WoodSkins
        for(String name : util.plugin().woodskindata.getConfig().getConfigurationSection("WoodSkins").getKeys(false)){
            List<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add("&7Select {name} as your Wood Skin");
            lore.add("");
            lore.add("&7Cost: &6{cost}");
            lore.add("");
            lore.add("{status}");
            util.saveIfNotExistsLang("WoodSkins." + name + ".lore", lore);
        }
        // Island Topper
        for(String name : util.plugin().islandToppersData.getConfig().getConfigurationSection("IslandTopper").getKeys(false)) {
            List<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add("&7Select {name} as your Island Topper");
            lore.add("");
            lore.add("&7Cost: &6{cost}");
            lore.add("");
            lore.add("{status}");
            util.saveIfNotExistsLang("IslandTopper." + name + ".lore", lore);
        }

        // Main cfg
        if(util.plugin().getConfig().getBoolean("Use-Name") != true){
            util.plugin().getConfig().set("Use-Name", false);
        }

        // saving
        util.saveIfNotExistsLang("cosmetics.selected", "&aSELECTED!");
        util.saveIfNotExistsLang("cosmetics.click-to-select", "&eClick to select.");
        util.saveIfNotExistsLang("cosmetics.click-to-purchase", "&eClick to purchase.");
        util.saveIfNotExistsLang("cosmetics.no-coins", "&cYou don't have enough coins!");
        util.saveIfNotExistsLang("cosmetics.spray-msg", "&cYou must wait 3 seconds between spray uses!");

        util.plugin().getConfig().options().copyDefaults(true);
        util.plugin().saveConfig();
    }

    public static void createHeightValue() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(util.plugin(), new Runnable(){
            @Override
            public void run(){
                if (!new BwcAPI().isProxy()) {
                    BedWars api = com.andrei1058.bedwars.BedWars.getAPI();
                    for (IArena arena : api.getArenaUtil().getArenas()) {
                        if (arena.getConfig().getInt("IslandTopper.height") == 0) {
                            arena.getConfig().set("IslandTopper.height", 10);
                            arena.getConfig().save();

                        }

                    }
                }
            }
        });
    }

}
