package me.defender.cosmetics.category.finalkilleffects;

import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.CosmeticsAPI;
import me.defender.cosmetics.api.cosmetics.category.FinalKillEffect;
import me.defender.cosmetics.api.cosmetics.CosmeticsType;
import me.defender.cosmetics.api.cosmetics.FieldsType;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.api.event.FinalKillEffectsExecuteEvent;
import me.defender.cosmetics.util.DebugUtil;
import me.defender.cosmetics.util.StartupUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FinalKillEffectHandler2023 implements Listener {

    @EventHandler
    public void onFinalKill2023(com.tomkeuper.bedwars.api.events.player.PlayerKillEvent e){
        CosmeticsAPI api = Cosmetics.getInstance().getApi();
        if(e.getKiller() == null) return;

        boolean isFinalKillEffectsEnabled = Cosmetics.getInstance().getConfig().getBoolean("final-kill-effects.enabled");
        if (!isFinalKillEffectsEnabled) return;

        String selected = api.getSelectedCosmetic(e.getKiller(), CosmeticsType.FinalKillEffects);
        Player victim = e.getVictim();
        Player killer = e.getKiller();

        FinalKillEffectsExecuteEvent event = new FinalKillEffectsExecuteEvent(victim, killer, selected);
        Bukkit.getPluginManager().callEvent(event);
        if(!e.getCause().isFinalKill()) return;


        for(FinalKillEffect finalKillEffects : StartupUtils.finalKillList){
            if(selected.equals(finalKillEffects.getIdentifier())){
                if(finalKillEffects.getField(FieldsType.RARITY, e.getKiller()) == RarityType.NONE) return;
                finalKillEffects.execute(e.getKiller(), e.getVictim(), e.getVictim().getLocation(), false);
            }
        }
        DebugUtil.addMessage("Playing " + selected + " Final Kill Effect for " + e.getKiller().getDisplayName());
    }
}
