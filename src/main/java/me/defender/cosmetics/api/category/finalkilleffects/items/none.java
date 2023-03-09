package me.defender.cosmetics.api.category.finalkilleffects.items;

import me.defender.cosmetics.api.category.finalkilleffects.FinalKillEffect;
import me.defender.cosmetics.api.enums.RarityType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class none extends FinalKillEffect {
    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.BARRIER);
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
            return List.of("&7Selecting this option disables your", "&7Final Kill Effect.");
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
    public void execute(Player killer, Player victim) {

    }
}
