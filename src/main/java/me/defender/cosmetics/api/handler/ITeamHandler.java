package me.defender.cosmetics.api.handler;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface ITeamHandler {
    Location getBed();
    List<Player> getPlayers();
}
