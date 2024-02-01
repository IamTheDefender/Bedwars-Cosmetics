package me.defender.cosmetics.category.islandtoppers.items;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.api.cosmetics.category.IslandTopper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DummyTopper extends IslandTopper {
    @Override
    public ItemStack getItem() {
        return XMaterial.BARRIER.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "disabled";
    }

    @Override
    public String getDisplayName() {
        return "DISABLED";
    }

    @Override
    public List<String> getLore() {
        return List.of("Island Toppers are DISABLED for some reason!");
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.NONE;
    }

    @Override
    public void execute(Player player, Location topperLocation, String selected) {
    }
}