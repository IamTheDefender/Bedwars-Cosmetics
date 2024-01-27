

package me.defender.cosmetics.category.islandtoppers;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.hakan.core.HCore;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.cosmetics.category.IslandTopper;
import me.defender.cosmetics.api.cosmetics.CosmeticsType;
import me.defender.cosmetics.api.cosmetics.FieldsType;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.util.StartupUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static me.defender.cosmetics.util.Utility.plugin;

public class IslandTopperHandler1058 implements Listener {
    @EventHandler
    public void onSpawn1058(GameStateChangeEvent e) {

        boolean isIslandToppersEnabled = Cosmetics.getInstance().getConfig().getBoolean("island-toppers.enabled");
        if (!isIslandToppersEnabled) return;

        HCore.syncScheduler().after(20).run((runnable) -> {
            for (ITeam teams : e.getArena().getTeams()) {
                Player player = null;
                for (Player p : teams.getMembers()) {
                    if (!new BwcAPI().getSelectedCosmetic(p, CosmeticsType.IslandTopper).equals("none")) {
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
                String selected = new BwcAPI().getSelectedCosmetic(player, CosmeticsType.IslandTopper);
                for(IslandTopper islandTopper : StartupUtils.islandTopperList){
                    if(islandTopper.getIdentifier().equals(selected)){
                        if(islandTopper.getField(FieldsType.RARITY, player) != RarityType.NONE) {
                            islandTopper.execute(player, location, selected);
                        }
                    }
                }
            }
        });
    }
}