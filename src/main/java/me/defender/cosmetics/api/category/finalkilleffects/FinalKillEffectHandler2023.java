package me.defender.cosmetics.api.category.finalkilleffects;

import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.event.FinalKillEffectsExecuteEvent;
import me.defender.cosmetics.api.util.DebugUtil;
import me.defender.cosmetics.api.util.StartupUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FinalKillEffectHandler2023 implements Listener {

    @EventHandler
    public void onFinalKill2023(com.tomkeuper.bedwars.api.events.player.PlayerKillEvent e){
        BwcAPI api = new BwcAPI();
        if(e.getKiller() == null) return;

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
