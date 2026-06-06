

package xyz.iamthedefender.cosmetics.category.islandtoppers.handler;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.islandtoppers.AbstractIslandTopper;

public class IslandTopperHandler1058 extends AbstractIslandTopper {
    @EventHandler
    public void onSpawn1058(GameStateChangeEvent e) {

        if (!enabled()) return;

        Run.delayed(() -> {
            for (ITeam teams : e.getArena().getTeams()) {
                Player player = null;
                for (Player p : teams.getMembers()) {
                    if (!CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(p, CosmeticType.ISLAND_TOPPERS).equals("none")) {
                        player = p;
                    }
                }

                if (player == null) return;
                String pathForLocation = "Team." + teams.getName() + ".IslandTopper.";
                ConfigManager config = e.getArena().getConfig();
                Location location = config.getConfigLoc(pathForLocation + "location");
                if (location == null) {
                    return;
                }
                execute(player, location);
            }
        }, 20L);
    }
}

