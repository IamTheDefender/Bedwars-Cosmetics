package xyz.iamthedefender.cosmetics.category.sprays.handler;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.screamingsandals.bedwars.api.events.BedwarsGameStartEvent;
import xyz.iamthedefender.cosmetics.category.sprays.AbstractSpray;

public class SprayHandlerSBW extends AbstractSpray {

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent e) {

        if (!enabled()) return;

        Player p = e.getPlayer();

        if (!(e.getRightClicked() instanceof ItemFrame)) {
            return;
        }

        ItemFrame itemFrame = (ItemFrame) e.getRightClicked();
        if (!canApply(itemFrame)) return;

        execute(p, itemFrame);
        e.setCancelled(true);
    }


    @EventHandler
    public void onGameStart(BedwarsGameStartEvent event) {

        if (!enabled()) return;

        markSprayFrames(event.getGame().getGameWorld());
    }

}


