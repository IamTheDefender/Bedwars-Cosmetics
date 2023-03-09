

package me.defender.cosmetics.api.category.glyphs.util;

import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class glyphUtil
{
    public static BukkitTask task = null;
    
    public static void sendglyphs(Player p, File file, Location loc) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        ImageParticles particles = new ImageParticles(image, 1);
        particles.setAnchor(50, 10);
        particles.setDisplayRatio(0.1);
        Map<Location, Color> particle = particles.getParticles(loc, loc.getPitch(), 180.0);
        for (Location spot : particle.keySet()) {
            FastParticle.spawnParticle(p.getWorld(), ParticleType.REDSTONE, spot, 1, particle.get(spot));
        }
    }
       

}
