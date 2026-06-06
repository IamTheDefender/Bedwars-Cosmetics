package xyz.iamthedefender.cosmetics.versionsupport;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.profiles.builder.XSkull;
import com.cryptomorin.xseries.profiles.objects.Profileable;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import xyz.iamthedefender.cosmetics.api.particle.ParticleWrapper;
import xyz.iamthedefender.cosmetics.api.versionsupport.IVersionSupport;

public class VersionSupport_1_8_R3 implements IVersionSupport {

    @Override
    public @NotNull String getVersion() {
        return "v1.8.8 handler";
    }

    @Override
    public ItemStack getSkull(String base64) {
        ItemStack head = XMaterial.PLAYER_HEAD.parseItem();

        if (head == null) throw new RuntimeException("Failed to get skull (v1.8.8)");

        ItemMeta itemMeta = head.getItemMeta();

        if (itemMeta == null) return head;

        itemMeta = XSkull.of(itemMeta).profile(Profileable.detect(base64)).lenient().apply();

        head.setItemMeta(itemMeta);

        return head;
    }

    @Override
    public @NotNull ItemStack applyRenderer(MapRenderer mapRenderer, MapView mapView) {
        ItemStack map = XMaterial.FILLED_MAP.parseItem();
        mapView.getRenderers().forEach(mapView::removeRenderer);
        mapView.addRenderer(mapRenderer);
        map.setDurability(mapView.getId());
        return map;
    }

    @Override
    public boolean isValidParticle(String name) {
        try {
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            String serverVersion = packageName.substring(packageName.lastIndexOf('.') + 1);
            Class<?> enumParticleClass = Class.forName("net.minecraft.server." + serverVersion + ".EnumParticle");
            Enum.valueOf((Class<Enum>) enumParticleClass, name.toUpperCase());
        } catch (ClassNotFoundException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private EnumParticle resolveEnumParticle(ParticleWrapper particleWrapper) {
        String name = particleWrapper.getParticleType().getName().getKey().toUpperCase();
        try {
            return EnumParticle.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public void displayParticle(Player player, Location location, ParticleWrapper particle) {
        displayParticle(player, location, particle, 1, 1.0f, null, null);
    }

    @Override
    public void displayParticle(Player player, Location location, ParticleWrapper particle, int count) {
        displayParticle(player, location, particle, count, 1.0f, null, null);
    }

    @Override
    public void displayParticle(Player player, Location location, ParticleWrapper particle, int count, float speed) {
        displayParticle(player, location, particle, count, speed, null, null);
    }

    @Override
    public void displayParticle(Player player, Location location, ParticleWrapper particle, int count, float speed, Vector offset) {
        displayParticle(player, location, particle, count, speed, offset, null);
    }

    @Override
    public void displayParticle(Player player, Location location, ParticleWrapper particleWrapper, Color color) {
        displayParticle(player, location, particleWrapper, 1, 1.0f, null, color);
    }

    private void displayParticle(Player player, Location location, ParticleWrapper particleWrapper, int count, float speed, Vector offset, Color color) {
        try {
            EnumParticle enumParticle = resolveEnumParticle(particleWrapper);
            if (enumParticle == null) return;

            int[] particleData = isBlockDataParticle(enumParticle) ? new int[]{Material.SAND.getId()} : new int[0];

            for (int i = 0; i < count; i++) {
                float offsetX = offset == null ? 0.0F : (float) offset.getX();
                float offsetY = offset == null ? 0.0F : (float) offset.getY();
                float offsetZ = offset == null ? 0.0F : (float) offset.getZ();

                if (color != null) {
                    offsetX = color.getRed() / 255f;
                    offsetY = color.getGreen() / 255f;
                    offsetZ = color.getBlue() / 255f;
                }

                PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                        enumParticle,
                        false,
                        (float) location.getX(),
                        (float) location.getY(),
                        (float) location.getZ(),
                        offsetX,
                        offsetY,
                        offsetZ,
                        speed,
                        color != null ? 0 : 1,
                        particleData
                );

                if (player != null) {
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                } else {
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        ((CraftPlayer) onlinePlayer).getHandle().playerConnection.sendPacket(packet);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to display particle", e);
        }
    }

    private boolean isBlockDataParticle(EnumParticle particle) {
        return particle == EnumParticle.BLOCK_CRACK
                || particle == EnumParticle.ITEM_CRACK
                || particle == EnumParticle.BLOCK_DUST;
    }
}