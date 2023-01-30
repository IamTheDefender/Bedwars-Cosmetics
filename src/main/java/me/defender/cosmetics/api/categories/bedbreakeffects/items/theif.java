package me.defender.cosmetics.api.categories.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.HCore;
import me.defender.cosmetics.api.categories.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.enums.RarityType;
import org.bukkit.Location;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.List;

public class theif extends BedDestroy {
    @Override
    public ItemStack getItem() {
        return XMaterial.ENDER_PEARL.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "theif";
    }

    @Override
    public String getDisplayName() {
        return "Theif";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns a enderman that", "&7steal the bed!");
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
        Enderman enderman = (Enderman) player.getWorld().spawnEntity(bedLocation, EntityType.ENDERMAN);
        assert XMaterial.RED_BED.parseMaterial() != null;
        enderman.setCarriedMaterial(new MaterialData(XMaterial.RED_BED.parseMaterial()));
        HCore.syncScheduler().after(70L).run(enderman::remove);
    }
}
