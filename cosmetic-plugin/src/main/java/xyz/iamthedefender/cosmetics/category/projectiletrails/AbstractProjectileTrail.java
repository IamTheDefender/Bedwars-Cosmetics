package xyz.iamthedefender.cosmetics.category.projectiletrails;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import xyz.iamthedefender.cosmetics.api.event.AbstractListener;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.projectiletrails.util.ProjectileEffectsUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

public class AbstractProjectileTrail extends AbstractListener {

    public void execute(ProjectileLaunchEvent event, Plugin plugin) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        if (!StartupUtils.isFeatureEnabled("projectile-trails")) return;

        Player shooter = (Player) event.getEntity().getShooter();
        event.getEntity().setMetadata("shooter", new FixedMetadataValue(plugin, shooter.getName()));

        Run.every((r) -> {
            if (event.getEntity() == null || event.getEntity().isOnGround() || event.getEntity().isDead()) {
                r.cancel();
                return;
            }

            ProjectileEffectsUtil.sendEffect(event.getEntity(), shooter);
        }, 1L);
    }
}
