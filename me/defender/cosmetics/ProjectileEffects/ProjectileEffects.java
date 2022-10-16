package me.defender.cosmetics.ProjectileEffects;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.defender.api.utils.util;

public class ProjectileEffects implements Listener{
	
	BukkitTask task;
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent e) {
		if(!(e.getEntity().getShooter() instanceof Player))
			return;
		if(e.isCancelled() == true)
			return;
		Player p = (Player) e.getEntity().getShooter();
		e.getEntity().setMetadata("PTOF." + p.getName(), new FixedMetadataValue(util.plugin(), ""));
		if(e.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getEntity();
			
			arrow.setCritical(false);
		}
		
		task = new BukkitRunnable() {
			
			@Override
			public void run() {
				if(e.getEntity().isOnGround()){
					this.cancel();
				}
				if(e.getEntity().isDead() ||e.getEntity() == null){
					this.cancel();
				}
				ProjectileEffectsUtil.sendEffect(e.getEntity(), p);
				
			}
		}.runTaskTimer(util.plugin(), 0, 1);
		
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if(!(e.getEntity().getShooter() instanceof Player))
			return;
		Player p = (Player) e.getEntity().getShooter();
		if(e.getEntity().hasMetadata("PTOF." + p.getName())) {
			task.cancel();
		}
	}

}
