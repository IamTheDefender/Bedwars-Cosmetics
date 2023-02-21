package me.defender.cosmetics.api.categories.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.categories.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.enums.RarityType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Bed destroy effect.
 * Disable your bed destroy effect.
 */
public class none extends BedDestroy {

    /** {@inheritDoc} */
    @Override
    public ItemStack getItem() {
        assert XMaterial.BARRIER.parseMaterial() != null;
        return new ItemStack(XMaterial.BARRIER.parseMaterial());
    }
    /** {@inheritDoc} */
    @Override
    public String base64() {
        return null;
    }
    /** {@inheritDoc} */
    @Override
    public String getIdentifier() {
        return "none";
    }
    /** {@inheritDoc} */
    @Override
    public String getDisplayName() {
        return "NONE";
    }
    /** {@inheritDoc} */
    @Override
    public List<String> getLore() {
            return List.of("&7Selecting this option disables your", "&7Bed Destroy");
    }
    /** {@inheritDoc} */
    @Override
    public int getPrice() {
        return 0;
    }
    /** {@inheritDoc} */
    @Override
    public RarityType getRarity() {
        return RarityType.NONE;
    }
    /** {@inheritDoc} */
    @Override
    public void execute(Player player, Location bedLocation, ITeam victimTeam) {
    }
}
