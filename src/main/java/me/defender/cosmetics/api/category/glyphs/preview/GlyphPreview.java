package me.defender.cosmetics.api.category.glyphs.preview;

import com.cryptomorin.xseries.XSound;
import com.hakan.core.HCore;
import com.hakan.core.ui.inventory.InventoryGui;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.api.category.glyphs.Glyph;
import me.defender.cosmetics.api.category.glyphs.util.ImageParticles;
import me.defender.cosmetics.api.category.glyphs.util.glyphUtil;
import me.defender.cosmetics.api.category.shopkeeperskins.ShopKeeperSkin;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.configuration.ConfigUtils;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.StartupUtils;
import me.defender.cosmetics.api.util.Utility;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static me.defender.cosmetics.api.util.StartupUtils.getCosmeticLocation;
import static me.defender.cosmetics.api.util.StartupUtils.getPlayerLocation;

public class GlyphPreview {

    private final Map<UUID, Map<Integer, ItemStack>> inventories = new HashMap<>();

    public void sendPreviewGlyph(Player player, String selected, InventoryGui gui) {
        for (Glyph glyph : StartupUtils.glyphsList) {
            if (glyph.getIdentifier().equals(selected)){
                if (glyph.getField(FieldsType.RARITY, player) == RarityType.NONE) {
                    gui.open(player);
                    XSound.ENTITY_VILLAGER_NO.play(player, 1.0f, 1.0f);
                    return;
                }
            }
        }
        UUID playerUUID = player.getUniqueId();

        Location beforeLocation = player.getLocation().clone();

        Inventory playerInv = player.getInventory();
        if (!inventories.containsKey(playerUUID)) inventories.put(playerUUID, new HashMap<>());

        Map<Integer, org.bukkit.inventory.ItemStack> items = inventories.get(playerUUID);

        for (int i = 0; i < playerInv.getSize(); i++) {
            if (playerInv.getItem(i) == null) continue;
            if (playerInv.getItem(i).getType() == null) continue;
            if (playerInv.getItem(i).getType() == Material.AIR) continue;

            items.put(i, playerInv.getItem(i));
        }

        playerInv.clear();
        player.closeInventory();
        Location cosmeticLocation = null, playerLocation = null;

        try {
            cosmeticLocation = getCosmeticLocation();
            playerLocation = getPlayerLocation();
        }catch (Exception exception){
            exception.printStackTrace();
            player.sendMessage(ColorUtil.colored("&cEither Preview location or Player location is not set! Contact the admin."));
        }
        if(cosmeticLocation == null || playerLocation == null) return;

        final Location finalPlayerLocation = playerLocation;
        final Location finalCosmeticLocation = cosmeticLocation;

        ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(finalPlayerLocation, EntityType.ARMOR_STAND);
        as.setVisible(false);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
                100, 2));

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.equals(player)) continue;

            player1.hidePlayer(player);
        }

        sendGlyphParticles(player, finalCosmeticLocation, selected);

        PacketPlayOutCamera cameraPacket = new PacketPlayOutCamera(((CraftArmorStand) as).getHandle());
        PacketPlayOutCamera resetPacket = new PacketPlayOutCamera(((CraftPlayer) player).getHandle());

        sendPacket(player, cameraPacket);

        HCore.syncScheduler().after(5, TimeUnit.SECONDS).run(() -> {
            if (!as.isDead()) as.remove();

            sendPacket(player, resetPacket);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            player.teleport(beforeLocation);

            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (player1.equals(player)) continue;

                player1.showPlayer(player);
            }
            for (Map.Entry<Integer, org.bukkit.inventory.ItemStack> entry: items.entrySet()) {
                playerInv.setItem(entry.getKey(), entry.getValue());
            }

            gui.open(player);
        });
    }

    private void sendGlyphParticles(Player player, Location location, String selected) {
        ConfigManager config = ConfigUtils.getGlyphs();

        String glyphFile = config.getString(CosmeticsType.Glyphs.getSectionKey() + "." + selected + ".file");

        if (glyphFile == null) {
            player.sendMessage(ColorUtil.colored("&cLooks like the glyphFile is null? Contact a developer!"));
            Logger.getLogger("Minecraft").log(Level.SEVERE, glyphFile + " is null! 1");
            return;
        }

        File file = new File(
                Utility.plugin().getDataFolder().getPath() +
                        "/Glyphs/" +
                        glyphFile);

        if (!file.exists()){
            player.sendMessage(ColorUtil.colored("&cLooks like the glyphFile is null? Contact a developer!"));
            Logger.getLogger("Minecraft").log(Level.SEVERE, glyphFile + " is null! 2");
            return;
        }

        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (final IOException e) {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "UNABLE TO READ FILE! GLYPHUTIL()");
        }
        if (image == null) return;

        ImageParticles imageParticles = new ImageParticles(image, 1);
        imageParticles.setAnchor(50, 10);
        imageParticles.setDisplayRatio(0.1);

        location.add(0, 2, 0);

        Map<Location, Color> particles = imageParticles.getParticles(location, location.getPitch(), 180.0f);

        HCore.asyncScheduler().every(100, TimeUnit.MILLISECONDS).limit(50).run(()-> {
            for (Location spot : particles.keySet()) {
                HCore.syncScheduler().run(() -> new ParticleBuilder(ParticleEffect.REDSTONE, spot)
                        .setParticleData(new RegularColor(particles.get(spot).getRed(),
                                particles.get(spot).getGreen(),
                                particles.get(spot).getBlue()))
                        .display(player));
            }
        });
    }

    private void sendPacket(Player player, Packet<?>... packets) {
        for (Packet<?> packet : packets) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }
}