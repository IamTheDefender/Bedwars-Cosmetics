package xyz.iamthedefender.cosmetics.category.deathcries.handler;

import com.tomkeuper.bedwars.api.events.player.PlayerKillEvent;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.category.deathcries.AbstractDeathCry;

public class DeathCryHandler2023 extends AbstractDeathCry {

    @EventHandler
    public void onPlayerDeath2023(PlayerKillEvent e) {
        execute(e.getVictim());
    }
}

