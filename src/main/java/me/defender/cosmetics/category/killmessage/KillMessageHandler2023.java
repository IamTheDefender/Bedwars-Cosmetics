package me.defender.cosmetics.category.killmessage;

import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.category.killmessage.util.KillMessageUtils;
import me.defender.cosmetics.api.cosmetics.CosmeticsType;
import me.defender.cosmetics.util.DebugUtil;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static me.defender.cosmetics.util.Utility.plugin;

public class KillMessageHandler2023 implements Listener {
    @EventHandler
    public void onPlayerKillByOtherPlayer2023(com.tomkeuper.bedwars.api.events.player.PlayerKillEvent e) {
        if (e.getKiller() == null) return;
        if (e.getVictim() == null) return;

        boolean isKillMessagesEnabled = Cosmetics.getInstance().getConfig().getBoolean("kill-messages.enabled");
        if (!isKillMessagesEnabled) return;

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

        String selected = new BwcAPI().getSelectedCosmetic(e.getKiller(), CosmeticsType.KillMessage);

        // Send the appropriate kill message to all players in the arena
        switch (e.getCause()) {
            case PVP:
                if(KillMessageUtils.exists(selected, "PvP") && !KillMessageUtils.isNone(selected)) {
                    e.setMessage(player -> KillMessageUtils.sendKillMessage(player, e.getVictim().getName(),
                            e.getKiller(), isFinalKill, color3, color2, "PvP"));
                }
                break;
            case EXPLOSION:
            case EXPLOSION_FINAL_KILL:
                if(KillMessageUtils.exists(selected, "Explosion") && !KillMessageUtils.isNone(selected) && !KillMessageUtils.isNone(selected)) {
                    e.setMessage(player -> KillMessageUtils.sendKillMessage(player, e.getVictim().getName(),
                            e.getKiller(), isFinalKill, color3, color2, "Explosion"));
                }
                break;
            case PLAYER_SHOOT:
            case PLAYER_SHOOT_FINAL_KILL:
                if(KillMessageUtils.exists(selected, "Shoot") && !KillMessageUtils.isNone(selected)) {
                    e.setMessage(player -> KillMessageUtils.sendKillMessage(player, e.getVictim().getName(),
                            e.getKiller(), isFinalKill, color3, color2, "Shoot"));
                }
                break;
            case VOID:
            case VOID_FINAL_KILL:
                if(KillMessageUtils.exists(selected, "Void") && !KillMessageUtils.isNone(selected)) {
                    e.setMessage(player -> KillMessageUtils.sendKillMessage(player, e.getVictim().getName(),
                            e.getKiller(), isFinalKill, color3, color2, "Void"));
                }
                break;
        }
    }
}