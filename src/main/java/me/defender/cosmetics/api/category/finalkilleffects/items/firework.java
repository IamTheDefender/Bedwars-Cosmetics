package me.defender.cosmetics.api.category.finalkilleffects.items;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.category.finalkilleffects.FinalKillEffect;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.category.victorydances.util.UsefulUtilsVD;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class firework extends FinalKillEffect {
    @Override
    public ItemStack getItem() {
        return XMaterial.FIREWORK_ROCKET.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "firework";
    }

    @Override
    public String getDisplayName() {
        return "Firework";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns a firework at the", "&7location of victim!");
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
    public void execute(Player killer, Player victim) {
        UsefulUtilsVD.spawnFireWorks(victim, 1, Color.RED, Color.GREEN, victim.getLocation());
    }
}
