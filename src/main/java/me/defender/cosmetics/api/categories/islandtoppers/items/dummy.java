package me.defender.cosmetics.api.categories.islandtoppers.items;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.categories.islandtoppers.IslandTopper;
import me.defender.cosmetics.api.enums.RarityType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class dummy extends IslandTopper {
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
        return Arrays.asList("Island Toppers are DISABLED for some reason!");
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
