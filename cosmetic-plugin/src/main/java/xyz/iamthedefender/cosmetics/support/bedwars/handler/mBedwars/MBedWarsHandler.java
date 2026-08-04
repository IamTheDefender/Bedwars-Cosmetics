package xyz.iamthedefender.cosmetics.support.bedwars.handler.mBedwars;

import de.marcely.bedwars.api.arena.Arena;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.api.handler.*;
import xyz.iamthedefender.cosmetics.support.language.LanguageImpl;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class MBedWarsHandler implements IHandler {

    private MBedWarsAddon addon = null;
    private final LanguageImpl language = new LanguageImpl(new File(getAddonPath(), "messages.yml"));


    @Override
    public void register() {
        addon = new MBedWarsAddon();
        addon.register();

        //TODO: register events
    }

    @Override
    public ISetupSession getSetupSession(UUID playerUUID) {
        return new ISetupSession() {
            @Override
            public UUID getPlayerUUID() {
                return null;
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
                // Do nothing
            }

            @Override
            public void removePlayerScoreboard(Player player) {
                // Do nothing
            }
        };
    }

    @Override
    public IArenaUtil getArenaUtil() {
        return new IArenaUtil() {
            @Override
            public IArenaHandler getArenaByPlayer(Player player) {


                return new IArenaHandler() {
                    @Override
                    public ITeamHandler getTeam(Player player) {
                        return new ITeamHandler() {
                            @Override
                            public Location getBed() {
                                return null;
                            }

                            @Override
                            public List<Player> getPlayers() {
                                return List.of();
                            }

                            @Override
                            public String getName() {
                                return "";
                            }

                            @Override
                            public List<Location> getStoreLocations() {
                                return List.of();
                            }

                            @Override
                            public Location getSpawn() {
                                return null;
                            }

                            @Override
                            public int getSize() {
                                return 0;
                            }
                        };
                    }
                };
            }
        };
    }

    @Override
    public ILanguage getLanguageUtil() {
        return language;
    }

    @Override
    public String getAddonPath() {
        return addon.getDataFolder().getAbsolutePath();
    }

    @Override
    public HandlerType getHandlerType() {
        return HandlerType.SHARED;
    }
}
