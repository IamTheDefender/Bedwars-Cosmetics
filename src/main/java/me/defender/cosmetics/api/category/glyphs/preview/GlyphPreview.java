package me.defender.cosmetics.api.category.glyphs.preview;

import com.cryptomorin.xseries.XSound;
import com.hakan.core.ui.inventory.InventoryGui;
import me.defender.cosmetics.api.category.glyphs.Glyph;
import me.defender.cosmetics.api.category.shopkeeperskins.ShopKeeperSkin;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.StartupUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GlyphPreview {

    private final Map<UUID, Map<Integer, ItemStack>> inventories = new HashMap<>();

    public void sendPreviewGlyph(Player player, String selected, InventoryGui gui) {
        for (Glyph glyph : StartupUtils.glyphsList) {
            if (glyph.getIdentifier().equals(selected)){
                if (glyph.getField(FieldsType.RARITY, player) == RarityType.NONE) {
                    gui.open(player);
                    XSound.ENTITY_VILLAGER_NO.play(player, 1.0f, 1.0f);
                    return;
                }
            }
        }

        //TODO
    }
}
