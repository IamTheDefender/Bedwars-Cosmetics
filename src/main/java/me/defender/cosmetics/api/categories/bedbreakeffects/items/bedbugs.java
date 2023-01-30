package me.defender.cosmetics.api.categories.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.HCore;
import me.defender.cosmetics.api.categories.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.categories.victorydances.util.UsefulUtilsVD;
import org.bukkit.Location;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class bedbugs extends BedDestroy {
    @Override
    public ItemStack getItem() {
        return XMaterial.ENDERMITE_SPAWN_EGG.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "bed-bugs";
    }

    @Override
    public String getDisplayName() {
        return "Bed Bugs";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns many bed bugs near", "&7the bed location!");
    }

    @Override
    public int getPrice() {
        return 10000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.RARE;
    }

    @Override
    public void execute(Player player, Location bedLocation, ITeam victimTeam) {
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
