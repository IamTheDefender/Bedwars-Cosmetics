package me.defender.cosmetics.api.category.islandtoppers;

import com.hakan.core.HCore;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.StartupUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IslandTopperHandler2023 implements Listener {
    @EventHandler
    public void onSpawn2023(com.tomkeuper.bedwars.api.events.gameplay.GameStateChangeEvent e) {
        HCore.syncScheduler().after(20).run((runnable) -> {
            for (com.tomkeuper.bedwars.api.arena.team.ITeam teams : e.getArena().getTeams()) {
                Player player = null;
                for (Player p : teams.getMembers()) {
                    if (!new BwcAPI().getSelectedCosmetic(p, CosmeticsType.IslandTopper).equals("none")) {
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