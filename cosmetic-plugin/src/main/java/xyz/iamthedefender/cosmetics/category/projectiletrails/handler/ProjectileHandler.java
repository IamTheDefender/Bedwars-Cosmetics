package xyz.iamthedefender.cosmetics.category.projectiletrails.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;
import xyz.iamthedefender.cosmetics.category.projectiletrails.AbstractProjectileTrail;

public class ProjectileHandler extends AbstractProjectileTrail {

    private final Plugin plugin;

    public ProjectileHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        execute(event, plugin);
    }
}

