package xyz.iamthedefender.cosmetics.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.iamthedefender.cosmetics.api.util.Utility;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        // Stop any active previews to ensure resources like NPCs and ArmorStands are cleaned up
        Utility.getApi().getPreviewList().forEach(preview -> preview.stopPreview(event.getPlayer()));
    }
}
