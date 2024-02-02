package me.defender.cosmetics.category.bedbreakeffects;

import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.cosmetics.CosmeticsType;
import me.defender.cosmetics.api.cosmetics.FieldsType;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.api.cosmetics.category.BedDestroy;
import me.defender.cosmetics.api.event.BedBreakEffectExecuteEvent;
import me.defender.cosmetics.util.DebugUtil;
import me.defender.cosmetics.util.StartupUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class BedDestroyHandler1058 implements Listener{
	
	@EventHandler
	public void onBedBreak1058(PlayerBedBreakEvent e) {
		String selected = Cosmetics.getInstance().getApi().getSelectedCosmetic(e.getPlayer(), CosmeticsType.BedBreakEffects);
		BedBreakEffectExecuteEvent event = new BedBreakEffectExecuteEvent(e.getPlayer());
		Bukkit.getServer().getPluginManager().callEvent(event);

		if(event.isCancelled()) return;
		boolean isBedDestroysEnabled = Cosmetics.getInstance().getConfig().getBoolean("bed-break-effects.enabled");
		if (!isBedDestroysEnabled) return;

		DebugUtil.addMessage("Executing " + selected + " Bed Break Effect for " + e.getPlayer().getDisplayName());
		Player p = e.getPlayer();
		Location loc = e.getVictimTeam().getBed();

		for(BedDestroy bedDestroy : StartupUtils.bedDestroyList){
			if(selected.equals(bedDestroy.getIdentifier())){
				if(bedDestroy.getField(FieldsType.RARITY, p) != RarityType.NONE) {
					bedDestroy.execute1058(p, loc, e.getVictimTeam());
				}
			}
		}
	}
}