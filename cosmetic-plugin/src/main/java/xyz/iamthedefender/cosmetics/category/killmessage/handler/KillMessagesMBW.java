package xyz.iamthedefender.cosmetics.category.killmessage.handler;

import de.marcely.bedwars.api.arena.Team;
import de.marcely.bedwars.api.event.player.PlayerKillPlayerEvent;
import de.marcely.bedwars.api.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.jetbrains.annotations.Nullable;
import xyz.iamthedefender.cosmetics.category.killmessage.AbstractKillMessage;

public class KillMessagesMBW extends AbstractKillMessage {

    @EventHandler
    public void onPlayerKillByOtherPlayer(PlayerKillPlayerEvent e) {
        if (e.getDamaged() == null) return;
        if (e.getKiller() == null) return;

        Player victim = e.getDamaged();
        Player killer = e.getKiller();

        if (!enabled()) return;

        Team victimTeam = e.getArena().getPlayerTeam(victim);
        Team killerTeam = e.getArena().getPlayerTeam(killer);

        if (victimTeam == null || killerTeam == null) {
            return;
        }

        ChatColor color2 = ChatColor.valueOf(killerTeam.getBungeeChatColor().name());
        ChatColor color3 = ChatColor.valueOf(victimTeam.getBungeeChatColor().name());

        boolean isFinalKill = e.getArena().isBedDestroyed(victimTeam);
        String selected = getSelected(killer);

        if (selected == null) return;

        DamageCause cause = findLastCause(victim);

        if (cause == null) {
            return;
        }

        switch (cause) {
            case PVP:
                e.setDeathMessage(Message.build(getMessage(null, selected, victim, killer, isFinalKill, color3, color2, DamageCause.PVP)));
                break;
            case EXPLOSION:
                e.setDeathMessage(Message.build(getMessage(null, selected, victim, killer, isFinalKill, color3, color2, DamageCause.EXPLOSION)));
                break;
            case PLAYER_SHOOT:
                e.setDeathMessage(Message.build(getMessage(null, selected, victim, killer, isFinalKill, color3, color2, DamageCause.PLAYER_SHOOT)));
                break;
            case VOID:
                e.setDeathMessage(Message.build(getMessage(null, selected, victim, killer, isFinalKill, color3, color2, DamageCause.VOID)));
                break;
        }
    }

    private @Nullable DamageCause findLastCause(Player victim) {
        return victim.getLastDamageCause() != null ? translateCause(victim.getLastDamageCause().getCause()) : null;
    }
}
