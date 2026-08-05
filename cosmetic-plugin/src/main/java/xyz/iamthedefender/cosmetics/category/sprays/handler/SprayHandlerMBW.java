package xyz.iamthedefender.cosmetics.category.sprays.handler;

import de.marcely.bedwars.api.event.arena.ArenaPreparingStartEvent;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import xyz.iamthedefender.cosmetics.category.sprays.AbstractSpray;

import java.util.Objects;

public class SprayHandlerMBW extends AbstractSpray {

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
    public void onGameStart(ArenaPreparingStartEvent event) {

        if (!enabled()) return;

        markSprayFrames(Objects.requireNonNull(event.getArena().getGameWorld()));
    }

}
