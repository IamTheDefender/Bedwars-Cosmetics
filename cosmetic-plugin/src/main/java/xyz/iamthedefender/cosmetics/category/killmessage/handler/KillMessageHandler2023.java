package xyz.iamthedefender.cosmetics.category.killmessage.handler;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.category.killmessage.AbstractKillMessage;

public class KillMessageHandler2023 extends AbstractKillMessage {
    @EventHandler
    public void onPlayerKillByOtherPlayer2023(com.tomkeuper.bedwars.api.events.player.PlayerKillEvent e) {
        if (e.getKiller() == null) return;
        if (e.getVictim() == null) return;

        if (!enabled()) return;

        // Death Cries disabling sound stuff
        if (shouldDisableDeathCrySound(e.getVictim())) {
            e.setPlaySound(false);
        }

        ChatColor color2 = e.getArena().getTeam(e.getKiller()).getColor().chat();
        ChatColor color3 = e.getArena().getTeam(e.getVictim()).getColor().chat();

        boolean isFinalKill = e.getCause().isFinalKill();
        String selected = getSelected(e.getKiller());

        if (selected == null) return;

        // Send the appropriate kill message to all players in the arena
        switch (e.getCause()) {
            case PVP:
                e.setMessage(player -> getMessage(player, selected, e.getVictim(), e.getKiller(), isFinalKill, color3, color2, DamageCause.PVP));
                break;
            case EXPLOSION:
            case EXPLOSION_FINAL_KILL:
                e.setMessage(player -> getMessage(player, selected, e.getVictim(), e.getKiller(), isFinalKill, color3, color2, DamageCause.EXPLOSION));
                break;
            case PLAYER_SHOOT:
            case PLAYER_SHOOT_FINAL_KILL:
                e.setMessage(player -> getMessage(player, selected, e.getVictim(), e.getKiller(), isFinalKill, color3, color2, DamageCause.PLAYER_SHOOT));
                break;
            case VOID:
            case VOID_FINAL_KILL:
                e.setMessage(player -> getMessage(player, selected, e.getVictim(), e.getKiller(), isFinalKill, color3, color2, DamageCause.VOID));
                break;
        }
    }
}

