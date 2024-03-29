package me.defender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.api.cosmetics.category.VictoryDance;
import me.defender.cosmetics.category.shopkeeperskins.ShopKeeperHandler1058;
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
        if (ShopKeeperHandler1058.arenas.containsKey(winner.getWorld().getName())) {
            new BukkitRunnable() {
                long time = winner.getWorld().getTime();
                public void run() {
                    time += 1000;
                    if (time > 24000) {
                        time = 0;
                    }
                    winner.getWorld().setTime(time);
                }
            }.runTaskTimer(Cosmetics.getInstance(), 0L, 8L);
        } else {
            winner.getWorld().setTime(winner.getWorld().getTime());
        }
    }
}
