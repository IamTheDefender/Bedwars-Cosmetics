

package me.defender.cosmetics.api.category.sprays.util;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.hakan.core.HCore;
import com.hakan.core.particle.Particle;
import com.hakan.core.particle.type.ParticleType;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.configuration.ConfigUtils;
import me.defender.cosmetics.api.util.DebugUtil;
import me.defender.cosmetics.api.util.Utility;
import me.defender.cosmetics.api.category.shopkeeperskins.ShopKeeperHandler1058;
import me.defender.cosmetics.support.sounds.GSound;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpraysUtil
{
    public static HashMap<String, Long> cooldown = new HashMap<>();

    
    @SuppressWarnings("deprecation")
	public static void spawnSprays( Player p, ItemFrame f) {
        HCore.syncScheduler().run(() -> {
            MapView view = Bukkit.createMap(p.getWorld());
            // If player is in cool down
            if (SpraysUtil.cooldown.containsKey(p.getName())) {
                if (SpraysUtil.cooldown.get(p.getName()) > System.currentTimeMillis()) {
                    p.playSound(p.getLocation(), XSound.ENTITY_VILLAGER_NO.parseSound(), 1.0f, 1.0f);
                    p.sendMessage(ColorUtil.colored(Utility.getMSGLang(p, "cosmetics.spray-msg")));
                    return;
                }
                SpraysUtil.cooldown.remove(p.getName());
            }
            // The player is no longer in the cool down or it's the first time
            else if (ShopKeeperHandler1058.arenas.containsKey(p.getWorld().getName())) {

                SpraysUtil.cooldown.put(p.getName(), System.currentTimeMillis() + 3000L);
                view.removeRenderer(view.getRenderers().get(0));
                final CustomRenderer renderer = new CustomRenderer();
                String Spray = new BwcAPI().getSelectedCosmetic(p, CosmeticsType.Sprays);
                ConfigManager config = ConfigUtils.getSprays();
                String Sprays = config.getString(CosmeticsType.Sprays.getSectionKey() + "." + Spray + ".url");
                String SpraysFile = config.getString(CosmeticsType.Sprays.getSectionKey() + "." + Spray + ".file");
                DebugUtil.addMessage("Playing "+ Spray + " Spray for " + p.getDisplayName());
                if(SpraysFile == null){
                    if (!renderer.load(Sprays)) {
                        p.sendMessage(ColorUtil.colored("&cLooks like there's an error rendering the Spray, contact the admin!"));
                        Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not load the URL for the " + Spray + " Check if the URL in Sprays.yml is valid!");
                    }else {
                        view.addRenderer(renderer);
                        final ItemStack map = XMaterial.FILLED_MAP.parseItem();
                        map.setDurability(view.getId());
                        map.setAmount(1);
                        f.setItem(map);
                        f.setRotation(Rotation.NONE);
                        Sound sound = GSound.ENTITY_SILVERFISH_HURT.parseSound();
                        p.playSound(f.getLocation(), sound, 1f, 1f);
                        HCore.playParticle(f.getLocation(), new Particle(ParticleType.FIREWORKS_SPARK, 4, f.getLocation().toVector()));
                        for (final Entity en : f.getNearbyEntities(1.0, 1.0, 1.0)) {
                            if (en.getType() == EntityType.ARMOR_STAND && en.hasMetadata("HOLO_ITEM_FRAME")) {
                                en.remove();
                            }
                        }
                    }
                }else{
                    Sprays = config.getString(CosmeticsType.Sprays.getSectionKey() + "." + Spray + ".file");
                    File file = new File(System.getProperty("user.dir") + "/plugins/BW1058-Cosmetics/" + Utility.plugin().getConfig().getString("Spray-Dir") + "/" + Sprays);
                    if (!renderer.load(file)) {
                        p.sendMessage(ColorUtil.colored("&cLooks like there's an error rendering the Spray, contact the admin!"));
                        Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not load the File for the " + Spray + " Check if the File in Sprays.yml is valid!");
                    }else {
                        view.addRenderer(renderer);
                        final ItemStack map = XMaterial.FILLED_MAP.parseItem();
                        map.setDurability(view.getId());
                        map.setAmount(1);
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
        });

    }
}


