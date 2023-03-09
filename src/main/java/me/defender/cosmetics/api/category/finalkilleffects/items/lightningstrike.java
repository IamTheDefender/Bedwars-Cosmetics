package me.defender.cosmetics.api.category.finalkilleffects.items;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.category.finalkilleffects.FinalKillEffect;
import me.defender.cosmetics.api.enums.RarityType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class lightningstrike extends FinalKillEffect {
    @Override
    public ItemStack getItem() {
        return XMaterial.STRING.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "lightning-strike";
    }

    @Override
    public String getDisplayName() {
        return "Lightning Strike";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns a thunder strike at", "&7location of victim!");
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
        Location loc = victim.getLocation();
        loc.getWorld().strikeLightningEffect(loc);
    }
}
