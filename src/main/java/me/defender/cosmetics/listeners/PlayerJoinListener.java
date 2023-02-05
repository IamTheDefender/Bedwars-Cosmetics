

package me.defender.cosmetics.listeners;

import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.categories.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.categories.deathcries.DeathCry;
import me.defender.cosmetics.api.categories.finalkilleffects.FinalKillEffect;
import me.defender.cosmetics.api.categories.glyphs.Glyph;
import me.defender.cosmetics.api.categories.islandtoppers.IslandTopper;
import me.defender.cosmetics.api.categories.killmessage.KillMessage;
import me.defender.cosmetics.api.categories.projectiletrails.ProjectileTrail;
import me.defender.cosmetics.api.categories.shopkeeperskins.ShopKeeperSkin;
import me.defender.cosmetics.api.categories.sprays.Spray;
import me.defender.cosmetics.api.categories.victorydances.VictoryDance;
import me.defender.cosmetics.api.categories.woodskins.WoodSkin;
import me.defender.cosmetics.api.utils.Utility;
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
            PlayerData playerData = new PlayerData(event.getPlayer().getUniqueId());
            Utility.playerDataList.put(event.getPlayer().getUniqueId(), playerData);
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
            Utility.playerDataList.put(event.getPlayer().getUniqueId(), playerData);
            PlayerOwnedData playerOwnedData = new PlayerOwnedData(event.getPlayer().getUniqueId());
            Utility.playerOwnedDataList.put(event.getPlayer().getUniqueId(), playerOwnedData);
            playerOwnedData.updateOwned();
        }

        // Saving for SQLite is different, workaround for SQLite database is busy
        if(!api.isMySQL()) {
            if (!Utility.playerDataList.containsKey(event.getPlayer().getUniqueId())) {
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
                Utility.playerDataList.put(event.getPlayer().getUniqueId(), playerData);
            }

            if (!Utility.playerOwnedDataList.containsKey(event.getPlayer().getUniqueId())) {
                PlayerOwnedData playerOwnedData = new PlayerOwnedData(event.getPlayer().getUniqueId());
                Utility.playerOwnedDataList.put(event.getPlayer().getUniqueId(), playerOwnedData);
                playerOwnedData.updateOwned();
            } else {
                Utility.playerOwnedDataList.get(event.getPlayer().getUniqueId()).updateOwned();
            }
        }
    }
}
