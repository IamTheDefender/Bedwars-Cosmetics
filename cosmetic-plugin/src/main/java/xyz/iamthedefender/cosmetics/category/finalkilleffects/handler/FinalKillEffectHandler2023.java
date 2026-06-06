package xyz.iamthedefender.cosmetics.category.finalkilleffects.handler;

import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.category.finalkilleffects.AbstractFinalKillEffect;

public class FinalKillEffectHandler2023 extends AbstractFinalKillEffect {

    @EventHandler
    public void onFinalKill2023(com.tomkeuper.bedwars.api.events.player.PlayerKillEvent e){
        if (e.getKiller() == null) return;
        if (!e.getCause().isFinalKill()) return;

        execute(e.getKiller(), e.getVictim(), e.getVictim().getLocation(), false);
    }
}

