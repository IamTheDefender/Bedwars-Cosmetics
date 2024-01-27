package me.defender.cosmetics.category.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.cosmetics.category.BedDestroy;
import me.defender.cosmetics.api.cosmetics.RarityType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Bed destroy effect.
 * Spawn a lightning strike at the location of bed!
 */
public class LightStrikeBedDestroy extends BedDestroy {
    /** {@inheritDoc} */
    @Override
    public ItemStack getItem() {
        return XMaterial.WHITE_STAINED_GLASS.parseItem();
    }
    /** {@inheritDoc} */
    @Override
    public String base64() {
        return null;
    }
    /** {@inheritDoc} */
    @Override
    public String getIdentifier() {
        return "light-strike";
    }
    /** {@inheritDoc} */
    @Override
    public String getDisplayName() {
        return "Lightning Strike";
    }
    /** {@inheritDoc} */
    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns a lightning strike at", "&7the location of bed!");
    }
    /** {@inheritDoc} */
    @Override
    public int getPrice() {
        return 5000;
    }
    /** {@inheritDoc} */
    @Override
    public RarityType getRarity() {
        return RarityType.COMMON;
    }
    /** {@inheritDoc} */
    @Override
    public void execute1058(Player player, Location bedLocation, ITeam victimTeam) {
        player.getWorld().strikeLightningEffect(bedLocation);
    }

    @Override
    public void execute2023(Player player, Location bedLocation, com.tomkeuper.bedwars.api.arena.team.ITeam victimTeam) {
        player.getWorld().strikeLightningEffect(bedLocation);
    }
}
