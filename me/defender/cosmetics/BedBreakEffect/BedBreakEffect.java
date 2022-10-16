package me.defender.cosmetics.BedBreakEffect;

import me.defender.api.BwcAPI;
import me.defender.api.enums.Cosmetics;
import me.defender.api.events.BedBreakEffectExecute;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;

public class BedBreakEffect implements Listener{
	
	@EventHandler
	public void onBedBreak(PlayerBedBreakEvent e) {
		String selected = new BwcAPI().getSelectedCosmetic(e.getPlayer(), Cosmetics.BedBreakEffects);
		BedBreakEffectExecute event = new BedBreakEffectExecute(e.getPlayer(), e.getVictimTeam());
		Bukkit.getServer().getPluginManager().callEvent(event);

		if(event.isCancelled())
			return;

		Player p = e.getPlayer();
		Location loc = e.getVictimTeam().getBed();
		switch (selected) {
		case "Tornado":
			BedBreakEffectUtils.playTornado(p, loc);
			break;
		case "Ghost":
			BedBreakEffectUtils.playGhost(p, loc);
			break;
		case "Bed-Bugs":
			BedBreakEffectUtils.bedbugs(p, loc);
			break;
		case "Hologram":
			BedBreakEffectUtils.hologram(p, e.getVictimTeam().getName(), loc);
			break;
		case "Squid-Missile":
			BedBreakEffectUtils.squidMissile(p, loc);
			break;
		case "Pig-Missile":
			BedBreakEffectUtils.pigMissile(p, loc);
			break;
		case "Fireworks":
			BedBreakEffectUtils.fireworks(p, loc);
			break;
		case "Lightning-Strike":
			BedBreakEffectUtils.lightingStrike(p, loc);
			break;
		case "Theif":
			BedBreakEffectUtils.theif(p, loc);
			break;
		}
	}

}
