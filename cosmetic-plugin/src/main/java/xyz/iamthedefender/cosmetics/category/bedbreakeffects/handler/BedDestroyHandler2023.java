package xyz.iamthedefender.cosmetics.category.bedbreakeffects.handler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.BedDestroy;
import xyz.iamthedefender.cosmetics.api.event.AbstractListener;
import xyz.iamthedefender.cosmetics.api.event.BedBreakEffectExecuteEvent;
import xyz.iamthedefender.cosmetics.category.bedbreakeffects.AbstractBedDestroy;
import xyz.iamthedefender.cosmetics.util.BedWarsWrapper;
import xyz.iamthedefender.cosmetics.util.DebugUtil;

public class BedDestroyHandler2023 extends AbstractBedDestroy {

    @EventHandler
    public void onBedBreak2023(com.tomkeuper.bedwars.api.events.player.PlayerBedBreakEvent e) {
        execute(
                e.getPlayer(),
                e.getVictimTeam().getBed(),
                BedWarsWrapper.wrap(e.getVictimTeam())
        );
    }
}

