package me.defender.cosmetics.api.category.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.HCore;
import me.defender.cosmetics.api.category.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.category.victorydances.util.UsefulUtilsVD;
import org.bukkit.Location;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bed destroy effect.
 * This effect spawns many bed bugs near the bed location.
 */
public class BedBugsBedDestroy extends BedDestroy {
    /** {@inheritDoc} */
    @Override
    public ItemStack getItem() {
        return XMaterial.ENDERMITE_SPAWN_EGG.parseItem();
    }
    /** {@inheritDoc} */
    @Override
    public String base64() {
        return null;
    }
    /** {@inheritDoc} */
    @Override
    public String getIdentifier() {
        return "bed-bugs";
    }
    /** {@inheritDoc} */
    @Override
    public String getDisplayName() {
        return "Bed Bugs";
    }
    /** {@inheritDoc} */
    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns many bed bugs near", "&7the bed location!");
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
        List<Endermite> endermites = new ArrayList<>();
        HCore.syncScheduler().every(1L).limit(4).run(() -> {
            Location loc = UsefulUtilsVD.getRandomLocation(bedLocation, 1);
            Endermite mite = (Endermite) player.getWorld().spawnEntity(loc.add(0,1,0), EntityType.ENDERMITE);
            endermites.add(mite);
        });

        HCore.syncScheduler().after(100L).run(() -> {
           endermites.forEach(Entity::remove);
        });
    }

    @Override
    public void execute2023(Player player, Location bedLocation, com.tomkeuper.bedwars.api.arena.team.ITeam victimTeam) {
        List<Endermite> endermites = new ArrayList<>();
        HCore.syncScheduler().every(1L).limit(4).run(() -> {
            Location loc = UsefulUtilsVD.getRandomLocation(bedLocation, 1);
            Endermite mite = (Endermite) player.getWorld().spawnEntity(loc.add(0,1,0), EntityType.ENDERMITE);
            endermites.add(mite);
        });

        HCore.syncScheduler().after(100L).run(() -> {
            endermites.forEach(Entity::remove);
        });
    }
}