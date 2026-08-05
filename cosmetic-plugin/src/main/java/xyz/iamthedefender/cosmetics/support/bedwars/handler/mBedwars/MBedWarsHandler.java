package xyz.iamthedefender.cosmetics.support.bedwars.handler.mBedwars;

import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.arena.Team;
import de.marcely.bedwars.api.world.WorldStorage;
import de.marcely.bedwars.api.world.hologram.HologramControllerType;
import de.marcely.bedwars.api.world.hologram.HologramEntity;
import de.marcely.bedwars.tools.location.XYZD;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.handler.*;
import xyz.iamthedefender.cosmetics.category.bedbreakeffects.handler.BedDestroyMBW;
import xyz.iamthedefender.cosmetics.category.deathcries.handler.DeathCryMBW;
import xyz.iamthedefender.cosmetics.category.finalkilleffects.handler.FinalKillHandlerMBW;
import xyz.iamthedefender.cosmetics.category.glyphs.handler.GlyphHandlerMBW;
import xyz.iamthedefender.cosmetics.category.islandtoppers.handler.IslandTopperHandlerMBW;
import xyz.iamthedefender.cosmetics.category.killmessage.handler.KillMessagesMBW;
import xyz.iamthedefender.cosmetics.category.sprays.handler.SprayHandlerMBW;
import xyz.iamthedefender.cosmetics.category.victorydance.handler.VictoryDanceMBW;
import xyz.iamthedefender.cosmetics.category.woodskin.handler.WoodSkinMBW;
import xyz.iamthedefender.cosmetics.support.language.LanguageImpl;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MBedWarsHandler implements IHandler {

    private MBedWarsAddon addon = null;
    private final LanguageImpl language = new LanguageImpl(new File(getAddonPath(), "messages.yml"));


    @Override
    public void register() {
        addon = new MBedWarsAddon();
        addon.register();

        StartupUtils.registerListeners(
                new BedDestroyMBW(),
                new DeathCryMBW(),
                new FinalKillHandlerMBW(),
                new GlyphHandlerMBW(),
                new IslandTopperHandlerMBW(),
                new KillMessagesMBW(),
                new SprayHandlerMBW(),
                new VictoryDanceMBW(),
                new WoodSkinMBW()
                // more to come
        );
    }

    @Override
    public ISetupSession getSetupSession(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) return null;

        Arena arena = BedwarsAPI.getGameAPI().getArenas().stream().filter(someArena -> someArena.getGameWorldName().equals(player.getWorld().getName())).findFirst().orElse(null);

        if (arena == null) return null;

        File file = new File(CosmeticsPlugin.getInstance().getDataFolder(), ".data/arenas/" + arena.getName() + ".yml");

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
                Arena arena = BedwarsAPI.getGameAPI().getArenaByPlayer(player);

                if (arena == null) return null;

                return new IArenaHandler() {
                    @Override
                    public ITeamHandler getTeam(Player player) {
                        Team team = arena.getPlayerTeam(player);

                        if (team == null) return null;

                        return new ITeamHandler() {
                            @Override
                            public Location getBed() {
                                XYZD bedRaw = arena.getBedLocation(team);

                                if (bedRaw == null)
                                    throw new IllegalArgumentException("Failed to resolve team bed for " + player.getName() + " in arena " + arena.getName());

                                return bedRaw.toLocation(player.getWorld());
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
                                return Objects.requireNonNull(arena.getTeamSpawn(team)).toLocation(player.getWorld());
                            }

                            @Override
                            public int getSize() {
                                return getPlayers().size();
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
