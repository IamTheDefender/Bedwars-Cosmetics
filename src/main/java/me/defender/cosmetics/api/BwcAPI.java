package me.defender.cosmetics.api;

import com.hakan.core.HCore;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.cosmetics.CosmeticsType;
import me.defender.cosmetics.util.Utility;
import me.defender.cosmetics.database.PlayerData;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class BwcAPI {

    /**
     * Check if MySQL is enabled.
     * @return true if enabled.
     */
    public Boolean isMySQL() {
        return  Cosmetics.getInstance().getConfig().getBoolean("mysql.enable");
    }

    /**
     * Get the selected cosmetic for a player.
     * @param p   Player
     * @param cos Cosmetic type
     * @return    String
     */
    public String getSelectedCosmetic(Player p, CosmeticsType cos){
        if(p == null){
            return null;
        }
        PlayerData playerData = Cosmetics.getInstance().getPlayerManager().getPlayerData(p.getUniqueId());
        switch (cos){
            case BedBreakEffects:
                return playerData.getBedDestroy();
            case DeathCries:
                return playerData.getDeathCry();
            case FinalKillEffects:
                return playerData.getFinalKillEffect();
            case Glyphs:
                return playerData.getGlyph();
            case IslandTopper:
                return playerData.getIslandTopper();
            case KillMessage:
                return playerData.getKillMessage();
            case ProjectileTrails:
                return playerData.getProjectileTrail();
            case ShopKeeperSkin:
                return playerData.getShopkeeperSkin();
            case Sprays:
                return playerData.getSpray();
            case VictoryDances:
                return playerData.getVictoryDance();
            case WoodSkins:
                return playerData.getWoodSkin();
        }
        return "User not found!";
    }

    /**
     * Set the selected cosmetic for a player.
     * @param p     Player.
     * @param cos   Cosmetic type.
     * @param value Cosmetic value.
     */
    public void setSelectedCosmetic(Player p, CosmeticsType cos, String value){
        PlayerData playerData = Cosmetics.getInstance().getPlayerManager().getPlayerData(p.getUniqueId());
        switch (cos){
            case BedBreakEffects:
                playerData.setBedDestroy(value);
                break;
            case DeathCries:
                playerData.setDeathCry(value);
                break;
            case FinalKillEffects:
                playerData.setFinalKillEffect(value);
                break;
            case Glyphs:
                playerData.setGlyph(value);
                break;
            case IslandTopper:
                playerData.setIslandTopper(value);
                break;
            case KillMessage:
                playerData.setKillMessage(value);
                break;
            case ProjectileTrails:
                playerData.setProjectileTrail(value);
                break;
            case ShopKeeperSkin:
                playerData.setShopkeeperSkin(value);
                break;
            case Sprays:
                playerData.setSpray(value);
                break;
            case VictoryDances:
                playerData.setVictoryDance(value);
                break;
            case WoodSkins:
                playerData.setWoodSkin(value);
                break;
        }
        // Ran ASYNC
        HCore.asyncScheduler().run(playerData::save);
    }

    /**
     * Get the vault economy.
     * @return the vault economy.
     */
    public Economy getEco(){
        final RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        return rsp.getProvider();
    }

    /**
     * Check if the plugin is running on a proxy.
     * @return true if running on a proxy.
     */
    public Boolean isProxy(){
        return Bukkit.getPluginManager().getPlugin("BedWarsProxy") != null;
    }
}