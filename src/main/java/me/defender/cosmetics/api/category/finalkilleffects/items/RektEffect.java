package me.defender.cosmetics.api.category.finalkilleffects.items;

import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.HCore;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.api.category.finalkilleffects.FinalKillEffect;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.Utility;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

import static me.defender.cosmetics.api.util.Utility.plugin;

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
        if (!onlyVictim) {
            ArmorStand stand = (ArmorStand) victim.getWorld().spawnEntity(victim.getEyeLocation(), EntityType.ARMOR_STAND);
            stand.setSmall(true);
            stand.setGravity(false);
            stand.setVisible(false);
            stand.setCustomNameVisible(true);
            stand.setCustomName(ColorUtil.colored("&6" + killer.getDisplayName() + " &ehas #rekt &6" + victim.getDisplayName()
                    + "&ehere"));

            new BukkitRunnable() {
                @Override
                public void run() {
                    stand.remove();
                }
            }.runTaskLater(plugin(), 200L);
        } else {
            EntityArmorStand stand = new EntityArmorStand(((CraftWorld) location.getWorld()).getHandle());
            stand.setPosition(location.getX(), location.getY() + 2, location.getZ());

            stand.setSmall(true);
            stand.setGravity(false);
            stand.setInvisible(true);
            stand.setCustomNameVisible(true);
            stand.setCustomName(ColorUtil.colored("&6" + killer.getDisplayName() + " &ehas #rekt &6Derperino " +
                    "&ehere"));

            PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving(stand);
            PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(stand.getId());

            HCore.sendPacket(victim, spawnPacket);

            new BukkitRunnable() {
                @Override
                public void run() {
                    HCore.sendPacket(victim, destroyPacket);
                }
            }.runTaskLater(plugin(), 80L);
        }
    }
}