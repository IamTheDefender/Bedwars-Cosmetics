package xyz.iamthedefender.cosmetics.category.deathcries.handler;

import org.bukkit.event.EventHandler;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerKilledEvent;
import xyz.iamthedefender.cosmetics.category.deathcries.AbstractDeathCry;

public class DeathCrySBW extends AbstractDeathCry {

    @EventHandler
    public void onPlayerDeathSBW(BedwarsPlayerKilledEvent event) {
        execute(event.getPlayer());
    }

}

