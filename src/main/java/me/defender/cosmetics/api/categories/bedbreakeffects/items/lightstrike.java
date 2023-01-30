package me.defender.cosmetics.api.categories.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.categories.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.enums.RarityType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class lightstrike extends BedDestroy {
    @Override
    public ItemStack getItem() {
        return XMaterial.WHITE_STAINED_GLASS.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "light-strike";
    }

    @Override
    public String getDisplayName() {
        return "Lightning Strike";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns a lightning strike at", "&7the location of bed!");
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
        player.getWorld().strikeLightningEffect(bedLocation);
    }
}
