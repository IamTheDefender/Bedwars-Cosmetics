package xyz.iamthedefender.cosmetics.category.killmessage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.Nullable;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerDeathMessageSendEvent;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerKilledEvent;
import org.screamingsandals.bedwars.game.Team;
import org.screamingsandals.bedwars.game.TeamColor;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.category.killmessage.util.KillMessageUtils;
import xyz.iamthedefender.cosmetics.util.DebugUtil;

public class KillMessagesSBW implements Listener {

    @EventHandler
    public void onPlayerKillByOtherPlayer(BedwarsPlayerDeathMessageSendEvent e) {
        if (e.getVictim() == null) return;
        if (e.getVictim().getKiller() == null) return;

        Player victim = e.getVictim();
        Player killer = e.getVictim().getKiller();

        boolean isKillMessagesEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("kill-messages.enabled");
        if (!isKillMessagesEnabled) return;

        RunningTeam victimTeam = e.getGame().getTeamOfPlayer(victim);
        RunningTeam killerTeam = e.getGame().getTeamOfPlayer(killer);

        if (victimTeam == null || killerTeam == null) {
            return;
        }

        ChatColor color2 = TeamColor.valueOf(killerTeam.getColor().name()).chatColor;
        ChatColor color3 = TeamColor.valueOf(victimTeam.getColor().name()).chatColor;

        // KILL MESSAGES!
        if (CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(killer, CosmeticsType.KillMessage).equals("Default")) {
            return;
        }

        boolean isFinalKill = !victimTeam.isTargetBlockExists();

        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(killer, CosmeticsType.KillMessage);

        DamageCause cause = findLastCause(e.getVictim());

        if (cause == null) {
            return;
        }

        switch (cause) {
            case PVP:
                if (KillMessageUtils.exists(selected, "PvP") && !KillMessageUtils.isNone(selected)) {
                    e.setMessage(KillMessageUtils.sendKillMessage(e.getVictim().getName(),
                            killer, isFinalKill, color3, color2, "PvP"));
                }
                break;
            case EXPLOSION:
                if (KillMessageUtils.exists(selected, "Explosion") && !KillMessageUtils.isNone(selected) && !KillMessageUtils.isNone(selected)) {
                    e.setMessage(KillMessageUtils.sendKillMessage(e.getVictim().getName(),
                            killer, isFinalKill, color3, color2, "Explosion"));
                }
                break;
            case PLAYER_SHOOT:
                if (KillMessageUtils.exists(selected, "Shoot") && !KillMessageUtils.isNone(selected)) {
                    e.setMessage(KillMessageUtils.sendKillMessage(e.getVictim().getName(),
                            killer, isFinalKill, color3, color2, "Shoot"));
                }
                break;
            case VOID:
                if (KillMessageUtils.exists(selected, "Void") && !KillMessageUtils.isNone(selected)) {
                    e.setMessage(KillMessageUtils.sendKillMessage(e.getVictim().getName(),
                            killer, isFinalKill, color3, color2, "Void"));
                }
                break;
        }
    }

    private @Nullable DamageCause findLastCause(Player victim) {
        return victim.getLastDamageCause() != null ? translateCause(victim.getLastDamageCause().getCause()) : null;
    }

    private DamageCause translateCause(EntityDamageEvent.DamageCause damageCause) {
        if (damageCause.name().contains("ENTITY_ATTACK")) return DamageCause.PVP;

        if (damageCause.name().contains("EXPLOSION")) return DamageCause.EXPLOSION;

        if (damageCause.name().contains("PROJECTILE")) return DamageCause.PLAYER_SHOOT;

        if (damageCause == EntityDamageEvent.DamageCause.VOID) return DamageCause.VOID;

        return null;
    }

    enum DamageCause {
        PVP, EXPLOSION, PLAYER_SHOOT, VOID
    }
}
