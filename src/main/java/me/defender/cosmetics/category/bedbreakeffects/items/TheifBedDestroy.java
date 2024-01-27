package me.defender.cosmetics.category.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.HCore;
import me.defender.cosmetics.api.cosmetics.category.BedDestroy;
import me.defender.cosmetics.api.cosmetics.RarityType;
import org.bukkit.Location;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.List;
/**
 * Bed destroy effect.
 * Spawns a enderman that steal the bed!
 */
public class TheifBedDestroy extends BedDestroy {
    /** {@inheritDoc} */
    @Override
    public ItemStack getItem() {
        return XMaterial.ENDER_PEARL.parseItem();
    }
    /** {@inheritDoc} */
    @Override
    public String base64() {
        return null;
    }
    /** {@inheritDoc} */
    @Override
    public String getIdentifier() {
        return "theif";
    }
    /** {@inheritDoc} */
    @Override
    public String getDisplayName() {
        return "Theif";
    }
    /** {@inheritDoc} */
    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns a enderman that", "&7steal the bed!");
    }
    /** {@inheritDoc} */
    @Override
    public int getPrice() {
        return 10000;
    }
    /** {@inheritDoc} */
    @Override
    public RarityType getRarity() {
        return RarityType.RARE;
    }
    /** {@inheritDoc} */
    @Override
    public void execute1058(Player player, Location bedLocation, ITeam victimTeam) {
        Enderman enderman = (Enderman) player.getWorld().spawnEntity(bedLocation, EntityType.ENDERMAN);
        assert XMaterial.RED_BED.parseMaterial() != null;
        enderman.setCarriedMaterial(new MaterialData(XMaterial.RED_BED.parseMaterial()));
        HCore.syncScheduler().after(70L).run(enderman::remove);
    }

    @Override
    public void execute2023(Player player, Location bedLocation, com.tomkeuper.bedwars.api.arena.team.ITeam victimTeam) {
        Enderman enderman = (Enderman) player.getWorld().spawnEntity(bedLocation, EntityType.ENDERMAN);
        assert XMaterial.RED_BED.parseMaterial() != null;
        enderman.setCarriedMaterial(new MaterialData(XMaterial.RED_BED.parseMaterial()));
        HCore.syncScheduler().after(70L).run(enderman::remove);
    }
}
