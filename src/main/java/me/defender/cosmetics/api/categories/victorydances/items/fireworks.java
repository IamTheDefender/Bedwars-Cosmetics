package me.defender.cosmetics.api.categories.victorydances.items;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.categories.victorydances.VictoryDance;
import me.defender.cosmetics.api.categories.victorydances.util.UsefulUtilsVD;
import me.defender.cosmetics.api.categories.shopkeeperskins.ShopKeeperHandler;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.utils.Utility;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class fireworks extends VictoryDance {
    @Override
    public ItemStack getItem() {
        return XMaterial.FIREWORK_ROCKET.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "fireworks";
    }

    @Override
    public String getDisplayName() {
        return "Fireworks";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Celebrate with a splendid", "&7fireworks show!");
    }

    @Override
    public int getPrice() {
        return 5000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.COMMON;
    }

    @Override
    public void execute(Player winner) {
        new BukkitRunnable() {
            public void run() {
                if (ShopKeeperHandler.arenas.containsKey(winner.getWorld().getName())) {
                    final Location loc = winner.getEyeLocation();
                    UsefulUtilsVD.spawnFireWorks(winner, 1, Color.RED, Color.BLUE, loc);
                    new BukkitRunnable() {
                        public void run() {
                            UsefulUtilsVD.spawnFireWorks(winner, 1, Color.ORANGE, Color.YELLOW, loc);
                        }
                    }.runTaskLater(Utility.plugin(), 300L);
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Utility.plugin(), 0L, 60L);
    }
}
