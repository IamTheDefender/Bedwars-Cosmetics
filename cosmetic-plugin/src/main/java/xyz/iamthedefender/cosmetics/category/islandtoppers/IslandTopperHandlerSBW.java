package xyz.iamthedefender.cosmetics.category.islandtoppers;

import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.events.gameplay.GameStateChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.api.events.BedwarsGameStartEvent;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.IslandTopper;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.io.File;
import java.util.Random;

public class IslandTopperHandlerSBW implements Listener {

    @EventHandler
    public void onGameStartSBW(BedwarsGameStartEvent event) {

        boolean isIslandToppersEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("island-toppers.enabled");
        if (!isIslandToppersEnabled) return;

        spawnTopper(event);
    }

    private void spawnTopper(BedwarsGameStartEvent event) {
        Run.delayed(() -> {
            event.getGame().getRunningTeams().forEach(runningTeam -> {
                Player player = null;
                for (Player p : runningTeam.getConnectedPlayers()) {
                    if (!CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(p, CosmeticsType.IslandTopper).equals("none")) {
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
                }catch (Exception ignored) {}

                if (location == null) {
                    DebugUtil.addMessage("Invalid or no location found for team " + runningTeam.getName() + ", player " + player.getName() + " in " + event.getGame().getName());
                    return;
                }

                String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(player, CosmeticsType.IslandTopper);
                for(IslandTopper islandTopper : StartupUtils.islandTopperList){
                    if (islandTopper.getIdentifier().equals(selected)){
                        if (islandTopper.getField(FieldsType.RARITY, player) != RarityType.NONE) {
                            islandTopper.execute(player, location, selected);
                        }
                    }
                }
            });
        }, 20L);
    }

}
