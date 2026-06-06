

package xyz.iamthedefender.cosmetics.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.data.PlayerData;
import xyz.iamthedefender.cosmetics.data.PlayerOwnedData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PlayerData playerData = CosmeticsPlugin.getInstance().getPlayerManager().getPlayerData(event.getPlayer().getUniqueId());

        if (!playerData.exists()) {
            setDefaultCosmetics(playerData, event.getPlayer());
            playerData.createData();
            playerData.load();
        }

        if (!CosmeticsPlugin.getInstance().getPlayerManager().getPlayerOwnedDataHashMap().containsKey(event.getPlayer().getUniqueId())) {
            updatePlayerOwnedDataAsync(event.getPlayer());
            return;
        }

        updatePlayerOwnedData(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        // Stop any active previews to ensure resources like NPCs and ArmorStands are cleaned up
        Utility.getApi().getPreviewList().forEach(preview -> preview.stopPreview(event.getPlayer()));
    }

    private void setDefaultCosmetics(PlayerData playerData, Player player) {
        for (CosmeticType<?> cosmeticType : CosmeticType.values()) {
            try {

                Method getDefault = cosmeticType.getCosmeticsClass().getMethod("getDefault", Player.class);
                // Just invokes the static getDefault method, nothing to worry about (hopefully)

                Cosmetics cosmetic = (Cosmetics) getDefault.invoke(
                        null,
                        player
                );

                if (cosmetic == null) {
                    CosmeticsPlugin.getInstance().getLogger().warning("No default cosmetic found for category: " + cosmeticType.name());
                    continue;
                }

                playerData.setSelectedData(cosmeticType, cosmetic.getIdentifier());
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    private void updatePlayerOwnedData(Player player) {
        PlayerOwnedData playerOwnedData = CosmeticsPlugin.getInstance().getPlayerManager().getPlayerOwnedData(player.getUniqueId());
        playerOwnedData.updateOwned();
    }

    private void updatePlayerOwnedDataAsync(Player player) {
        Run.async(() -> {
            PlayerOwnedData playerOwnedData = CosmeticsPlugin.getInstance().getPlayerManager().getPlayerOwnedData(player.getUniqueId());
            playerOwnedData.updateOwned();
        });
    }
}
