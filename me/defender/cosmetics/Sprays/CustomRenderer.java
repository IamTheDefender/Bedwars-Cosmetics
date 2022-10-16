// 
// @author IamTheDefender
// 

package me.defender.cosmetics.Sprays;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;

import java.io.File;
import java.io.IOException;
import java.awt.Image;
import org.bukkit.map.MapPalette;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.image.BufferedImage;
import org.bukkit.map.MapRenderer;

public class CustomRenderer extends MapRenderer
{
    private BufferedImage image;
    private boolean done;
    
    public CustomRenderer() {
        this.done = false;
    }
    
    public CustomRenderer(String url) {
        this.done = false;
    }
    public CustomRenderer(File file) {
        this.done = false;
    }
    
    public boolean load(String url) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(url));
            image = MapPalette.resizeImage(image);
        }
        catch (final IOException e) {
            return false;
        }
        this.image = image;
        return true;
    }
    public boolean load(File file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
            image = MapPalette.resizeImage(image);
        }
        catch (final IOException e) {
            return false;
        }
        this.image = image;
        return true;
    }
    
    public void render(final MapView view, final MapCanvas canvas, final Player p) {
        if (this.done) {
            return;
        }
        canvas.drawImage(0, 0, this.image);
        this.done = true;
    }
}
