package xyz.iamthedefender.cosmetics.category.victorydance.handler;

import de.marcely.bedwars.api.event.arena.RoundEndEvent;
import de.marcely.bedwars.api.event.player.PlayerQuitArenaEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import xyz.iamthedefender.cosmetics.category.victorydance.AbstractVictoryDance;

import java.util.UUID;
import java.util.stream.Collectors;

public class VictoryDanceMBW extends AbstractVictoryDance {

    @EventHandler
    public void onGameEnd(RoundEndEvent e) {

        if (e.getWinnerTeam() == null) return;

        for (UUID uuid : e.getArena().getPlayersInTeam(e.getWinnerTeam()).stream().map(Player::getUniqueId).collect(Collectors.toList())) {
            execute(Bukkit.getPlayer(uuid));
        }
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerQuitArenaEvent event) {
        Player player = event.getPlayer();

        stopExecution(player);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        stopExecution(player);
    }

}
