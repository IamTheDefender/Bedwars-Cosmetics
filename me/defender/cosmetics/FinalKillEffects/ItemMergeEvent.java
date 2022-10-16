package me.defender.cosmetics.FinalKillEffects;

import me.defender.cosmetics.ShopKeeperSkins.ShopKeeperSkin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ItemMergeEvent implements Listener {

    @EventHandler
    public void itemMergeEvent(org.bukkit.event.entity.ItemMergeEvent e){
        if(e.getEntity().getItemStack().getType() == Material.COOKIE){
            if(ShopKeeperSkin.arenas.containsKey(e.getEntity().getWorld())){
                e.setCancelled(true);
            }
        }
    }
}
