

package xyz.iamthedefender.cosmetics.category.glyphs.handler;

import com.andrei1058.bedwars.api.events.player.PlayerGeneratorCollectEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.category.glyphs.AbstractGlyph;
import xyz.iamthedefender.cosmetics.category.victorydance.util.UsefulUtilsVD;

public class GlyphHandler1058 extends AbstractGlyph {

	@EventHandler
	public void onGenCollect1058(PlayerGeneratorCollectEvent e) {

		if (e.getItemStack().getType() == Material.DIAMOND || e.getItemStack().getType() == Material.EMERALD) {
				for (Block loc : UsefulUtilsVD.getBlocksInRadius(e.getPlayer().getLocation(), 2, false)) {
					if (loc.getType() == Material.DIAMOND_BLOCK || loc.getType() == Material.EMERALD_BLOCK) {
						execute(e.getPlayer(), loc.getLocation().add(0,8,0).subtract(2,0,0));
					}
				}
		}
	}
}

