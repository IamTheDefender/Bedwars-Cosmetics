

package me.defender.cosmetics.api.categories.killmessage;

import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.categories.killmessage.util.KillMessageUtils;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.utils.DebugUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class KillMessageHandler implements Listener
{
    @EventHandler
    public void onPlayerKillByOtherPlayer(PlayerKillEvent e) {
        if (e.getKiller() == null) return;
        if (e.getVictim() == null) return;

        BwcAPI api = new BwcAPI();

        // Death Cries disabling sound stuff
        if (!api.getSelectedCosmetic(e.getVictim(), CosmeticsType.DeathCries).equals("NONE")) {
            e.setPlaySound(false);
        }

        ChatColor color2 = e.getArena().getTeam(e.getKiller()).getColor().chat();
        ChatColor color3 = e.getArena().getTeam(e.getVictim()).getColor().chat();

        // KILL MESSAGES!
        if(new BwcAPI().getSelectedCosmetic(e.getKiller(), CosmeticsType.KillMessage).equals("Default")){
            return;
        }

        DebugUtil.addMessage("Playing Kill message for " + e.getKiller());

        // Get the final kill flag based on the kill cause
        boolean isFinalKill = e.getCause().isFinalKill();

        // Get the players in the arena
        List<Player> players = e.getArena().getWorld().getPlayers();
        String selected = new BwcAPI().getSelectedCosmetic(e.getKiller(), CosmeticsType.KillMessage);

        // Send the appropriate kill message to all players in the arena
        switch (e.getCause()) {
            case PVP:
                if(KillMessageUtils.exists(selected, "PvP")) {
                    e.setMessage(player -> "");
                    for (Player p : players) {
                        KillMessageUtils.sendKillMessage(p, e.getVictim().getDisplayName(),
                                e.getKiller(), isFinalKill, color3, color2, "PvP", null, false, null, null);
                    }
                }
                break;
            case EXPLOSION:
            case EXPLOSION_FINAL_KILL:
                if(KillMessageUtils.exists(selected, "PvP")) {
                    e.setMessage(player -> "");
                    for (Player p : players) {
                        KillMessageUtils.sendKillMessage(p, e.getVictim().getDisplayName(),
                                e.getKiller(), isFinalKill, color3, color2, "Explosion", "", false, null, null);
                    }
                }
                break;
            case PLAYER_SHOOT:
            case PLAYER_SHOOT_FINAL_KILL:
                if(KillMessageUtils.exists(selected, "PvP")) {
                    e.setMessage(player -> "");
                    for (Player p : players) {
                        KillMessageUtils.sendKillMessage(p, e.getVictim().getDisplayName(),
                                e.getKiller(), isFinalKill, color3, color2, "Shoot", "", false, null, null);
                    }
                }
                break;
            case VOID:
            case VOID_FINAL_KILL:
                if(KillMessageUtils.exists(selected, "PvP")) {
                    e.setMessage(player -> "");
                    for (Player p : players) {
                        KillMessageUtils.sendKillMessage(p, e.getVictim().getDisplayName(),
                                e.getKiller(), isFinalKill, color3, color2, "Void", "", false, null, null);
                    }
                }
                break;
        }




    }
}