package me.defender.cosmetics.api.categories.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.categories.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.enums.RarityType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class none extends BedDestroy {

    @Override
    public ItemStack getItem() {
        assert XMaterial.BARRIER.parseMaterial() != null;
        return new ItemStack(XMaterial.BARRIER.parseMaterial());
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
            return List.of("&7Selecting this option disables your", "&7Bed Destroy");
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
    public void execute(Player player, Location bedLocation, ITeam victimTeam) {
    }
}
