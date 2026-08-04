package xyz.iamthedefender.cosmetics.category.bedbreakeffects.handler;

import de.marcely.bedwars.api.arena.Team;
import de.marcely.bedwars.api.event.arena.ArenaBedBreakEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.category.bedbreakeffects.AbstractBedDestroy;
import xyz.iamthedefender.cosmetics.util.BedWarsWrapper;

import java.util.Objects;

public class BedDestroyMBW extends AbstractBedDestroy {

    @EventHandler
    public void onBedDestroy(ArenaBedBreakEvent event) {
        Player player = event.getPlayer();

        if (event.getResult() != ArenaBedBreakEvent.Result.DONT_CANCEL) return;

        Team team = event.getTeam();

        execute(
                player,
                Objects.requireNonNull(event.getArena().getBedLocation(team)).toLocation(player.getWorld()),
                BedWarsWrapper.wrap(team, event.getArena())
        );
    }

}
