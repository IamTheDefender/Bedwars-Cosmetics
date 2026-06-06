package xyz.iamthedefender.cosmetics.category.finalkilleffects.handler;

import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.category.finalkilleffects.AbstractFinalKillEffect;

public class FinalKillEffectHandler1058 extends AbstractFinalKillEffect {

    @EventHandler
    public void onFinalKill1058(PlayerKillEvent e){
        if (e.getKiller() == null) return;
        if (!e.getCause().isFinalKill()) return;

        execute(e.getKiller(), e.getVictim(), e.getVictim().getLocation(), false);
    }
}

