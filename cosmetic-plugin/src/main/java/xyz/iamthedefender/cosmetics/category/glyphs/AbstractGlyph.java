package xyz.iamthedefender.cosmetics.category.glyphs;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.Glyph;
import xyz.iamthedefender.cosmetics.api.event.AbstractListener;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.HashMap;

public class AbstractGlyph extends AbstractListener {

    public static HashMap<Location, Integer> glyphs = new HashMap<>();

    public void execute(Player player, Location location) {
        if (!StartupUtils.isFeatureEnabled("glyphs")) return;

        Glyph glyph = getSelectedCosmetic(player, CosmeticType.GLYPHS);
        if (glyph == null || glyph.getField(FieldsType.RARITY, player) == RarityType.NONE) return;

        DebugUtil.addMessage("Executing " + glyph.getIdentifier() + " Glyph for " + player.getDisplayName());
        glyph.execute(player, location);
    }
}
