// 
// @author IamTheDefender
// 

package me.defender.cosmetics.IslandToppers;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.hakan.core.HCore;
import me.defender.api.BwcAPI;
import me.defender.api.enums.Cosmetics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import com.sk89q.worldedit.MaxChangedBlocksException;

import java.io.File;
import java.io.IOException;

import org.bukkit.event.Listener;

import static me.defender.api.utils.util.plugin;

public class IslandToppers implements Listener
{
    @EventHandler
    public void onSpawn(GameStateChangeEvent e) {
        HCore.syncScheduler().after(20).run((runnable) -> {
            int height = e.getArena().getConfig().getInt("IslandTopper.height");
            for(ITeam teams : e.getArena().getTeams()){
                Player player = null;
                for(Player p : teams.getMembers()){
                    if(!new BwcAPI().getSelectedCosmetic(p, Cosmetics.IslandTopper).equals("None")){
                        if(player != null){
                            player = p;
                        }
                    }
                }
                if(player == null){
                    return;
                }
                int x = teams.getSpawn().getBlockX();
                int y = teams.getSpawn().getBlockY();
                int z = teams.getSpawn().getBlockZ();
                int y2 = y + height;
                BwcAPI api = new BwcAPI();
                String selected = api.getSelectedCosmetic(player, Cosmetics.IslandTopper);
                if(!selected.equals("None")){
                    String filestring = plugin().islandToppersData.getConfig().getString("IslandToppers." + selected + ".File");
                    if(filestring == null){
                        Bukkit.getLogger().warning("File not found! (" + selected + " Island topper!)");
                        return;
                    }
                    File file = new File(System.getProperty("user.dir") + "/plugins/Bedwars1058-Cosmetics/IslandToppers/" + filestring);
                    if(!file.exists()){
                        Bukkit.getLogger().warning("File not found! (" + selected + " Island topper!)");
                        return;
                    }
                    try {
                        IslandToppersUtil.sendIslandTopper(e.getArena().getWorld(), x, y2, z, player, file);
                    } catch (MaxChangedBlocksException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });


    }
}
