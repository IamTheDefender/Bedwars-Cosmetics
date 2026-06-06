package xyz.iamthedefender.cosmetics.category.bedbreakeffects.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.events.BedwarsTargetBlockDestroyedEvent;
import xyz.iamthedefender.cosmetics.category.bedbreakeffects.AbstractBedDestroy;
import xyz.iamthedefender.cosmetics.util.BedWarsWrapper;

public class BedDestroySBW extends AbstractBedDestroy {

    @EventHandler
    public void onBedDestroy(BedwarsTargetBlockDestroyedEvent event) {
        Player player = event.getPlayer();

        RunningTeam team = event.getTeam();

        execute(
                player,
                team.getTargetBlock(),
                BedWarsWrapper.wrap(team)
        );
    }
}

