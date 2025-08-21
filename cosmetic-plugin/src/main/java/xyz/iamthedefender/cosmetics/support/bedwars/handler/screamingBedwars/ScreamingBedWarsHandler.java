package xyz.iamthedefender.cosmetics.support.bedwars.handler.screamingBedwars;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.BedwarsAPI;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GameStore;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.handler.*;
import xyz.iamthedefender.cosmetics.category.bedbreakeffects.BedDestroySBW;
import xyz.iamthedefender.cosmetics.category.finalkilleffects.FinalKillHandlerSBW;
import xyz.iamthedefender.cosmetics.category.killmessage.KillMessagesSBW;
import xyz.iamthedefender.cosmetics.category.projectiletrails.ProjectileHandler;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.ShopKeeperHandlerSBW;
import xyz.iamthedefender.cosmetics.category.sprays.SprayHandlerSBW;
import xyz.iamthedefender.cosmetics.category.victorydance.VictoryDanceSBW;
import xyz.iamthedefender.cosmetics.category.woodskin.WoodSkinSBW;
import xyz.iamthedefender.cosmetics.support.language.LanguageImpl;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ScreamingBedWarsHandler implements IHandler {

    private static final BedwarsAPI api = BedwarsAPI.getInstance();
    private LanguageImpl language;
    private File dataFolder;


    @Override
    public void register() {
        dataFolder = new File(Main.getInstance().getDataFolder(), "Addons/" + CosmeticsPlugin.getInstance().getDescription().getName());
        language = new LanguageImpl(new File(dataFolder, "messages.yml"));


        StartupUtils.registerListeners(new ProjectileHandler(CosmeticsPlugin.getInstance()));

        // TODO: add for island toppers & death cry
        StartupUtils.registerListeners(
                new WoodSkinSBW(),
                new VictoryDanceSBW(),
                new SprayHandlerSBW(),
                new ShopKeeperHandlerSBW(),
                new KillMessagesSBW(),
                new FinalKillHandlerSBW(),
                new BedDestroySBW()
        );
    }

    @Override
    public ISetupSession getSetupSession(UUID playerUUID) {
        return new ISetupSession() {
            @Override
            public UUID getPlayerUUID() {
                return playerUUID;
            }

            @Override
            public FileConfiguration getConfig() {
                return null;
            }

            @Override
            public void saveConfigLoc(String path, Location value) {

            }

            @Override
            public void saveConfig() {

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
        return dataFolder.getAbsolutePath();
    }

    @Override
    public HandlerType getHandlerType() {
        return HandlerType.MULTIARENA;
    }
}
