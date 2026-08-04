package xyz.iamthedefender.cosmetics.util;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.arena.Team;
import de.marcely.bedwars.api.world.WorldStorage;
import de.marcely.bedwars.api.world.hologram.HologramControllerType;
import de.marcely.bedwars.api.world.hologram.HologramEntity;
import de.marcely.bedwars.tools.location.XYZD;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.game.GameStore;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.handler.IArenaHandler;
import xyz.iamthedefender.cosmetics.api.handler.ITeamHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BedWarsWrapper {

    public static ITeamHandler wrap(Team team, Arena arena) {
        return new ITeamHandler() {
            @Override
            public Location getBed() {
                XYZD bedRaw = arena.getBedLocation(team);

                if (bedRaw == null)
                    throw new IllegalArgumentException("Failed to resolve team bed for " + team + " in arena " + arena.getName());

                return bedRaw.toLocation(arena.getGameWorld());
            }

            @Override
            public List<Player> getPlayers() {
                return arena.getPlayersInTeam(team);
            }

            @Override
            public String getName() {
                return team.name();
            }

            @Override
            public List<Location> getStoreLocations() {
                WorldStorage storage = BedwarsAPI.getWorldStorage(arena.getGameWorld());
                List<Location> stores = new ArrayList<>();

                for (HologramEntity hologramEntity : storage.getHolograms()) {
                    if (hologramEntity.getControllerType() != HologramControllerType.DEALER && hologramEntity.getControllerType() != HologramControllerType.UPGRADE_DEALER)
                        continue;

                    if (!arena.isInside(hologramEntity.getLocation()))
                        continue;

                    Location loc = hologramEntity.getLocation();

                    if (loc.distance(getSpawn()) <= 30) {
                        stores.add(loc);
                    }
                }

                return stores;
            }

            @Override
            public Location getSpawn() {
                return Objects.requireNonNull(arena.getTeamSpawn(team)).toLocation(arena.getGameWorld());
            }

            @Override
            public int getSize() {
                return getPlayers().size();
            }
        };
    }

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
