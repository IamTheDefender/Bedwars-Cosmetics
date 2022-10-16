// 
// @author IamTheDefender
// 

package me.defender.listeners;

import me.defender.api.BwcAPI;
import me.defender.api.enums.Cosmetics;
import me.defender.cache.Cache;
import org.bukkit.event.EventHandler;
import java.sql.SQLException;
import org.bukkit.entity.Player;
import me.defender.database.mysql.model.PlayerData;
import me.defender.cosmetics.KillMessage.KillMessagesUtil;
import me.defender.api.utils.util;
import org.bukkit.event.player.PlayerJoinEvent;
import me.defender.Main;
import org.bukkit.event.Listener;

public class OnJoin implements Listener
{
    private final Main plugin;
    
    public OnJoin() {
        this.plugin = Main.getPlugin(Main.class);
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) throws SQLException {
        final Player p = e.getPlayer();
        util.updateOwned(e.getPlayer());


            final String shop = util.getUUID(e.getPlayer()) + ".ShopKeeperSkin";
            final String killM = util.getUUID(e.getPlayer()) + ".KillMessage";
            final String sprays = util.getUUID(e.getPlayer()) + ".Sprays";
            final String woodskin = util.getUUID(e.getPlayer()) + ".WoodSkins";
            final String glyphs = util.getUUID(e.getPlayer()) + ".Glyphs";
            final String vd = util.getUUID(e.getPlayer()) + ".VictoryDances";
            final String dc = util.getUUID(e.getPlayer()) + ".DeathCries";
            final String bbe = util.getUUID(e.getPlayer()) + ".BedBreakEffects";
            final String pt = util.getUUID(e.getPlayer()) + ".ProjectileTrails";
            final String finalkill = util.getUUID(e.getPlayer()) + ".FinalKillEffects";
            if (this.plugin.playerData.getConfig().getString(shop) == null) {
                this.plugin.playerData.getConfig().set(shop, "None");
                this.plugin.playerData.getConfig().options().copyDefaults(true);
                this.plugin.playerData.savecfg();
            }
            if (util.plugin().playerData.getConfig().getString(killM) == null) {
                this.plugin.playerData.getConfig().set(killM, "Default");
                this.plugin.playerData.getConfig().options().copyDefaults(true);
                this.plugin.playerData.savecfg();
            }
            if (util.plugin().playerData.getConfig().getString(sprays) == null) {
                this.plugin.playerData.getConfig().set(sprays, "Server-Logo");
                this.plugin.playerData.getConfig().options().copyDefaults(true);
                this.plugin.playerData.savecfg();
            }
            if (util.plugin().playerData.getConfig().getString(woodskin) == null) {
                this.plugin.playerData.getConfig().set(woodskin, "OakPlank");
                this.plugin.playerData.getConfig().options().copyDefaults(true);
                this.plugin.playerData.savecfg();
            }
            if (util.plugin().playerData.getConfig().getString(vd) == null) {
                this.plugin.playerData.getConfig().set(vd, "None");
                this.plugin.playerData.getConfig().options().copyDefaults(true);
                this.plugin.playerData.savecfg();
            }
            if (util.plugin().playerData.getConfig().getString(glyphs) == null) {
                this.plugin.playerData.getConfig().set(glyphs, "None");
                this.plugin.playerData.getConfig().options().copyDefaults(true);
                this.plugin.playerData.savecfg();
            }
            if (util.plugin().playerData.getConfig().getString(dc) == null) {
                this.plugin.playerData.getConfig().set(dc, "None");
                this.plugin.playerData.getConfig().options().copyDefaults(true);
                this.plugin.playerData.savecfg();
            }
            if (util.plugin().playerData.getConfig().getString(bbe) == null) {
                this.plugin.playerData.getConfig().set(bbe, "None");
                this.plugin.playerData.getConfig().options().copyDefaults(true);
                this.plugin.playerData.savecfg();
            }
            if (util.plugin().playerData.getConfig().getString(pt) == null) {
                this.plugin.playerData.getConfig().set(pt, "None");
                this.plugin.playerData.getConfig().options().copyDefaults(true);
                this.plugin.playerData.savecfg();
            }

            if (util.plugin().playerData.getConfig().getString(finalkill) == null) {
                this.plugin.playerData.getConfig().set(finalkill, "None");
                this.plugin.playerData.getConfig().options().copyDefaults(true);
                this.plugin.playerData.savecfg();

        }
        BwcAPI api = new BwcAPI();
            if(api.getSelectedCosmetic(e.getPlayer(), Cosmetics.IslandTopper) == null){
                util.savePlayerData(e.getPlayer(), Cosmetics.IslandTopper.toString(), "None");
            }
        if(util.plugin().getConfig().getBoolean("MySQL.Enabled")) {
            Cache.cacheFromMySQL(p);

        PlayerData data = util.plugin().getDb().findPlayerDataByName(util.getUUID(p));
        if (data == null) {
            data = new PlayerData(util.getUUID(e.getPlayer()), "None", "Server-Logo", "None", "None", "None", "Default", "Oak-Plank");
            util.plugin().getDb().createPlayerData(data);
        }
        }

    }
}
