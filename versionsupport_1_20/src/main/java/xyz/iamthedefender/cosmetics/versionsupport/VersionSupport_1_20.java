package xyz.iamthedefender.cosmetics.versionsupport;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.profiles.builder.XSkull;
import com.cryptomorin.xseries.profiles.objects.Profileable;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import xyz.iamthedefender.cosmetics.api.particle.ParticleWrapper;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.api.versionsupport.IVersionSupport;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

public class VersionSupport_1_20 implements IVersionSupport {

    @Override
    public @NotNull String getVersion() {
        return "v1.20 handler";
    }

    @Override
    public ItemStack getSkull(String base64) {
        ItemStack head = XMaterial.PLAYER_HEAD.parseItem();

        if (head == null) throw new RuntimeException("Failed to get skull (v1.20)");

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
        MapMeta mapMeta = (MapMeta) map.getItemMeta();
        if (mapMeta == null) {
            Utility.getApi().getPlugin().getLogger().severe("Failed to apply renderer to map, map meta is null!");
            return map;
        }
        mapMeta.setMapView(mapView);
        map.setItemMeta(mapMeta);
        return map;
    }

    @Override
    public boolean isValidParticle(String name) {
        try {
            Particle.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private PlayerProfile getProfile(String url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        URL urlObject;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Invalid URL", exception);
        }
        textures.setSkin(urlObject);
        profile.setTextures(textures);
        return profile;
    }

    private URL getUrlFromBase64(String base64) throws MalformedURLException {
        String decoded = new String(Base64.getDecoder().decode(base64));
        return new URL(decoded.substring("{\"textures\":{\"SKIN\":{\"url\":\"".length(), decoded.length() - "\"}}}".length()));
    }

    @Override
    public void displayParticle(Player player, Location location, ParticleWrapper particleWrapper, Color color) {
        Particle dustParticle;
        try {
            dustParticle = Particle.valueOf("DUST");
        } catch (IllegalArgumentException e) {
            dustParticle = Particle.valueOf("REDSTONE");
        }
        Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1.0f);
        if (player != null) {
            player.spawnParticle(dustParticle, location, 1, 0, 0, 0, 0, dustOptions);
            return;
        }
        location.getWorld().spawnParticle(dustParticle, location, 1, 0, 0, 0, 0, dustOptions);
    }

    @Override
    public void displayParticle(Player player, Location location, ParticleWrapper particle) {
        Particle bukkitParticle = resolveBukkitParticle(particle);
        if (bukkitParticle == null) return;
        if (player != null) {
            player.spawnParticle(bukkitParticle, location, 1);
            return;
        }
        location.getWorld().spawnParticle(bukkitParticle, location, 1);
    }

    @Override
    public void displayParticle(Player player, Location location, ParticleWrapper particle, int count) {
        Particle bukkitParticle = resolveBukkitParticle(particle);
        if (bukkitParticle == null) return;
        if (player != null) {
            player.spawnParticle(bukkitParticle, location, count);
            return;
        }
        location.getWorld().spawnParticle(bukkitParticle, location, count);
    }

    @Override
    public void displayParticle(Player player, Location location, ParticleWrapper particle, int count, float speed) {
        Particle bukkitParticle = resolveBukkitParticle(particle);
        if (bukkitParticle == null) return;


        if (player != null) {

            if (bukkitParticle == Particle.BLOCK_DUST) {
                player.spawnParticle(bukkitParticle, location, count, 0, 0, 0, speed, Material.STONE.createBlockData());
            }else {
                player.spawnParticle(bukkitParticle, location, count, 0, 0, 0, speed);
            }

            return;
        }

        if (bukkitParticle == Particle.BLOCK_DUST) {
            location.getWorld().spawnParticle(bukkitParticle, location, count, 0, 0, 0, speed, Material.STONE.createBlockData());
        }else {
            location.getWorld().spawnParticle(bukkitParticle, location, count, 0, 0, 0, speed);
        }
    }

    @Override
    public void displayParticle(Player player, Location location, ParticleWrapper particle, int count, float speed, Vector offset) {
        Particle bukkitParticle = resolveBukkitParticle(particle);
        if (bukkitParticle == null) return;
        if (player != null) {
            player.spawnParticle(bukkitParticle, location, count, offset.getX(), offset.getY(), offset.getZ(), speed);
            return;
        }
        location.getWorld().spawnParticle(bukkitParticle, location, count, offset.getX(), offset.getY(), offset.getZ(), speed);
    }

    private Particle resolveBukkitParticle(ParticleWrapper particleWrapper) {
        String name = particleWrapper.getParticleType().getName().getKey().toUpperCase();
        try {
            return Particle.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}