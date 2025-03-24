package xyz.iamthedefender.cosmetics.category.finalkilleffects.items;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.cryptomorin.xseries.XMaterial;
import xyz.iamthedefender.cosmetics.api.util.ColorUtil;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.FinalKillEffect;
import xyz.iamthedefender.cosmetics.util.EntityUtil;

import java.util.Arrays;
import java.util.List;

public class RektEffect extends FinalKillEffect {
    @Override
    public ItemStack getItem() {
        return XMaterial.OAK_SIGN.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "rekt";
    }

    @Override
    public String getDisplayName() {
        return "Rekt";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns a rekt sign at the", "&7location of victim");
    }

    @Override
    public int getPrice() {
        return 5000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.COMMON;
    }

    @Override
    public void execute(Player killer, Player victim, Location location, boolean onlyVictim) {
        ArmorStand stand;

        if (onlyVictim) {
            stand = (ArmorStand) victim.getWorld().spawnEntity(location.add(0, 2, 0), EntityType.ARMOR_STAND);
            EntityUtil.entityForPlayerOnly(stand, victim);
            stand.setCustomName(ColorUtil.translate("&6" + killer.getDisplayName() + " &ehas #rekt &6Derperino &ehere"));

            schedulePlayerSpecificStandRemoval(stand, victim, 80L);
        } else {
            stand = (ArmorStand) victim.getWorld().spawnEntity(victim.getEyeLocation(), EntityType.ARMOR_STAND);
            stand.setCustomName(ColorUtil.translate("&6" + killer.getDisplayName() + " &ehas #rekt &6" + victim.getDisplayName() + "&ehere"));

            scheduleStandRemoval(stand, 200L);
        }

        setupArmorStand(stand);
    }

    private void setupArmorStand(ArmorStand stand) {
        stand.setSmall(true);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setCustomNameVisible(true);
    }

    private void scheduleStandRemoval(ArmorStand stand, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                stand.remove();
            }
        }.runTaskLater(CosmeticsPlugin.getInstance(), delay);
    }

    private void schedulePlayerSpecificStandRemoval(ArmorStand stand, Player player, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                // Create packet to destroy the entity
                PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
                int[] entityIds = new int[] { stand.getEntityId() };
                packet.getIntegerArrays().write(0, entityIds);

                // Clean up and send packet
                CosmeticsPlugin.getInstance().getEntityPlayerHashMap().remove(stand.getEntityId());
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                stand.remove();
            }
        }.runTaskLater(CosmeticsPlugin.getInstance(), delay);
    }
}