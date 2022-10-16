// 
// @author IamTheDefender
// 

package me.defender.api.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.defender.api.BwcAPI;
import me.defender.api.enums.Cosmetics;
import me.defender.menus.*;
import org.bukkit.entity.Player;

import me.defender.api.utils.util;

public class MainMenuUtils
{


    
    public static void saveLores() {
        List<String> sprays = Arrays.asList("§7Select a spray to show off all", "§7over the place! Sprays slot can", "§7be found on every spawn islands", "§7and some center islands.", "", "§7Unlocked:§a ", "{ownedSpray}","§7Currently Selected:", "{spray}", "", "§eClick to view.");
        util.saveIfNotExistsLang("cosmetics.main-menu.Sprays.lore", sprays);
        util.saveIfNotExistsLang("cosmetics.main-menu.Sprays.name", "&aSprays");

        List<String> pts = Arrays.asList("§7Change your projectile particle", "§7trail effects.", "", "§7Unlocked:§a ", "{ownedpt}","§7Currently Selected:", "{projectile}", "", "§eClick to view.");
        util.saveIfNotExistsLang("cosmetics.main-menu.Projectile-Trails.lore", pts);
        util.saveIfNotExistsLang("cosmetics.main-menu.Projectile-Trails.name", "&aProjectile Trails");

        List<String> finalke = Arrays.asList("§7A selection of various effects", "§7to chosse from that will trigger", "§7whenever you final kill an", "§7enemy!", "", "§7Unlocked:§a ", "{ownedfinalkill}","§7Currently Selected:", "§a" + "{finalkill}", "", "§eClick to view.");
        util.saveIfNotExistsLang("cosmetics.main-menu.FinalKill-Effects.lore", finalke);
        util.saveIfNotExistsLang("cosmetics.main-menu.FinalKill-Effects.name", "&aFinal Kill Effects");

        List<String> km = Arrays.asList("§7Select a Kill Message package to", "§7replace chat messages when you", "§7kill players.", "", "§7Unlocked:§a ","{ownedkm}","§7Currently Selected:", "{killmsg}", "", "§eClick to view.");
        util.saveIfNotExistsLang("cosmetics.main-menu.Kill-Messages.lore", km);
        util.saveIfNotExistsLang("cosmetics.main-menu.Kill-Messages.name", "&aKill Messages");

        List<String> gly = Arrays.asList("§7Select a Glyph image which will", "§7appear when picking up diamonds and", "§7emeralds!", "", "§7Unlocked:§a ","{ownedgly}","§7Currently Selected:", "{glyphs}", "", "§eClick to view.");
        util.saveIfNotExistsLang("cosmetics.main-menu.Glyphs.lore", gly);
        util.saveIfNotExistsLang("cosmetics.main-menu.Glyphs.name", "&aGlyphs");

        List<String> bbe = Arrays.asList("§7Select from various Bed Destroy", "§7effects, which will occur when you", "§7break a bed!", "", "§7Unlocked:§a ","{ownedbbe}","§7Currently Selected:", "{bedbreak}", "", "§eClick to view.");
        util.saveIfNotExistsLang("cosmetics.main-menu.Bed-Destroys.lore", bbe);
        util.saveIfNotExistsLang("cosmetics.main-menu.Bed-Destroys.name", "&aBed Destroys");

        List<String> ws = Arrays.asList("§7Change the skin of wood", "§7in-game.", "", "§7Unlocked:§a ","{ownedws}","§7Currently Selected:", "{woodskin}", "", "§eClick to view.");
        util.saveIfNotExistsLang("cosmetics.main-menu.WoodSkins.lore", ws);
        util.saveIfNotExistsLang("cosmetics.main-menu.WoodSkins.name", "&aWood Skins");

        List<String> vd = Arrays.asList("§7Celebrate by gloating and", "§7showing off to other players", "§7whenever you win!", "", "§7Unlocked:§a ","{ownedvd}","§7Currently Selected:", "{victory}", "", "§eClick to view.");
        util.saveIfNotExistsLang("cosmetics.main-menu.Victory-Dances.lore", vd);
        util.saveIfNotExistsLang("cosmetics.main-menu.Victory-Dances.name", "&aVictory Dances");

        List<String> islandtoppers = Arrays.asList("§7Select an Island Topper to", "§7decorate your island with! In", "§7Doubles and Teams Mode a random", "§7player's choice from each team is choosen.", "", "§7Currently Selected:", "§a{islandtopper}", "", "§eClick to select.");
        util.saveIfNotExistsLang("cosmetics.main-menu.Island-Toppers.lore", islandtoppers);
        util.saveIfNotExistsLang("cosmetics.main-menu.Island-Toppers.name", "&aIsland Toppers");

        List<String> shopkeepers = Arrays.asList("§7Select from various ShopKeeper", "§7skin, which will replace how the", "§7ShopKeeper look in-game! In", "§7Doubles and Teams Mode a random", "§7player's choice from each team", "§7is choosen.", "", "§7Unlocked:§a ","{ownedshopkeeper}","§7Currently Selected:", "{shopkeeper}", "", "§eClick to view.");
        util.saveIfNotExistsLang("cosmetics.main-menu.ShopKeeperSkins.lore", shopkeepers);
        util.saveIfNotExistsLang("cosmetics.main-menu.ShopKeeperSkins.name", "&aShopKeeperSkins");

        List<String> dc = Arrays.asList("§7Let others know just how salty", "§7your tears are every time you", "§7die with these death cries!", "","§7Unlocked:§a ", "{owneddc}","§7Currently Selected:", "{deathcries}", "", "§eClick to view.");
        util.saveIfNotExistsLang("cosmetics.main-menu.Death-Cries.lore", dc);
        util.saveIfNotExistsLang("cosmetics.main-menu.Death-Cries.name", "&aDeath Cries");

    	}


    public static List<String> formatLore(List<String> lores, Player p){
        BwcAPI api = new BwcAPI();
        String spray = "§a" + api.getSelectedCosmetic(p, Cosmetics.Sprays).replace("-", " ");
        String killmsg = "§a" + api.getSelectedCosmetic(p, Cosmetics.KillMessage).replace("-", " ");
        String shopkeeper = "§a" + api.getSelectedCosmetic(p, Cosmetics.ShopKeeperSkin).replace("-", " ");
        String woodskin = "§a" + api.getSelectedCosmetic(p, Cosmetics.WoodSkins).replace("-", " ");
        String victory = "§a" + api.getSelectedCosmetic(p, Cosmetics.VictoryDances).replace("-", " ");
        String deathcries = "§a" + api.getSelectedCosmetic(p, Cosmetics.DeathCries).replace("-", " ");
        String glyphs = "§a" + api.getSelectedCosmetic(p, Cosmetics.Glyphs).replace("-", " ");
        String bedbreak = "§a" + api.getSelectedCosmetic(p, Cosmetics.BedBreakEffects).replace("-", " ");
        String projectile = "§a" + api.getSelectedCosmetic(p, Cosmetics.ProjectileTrails).replace("-", " ");
        String finalkill = "§a" + api.getSelectedCosmetic(p, Cosmetics.FinalKillEffects).replace("-", " ");
        String islandtopper = "§a" + api.getSelectedCosmetic(p, Cosmetics.IslandTopper).replace("-", " ");

        String ownedSpray = "§a" + util.getOwned(p, "Spray") + "/" + util.spray.size() + " §8(" + Math.round(Float.parseFloat(util.getOwned(p, "Spray")) / util.spray.size() * 100.0f) + "%)";
        String ownedpt = "§a" + util.getOwned(p, "PT") + "/" + util.pt.size() + " §8(" + Math.round(Float.parseFloat(util.getOwned(p, "PT")) / util.pt.size() * 100.0f) + "%)";
        String ownedfinalkill = "§a" +util.getOwned(p, "FinalKill") + "/" + util.finalkill.size() + " §8(" + Math.round(Float.parseFloat(util.getOwned(p, "FinalKill")) / util.finalkill.size() * 100.0f) + "%)";
        String ownedkm = "§a" +util.getOwned(p, "KM") + "/" + util.km.size() + " §8(" + Math.round(Float.parseFloat(util.getOwned(p, "KM")) / util.km.size() * 100.0f) + "%)";
        String ownedgly = "§a" + util.getOwned(p, "GLY") + "/" + util.gly.size() + " §8(" + Math.round(Float.parseFloat(util.getOwned(p, "GLY")) / util.gly.size() * 100.0f) + "%)";
        String ownedbbe = "§a" + util.getOwned(p, "BBE") + "/" + util.bbe.size() + " §8(" + Math.round(Float.parseFloat(util.getOwned(p, "BBE")) / util.bbe.size() * 100.0f) + "%)";
        String ownedws = "§a" + util.getOwned(p, "WS") + "/" + util.ws.size() + " §8(" + Math.round(Float.parseFloat(util.getOwned(p, "WS")) / util.ws.size() * 100.0f) + "%)";
        String ownedvd = "§a" + util.getOwned(p, "VD") + "/" + util.vd.size() + " §8(" + Math.round(Float.parseFloat(util.getOwned(p, "VD")) / util.vd.size() * 100.0f) + "%)";
        String ownedshopkeeper = "§a"+ util.getOwned(p, "ShopKeeper") + "/" + util.shopkeeper.size() + " §8(" + Math.round(Float.parseFloat(util.getOwned(p, "ShopKeeper")) / util.shopkeeper.size() * 100.0f) + "%)";
        String owneddc = "§a" + util.getOwned(p, "DC") + "/" + util.dc.size() + " §8(" + Math.round(Float.parseFloat(util.getOwned(p, "DC")) / util.dc.size() * 100.0f) + "%)";
        String ownedis = "§a" + util.getOwned(p, "Island") + "/" + util.island.size() + " §8(" + Math.round(Float.parseFloat(util.getOwned(p, "Island")) / util.island.size() * 100.0f) + "%)";

        List<String> lore = new ArrayList<>();
        for(int i = 0; i < lores.size(); i++){
           String msg = lores.get(i);
           if(msg.contains("{islandtopper}")){
               lores.set(i, islandtopper);
           }
           if(msg.contains("{spray}")){
               lores.set(i, spray);
           }
           if(msg.contains("{ownedSpray}")){
               lores.set(i, ownedSpray);
           }
           if(msg.contains("{killmsg}")){
               lores.set(i, killmsg);
           }
           if(msg.contains("{ownedkm}")){
               lores.set(i, ownedkm);
           }
           if(msg.contains("{shopkeeper}")){
               lores.set(i, shopkeeper);
           }
           if(msg.contains("{ownedshopkeeper}")){
               lores.set(i, ownedshopkeeper);
           }
           if(msg.contains("{woodskin}")){
               lores.set(i, woodskin);
           }
           if(msg.contains("{ownedws}")){
               lores.set(i, ownedws);
           }
           if(msg.contains("{victory}")){
               lores.set(i, victory);
           }
           if(msg.contains("{ownedvd}")){
               lores.set(i, ownedvd);
           }
           if(msg.contains("{deathcries}")){
               lores.set(i, deathcries);
           }
           if(msg.contains("{owneddc}")){
               lores.set(i, owneddc);
           }
           if(msg.contains("{glyphs}")){
               lores.set(i, glyphs);
           }
           if(msg.contains("{ownedgly}")){
               lores.set(i, ownedgly);
           }
           if(msg.contains("{bedbreak}")){
               lores.set(i, bedbreak);
           }
           if(msg.contains("{ownedbbe}")){
               lores.set(i, ownedbbe);
           }
           if(msg.contains("{projectile}")){
               lores.set(i, projectile);
           }
           if(msg.contains("{ownedpt}")){
               lores.set(i, ownedpt);
           }
           if(msg.contains("{finalkill}")){
               lores.set(i, finalkill);
           }
           if(msg.contains("{ownedfinalkill}")){
               lores.set(i, ownedfinalkill);
           }
        }
        for(String lored : lores){
            lore.add(lored);
        }
        return lore;
    }

    public static void openMenus(Player p, String name){
        switch (name){
            case "Sprays":
                new spraysGUI().open(p);
                break;
            case "Projectile-Trails":
                new projectileTrailsGUI().open(p);
                break;
            case "FinalKill-Effects":
                new finalKillEffects().open(p);
                break;
            case "Kill-Messages":
                new killMessageGUI().open(p);
                break;
            case "Glyphs":
                new glyphsGUI().open(p);
                break;
            case "Bed-Destroys":
                new bedBreakEffectsGUI().open(p);
                break;
            case "WoodSkins":
               new woodSkinsGUI().open(p);
                break;
            case "Victory-Dances":
               new victoryDanceGUI().open(p);
                break;
            case "Island-Toppers":
                new islandTopperGUI().open(p);
                break;
            case "ShopKeeperSkins":
                new shopKeeperGUI().open(p);
                break;
            case "Death-Cries":
               new deathCries().open(p);
                break;

        }
    }
}
