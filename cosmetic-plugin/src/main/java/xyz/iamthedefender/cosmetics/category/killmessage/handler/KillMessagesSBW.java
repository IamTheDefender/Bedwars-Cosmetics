package xyz.iamthedefender.cosmetics.category.killmessage.handler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.jetbrains.annotations.Nullable;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerDeathMessageSendEvent;
import org.screamingsandals.bedwars.game.TeamColor;
import xyz.iamthedefender.cosmetics.category.killmessage.AbstractKillMessage;

public class KillMessagesSBW extends AbstractKillMessage {

    @EventHandler
    public void onPlayerKillByOtherPlayer(BedwarsPlayerDeathMessageSendEvent e) {
        if (e.getVictim() == null) return;
        if (e.getVictim().getKiller() == null) return;

        Player victim = e.getVictim();
        Player killer = e.getVictim().getKiller();

        if (!enabled()) return;

        RunningTeam victimTeam = e.getGame().getTeamOfPlayer(victim);
        RunningTeam killerTeam = e.getGame().getTeamOfPlayer(killer);

        if (victimTeam == null || killerTeam == null) {
            return;
        }

        ChatColor color2 = TeamColor.valueOf(killerTeam.getColor().name()).chatColor;
        ChatColor color3 = TeamColor.valueOf(victimTeam.getColor().name()).chatColor;

        boolean isFinalKill = !victimTeam.isTargetBlockExists();
        String selected = getSelected(killer);

        if (selected == null) return;

        DamageCause cause = findLastCause(e.getVictim());

        if (cause == null) {
            return;
        }

        switch (cause) {
            case PVP:
                e.setMessage(getMessage(null, selected, e.getVictim(), killer, isFinalKill, color3, color2, DamageCause.PVP));
                break;
            case EXPLOSION:
                e.setMessage(getMessage(null, selected, e.getVictim(), killer, isFinalKill, color3, color2, DamageCause.EXPLOSION));
                break;
            case PLAYER_SHOOT:
                e.setMessage(getMessage(null, selected, e.getVictim(), killer, isFinalKill, color3, color2, DamageCause.PLAYER_SHOOT));
                break;
            case VOID:
                e.setMessage(getMessage(null, selected, e.getVictim(), killer, isFinalKill, color3, color2, DamageCause.VOID));
                break;
        }
    }

    private @Nullable DamageCause findLastCause(Player victim) {
        return victim.getLastDamageCause() != null ? translateCause(victim.getLastDamageCause().getCause()) : null;
    }

}

