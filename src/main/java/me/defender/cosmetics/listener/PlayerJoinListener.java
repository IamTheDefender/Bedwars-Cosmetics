

package me.defender.cosmetics.listener;

import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.cosmetics.category.BedDestroy;
import me.defender.cosmetics.api.cosmetics.category.DeathCry;
import me.defender.cosmetics.api.cosmetics.category.FinalKillEffect;
import me.defender.cosmetics.api.cosmetics.category.Glyph;
import me.defender.cosmetics.api.cosmetics.category.IslandTopper;
import me.defender.cosmetics.api.cosmetics.category.KillMessage;
import me.defender.cosmetics.api.cosmetics.category.ProjectileTrail;
import me.defender.cosmetics.api.cosmetics.category.ShopKeeperSkin;
import me.defender.cosmetics.api.cosmetics.category.Spray;
import me.defender.cosmetics.api.cosmetics.category.VictoryDance;
import me.defender.cosmetics.api.cosmetics.category.WoodSkin;
import me.defender.cosmetics.util.Utility;
import me.defender.cosmetics.database.PlayerData;
import me.defender.cosmetics.database.PlayerOwnedData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    public PlayerJoinListener() {
        Cosmetics plugin = Cosmetics.getPlugin(Cosmetics.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        BwcAPI api = new BwcAPI();

        // Saving for MySQL is different
        if (api.isMySQL()) {
            PlayerData playerData = Cosmetics.getInstance().getPlayerManager().getPlayerData(event.getPlayer().getUniqueId());
            if (!playerData.exists()) {
                playerData.setBedDestroy(BedDestroy.getDefault(event.getPlayer()).getIdentifier());
                playerData.setDeathCry(DeathCry.getDefault(event.getPlayer()).getIdentifier());
                playerData.setFinalKillEffect(FinalKillEffect.getDefault(event.getPlayer()).getIdentifier());
                playerData.setGlyph(Glyph.getDefault(event.getPlayer()).getIdentifier());
                playerData.setIslandTopper(IslandTopper.getDefault(event.getPlayer()).getIdentifier());
                playerData.setKillMessage(KillMessage.getDefault(event.getPlayer()).getIdentifier());
                playerData.setProjectileTrail(ProjectileTrail.getDefault(event.getPlayer()).getIdentifier());
                playerData.setShopkeeperSkin(ShopKeeperSkin.getDefault(event.getPlayer()).getIdentifier());
                playerData.setSpray(Spray.getDefault(event.getPlayer()).getIdentifier());
                playerData.setVictoryDance(VictoryDance.getDefault(event.getPlayer()).getIdentifier());
                playerData.setWoodSkin(WoodSkin.getDefault(event.getPlayer()).getIdentifier());
                playerData.createData();
            }
            PlayerOwnedData playerOwnedData = Cosmetics.getInstance().getPlayerManager().getPlayerOwnedData(event.getPlayer().getUniqueId());
            playerOwnedData.updateOwned();
        }

        // Saving for SQLite is different, workaround for SQLite database is busy
        if(!api.isMySQL()) {
            if (!Cosmetics.getInstance().getPlayerManager().getPlayerDataHashMap().containsKey(event.getPlayer().getUniqueId())) {
                PlayerData playerData = new PlayerData(event.getPlayer().getUniqueId());
                if (!playerData.exists()) {
                    playerData.setBedDestroy(BedDestroy.getDefault(event.getPlayer()).getIdentifier());
                    playerData.setDeathCry(DeathCry.getDefault(event.getPlayer()).getIdentifier());
                    playerData.setFinalKillEffect(FinalKillEffect.getDefault(event.getPlayer()).getIdentifier());
                    playerData.setGlyph(Glyph.getDefault(event.getPlayer()).getIdentifier());
                    playerData.setIslandTopper(IslandTopper.getDefault(event.getPlayer()).getIdentifier());
                    playerData.setKillMessage(KillMessage.getDefault(event.getPlayer()).getIdentifier());
                    playerData.setProjectileTrail(ProjectileTrail.getDefault(event.getPlayer()).getIdentifier());
                    playerData.setShopkeeperSkin(ShopKeeperSkin.getDefault(event.getPlayer()).getIdentifier());
                    playerData.setSpray(Spray.getDefault(event.getPlayer()).getIdentifier());
                    playerData.setVictoryDance(VictoryDance.getDefault(event.getPlayer()).getIdentifier());
                    playerData.setWoodSkin(WoodSkin.getDefault(event.getPlayer()).getIdentifier());
                    playerData.createData();
                }
                Cosmetics.getInstance().getPlayerManager().addPlayerData(playerData);
            }

            if (!Cosmetics.getInstance().getPlayerManager().getPlayerOwnedDataHashMap().containsKey(event.getPlayer().getUniqueId())) {
                PlayerOwnedData playerOwnedData = Cosmetics.getInstance().getPlayerManager().getPlayerOwnedData(event.getPlayer().getUniqueId());
                playerOwnedData.updateOwned();
            } else {
              Cosmetics.getInstance().getPlayerManager().getPlayerOwnedData(event.getPlayer().getUniqueId()).updateOwned();
            }
        }
    }
}
