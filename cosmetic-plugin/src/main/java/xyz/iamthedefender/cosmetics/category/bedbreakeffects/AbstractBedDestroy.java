package xyz.iamthedefender.cosmetics.category.bedbreakeffects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.BedDestroy;
import xyz.iamthedefender.cosmetics.api.event.AbstractListener;
import xyz.iamthedefender.cosmetics.api.event.BedBreakEffectExecuteEvent;
import xyz.iamthedefender.cosmetics.api.handler.ITeamHandler;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

public class AbstractBedDestroy extends AbstractListener {

    public void execute(Player player, Location location, ITeamHandler teamHandler) {
        if (!StartupUtils.isCosmeticEnabled(CosmeticType.BED_DESTROY)) return;

        BedDestroy selected = getSelectedCosmetic(player, CosmeticType.BED_DESTROY);

        if (selected == null || !executeEvent(player) || selected.getField(FieldsType.RARITY, player) == RarityType.NONE) return;

        selected.execute(player, location, teamHandler);
    }

    private boolean executeEvent(Player player) {
        BedBreakEffectExecuteEvent bedDestroyEvent = new BedBreakEffectExecuteEvent(player);
        Bukkit.getServer().getPluginManager().callEvent(bedDestroyEvent);

        return !bedDestroyEvent.isCancelled();
    }
}
