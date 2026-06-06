package xyz.iamthedefender.cosmetics.category.islandtoppers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.IslandTopper;
import xyz.iamthedefender.cosmetics.api.event.AbstractListener;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

public class AbstractIslandTopper extends AbstractListener {

    public boolean enabled() {
        return StartupUtils.isFeatureEnabled("island-toppers");
    }

    public void execute(Player player, Location location) {
        IslandTopper islandTopper = getSelectedCosmetic(player, CosmeticType.ISLAND_TOPPERS);
        if (islandTopper == null || islandTopper.getField(FieldsType.RARITY, player) == RarityType.NONE) return;

        islandTopper.execute(player, location, islandTopper.getIdentifier());
    }
}
