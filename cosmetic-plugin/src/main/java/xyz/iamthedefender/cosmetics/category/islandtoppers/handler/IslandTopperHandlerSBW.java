package xyz.iamthedefender.cosmetics.category.islandtoppers.handler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.screamingsandals.bedwars.api.events.BedwarsGameStartEvent;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.islandtoppers.AbstractIslandTopper;
import xyz.iamthedefender.cosmetics.util.DebugUtil;

import java.io.File;
import java.util.Random;

public class IslandTopperHandlerSBW extends AbstractIslandTopper {

    @EventHandler
    public void onGameStartSBW(BedwarsGameStartEvent event) {

        if (!enabled()) return;

        spawnTopper(event);
    }

    private void spawnTopper(BedwarsGameStartEvent event) {
        Run.delayed(() -> {
            event.getGame().getRunningTeams().forEach(runningTeam -> {
                Player player = null;
                for (Player p : runningTeam.getConnectedPlayers()) {
                    if (!CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(p, CosmeticType.ISLAND_TOPPERS).equals("none")) {
                        player = p;

                        if (new Random().nextDouble() < 0.5)
                            break;
                    }
                }

                if (player == null) return;
                String pathForLocation = "Team." + runningTeam.getName() + ".IslandTopper.";
                File file = new File(CosmeticsPlugin.getInstance().getDataFolder(), ".data/arenas/" + event.getGame().getName() + ".yml");

                if (!file.exists() || !file.getAbsolutePath().endsWith(".yml")) {
                    DebugUtil.addMessage("Failed to find the file required for IslandToppers for player " + player.getName() + ": " + file.getAbsolutePath());
                    return;
                }

                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

                String locationString = configuration.getString(pathForLocation + "location");

                if (locationString == null) {
                    DebugUtil.addMessage("Failed to find IslandTopper team location for player " + player.getName() + " and team " + runningTeam.getName() + " in " + event.getGame().getName());
                    return;
                }

                Location location = null;

                try {
                    String[] data = locationString.replace("[", "").replace("]", "").split(",");
                    location = new Location(Bukkit.getWorld(data[5]), Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Float.parseFloat(data[3]), Float.parseFloat(data[4]));
                } catch (Exception ignored) {
                }

                if (location == null) {
                    DebugUtil.addMessage("Invalid or no location found for team " + runningTeam.getName() + ", player " + player.getName() + " in " + event.getGame().getName());
                    return;
                }

                execute(player, location);
            });
        }, 20L);
    }

}


