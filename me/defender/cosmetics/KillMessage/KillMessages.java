// 
// @author IamTheDefender
// 

package me.defender.cosmetics.KillMessage;

import me.defender.api.BwcAPI;
import me.defender.api.enums.Cosmetics;
import org.bukkit.event.EventHandler;
import org.bukkit.ChatColor;
import java.util.function.Function;
import org.bukkit.entity.Player;
import me.defender.api.utils.util;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import org.bukkit.event.Listener;

public class KillMessages implements Listener
{
    @EventHandler
    public void onPlayerKillByOtherPlayer(PlayerKillEvent e) {
        if (e.getKiller() == null) {
            return;
        }
        if (e.getVictim() == null) {
            return;
        }
        BwcAPI api = new BwcAPI();

        // Death Cries disabling sound stuff
        if (!api.getSelectedCosmetic(e.getVictim(), Cosmetics.DeathCries).equals("None")) {
            e.setPlaySound(false);
        }

        Function<Player, String> func = a -> "";
        ChatColor color2 = e.getArena().getTeam(e.getKiller()).getColor().chat();
        ChatColor color3 = e.getArena().getTeam(e.getVictim()).getColor().chat();

        // KILL MESSAGES!
        if(new BwcAPI().getSelectedCosmetic(e.getKiller(), Cosmetics.KillMessage).equals("Default")){
            return;
        }

            // Normal void kill
            if(e.getCause().equals(PlayerKillEvent.PlayerKillCause.VOID)){
                e.setMessage(func);
                for(Player p : e.getArena().getPlayers()){
                    SendKillMessages.sendvoidkm(p, e.getVictim().getDisplayName(), e.getKiller().getDisplayName(), false, color3, color2);
                }
                return;
            }

            // Final void kill
            if(e.getCause().equals(PlayerKillEvent.PlayerKillCause.VOID_FINAL_KILL)){
                e.setMessage(func);
                for(Player p : e.getArena().getPlayers()){
                    SendKillMessages.sendvoidkm(p, e.getVictim().getDisplayName(), e.getKiller().getDisplayName(), true, color3, color2);
                }
                return;
            }

            // Normal Shoot kill
            if(e.getCause().equals(PlayerKillEvent.PlayerKillCause.PLAYER_SHOOT)){
                e.setMessage(func);
                for(Player p : e.getArena().getPlayers()){
                    SendKillMessages.sendshootkm(p, e.getVictim().getDisplayName(), e.getKiller().getDisplayName(), false, color3, color2);
                }
                return;
            }

            // Final Shoot kill
            if(e.getCause().equals(PlayerKillEvent.PlayerKillCause.PLAYER_SHOOT_FINAL_KILL)){
                e.setMessage(func);
                for(Player p : e.getArena().getPlayers()){
                    SendKillMessages.sendshootkm(p, e.getVictim().getDisplayName(), e.getKiller().getDisplayName(), true, color3, color2);
                }
                return;
            }

            // Normal Explosion kill
            if(e.getCause().equals(PlayerKillEvent.PlayerKillCause.EXPLOSION)){
                e.setMessage(func);
                for(Player p : e.getArena().getPlayers()){
                    SendKillMessages.sendexplosionkm(p, e.getVictim().getDisplayName(), e.getKiller().getDisplayName(), false, color3, color2);
                }
                return;
            }

            // Final Explosion kill
            if(e.getCause().equals(PlayerKillEvent.PlayerKillCause.EXPLOSION_FINAL_KILL)){
                e.setMessage(func);
                for(Player p : e.getArena().getPlayers()){
                    SendKillMessages.sendexplosionkm(p, e.getVictim().getDisplayName(), e.getKiller().getDisplayName(), true, color3, color2);
                }
                return;
            }

            if(e.getCause().isFinalKill()) {
                e.setMessage(func);
                for (Player p : e.getArena().getPlayers()) {
                    SendKillMessages.sendpvpkm(p, e.getVictim().getDisplayName(), e.getKiller().getDisplayName(), true, color3, color2);
                }
            }
            if(!e.getCause().isFinalKill()) {
                e.setMessage(func);
                for (Player p : e.getArena().getPlayers()) {
                    SendKillMessages.sendpvpkm(p, e.getVictim().getDisplayName(), e.getKiller().getDisplayName(), false, color3, color2);
                }
            }
    }
}
