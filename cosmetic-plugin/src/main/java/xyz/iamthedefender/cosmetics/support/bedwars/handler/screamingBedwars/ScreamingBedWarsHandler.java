package xyz.iamthedefender.cosmetics.support.bedwars.handler.screamingBedwars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.BedwarsAPI;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GameStore;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.handler.*;
import xyz.iamthedefender.cosmetics.category.bedbreakeffects.BedDestroySBW;
import xyz.iamthedefender.cosmetics.category.deathcries.DeathCrySBW;
import xyz.iamthedefender.cosmetics.category.finalkilleffects.FinalKillHandlerSBW;
import xyz.iamthedefender.cosmetics.category.islandtoppers.IslandTopperHandlerSBW;
import xyz.iamthedefender.cosmetics.category.killmessage.KillMessagesSBW;
import xyz.iamthedefender.cosmetics.category.projectiletrails.ProjectileHandler;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.ShopKeeperHandlerSBW;
import xyz.iamthedefender.cosmetics.category.sprays.SprayHandlerSBW;
import xyz.iamthedefender.cosmetics.category.victorydance.VictoryDanceSBW;
import xyz.iamthedefender.cosmetics.category.woodskin.WoodSkinSBW;
import xyz.iamthedefender.cosmetics.support.language.LanguageImpl;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ScreamingBedWarsHandler implements IHandler {

    private static final BedwarsAPI api = BedwarsAPI.getInstance();
    private final File dataFolder = new File(Main.getInstance().getDataFolder(), "Addons/" + CosmeticsPlugin.getInstance().getDescription().getName());
    private final LanguageImpl language = new LanguageImpl(new File(getAddonPath(), "messages.yml"));

    @Override
    public void register() {
        StartupUtils.registerListeners(new ProjectileHandler(CosmeticsPlugin.getInstance()));

        StartupUtils.registerListeners(
                new WoodSkinSBW(),
                new VictoryDanceSBW(),
                new SprayHandlerSBW(),
                new ShopKeeperHandlerSBW(),
                new KillMessagesSBW(),
                new FinalKillHandlerSBW(),
                new BedDestroySBW(),
                new DeathCrySBW(),
                new IslandTopperHandlerSBW()
        );
    }

    @Override
    public ISetupSession getSetupSession(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) return null;

        org.screamingsandals.bedwars.api.game.Game worldGame = Main.getInstance().getGames()
                .stream().filter(game -> game.getGameWorld().getName().equalsIgnoreCase(player.getWorld().getName()))
                .findFirst().orElse(null);

        if (worldGame == null) return null;

        File file = new File(CosmeticsPlugin.getInstance().getDataFolder(), ".data/arenas/" + worldGame.getName() + ".yml");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                CosmeticsPlugin.getInstance().getLogger().warning("Failed to create configuration file: " + file.getAbsolutePath());
                e.printStackTrace();
                return null;
            }
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return new ISetupSession() {
            @Override
            public UUID getPlayerUUID() {
                return playerUUID;
            }

            @Override
            public FileConfiguration getConfig() {
                return config;
            }

            @Override
            public void saveConfigLoc(String path, Location value) {
                String data = value.getX() + "," + value.getY() + "," + value.getZ() + "," + value.getYaw() + "," + value.getPitch() + "," + value.getWorld().getName();
                config.set(path, data);
                saveConfig();
            }

            @Override
            public void saveConfig() {
                try {
                    config.save(file);
                } catch (IOException e) {
                    CosmeticsPlugin.getInstance().getLogger().warning("Failed to save configuration file: " + file.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public IScoreboardUtil getScoreboardUtil() {
        return new IScoreboardUtil() {
            @Override
            public void giveScoreboard(Player player, boolean b) {

            }

            @Override
            public void removePlayerScoreboard(Player player) {

            }
        };
    }

    @Override
    public IArenaUtil getArenaUtil() {
        return player -> {
            Game game = (Game) api.getGameOfPlayer(player);

            if (game == null) return null;

            return player1 -> {
                RunningTeam team = game.getTeamOfPlayer(player1);
                if (team == null) return null;

                return new ITeamHandler() {
                    @Override
                    public Location getBed() {
                        return team.getTargetBlock();
                    }

                    @Override
                    public List<Player> getPlayers() {
                        return game.getPlayersInTeam((org.screamingsandals.bedwars.game.Team) team).stream().map(gamePlayer -> gamePlayer.player).collect(Collectors.toList());
                    }

                    @Override
                    public String getName() {
                        return team.getName();
                    }

                    @Override
                    public List<Location> getStoreLocations() {
                        List<Location> teamBased = game.getGameStoreList().stream().filter(store -> (store.getTeam() != null && store.getTeam().getName().equals(team.getName())))
                                .map(GameStore::getStoreLocation).collect(Collectors.toList());

                        if (teamBased.isEmpty()) {
                            // Try to find the store within the range of say, like 20 blocks of team spawn

                            return game.getGameStoreList().stream().filter(store -> (store.getTeam() == null && store.getStoreLocation().distance(team.getTeamSpawn()) <= 30))
                                    .map(GameStore::getStoreLocation).collect(Collectors.toList());
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
            };
        };
    }

    @Override
    public ILanguage getLanguageUtil() {
        return language;
    }

    @Override
    public String getAddonPath() {

        if (!dataFolder.exists()) {
            dataFolder.getParentFile().mkdirs();
            dataFolder.mkdirs();
        }

        return dataFolder.getAbsolutePath();
    }

    @Override
    public HandlerType getHandlerType() {
        return HandlerType.MULTIARENA;
    }
}
