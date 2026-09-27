package xyz.iamthedefender.cosmetics.category.finalkilleffects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.FinalKillEffect;
import xyz.iamthedefender.cosmetics.api.event.AbstractListener;
import xyz.iamthedefender.cosmetics.api.event.FinalKillEffectsExecuteEvent;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractFinalKillEffect extends AbstractListener {

    private static final long KILL_COOLDOWN_MS = 2000;
    private final Map<UUID, Long> recentVictims = new ConcurrentHashMap<>();

    public void execute(Player killer, Player victim, Location location, boolean onlyVictim) {
        if (!StartupUtils.isFeatureEnabled("final-kill-effects")) return;

        UUID victimId = victim.getUniqueId();
        long now = System.currentTimeMillis();
        Long last = recentVictims.get(victimId);
        if (last != null && (now - last) < KILL_COOLDOWN_MS) return;
        recentVictims.put(victimId, now);

        String selected = getAPI().getSelectedCosmetic(killer, CosmeticType.FINAL_KILL_EFFECTS);
        if (!executeEvent(victim, killer, selected)) return;

        FinalKillEffect finalKillEffect = getSelectedCosmetic(killer, CosmeticType.FINAL_KILL_EFFECTS);
        if (finalKillEffect == null || finalKillEffect.getField(FieldsType.RARITY, killer) == RarityType.NONE) return;

        finalKillEffect.execute(killer, victim, location, onlyVictim);
        DebugUtil.addMessage("Playing " + selected + " Final Kill Effect for " + killer.getDisplayName());
    }

    private boolean executeEvent(Player victim, Player killer, String selected) {
        FinalKillEffectsExecuteEvent event = new FinalKillEffectsExecuteEvent(victim, killer, selected);
        Bukkit.getPluginManager().callEvent(event);

        return !event.isCancelled();
    }
}
