package xyz.iamthedefender.cosmetics.category.islandtoppers.handler;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.event.arena.ArenaPreparingStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.islandtoppers.AbstractIslandTopper;
import xyz.iamthedefender.cosmetics.util.DebugUtil;

import java.io.File;
import java.util.Random;

public class IslandTopperHandlerMBW extends AbstractIslandTopper {

    @EventHandler
    public void onGameStart(ArenaPreparingStartEvent event) {

        if (!enabled()) return;

        spawnTopper(event);
    }

    private void spawnTopper(ArenaPreparingStartEvent event) {
        Arena arena = event.getArena();

        Run.delayed(() -> event.getArena().getEnabledTeams().forEach(team -> {
            Player player = null;

            for (Player p : arena.getPlayersInTeam(team)) {
                if (!CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(p, CosmeticType.ISLAND_TOPPERS).equals("none")) {
                    player = p;

                    if (new Random().nextDouble() < 0.5)
                        break;
                }
            }

            if (player == null) return;
            String pathForLocation = "Team." + team + ".IslandTopper.";
            File file = new File(CosmeticsPlugin.getInstance().getDataFolder(), ".data/arenas/" + arena.getName() + ".yml");

            if (!file.exists() || !file.getAbsolutePath().endsWith(".yml")) {
                DebugUtil.addMessage("Failed to find the file required for IslandToppers for player " + player.getName() + ": " + file.getAbsolutePath());
                return;
            }

            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String locationString = configuration.getString(pathForLocation + "location");

            if (locationString == null) {
                DebugUtil.addMessage("Failed to find IslandTopper team location for player " + player.getName() + " and team " + team + " in " + arena.getName());
                return;
            }

            Location location = null;

            try {
                String[] data = locationString.replace("[", "").replace("]", "").split(",");
                location = new Location(Bukkit.getWorld(data[5]), Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Float.parseFloat(data[3]), Float.parseFloat(data[4]));
            } catch (Exception ignored) {
            }

            if (location == null) {
                DebugUtil.addMessage("Invalid or no location found for team " + team + ", player " + player.getName() + " in " + arena.getName());
                return;
            }

            execute(player, location);
        }), 20L);
    }

}
