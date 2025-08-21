package xyz.iamthedefender.cosmetics.category.finalkilleffects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerKilledEvent;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.event.FinalKillEffectsExecuteEvent;

public class FinalKillHandlerSBW implements Listener {

    @EventHandler
    public void onFinalKill(BedwarsPlayerKilledEvent event) {
        Player victim = event.getPlayer();
        Player killer = event.getKiller();

        if (victim == null || killer == null) return;

        boolean isFinalKillEffectsEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("final-kill-effects.enabled");
        if (!isFinalKillEffectsEnabled) return;


        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(killer, CosmeticsType.FinalKillEffects);

        RunningTeam victimTeam = event.getGame().getTeamOfPlayer(victim);

        if (victimTeam == null) return;

        boolean isFinalKill = !victimTeam.isTargetBlockExists();

        if (!isFinalKill) return;

        FinalKillEffectsExecuteEvent killEffectsExecuteEvent = new FinalKillEffectsExecuteEvent(victim, killer, selected);
        Bukkit.getPluginManager().callEvent(killEffectsExecuteEvent);

    }

}
