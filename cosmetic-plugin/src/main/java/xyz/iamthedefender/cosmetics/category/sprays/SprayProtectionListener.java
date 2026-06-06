package xyz.iamthedefender.cosmetics.category.sprays;

import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.category.sprays.util.SpraysUtil;

public class SprayProtectionListener implements Listener {

    @EventHandler
    public void onSprayFrameBreak(HangingBreakEvent event) {
        boolean isSpraysEnabled = xyz.iamthedefender.cosmetics.util.StartupUtils.isFeatureEnabled("sprays");
        if (!isSpraysEnabled) return;

        if (!(event.getEntity() instanceof ItemFrame)) return;
        if (!SpraysUtil.isSprayFrame(event.getEntity())) return;

        HangingBreakEvent.RemoveCause cause = event.getCause();
        if (cause == HangingBreakEvent.RemoveCause.OBSTRUCTION || cause == HangingBreakEvent.RemoveCause.PHYSICS) {
            event.setCancelled(true);
        }
    }
}
