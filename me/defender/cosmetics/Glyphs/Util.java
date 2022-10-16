// 
// @author IamTheDefender
// 

package me.defender.cosmetics.Glyphs;

import org.bukkit.Color;
import java.awt.image.BufferedImage;
import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import java.util.Map;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.bukkit.Location;
import java.io.File;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Util
{
    public static BukkitTask task;
    
    static {
        Util.task = null;
    }
    
    public static void sendglyphs(final Player p, final File file, final Location loc, final Location blockloc) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        final ImageParticles particles = new ImageParticles(image, 1);
        particles.setAnchor(50, 10);
        particles.setDisplayRatio(0.1);
        final Map<Location, Color> particle = particles.getParticles(loc, loc.getPitch(), 180.0);
        
                for (Location spot : particle.keySet()) {
                    FastParticle.spawnParticle(p.getWorld(), ParticleType.REDSTONE, spot, 1, particle.get(spot));
                }
    }
       

}
