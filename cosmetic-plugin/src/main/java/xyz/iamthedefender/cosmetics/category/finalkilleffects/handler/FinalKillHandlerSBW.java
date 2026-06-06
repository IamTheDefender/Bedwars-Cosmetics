package xyz.iamthedefender.cosmetics.category.finalkilleffects.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerKilledEvent;
import xyz.iamthedefender.cosmetics.category.finalkilleffects.AbstractFinalKillEffect;

public class FinalKillHandlerSBW extends AbstractFinalKillEffect {

    @EventHandler
    public void onFinalKill(BedwarsPlayerKilledEvent event) {
        Player victim = event.getPlayer();
        Player killer = event.getKiller();

        if (victim == null || killer == null)
            return;

        RunningTeam victimTeam = event.getGame().getTeamOfPlayer(victim);

        if (victimTeam == null)
            return;

        boolean isFinalKill = !victimTeam.isTargetBlockExists();

        if (!isFinalKill)
            return;

        execute(killer, victim, victim.getLocation(), false);
    }

}


