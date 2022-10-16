// 
// @author IamTheDefender
// 

package me.defender.cosmetics.Sprays;

import org.bukkit.Material;
import org.bukkit.metadata.FixedMetadataValue;
import me.defender.api.utils.util;
import org.bukkit.entity.ArmorStand;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.Listener;

public class OnRightClick implements Listener
{

    @EventHandler
    public void onRightClick(final PlayerInteractEntityEvent e) {
         Player p = e.getPlayer();
        if (e.getRightClicked() instanceof ItemFrame) {
            ItemFrame itemFrame = (ItemFrame) e.getRightClicked();
            if(itemFrame.getItem() == null || itemFrame.getItem().getType() == Material.AIR || itemFrame.getItem().getType() == Material.MAP || itemFrame.getItem().getType() == Material.EMPTY_MAP) {
                SpraysUtil.spawnSprays(p, (ItemFrame) e.getRightClicked());
                e.setCancelled(true);
            }

        }
    }


    @EventHandler
    public void onGameStart(final GameStateChangeEvent event) {
        if (event.getNewState().name().equals("playing")) {
            for (final Entity e : event.getArena().getWorld().getEntities()) {
                if (e.getType() == EntityType.ITEM_FRAME) {
                    ItemFrame itemFrame = (ItemFrame) e;
                    if(itemFrame.getItem() == null || itemFrame.getItem().getType() == Material.AIR) {
                        ArmorStand stand = (ArmorStand) e.getWorld().spawnEntity(e.getLocation().subtract(0.0, 0.9, 0.0), EntityType.ARMOR_STAND);
                        stand.setVisible(false);
                        stand.setGravity(false);
                        stand.setCustomName(util.color("&eClick!"));
                        stand.setMetadata("HOLO_ITEM_FRAME", new FixedMetadataValue(util.plugin(), ""));
                        stand.setCustomNameVisible(true);
                        stand.setMarker(true);
                        stand.setSmall(true);
                    }
                }
            }
        }
    }
}
