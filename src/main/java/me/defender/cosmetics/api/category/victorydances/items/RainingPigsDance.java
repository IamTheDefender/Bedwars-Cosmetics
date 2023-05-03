package me.defender.cosmetics.api.category.victorydances.items;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.category.victorydances.VictoryDance;
import me.defender.cosmetics.api.category.shopkeeperskins.ShopKeeperHandler;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.Utility;
import me.defender.cosmetics.api.category.victorydances.util.UsefulUtilsVD;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class RainingPigsDance extends VictoryDance {
    @Override
    public ItemStack getItem() {
        return XMaterial.CARROT_ON_A_STICK.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "raining-pigs";
    }

    @Override
    public String getDisplayName() {
        return "Raining Pigs";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7The sky will ran with pigs.");
    }

    @Override
    public int getPrice() {
        return 30000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.RARE;
    }

    @Override
    public void execute(Player winner) {
        new BukkitRunnable() {
            public void run() {
                if (ShopKeeperHandler.arenas.containsKey(winner.getWorld().getName())) {
                    final Location loc = UsefulUtilsVD.getRandomLocation(winner.getLocation(), 20);
                    final Pig pig = (Pig) winner.getWorld().spawnEntity(loc, EntityType.PIG);
                    pig.setSaddle(true);
                    pig.setNoDamageTicks(Integer.MAX_VALUE);
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Utility.plugin(), 1L, 1L);
    }
}
