package xyz.iamthedefender.cosmetics.category.victorydance.handler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.screamingsandals.bedwars.api.events.BedwarsGameEndingEvent;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerLeaveEvent;
import xyz.iamthedefender.cosmetics.category.victorydance.AbstractVictoryDance;

import java.util.UUID;
import java.util.stream.Collectors;

public class VictoryDanceSBW extends AbstractVictoryDance {

    @EventHandler
    public void onGameEnd1058(BedwarsGameEndingEvent e) {

        if (e.getWinningTeam() == null) return;

        for (UUID uuid : e.getWinningTeam().getConnectedPlayers().stream().map(Player::getUniqueId).collect(Collectors.toList())) {
            execute(Bukkit.getPlayer(uuid));
        }
    }

    @EventHandler
    public void onPlayerLeaveArena(BedwarsPlayerLeaveEvent event) {
        Player player = event.getPlayer();

        stopExecution(player);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        stopExecution(player);
    }

}

