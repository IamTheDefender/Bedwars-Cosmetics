package xyz.iamthedefender.cosmetics.category.shopkeeperskins.handler;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.utils.ShopKeeperSkinsUtils;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.MathUtil;

import java.util.List;

public class ShopKeeperHandler1058 extends AbstractShopKeeperSkin {

    private final CosmeticsPlugin plugin;

    public ShopKeeperHandler1058() {
        this.plugin = CosmeticsPlugin.getPlugin(CosmeticsPlugin.class);
    }

    @EventHandler
    public void onGameStart1058(GameStateChangeEvent event) {
        if (!enabled() || !event.getNewState().name().equals("playing")) {
            return;
        }

        String worldName = event.getArena().getWorldName();
        arenas.put(worldName, true);
        ShopKeeperSkinsUtils.clearRuntimeDisplays(worldName);

        List<ITeam> teams = event.getArena().getTeams();
        DebugUtil.addMessage("Executing ShopKeeper Skins for arena " + event.getArena().getArenaName());
        new BukkitRunnable() {
            @Override
            public void run() {
                for (ITeam team : teams) {
                    if (team.getMembers().isEmpty()) {
                        continue;
                    }

                    Location shopLocation = team.getShop();
                    Location upgradeLocation = team.getTeamUpgrades();
                    Player player = team.getMembers().get(MathUtil.getRandom(0, team.getMembers().size() - 1));

                    DebugUtil.addMessage("Executing ShopKeeper Skins for team " + team.getName());
                    execute(player, shopLocation, upgradeLocation, ShopKeeperHandler1058.this::openShop, ShopKeeperHandler1058.this::openUpgrades);
                }
            }
        }.runTaskLater(plugin, 30L);
    }

    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
        handleNpcTeleport(event);
    }

    @EventHandler
    public void onGameEnd1058(GameEndEvent event) {
        removeArenaLater(event.getArena().getWorldName());
    }

    private void openShop(Player player) {
        IArena arena = Arena.getArenaByPlayer(player);
        if (arena == null || arena.getStatus() != GameState.playing || !arena.isPlayer(player)) {
            return;
        }

        ShopManager.shop.open(player, PlayerQuickBuyCache.getQuickBuyCache(player.getUniqueId()), true);
    }

    private void openUpgrades(Player player) {
        IArena arena = Arena.getArenaByPlayer(player);
        if (arena == null || arena.getStatus() != GameState.playing || !arena.isPlayer(player)) {
            return;
        }

        UpgradesManager.getMenuForArena(arena).open(player);
    }
}
