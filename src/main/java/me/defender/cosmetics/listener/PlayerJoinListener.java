

package me.defender.cosmetics.listener;

import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.CosmeticsAPI;
import me.defender.cosmetics.api.cosmetics.category.*;
import me.defender.cosmetics.data.PlayerData;
import me.defender.cosmetics.data.PlayerOwnedData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    public PlayerJoinListener() {
        Cosmetics plugin = Cosmetics.getPlugin(Cosmetics.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CosmeticsAPI api = Cosmetics.getInstance().getApi();

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
        if (!api.isMySQL()) {
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
