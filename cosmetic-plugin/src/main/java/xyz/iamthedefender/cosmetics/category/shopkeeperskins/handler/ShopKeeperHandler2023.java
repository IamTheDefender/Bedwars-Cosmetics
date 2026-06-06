package xyz.iamthedefender.cosmetics.category.shopkeeperskins.handler;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.events.gameplay.GameEndEvent;
import com.tomkeuper.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.tomkeuper.bedwars.arena.Arena;
import com.tomkeuper.bedwars.shop.ShopManager;
import com.tomkeuper.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.handler.HandlerType;
import xyz.iamthedefender.cosmetics.api.handler.IHandler;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.utils.ShopKeeperSkinsUtils;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.MathUtil;

import java.util.List;

public class ShopKeeperHandler2023 extends AbstractShopKeeperSkin {

    @EventHandler
    public void onGameStart2023(GameStateChangeEvent event) {
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
                    execute(player, shopLocation, upgradeLocation, ShopKeeperHandler2023.this::openShop, ShopKeeperHandler2023.this::openUpgrades);

                    if (CosmeticsPlugin.getInstance().getHandler().getHandlerType() != HandlerType.BUNGEE) {
                        for (Player member : team.getMembers()) {
                            IHandler handler = CosmeticsPlugin.getInstance().getHandler();
                            handler.getScoreboardUtil().removePlayerScoreboard(member);
                            handler.getScoreboardUtil().giveScoreboard(member, true);
                        }
                    }
                }
            }
        }.runTaskLater(CosmeticsPlugin.getInstance(), 30L);
    }

    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
        handleNpcTeleport(event);
    }

    @EventHandler
    public void onGameEnd2023(GameEndEvent event) {
        removeArenaLater(event.getArena().getWorldName());
    }

    private void openShop(Player player) {
        IArena arena = Arena.getArenaByPlayer(player);
        if (arena == null || arena.getStatus() != GameState.playing || !arena.isPlayer(player)) {
            return;
        }

        ShopManager.shop.open(player, PlayerQuickBuyCache.getInstance().getQuickBuyCache(player.getUniqueId()), true);
    }

    private void openUpgrades(Player player) {
        IArena arena = Arena.getArenaByPlayer(player);
        if (arena == null || arena.getStatus() != GameState.playing || !arena.isPlayer(player)) {
            return;
        }

        BedWars.getUpgradeManager().getMenuForArena(arena).open(player);
    }
}
