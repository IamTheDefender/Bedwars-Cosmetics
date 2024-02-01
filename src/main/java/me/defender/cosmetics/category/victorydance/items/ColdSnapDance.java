package me.defender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.api.cosmetics.category.VictoryDance;
import me.defender.cosmetics.category.shopkeeperskins.ShopKeeperHandler1058;
import me.defender.cosmetics.category.victorydance.util.UsefulUtilsVD;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ColdSnapDance extends VictoryDance {
    @Override
    public ItemStack getItem() {
        return XMaterial.PACKED_ICE.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "cold-snap";
    }

    @Override
    public String getDisplayName() {
        return "Cold Snap";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Chill an area around you to", "&7absolute zero.");
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
                final Location loc = UsefulUtilsVD.getRandomLocation(winner.getLocation(), 3);
                if(ShopKeeperHandler1058.arenas.containsKey(winner.getWorld().getName())) {
                    if (loc.getBlock().getType() != Material.AIR) {
                        loc.getBlock().setType(Material.ICE);
                        winner.playSound(winner.getLocation(), XSound.BLOCK_NOTE_BLOCK_PLING.parseSound(), 1.0f, 1.0f);
                    }
                }else{
                    this.cancel();
                }
            }
        }.runTaskTimer(Cosmetics.getInstance(), 0L, 0L);
    }
}
