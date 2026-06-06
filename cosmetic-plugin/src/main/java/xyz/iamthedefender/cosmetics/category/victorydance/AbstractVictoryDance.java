package xyz.iamthedefender.cosmetics.category.victorydance;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.event.AbstractListener;
import xyz.iamthedefender.cosmetics.api.event.VictoryDancesExecuteEvent;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AbstractVictoryDance extends AbstractListener {

    protected final Map<UUID, VictoryDance> victoryDanceMap = new HashMap<>();

    public void execute(Player player) {
        if (player == null || !StartupUtils.isFeatureEnabled("victory-dances")) return;

        VictoryDancesExecuteEvent event = new VictoryDancesExecuteEvent(player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        VictoryDance victoryDance = getSelectedCosmetic(player, CosmeticType.VICTORY_DANCES);
        if (victoryDance == null || victoryDance.getField(FieldsType.RARITY, player) == RarityType.NONE) return;

        DebugUtil.addMessage("Executing " + victoryDance.getIdentifier() + " Victory Dance for " + player.getDisplayName());
        victoryDance.execute(player);
        victoryDanceMap.put(player.getUniqueId(), victoryDance);
    }

    public void stopExecution(Player player) {
        if (player == null) return;

        Optional.ofNullable(victoryDanceMap.get(player.getUniqueId()))
                .ifPresent(victoryDance -> victoryDance.stopExecution(player));
    }
}
