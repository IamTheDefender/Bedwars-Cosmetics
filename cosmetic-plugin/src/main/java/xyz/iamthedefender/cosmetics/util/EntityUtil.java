package xyz.iamthedefender.cosmetics.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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

    public static void setAI(LivingEntity entity, boolean ai) {

        try {
            entity.getClass().getMethod("setAI", boolean.class).invoke(entity, ai);
        } catch (NoSuchMethodException e) {
            try {
                Object nmsEntity = entity.getClass().getMethod("getHandle").invoke(entity);
                Object nbtTag = nmsEntity.getClass().getMethod("getNBTTag").invoke(nmsEntity);
                if (nbtTag == null) {
                    nbtTag = Class.forName("net.minecraft.server." + getVersion() + ".NBTTagCompound")
                            .getDeclaredConstructor().newInstance();
                }
                nbtTag.getClass().getMethod("setBoolean", String.class, boolean.class)
                        .invoke(nbtTag, "NoAI", !ai);
                nmsEntity.getClass().getMethod("a", nbtTag.getClass()).invoke(nmsEntity, nbtTag);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}
