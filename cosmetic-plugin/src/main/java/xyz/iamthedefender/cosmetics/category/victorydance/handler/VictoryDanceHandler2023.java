package xyz.iamthedefender.cosmetics.category.victorydance.handler;

import com.tomkeuper.bedwars.api.events.gameplay.GameEndEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerLeaveArenaEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.category.victorydance.AbstractVictoryDance;

import java.util.UUID;

public class VictoryDanceHandler2023 extends AbstractVictoryDance {

    @EventHandler
    public void onGameEnd2023(GameEndEvent e) {

        for (UUID uuid : e.getWinners()) {
            execute(Bukkit.getPlayer(uuid));
        }
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent event) {
        Player player = event.getPlayer();

        stopExecution(player);
    }
}

