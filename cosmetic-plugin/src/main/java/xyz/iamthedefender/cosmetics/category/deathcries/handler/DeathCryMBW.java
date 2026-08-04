package xyz.iamthedefender.cosmetics.category.deathcries.handler;

import de.marcely.bedwars.api.event.player.PlayerKillPlayerEvent;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.category.deathcries.AbstractDeathCry;

public class DeathCryMBW extends AbstractDeathCry {

    @EventHandler
    public void onPlayerDeath(PlayerKillPlayerEvent event) {
        execute(event.getDamaged());
    }
}
