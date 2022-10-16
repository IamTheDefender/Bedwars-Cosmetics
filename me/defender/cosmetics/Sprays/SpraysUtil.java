// 
// @author IamTheDefender
// 

package me.defender.cosmetics.Sprays;

import java.io.File;

import me.defender.api.BwcAPI;
import me.defender.api.enums.Cosmetics;
import me.defender.support.sounds.GSound;
import org.bukkit.*;

import org.bukkit.entity.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.map.MapView;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.defender.cosmetics.KillMessage.KillMessagesUtil;
import me.defender.cosmetics.ShopKeeperSkins.ShopKeeperSkin;
import me.defender.api.utils.util;

import java.util.HashMap;
import com.andrei1058.bedwars.api.BedWars;

public class SpraysUtil
{
    BedWars bedwarsAPI;
    public static HashMap<String, Long> cooldown;
    
    static {
        SpraysUtil.cooldown = new HashMap<String, Long>();
    }
    
    public SpraysUtil() {
        this.bedwarsAPI = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
    }
    
    @SuppressWarnings("deprecation")
	public static void spawnSprays( Player p, ItemFrame f) {
        MapView view = Bukkit.createMap(p.getWorld());
        if (SpraysUtil.cooldown.containsKey(p.getName())) {
            if (SpraysUtil.cooldown.get(p.getName()) > System.currentTimeMillis()) {
                p.playSound(p.getLocation(), util.villager_no, 1.0f, 1.0f);
                p.sendMessage(util.color(util.getMSGLang(p, "cosmetics.spray-msg")));
                return;
            }
            SpraysUtil.cooldown.remove(p.getName());
        }
        else if (ShopKeeperSkin.arenas.containsKey(p.getWorld().getName())) {

            SpraysUtil.cooldown.put(p.getName(), System.currentTimeMillis() + 3000L);
            view.removeRenderer(view.getRenderers().get(0));
            final CustomRenderer renderer = new CustomRenderer();
            String Spray = new BwcAPI().getSelectedCosmetic(p, Cosmetics.Sprays);
            String Sprays = util.plugin().spraysdata.getConfig().getString("Sprays." + Spray + ".Url");
            String SpraysFile = util.plugin().spraysdata.getConfig().getString("Sprays." + Spray + ".File");
            if(SpraysFile == null){
                if (!renderer.load(Sprays)) {
                    p.sendMessage(util.color("&cLooks like there's an error rendering the Spray, contact the admin!"));
                    Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not load the URL for the " + Spray + " Check if the URL in Sprays.yml is valid!");
                }else {
                    view.addRenderer(renderer);
                    final ItemStack map = new ItemStack(Material.MAP, 1, view.getId());
                    f.setItem(map);
                    f.setRotation(Rotation.NONE);
                    Sound sound = GSound.ENTITY_SILVERFISH_HURT.parseSound();
                    p.playSound(f.getLocation(), sound, 1f, 1f);
                    p.playEffect(f.getLocation(), Effect.FLYING_GLYPH, 15);
                    for (final Entity en : f.getNearbyEntities(1.0, 1.0, 1.0)) {
                        if (en.getType() == EntityType.ARMOR_STAND && en.hasMetadata("HOLO_ITEM_FRAME")) {
                            en.remove();
                        }
                    }
                }
            }else{
                Sprays = util.plugin().spraysdata.getConfig().getString("Sprays." + Spray + ".File");
                File file = new File(System.getProperty("user.dir") + "/plugins/Bedwars1058-Cosmetics/Sprays/" + Sprays);
                if (!renderer.load(file)) {
                    p.sendMessage(util.color("&cLooks like there's an error rendering the Spray, contact the admin!"));
                    Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not load the File for the " + Spray + " Check if the File in Sprays.yml is valid!");
                }else {
                    view.addRenderer(renderer);
                    final ItemStack map = new ItemStack(Material.MAP, 1, view.getId());
                    f.setItem(map);
                    f.setRotation(Rotation.NONE);
                    p.playEffect(f.getLocation(), Effect.FLYING_GLYPH, 10);
                    for (final Entity en : f.getNearbyEntities(1.0, 1.0, 1.0)) {
                        if (en.getType() == EntityType.ARMOR_STAND && en.hasMetadata("HOLO_ITEM_FRAME")) {
                            en.remove();
                        }
                    }
                }
            }


        }
    }
}


