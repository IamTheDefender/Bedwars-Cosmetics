package me.defender.cosmetics.api.category.victorydances.items;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.category.victorydances.VictoryDance;
import me.defender.cosmetics.api.category.shopkeeperskins.ShopKeeperHandler;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class NightShiftDance extends VictoryDance {
    @Override
    public ItemStack getItem() {
        return XMaterial.CLOCK.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "night-shift";
    }

    @Override
    public String getDisplayName() {
        return "Night Shift";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Set it to day, set it to night,", "&7repeat, like a god");
    }

    @Override
    public int getPrice() {
        return 10000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.RARE;
    }

    @Override
    public void execute(Player winner) {
        if (ShopKeeperHandler.arenas.containsKey(winner.getWorld().getName())) {
            new BukkitRunnable() {
                long time = winner.getWorld().getTime();
                public void run() {
                    time += 1000;
                    if (time > 24000) {
                        time = 0;
                    }
                    winner.getWorld().setTime(time);
                }
            }.runTaskTimer(Utility.plugin(), 0L, 8L);
        } else {
            winner.getWorld().setTime(winner.getWorld().getTime());
        }
    }
}
