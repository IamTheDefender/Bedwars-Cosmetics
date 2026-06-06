

package xyz.iamthedefender.cosmetics.category.sprays.handler;

import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import xyz.iamthedefender.cosmetics.category.sprays.AbstractSpray;

public class SpraysHandler1058 extends AbstractSpray {

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent e) {

        if (!enabled()) return;

        Player p = e.getPlayer();
        if (e.getRightClicked() instanceof ItemFrame) {
            ItemFrame itemFrame = (ItemFrame) e.getRightClicked();
            if (!canApply(itemFrame)) return;

            execute(p, itemFrame);
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onGameStart1058(GameStateChangeEvent event) {

        if (!enabled()) return;

        if (event.getNewState().name().equals("playing")) {
            markSprayFrames(event.getArena().getWorld());
        }
    }
}

