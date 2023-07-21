package me.defender.cosmetics.api.category.victorydances.items;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import me.defender.cosmetics.api.category.victorydances.VictoryDance;
import me.defender.cosmetics.api.category.victorydances.util.UsefulUtilsVD;
import me.defender.cosmetics.api.category.shopkeeperskins.ShopKeeperHandler;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.Utility;
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
                if(ShopKeeperHandler.arenas.containsKey(winner.getWorld().getName())) {
                    if (loc.getBlock().getType() != Material.AIR) {
                        loc.getBlock().setType(Material.ICE);
                        winner.playSound(winner.getLocation(), XSound.BLOCK_NOTE_BLOCK_PLING.parseSound(), 1.0f, 1.0f);
                    }
                }else{
                    this.cancel();
                }
            }
        }.runTaskTimer(Utility.plugin(), 0L, 0L);
    }
}
