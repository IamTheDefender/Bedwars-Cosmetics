package me.defender.cache;

import me.defender.api.BwcAPI;
import me.defender.api.enums.Cosmetics;
import me.defender.api.utils.util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static me.defender.api.utils.util.plugin;
import static me.defender.api.utils.util.savePlayerData;

public class Cache {

    // UPDATE PLAYERDATA.YML FROM MYSQL
    public static void cacheFromMySQL(Player player){

        new BukkitRunnable(){
            @Override
            public void run() {
                Player p = Bukkit.getOfflinePlayer(player.getUniqueId()).getPlayer();

                BwcAPI api = new BwcAPI();
                String spray = api.getSelectedCosmetic(p, Cosmetics.Sprays);
                String killmsg = api.getSelectedCosmetic(p, Cosmetics.KillMessage);
                String shopkeeper = api.getSelectedCosmetic(p, Cosmetics.ShopKeeperSkin);
                String woodskin = api.getSelectedCosmetic(p, Cosmetics.WoodSkins);
                String victory = api.getSelectedCosmetic(p, Cosmetics.VictoryDances);
                String deathcries = api.getSelectedCosmetic(p, Cosmetics.DeathCries);
                String glyphs  = api.getSelectedCosmetic(p, Cosmetics.Glyphs);
                String bedbreak = api.getSelectedCosmetic(p, Cosmetics.BedBreakEffects);
                String projectile = api.getSelectedCosmetic(p, Cosmetics.ProjectileTrails);
                String island = api.getSelectedCosmetic(p, Cosmetics.IslandTopper);

                if(!util.plugin().getDb().getSprays(p).equals(spray)){
                    savePlayerData(p, "Sprays", plugin().getDb().getSprays(p));
                }
                if(!plugin().getDb().getKillMessages(p).equals(killmsg)){
                    savePlayerData(p, "KillMessage", plugin().getDb().getKillMessages(p));
                }
                if(!plugin().getDb().getShopKeeperSkins(p).equals(shopkeeper)){
                    savePlayerData(p, "ShopKeeperSkin", plugin().getDb().getShopKeeperSkins(p));
                }
                if(!plugin().getDb().getWoodskins(p).equals(woodskin)){
                    savePlayerData(p, "WoodSkins", plugin().getDb().getWoodskins(p));
                }
                if(!plugin().getDb().getVictorydances(p).equals(victory)){
                    savePlayerData(p, "VictoryDances", plugin().getDb().getVictorydances(p));
                }
                if(!plugin().getDb().getDeathcries(p).equals(deathcries)){
                    savePlayerData(p, "DeathCries", plugin().getDb().getDeathcries(p));
                }
                if(!plugin().getDb().getGlyphs(p).equals(glyphs)){
                    savePlayerData(p, "Glyphs", plugin().getDb().getGlyphs(p));
                }
                if(!plugin().getDb().getBedBreakEffects(p).equals(bedbreak)){
                    savePlayerData(p, "BedBreakEffects", plugin().getDb().getBedBreakEffects(p));
                }
                if(!plugin().getDb().getProjectileTrails(p).equals(projectile)){
                    savePlayerData(p, "ProjectileTrails", plugin().getDb().getProjectileTrails(p));
                }
                if(!plugin().getDb().getIslandTopper(p).equals(island)){
                    savePlayerData(p, Cosmetics.IslandTopper.toString(), plugin().getDb().getIslandTopper(p));
                }
            }
        }.runTask(plugin());
    }


    // UPDATE THE MYSQL DB
    public static void updateMySQL(Player p){
        if(!util.plugin().getConfig().getBoolean("MySQL.Enabled")){
            return;
        }
        new BukkitRunnable(){
            @Override
            public void run() {
                BwcAPI api = new BwcAPI();
                String spray = api.getSelectedCosmetic(p, Cosmetics.Sprays);
                String killmsg = api.getSelectedCosmetic(p, Cosmetics.KillMessage);
                String shopkeeper = api.getSelectedCosmetic(p, Cosmetics.ShopKeeperSkin);
                String woodskin = api.getSelectedCosmetic(p, Cosmetics.WoodSkins);
                String victory = api.getSelectedCosmetic(p, Cosmetics.VictoryDances);
                String deathcries = api.getSelectedCosmetic(p, Cosmetics.DeathCries);
                String glyphs  = api.getSelectedCosmetic(p, Cosmetics.Glyphs);
                String bedbreak = api.getSelectedCosmetic(p, Cosmetics.BedBreakEffects);
                String projectile = api.getSelectedCosmetic(p, Cosmetics.ProjectileTrails);
                String island = api.getSelectedCosmetic(p, Cosmetics.IslandTopper);

                if(!spray.equals(plugin().getDb().getSprays(p))){
                    plugin().getDb().updateSprays(spray, p);
                }
                if(!killmsg.equals(plugin().getDb().getKillMessages(p))){
                    plugin().getDb().updateKillMessages(killmsg, p);
                }
                if(!shopkeeper.equals(plugin().getDb().getKillMessages(p))){
                    plugin().getDb().updateShopKeeperSkins(shopkeeper, p);
                }
                if(!woodskin.equals(plugin().getDb().getWoodskins(p))){
                    plugin().getDb().updateWoodskins(woodskin, p);
                }
                if(!victory.equals(plugin().getDb().getVictorydances(p))){
                    plugin().getDb().updateVictoryDances(victory, p);
                }
                if(!deathcries.equals(plugin().getDb().getDeathcries(p))){
                    plugin().getDb().updateDeathcries(deathcries, p);
                }
                if(!glyphs.equals(plugin().getDb().getGlyphs(p))){
                    plugin().getDb().updateGlyphs(glyphs, p);
                }
                if(!bedbreak.equals(plugin().getDb().getBedBreakEffects(p))){
                    plugin().getDb().updateBedbreakeffects(bedbreak, p);
                }
                if(!projectile.equals(plugin().getDb().getProjectileTrails(p))){
                    plugin().getDb().updateProjectileTrails(projectile, p);
                }
                if(!island.equals(plugin().getDb().getIslandTopper(p))){
                    plugin().getDb().updateIslandTopper(island, p);
                }
            }
        }.runTask(plugin());
    }
}
