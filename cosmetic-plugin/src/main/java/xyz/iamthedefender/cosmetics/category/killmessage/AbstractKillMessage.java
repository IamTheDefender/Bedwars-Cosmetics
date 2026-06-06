package xyz.iamthedefender.cosmetics.category.killmessage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.Nullable;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.event.AbstractListener;
import xyz.iamthedefender.cosmetics.category.killmessage.util.KillMessageUtils;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

public class AbstractKillMessage extends AbstractListener {

    public boolean enabled() {
        return StartupUtils.isFeatureEnabled("kill-messages");
    }

    public boolean shouldDisableDeathCrySound(Player victim) {
        String deathCry = getAPI().getSelectedCosmetic(victim, CosmeticType.DEATH_CRIES);
        return deathCry != null && !deathCry.equals("NONE");
    }

    public @Nullable String getSelected(Player killer) {
        String selected = getAPI().getSelectedCosmetic(killer, CosmeticType.KILL_MESSAGES);
        return selected == null || selected.equals("Default") ? null : selected;
    }

    public @Nullable String getMessage(Player viewer, String selected, Player victim, Player killer, boolean finalKill,
                                       ChatColor victimColor, ChatColor killerColor, DamageCause cause) {
        if (!KillMessageUtils.exists(selected, cause.configName) || KillMessageUtils.isNone(selected)) return null;

        DebugUtil.addMessage("Playing Kill message for " + killer);
        return viewer == null
                ? KillMessageUtils.sendKillMessage(victim.getName(), killer, finalKill, victimColor, killerColor, cause.configName)
                : KillMessageUtils.sendKillMessage(viewer, victim.getName(), killer, finalKill, victimColor, killerColor, cause.configName);
    }

    public @Nullable DamageCause translateCause(EntityDamageEvent.DamageCause damageCause) {
        if (damageCause.name().contains("ENTITY_ATTACK")) return DamageCause.PVP;
        if (damageCause.name().contains("EXPLOSION")) return DamageCause.EXPLOSION;
        if (damageCause.name().contains("PROJECTILE")) return DamageCause.PLAYER_SHOOT;
        if (damageCause == EntityDamageEvent.DamageCause.VOID) return DamageCause.VOID;
        return null;
    }

    public enum DamageCause {
        PVP("PvP"),
        EXPLOSION("Explosion"),
        PLAYER_SHOOT("Shoot"),
        VOID("Void");

        private final String configName;

        DamageCause(String configName) {
            this.configName = configName;
        }
    }
}
