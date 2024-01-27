package me.defender.cosmetics.category.projectiletrails;

import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.category.projectiletrails.util.ProjectileEffectsUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static me.defender.cosmetics.util.Utility.plugin;

public class ProjectileHandler implements Listener {

    private final Plugin plugin;

    public ProjectileHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }

        boolean isProjectileTrailsEnabled = Cosmetics.getInstance().getConfig().getBoolean("projectile-trails.enabled");
        if (!isProjectileTrailsEnabled) return;

        Player shooter = (Player) event.getEntity().getShooter();
        event.getEntity().setMetadata("shooter", new FixedMetadataValue(plugin, shooter.getName()));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (event.getEntity().isOnGround() || event.getEntity().isDead() || event.getEntity() == null) {
                    cancel();
                }
                ProjectileEffectsUtil.sendEffect(event.getEntity(), shooter);
            }
        }.runTaskTimer(plugin, 0, 1);
    }
}
