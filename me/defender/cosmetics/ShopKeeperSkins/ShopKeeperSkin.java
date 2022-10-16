// 
// @author IamTheDefender
// 

package me.defender.cosmetics.ShopKeeperSkins;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import java.util.concurrent.ThreadLocalRandom;

import me.defender.api.utils.util;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import java.util.List;
import org.bukkit.scheduler.BukkitRunnable;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import java.util.HashMap;
import me.defender.Main;
import org.bukkit.event.Listener;

public class ShopKeeperSkin implements Listener
{
    private final Main plugin;
    public static HashMap<String, Boolean> arenas;
    
    static {
        ShopKeeperSkin.arenas = new HashMap<>();
    }
    
    public ShopKeeperSkin() {
        this.plugin = Main.getPlugin(Main.class);
    }
    
    @EventHandler
    public void onGameStart(final GameStateChangeEvent event) {
        if (event.getNewState().name().equals("playing")) {
            ShopKeeperSkin.arenas.put(event.getArena().getWorldName(), true);
             List<ITeam> teams = event.getArena().getTeams();
            new BukkitRunnable() {
                public void run() {
                    for ( ITeam team : teams) {
                        if (team.getMembers().size() != 0) {
                             Location shopLocation = team.getShop();
                             Location upgradeLocation = team.getTeamUpgrades();
                             List<Entity> OldShopNPC = shopLocation.getWorld().getEntities();
                             List<Entity> nowUpgrades = upgradeLocation.getWorld().getEntities();
                            for ( Entity OldNpc : OldShopNPC) {
                                if (OldNpc.getLocation().distance(shopLocation) <= 0.2) {
                                    OldNpc.remove();
                                }
                            }
                            for ( Entity OldNpc : nowUpgrades) {
                                if (OldNpc.getLocation().distance(upgradeLocation) <= 0.2) {
                                    OldNpc.remove();
                                }
                            }
                            if (team.getSize() == 1) {
                                 Player player = team.getMembers().get(0);
                                new BukkitRunnable() {
                                    public void run() {
                                        util.spawnShopKeeperNPC(player, shopLocation, upgradeLocation);
                                    }
                                }.runTaskLater(ShopKeeperSkin.this.plugin, 10L);
                            }
                            else {
                                 int random = ThreadLocalRandom.current().nextInt(team.getSize());
                                 Player p = team.getMembers().get(random);
                                new BukkitRunnable() {
                                    public void run() {
                                        util.spawnShopKeeperNPC(p, shopLocation, upgradeLocation);
                                    }
                                }.runTaskLater(ShopKeeperSkin.this.plugin, 10L);
                            }
                        }
                        for(Player p : team.getMembers()){
                            BedWars api = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
                            api.getScoreboardUtil().removePlayerScoreboard(p);
                            api.getScoreboardUtil().givePlayerScoreboard(p, true);
                        }
                    }

                }
            }.runTaskLater(this.plugin, 30L);
        }
    }
    
    @EventHandler
    public void onGameEnd(final GameEndEvent e) {
        String name = e.getArena().getWorldName();
        new BukkitRunnable(){
            @Override
            public void run() {

                ShopKeeperSkin.arenas.remove(name);
            }
        }.runTaskLater(util.plugin(), 300L);
    }
}
