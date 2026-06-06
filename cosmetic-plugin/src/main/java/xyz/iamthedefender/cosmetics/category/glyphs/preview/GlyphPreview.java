package xyz.iamthedefender.cosmetics.category.glyphs.preview;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticPreview;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.util.ColorUtil;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;
import xyz.iamthedefender.cosmetics.category.glyphs.util.GlyphUtil;
import xyz.iamthedefender.cosmetics.category.glyphs.util.ImageParticles;
import xyz.iamthedefender.cosmetics.support.protocol.PacketEventsBridge;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlyphPreview extends CosmeticPreview {

    public GlyphPreview() {
        super(CosmeticType.GLYPHS);
    }

    @Override
    public void showPreview(Player player, Cosmetics selected, Location previewLocation, Location playerLocation) throws IllegalArgumentException {
        handleLocation(player, playerLocation);

        ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(playerLocation, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setBasePlate(false);
        as.setSmall(false);
        as.teleport(playerLocation);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 80, 2));

        sendGlyphParticles(player, previewLocation, selected.getIdentifier());
        
        
        Run.delayed(() -> {
            if (player.isOnline() && !as.isDead()) {
                PacketEventsBridge.sendCamera(player, as.getEntityId());
            }
        }, 2L);


        setOnEnd(player, () -> {
            if (!as.isDead()) as.remove();

            PacketEventsBridge.sendCamera(player, player.getEntityId());
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        });
    }

    private void sendGlyphParticles(Player player, Location location, String selected) {
        ConfigManager config = ConfigUtils.getGlyphs();

        String glyphFile = config.getString(CosmeticType.GLYPHS.getSectionKey() + "." + selected + ".file");

        if (glyphFile == null) {
            player.sendMessage(ColorUtil.translate("&cLooks like the glyphFile is null? Contact a developer!"));
            Logger.getLogger("Minecraft").log(Level.SEVERE, selected + " glyphFile is null!");
            return;
        }

        File file = new File(
                CosmeticsPlugin.getInstance().getHandler().getAddonPath() +
                        "/Glyphs/" +
                        glyphFile);

        if (!file.exists()){
            player.sendMessage(ColorUtil.translate("&cLooks like the glyphFile doesn't exist? Contact a developer!"));
            Logger.getLogger("Minecraft").log(Level.SEVERE, file.getAbsolutePath() + " does not exist!");
            return;
        }

        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (final IOException e) {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "UNABLE TO READ FILE! GlyphPreview");
        }
        if (image == null) return;

        ImageParticles imageParticles = new ImageParticles(image, 1);
        imageParticles.setAnchor(50, 10);
        imageParticles.setDisplayRatio(0.1);

        Location displayLoc = location.clone().add(0, 2, 0);

        Map<Location, Color> particles = imageParticles.getParticles(displayLoc, displayLoc.getPitch(), 180.0f);

        long perIteration = 2L;
        long time = getEndDelay() / perIteration;
        AtomicLong counter = new AtomicLong(time);

        Run.everyAsync((r) -> {
            if (counter.decrementAndGet() < 0) r.cancel();

            for (Location spot : particles.keySet()) {
                GlyphUtil.sendRedstoneParticle(player, spot, particles.get(spot));
            }
        }, perIteration);
    }
}
