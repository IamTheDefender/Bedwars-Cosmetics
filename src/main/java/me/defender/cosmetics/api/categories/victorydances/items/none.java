package me.defender.cosmetics.api.categories.victorydances.items;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.categories.victorydances.VictoryDance;
import me.defender.cosmetics.api.enums.RarityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class none extends VictoryDance {
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
        return "none";
    }

    @Override
    public String getDisplayName() {
        return "NONE";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Selecting this option disables your", "&7Victory Dance.");
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
    public void execute(Player winner) {}
}
