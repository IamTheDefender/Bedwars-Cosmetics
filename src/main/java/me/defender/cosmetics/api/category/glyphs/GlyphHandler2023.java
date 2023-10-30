package me.defender.cosmetics.api.category.glyphs;

import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.category.victorydances.util.UsefulUtilsVD;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.DebugUtil;
import me.defender.cosmetics.api.util.StartupUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GlyphHandler2023 implements Listener {

    @EventHandler
    public void onGenCollect2023(com.tomkeuper.bedwars.api.events.player.PlayerGeneratorCollectEvent e) {
        if (e.getItemStack().getType() == Material.DIAMOND || e.getItemStack().getType() == Material.EMERALD) {
            String selected = new BwcAPI().getSelectedCosmetic(e.getPlayer(), CosmeticsType.Glyphs);

            for (Block loc : UsefulUtilsVD.getBlocksInRadius(e.getPlayer().getLocation(), 2, false)) {
                if (loc.getType() == Material.DIAMOND_BLOCK || loc.getType() == Material.EMERALD_BLOCK) {
                    DebugUtil.addMessage("Executing " + selected + " Glyph for " + e.getPlayer().getDisplayName());
                    for(Glyph glyphs1 : StartupUtils.glyphsList){
                        if(selected.equals(glyphs1.getIdentifier())){
                            if(glyphs1.getField(FieldsType.RARITY, e.getPlayer()) == RarityType.NONE) return;
                            glyphs1.execute(e.getPlayer(), loc.getLocation().add(0,8,0).subtract(2,0,0));
                        }
                    }
                }
            }
        }
    }
}
