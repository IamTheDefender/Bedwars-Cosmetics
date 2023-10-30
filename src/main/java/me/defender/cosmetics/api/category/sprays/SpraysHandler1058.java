

package me.defender.cosmetics.api.category.sprays;

import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.util.StartupUtils;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class SpraysHandler1058 implements Listener
{

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent e) {
         Player p = e.getPlayer();
        if (e.getRightClicked() instanceof ItemFrame) {
            ItemFrame itemFrame = (ItemFrame) e.getRightClicked();
            if(itemFrame.getItem() == null) return;
            String selected = new BwcAPI().getSelectedCosmetic(p, CosmeticsType.Sprays);
            if(itemFrame.getItem().getType() == Material.AIR || itemFrame.getItem().getType() == Material.MAP || itemFrame.getItem().getType() == Material.EMPTY_MAP) {
                for(Spray spray : StartupUtils.sprayList){
                    if(spray.getIdentifier().equals(selected)){
                        spray.execute(p, itemFrame);
                    }
                }
                e.setCancelled(true);
            }

        }
    }


    @EventHandler
    public void onGameStart1058(GameStateChangeEvent event) {
        if (event.getNewState().name().equals("playing")) {
            for (final Entity e : event.getArena().getWorld().getEntities()) {
                if (e.getType() == EntityType.ITEM_FRAME) {
                    ItemFrame itemFrame = (ItemFrame) e;
                    if(itemFrame.getItem() == null || itemFrame.getItem().getType() == Material.AIR) {
                        ArmorStand stand = (ArmorStand) e.getWorld().spawnEntity(e.getLocation().subtract(0.0, 0.9, 0.0), EntityType.ARMOR_STAND);
                        stand.setVisible(false);
                        stand.setGravity(false);
                        stand.setCustomName(ColorUtil.colored("&eClick!"));
                        stand.setMetadata("HOLO_ITEM_FRAME", new FixedMetadataValue(Utility.plugin(), ""));
                        stand.setCustomNameVisible(true);
                        stand.setMarker(true);
                        stand.setSmall(true);
                    }
                }
            }
        }
    }
}