package xyz.iamthedefender.cosmetics.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.support.protocol.PacketEventsBridge;

public class EntityUtil {

    public static void entityForPlayerOnly(Entity entityToHide, Player player){
        if (!CosmeticsPlugin.getInstance().getEntityPlayerHashMap().containsKey(entityToHide.getEntityId())){
            CosmeticsPlugin.getInstance().getEntityPlayerHashMap().put(entityToHide.getEntityId(), player);
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.getUniqueId().equals(player.getUniqueId())){
                PacketEventsBridge.destroyEntities(onlinePlayer, entityToHide.getEntityId());
            }
        }
    }
}
