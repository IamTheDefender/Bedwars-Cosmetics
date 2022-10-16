// 
// @author IamTheDefender
// 

package me.defender.cosmetics.Glyphs;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.andrei1058.bedwars.api.events.player.PlayerGeneratorCollectEvent;

import io.netty.util.internal.ThreadLocalRandom;
import me.defender.api.utils.util;
import me.defender.cosmetics.VictoryDances.UsefulUtilsVD;

public class Glyphs implements Listener {
	HashMap<Location, Boolean> isRunning = new HashMap<>();

	public static HashMap<Location, Integer> glyphs = new HashMap<Location, Integer>();

	@EventHandler
	public void onGenCollect(PlayerGeneratorCollectEvent e) {
		if (e.getItemStack().getType() == Material.DIAMOND || e.getItemStack().getType() == Material.EMERALD) {
			int random = ThreadLocalRandom.current().nextInt();
			if (util.plugin().playerData.getConfig().getString("Gen." + random) == "YES")
				return;
			String selected = util.plugin().playerData.getConfig().getString(util.getUUID(e.getPlayer()) + ".Glyphs");

			final String path = util.plugin().glyconf.getConfig().getString("Glyphs." + selected + ".File");

			if (!selected.equals("None")) {
				for (final Block loc : UsefulUtilsVD.getBlocksInRadius(e.getPlayer().getLocation(), 2, false)) {
					if (loc.getType() == Material.DIAMOND_BLOCK || loc.getType() == Material.EMERALD_BLOCK) {

						if(isRunning.get(loc.getLocation()) != null){
							if(isRunning.get(loc.getLocation()))
								return;
						}

						new BukkitRunnable(){
							int i = 0;
							@Override
							public void run() {
								if(i >= 50){
									isRunning.remove(loc.getLocation());
									this.cancel();
									return;
								}
								i++;
								isRunning.put(loc.getLocation(), true);
								Util.sendglyphs(e.getPlayer(), new File(
                                                System.getProperty("user.dir") +
														"/plugins/" + "Bedwars1058-Cosmetics/" + "Glyphs/" + path),
										loc.getLocation().add(0.0, 8.0, 0.9).subtract(2.0, 0.0, 0.0), loc.getLocation());
							}
						}.runTaskTimer(util.plugin(), 20L, 20L);

					}
					}
				}
			}
		}
	}


