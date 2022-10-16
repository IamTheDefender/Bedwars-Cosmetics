package me.defender.cosmetics.ProjectileEffects;

import org.bukkit.Color;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import me.defender.api.utils.util;

public class ProjectileEffectsUtil {
	
	
	public static void sendEffect(Entity e, Player p) {
		String selected = util.plugin().playerData.getConfig().getString(util.getUUID(p) + ".ProjectileTrails");
		String effect = util.plugin().pedata.getConfig().getString("ProjectileTrails." + selected + ".Particle");
		Color color = util.plugin().pedata.getConfig().getColor("ProjectileTrails." + selected + ".Color");
		
		if(!selected.equals("None")) {
			FastParticle.spawnParticle(p, ParticleType.valueOf(effect), e.getLocation(), 2, color);
			
		}
	}

}
