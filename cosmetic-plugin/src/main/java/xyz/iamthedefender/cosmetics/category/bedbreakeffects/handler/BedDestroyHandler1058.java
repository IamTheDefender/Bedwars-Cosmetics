package xyz.iamthedefender.cosmetics.category.bedbreakeffects.handler;

import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticRegistry;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.BedDestroy;
import xyz.iamthedefender.cosmetics.api.event.BedBreakEffectExecuteEvent;
import xyz.iamthedefender.cosmetics.category.bedbreakeffects.AbstractBedDestroy;
import xyz.iamthedefender.cosmetics.util.BedWarsWrapper;
import xyz.iamthedefender.cosmetics.util.DebugUtil;


public class BedDestroyHandler1058 extends AbstractBedDestroy {
	
	@EventHandler
	public void onBedBreak1058(PlayerBedBreakEvent e) {
		Player player = e.getPlayer();
		Location location = e.getVictimTeam().getBed();

		execute(
				player,
				location,
				BedWarsWrapper.wrap(e.getVictimTeam())
		);
	}

}

