

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
import me.defender.cosmetics.api.category.shopkeeperskins.ShopKeeperHandler;
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

    /**
     * Spawn a spray for a player in an item frame.
     *
     * @param player The player for whom to spawn the spray.
     * @param itemFrame The item frame in which to display the spray.
     */
    public static void spawnSprays(Player player, ItemFrame itemFrame) {
        HCore.syncScheduler().run(() -> {
            MapView view = Bukkit.createMap(player.getWorld());
            String spray = new BwcAPI().getSelectedCosmetic(player, CosmeticsType.Sprays);
            ConfigManager config = ConfigUtils.getSprays();

            if (SpraysUtil.cooldown.containsKey(player.getName())) {
                // Player is in cooldown
                long cooldownEndTime = SpraysUtil.cooldown.get(player.getName());
                if (cooldownEndTime > System.currentTimeMillis()) {
                    player.playSound(player.getLocation(), XSound.ENTITY_VILLAGER_NO.parseSound(), 1.0f, 1.0f);
                    player.sendMessage(ColorUtil.colored(Utility.getMSGLang(player, "cosmetics.spray-msg")));
                    return;
                }
                // Player is no longer in cooldown
                SpraysUtil.cooldown.remove(player.getName());
            } else if (ShopKeeperHandler.arenas.containsKey(player.getWorld().getName())) {
                // Player is not in cooldown or it's the first time
                SpraysUtil.cooldown.put(player.getName(), System.currentTimeMillis() + 3000L);
                view.removeRenderer(view.getRenderers().get(0));
                final CustomRenderer renderer = new CustomRenderer();

                String sprayUrl = config.getString(CosmeticsType.Sprays.getSectionKey() + "." + spray + ".url");
                String sprayFile = config.getString(CosmeticsType.Sprays.getSectionKey() + "." + spray + ".file");

                DebugUtil.addMessage("Playing " + spray + " Spray for " + player.getDisplayName());

                if (sprayFile == null) {
                    if (!renderer.load(sprayUrl)) {
                        player.sendMessage(ColorUtil.colored("&cLooks like there's an error rendering the Spray, contact the admin!"));
                        Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not load the URL for the " + spray + ". Check if the URL in Sprays.yml is valid!");
                    } else {
                        addRendererAndShowSpray(player, itemFrame, renderer, view);
                    }
                } else {
                    sprayFile = config.getString(CosmeticsType.Sprays.getSectionKey() + "." + spray + ".file");
                    File file = new File(System.getProperty("user.dir") + "/plugins/BW1058-Cosmetics/" + Utility.plugin().getConfig().getString("Spray-Dir") + "/" + sprayFile);
                    if (!renderer.load(file)) {
                        player.sendMessage(ColorUtil.colored("&cLooks like there's an error rendering the Spray, contact the admin!"));
                        Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not load the File for the " + spray + ". Check if the File in Sprays.yml is valid!");
                    } else {
                        addRendererAndShowSpray(player, itemFrame, renderer, view);
                    }
                }
            }
        });
    }

    /**
     * Add the renderer to the map view and display the spray.
     *
     * @param player The player for whom to display the spray.
     * @param itemFrame The item frame in which to display the spray.
     * @param renderer The custom renderer to add.
     * @param view The map view to render.
     */
    private static void addRendererAndShowSpray(Player player, ItemFrame itemFrame, CustomRenderer renderer, MapView view) {
        view.addRenderer(renderer);
        final ItemStack map = XMaterial.FILLED_MAP.parseItem();
        map.setDurability(view.getId());
        map.setAmount(1);
        itemFrame.setItem(map);
        itemFrame.setRotation(Rotation.NONE);
        player.playEffect(itemFrame.getLocation(), Effect.FLYING_GLYPH, 10);
        for (final Entity entity : itemFrame.getNearbyEntities(1.0, 1.0, 1.0)) {
            if (entity.getType() == EntityType.ARMOR_STAND && entity.hasMetadata("HOLO_ITEM_FRAME")) {
                entity.remove();
            }
        }
    }

}


