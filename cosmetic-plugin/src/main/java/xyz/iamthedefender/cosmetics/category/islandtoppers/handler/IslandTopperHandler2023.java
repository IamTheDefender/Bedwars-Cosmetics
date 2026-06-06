package xyz.iamthedefender.cosmetics.category.islandtoppers.handler;

import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.events.gameplay.GameStateChangeEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.category.islandtoppers.AbstractIslandTopper;

public class IslandTopperHandler2023 extends AbstractIslandTopper {
    @EventHandler
    public void onSpawn2023(GameStateChangeEvent e) {

        if (!enabled()) return;

        if (e.getNewState() == GameState.playing) {
            spawnTopper(e);
        }
    }

    private void spawnTopper(GameStateChangeEvent e) {
        new BukkitRunnable(){

            @Override
            public void run() {
                for (com.tomkeuper.bedwars.api.arena.team.ITeam teams : e.getArena().getTeams()) {
                    Player player = null;
                    for (Player p : teams.getMembers()) {
                        if (!CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(p, CosmeticType.ISLAND_TOPPERS).equals("none")) {
                            player = p;
                        }
                    }

                    if (player == null) return;
                    String pathForLocation = "Team." + teams.getName() + ".IslandTopper.";
                    com.tomkeuper.bedwars.api.configuration.ConfigManager config = e.getArena().getConfig();
                    Location location = config.getConfigLoc(pathForLocation + "location");
                    if (location == null) {
                        return;
                    }
                    execute(player, location);
                }
            }
        }.runTaskLater(CosmeticsPlugin.getInstance(), 20L);
    }
}

