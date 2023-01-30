package me.defender.cosmetics.api.categories.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.categories.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.categories.victorydances.util.UsefulUtilsVD;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class fireworks extends BedDestroy {
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
        return "fireworks";
    }

    @Override
    public String getDisplayName() {
        return "Fireworks";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns a rocket to celebrate", "&7your bed break!");
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
    public void execute(Player player, Location bedLocation, ITeam victimTeam) {
        UsefulUtilsVD.spawnFireWorks(player, 1, Color.RED, Color.GREEN, bedLocation);
    }
}
