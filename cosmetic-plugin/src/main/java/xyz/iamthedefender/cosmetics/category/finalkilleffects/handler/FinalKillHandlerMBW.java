package xyz.iamthedefender.cosmetics.category.finalkilleffects.handler;

import de.marcely.bedwars.api.arena.Team;
import de.marcely.bedwars.api.event.player.PlayerKillPlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.category.finalkilleffects.AbstractFinalKillEffect;

public class FinalKillHandlerMBW extends AbstractFinalKillEffect {

    @EventHandler
    public void onFinalKill(PlayerKillPlayerEvent event) {
        Player victim = event.getDamaged();
        Player killer = event.getKiller();

        if (victim == null || killer == null)
            return;

        Team victimTeam = event.getArena().getPlayerTeam(victim);

        if (victimTeam == null)
            return;

        boolean isFinalKill = event.getArena().isBedDestroyed(victimTeam);

        if (!isFinalKill)
            return;

        execute(killer, victim, victim.getLocation(), false);
    }
}
