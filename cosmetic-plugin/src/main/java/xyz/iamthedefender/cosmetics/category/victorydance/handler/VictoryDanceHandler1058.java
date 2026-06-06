

package xyz.iamthedefender.cosmetics.category.victorydance.handler;

import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.category.victorydance.AbstractVictoryDance;

import java.util.UUID;

public class VictoryDanceHandler1058 extends AbstractVictoryDance {

    @EventHandler
    public void onGameEnd1058(GameEndEvent e) {

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

