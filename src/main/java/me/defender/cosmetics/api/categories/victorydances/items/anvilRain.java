package me.defender.cosmetics.api.categories.victorydances.items;

import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.HCore;
import me.defender.cosmetics.api.categories.victorydances.VictoryDance;
import me.defender.cosmetics.api.categories.shopkeeperskins.ShopKeeperHandler;
import me.defender.cosmetics.api.categories.victorydances.util.UsefulUtilsVD;
import me.defender.cosmetics.api.enums.RarityType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class anvilRain extends VictoryDance {
    @Override
    public ItemStack getItem() {
        return XMaterial.ANVIL.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "anvil-rain";
    }

    @Override
    public String getDisplayName() {
        return "Anvil Rain";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Anvils will rain from sky.");
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
        HCore.syncScheduler().every(1L).run((runnable) -> {
            if (ShopKeeperHandler.arenas.containsKey(winner.getWorld().getName())) {
                final Location loc = UsefulUtilsVD.getRandomLocation(winner.getLocation(), 20);
                final FallingBlock anvil = winner.getWorld().spawnFallingBlock(loc, Material.ANVIL, (byte)0);
                anvil.setHurtEntities(false);
                anvil.setDropItem(false);
            } else {
                runnable.cancel();
            }
        });
    }
}
