

package me.defender.cosmetics.api.categories.victorydances.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.defender.cosmetics.api.utils.CuboidUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class UsefulUtilsVD
{
    @SuppressWarnings("deprecation")
	public static void bounceBlock(final Block b) {
        if (b == null) {
            return;
        }
        Object fb;
        if (b.getType() == Material.TNT) {
            fb = b.getWorld().spawn(b.getLocation().add(0.0, 1.0, 0.0), TNTPrimed.class);
        }
        else {
            fb = b.getWorld().spawnFallingBlock(b.getLocation().add(0.0, 1.0, 0.0), b.getType(), b.getData());
            ((FallingBlock)fb).setDropItem(false);
        }
        b.setType(Material.AIR);
        final float x = -1.0f + (float)(Math.random() * 3.0);
        final float y = 0.5f;
        final float z = -0.3f + (float)(Math.random() * 1.6);
        ((Entity)fb).setVelocity(new Vector(x, y, z));
    }
    
    public static List<Location> generateSphere(final Location centerBlock, final int radius, final boolean hollow) {
        final List<Location> circleBlocks = new ArrayList<Location>();
        final int bx = centerBlock.getBlockX();
        final int by = centerBlock.getBlockY();
        final int bz = centerBlock.getBlockZ();
        for (int x = bx - radius; x <= bx + radius; ++x) {
            for (int y = by - radius; y <= by + radius; ++y) {
                for (int z = bz - radius; z <= bz + radius; ++z) {
                    final double distance = (bx - x) * (bx - x) + (bz - z) * (bz - z) + (by - y) * (by - y);
                    if (distance < radius * radius && (!hollow || distance >= (radius - 1) * (radius - 1))) {
                        final Location l = new Location(centerBlock.getWorld(), x, y, z);
                        circleBlocks.add(l);
                    }
                }
            }
        }
        return circleBlocks;
    }
    
    public static void spawnFireWorks(final Player p, final int amount, final Color color1, final Color color2, final Location loc) {
        final Firework fw = (Firework)loc.getWorld().spawnEntity(loc.subtract(0.0, 1.0, 0.0), EntityType.FIREWORK);
        final FireworkMeta fwm = fw.getFireworkMeta();
        fwm.addEffect(FireworkEffect.builder().withColor(color1).trail(false).flicker(true).build());
        fwm.addEffect(FireworkEffect.builder().withColor(color2).trail(false).flicker(true).build());
        fw.setFireworkMeta(fwm);
        fw.detonate();
        for (int i = 0; i < amount; ++i) {
            final Firework fw2 = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
            fw2.setFireworkMeta(fwm);
        }
    }
    
    public static Location getRandomLocation(final Location originalLocation, final int radius) {
        final Location location = originalLocation;
        final Double x = location.getX();
        final Double y = location.getY();
        final Double z = location.getZ();
        final Double maxX = x + radius;
        final Double minX = x - radius;
        final Double maxZ = z + radius;
        final Double minZ = z - radius;
        final Double maxY = y + radius;
        final Double minY = y - radius;
        final double newX = ThreadLocalRandom.current().nextDouble(minX, maxX);
        final double newY = ThreadLocalRandom.current().nextDouble(minY, maxY);
        final double newZ = ThreadLocalRandom.current().nextDouble(minZ, maxZ);
        location.setX(newX);
        location.setY(newY);
        location.setZ(newZ);
        if (location.getBlock().getType() == Material.AIR) {
            return originalLocation;
        }
        return location;
    }
    
    public static ItemStack gethead(final String value, final String name) {
        final ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final SkullMeta meta = (SkullMeta)head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", value));
        meta.setDisplayName(name);
        Field profileField = null;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        }
        catch (final IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        head.setItemMeta(meta);
        return head;
    }
    
    public static void launch(final Player player, final double distOverHead, final double distToFacing, final Class<? extends Projectile> projectileClass) {
        final Location start = player.getLocation().add(0.0, distOverHead, 0.0);
        final Vector facing = player.getEyeLocation().add(player.getLocation().getDirection().multiply(distToFacing)).toVector();
        final Vector initialV = facing.subtract(start.toVector()).normalize();
        final Projectile projectile = player.getWorld().spawn(start, Fireball.class);
        projectile.setVelocity(initialV);
    }
    
    public static List<Block> getBlocksInRadius(final Location location, final int radius, final boolean hollow) {
        final List<Block> blocks = new ArrayList<Block>();
        final int bX = location.getBlockX();
        final int bY = location.getBlockY();
        final int bZ = location.getBlockZ();
        for (int x = bX - radius; x <= bX + radius; ++x) {
            for (int y = bY - radius; y <= bY + radius; ++y) {
                for (int z = bZ - radius; z <= bZ + radius; ++z) {
                    final double distance = (bX - x) * (bX - x) + (bY - y) * (bY - y) + (bZ - z) * (bZ - z);
                    if (distance < radius * radius && (!hollow || distance >= (radius - 1) * (radius - 1))) {
                        final Location l = new Location(location.getWorld(), x, y, z);
                        if (l.getBlock().getType() != Material.BARRIER) {
                            blocks.add(l.getBlock());
                        }
                    }
                }
            }
        }
        return blocks;
    }


    public static List<Block> getFreeBlocks(Location location) {
        return (List<Block>)(new CuboidUtil(location, 20)).getAirBlocks().stream()
                .filter(b -> !b.getLocation().clone().subtract(0.0D, 0.1D, 0.0D).getBlock().getType().equals(Material.AIR))
                .filter(b -> b.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock().getType().equals(Material.AIR))
                .filter(b -> b.getLocation().clone().add(1.0D, 0.0D, 0.0D).getBlock().getType().equals(Material.AIR))
                .filter(b -> b.getLocation().clone().add(0.0D, 0.0D, 1.0D).getBlock().getType().equals(Material.AIR))
                .filter(b -> b.getLocation().clone().subtract(1.0D, 0.0D, 1.0D).getBlock().getType().equals(Material.AIR)).filter(b -> b.getLocation().clone().add(1.0D, 0.0D, 0.0D).getBlock().getType().equals(Material.AIR))
                .filter(b -> b.getLocation().clone().subtract(0.0D, 0.0D, 1.0D).getBlock().getType().equals(Material.AIR))
                .filter(b -> b.getLocation().clone().subtract(1.0D, 0.0D, 1.0D).getBlock().getType().equals(Material.AIR))
                .collect(Collectors.toList());
    }
}
