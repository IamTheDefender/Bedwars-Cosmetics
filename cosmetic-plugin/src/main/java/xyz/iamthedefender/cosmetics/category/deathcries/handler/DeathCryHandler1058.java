

package xyz.iamthedefender.cosmetics.category.deathcries.handler;

import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.category.deathcries.AbstractDeathCry;

public class DeathCryHandler1058 extends AbstractDeathCry
{
    @EventHandler
    public void onPlayerDeath1058(PlayerKillEvent e) {
        execute(e.getVictim());
    }
}

