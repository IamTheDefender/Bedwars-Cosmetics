

package me.defender.cosmetics.api.category.shopkeeperskins;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.hakan.core.HCore;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.util.MathUtil;
import me.defender.cosmetics.api.util.StartupUtils;
import me.defender.cosmetics.api.util.DebugUtil;
import me.defender.cosmetics.api.util.Utility;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Debug;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ShopKeeperHandler implements Listener
{
    private final Cosmetics plugin;
    public static HashMap<String, Boolean> arenas = new HashMap<>();
    
    public ShopKeeperHandler() {
        this.plugin = Cosmetics.getPlugin(Cosmetics.class);
    }
    
    @EventHandler
    public void onGameStart(GameStateChangeEvent event) {
        if (event.getNewState().name().equals("playing")) {
            ShopKeeperHandler.arenas.put(event.getArena().getWorldName(), true);
             List<ITeam> teams = event.getArena().getTeams();
            DebugUtil.addMessage("Executing ShopKeeper Skins for arena " + event.getArena().getArenaName());
            new BukkitRunnable() {
                public void run() {
                    for (ITeam team : teams) {
                        if (team.getMembers().size() == 0) continue; // Skip empty teams

                        Location shopLocation = team.getShop();
                        Location upgradeLocation = team.getTeamUpgrades();
                        World world = shopLocation.getWorld();
                        DebugUtil.addMessage("Executing ShopKeeper Skins for team " + team.getName());
                        // Delete existing NPCs
                        world.getEntities().stream()
                                .filter(e -> (e.getLocation().distance(shopLocation) <= 0.2 || e.getLocation().distance(upgradeLocation) <= 0.2 && e instanceof Villager))
                                .forEach(Entity::remove);

                        // Choose random player from the team
                        Player player = team.getMembers().get(MathUtil.getRandom(0, team.getMembers().size() -1));
                        String skin = new BwcAPI().getSelectedCosmetic(player, CosmeticsType.ShopKeeperSkin);
                       DebugUtil.addMessage("Selected skin: " + skin);
                        // Spawn new NPCs
                        for (ShopKeeperSkin skins : StartupUtils.shopKeeperSkinList) {
                            if (skin.equals(skins.getIdentifier())) {
                                try {
                                    skins.execute(player, shopLocation, upgradeLocation);
                                }catch (Exception e){
                                    if(e instanceof ClassNotFoundException)
                                    Bukkit.getLogger().severe("Citizens was not found! please install it.");

                                }
                            }
                        }

                        for (Player p : team.getMembers()) {
                            BedWars api = new BwcAPI().getBwAPI();
                            api.getScoreboardUtil().removePlayerScoreboard(p);
                            api.getScoreboardUtil().givePlayerScoreboard(p, true);
                        }
                    }
                }
            }.runTaskLater(this.plugin, 30L);

        }
    }


    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent e){
        if(e.getPlayer().hasMetadata("NPC2")){
            e.setCancelled(true);
            HCore.syncScheduler().after(2).run((() -> {
                CitizensAPI.getNPCRegistry().getNPC(e.getPlayer()).despawn();
            }));
        }
    }



    @EventHandler
    public void onGameEnd(GameEndEvent e) {
        String name = e.getArena().getWorldName();

        new BukkitRunnable(){
            @Override
            public void run() {

                ShopKeeperHandler.arenas.remove(name);
            }
        }.runTaskLater(Utility.plugin(), 300L);
    }
}
