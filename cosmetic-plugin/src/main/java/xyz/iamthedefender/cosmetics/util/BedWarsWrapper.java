package xyz.iamthedefender.cosmetics.util;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.game.GameStore;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.handler.IArenaHandler;
import xyz.iamthedefender.cosmetics.api.handler.ITeamHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BedWarsWrapper {

    public static ITeamHandler wrap(com.tomkeuper.bedwars.api.arena.team.ITeam team){
        return  new ITeamHandler() {
            @Override
            public Location getBed() {
                return team.getBed();
            }

            @Override
            public List<Player> getPlayers() {
                return team.getMembers();
            }

            @Override
            public String getName() {
                return team.getName();
            }

            public Location getTeamUpgrades() {
                return team.getTeamUpgrades();
            }

            public Location getShop() {
                return team.getShop();
            }

            @Override
            public List<Location> getStoreLocations() {
                return Arrays.asList(getShop(), getTeamUpgrades());
            }

            @Override
            public Location getSpawn() {
                return team.getSpawn();
            }

            @Override
            public int getSize() {
                return team.getSize();
            }
        };
    }
    public static ITeamHandler wrap(ITeam team){
        return  new ITeamHandler() {
            @Override
            public Location getBed() {
                return team.getBed();
            }

            @Override
            public List<Player> getPlayers() {
                return team.getMembers();
            }

            @Override
            public String getName() {
                return team.getName();
            }

            public Location getTeamUpgrades() {
                return team.getTeamUpgrades();
            }

            public Location getShop() {
                return team.getShop();
            }

            @Override
            public List<Location> getStoreLocations() {
                return Arrays.asList(getShop(), getTeamUpgrades());
            }

            @Override
            public Location getSpawn() {
                return team.getSpawn();
            }

            @Override
            public int getSize() {
                return team.getSize();
            }
        };
    }

    public static ITeamHandler wrap(RunningTeam team) {
        Game game = team.getGame();

        return new ITeamHandler() {
            @Override
            public Location getBed() {
                return team.getTargetBlock();
            }

            @Override
            public List<Player> getPlayers() {
                return team.getConnectedPlayers();
            }

            @Override
            public String getName() {
                return team.getName();
            }

            @Override
            public List<Location> getStoreLocations() {
                List<Location> teamBased = game.getGameStores().stream().filter(store -> (store.getTeam() != null && store.getTeam().getName().equals(team.getName())))
                        .map(org.screamingsandals.bedwars.api.game.GameStore::getStoreLocation).collect(Collectors.toList());

                if (teamBased.isEmpty()) {
                    // Try to find the store within the range of say, like 20 blocks of team spawn

                    return game.getGameStores().stream().filter(store -> (store.getTeam() == null && store.getStoreLocation().distance(team.getTeamSpawn()) <= 30))
                            .map(org.screamingsandals.bedwars.api.game.GameStore::getStoreLocation).collect(Collectors.toList());
                }

                return teamBased;
            }

            @Override
            public Location getSpawn() {
                return team.getTeamSpawn();
            }

            @Override
            public int getSize() {
                return team.getConnectedPlayers().size();
            }
        };
    }

    public static @Nullable IArenaHandler wrap(Player player) {
        return CosmeticsPlugin.getInstance().getHandler().getArenaUtil().getArenaByPlayer(player);
    }
}
