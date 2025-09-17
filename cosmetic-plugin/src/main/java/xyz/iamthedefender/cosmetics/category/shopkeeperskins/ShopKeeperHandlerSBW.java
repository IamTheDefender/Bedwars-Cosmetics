package xyz.iamthedefender.cosmetics.category.shopkeeperskins;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.BedwarsAPI;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.events.BedwarsGameStartedEvent;
import org.screamingsandals.bedwars.api.events.BedwarsOpenShopEvent;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.GameStore;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.ShopKeeperSkin;
import xyz.iamthedefender.cosmetics.api.handler.HandlerType;
import xyz.iamthedefender.cosmetics.api.handler.IHandler;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.util.BedWarsWrapper;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.MathUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.List;

public class ShopKeeperHandlerSBW implements Listener {

    @EventHandler
    public void onGameStateChange(BedwarsGameStartedEvent event) {
        Game game = event.getGame();

        if (!enabled()) return;

        Run.delayed(() -> {
            ShopKeeperHandler1058.arenas.put(game.getGameWorld().getName(), true);

            for (RunningTeam runningTeam : game.getRunningTeams()) {
                if (runningTeam.getConnectedPlayers().isEmpty()) continue;

                List<Location> storeLocations = BedWarsWrapper.wrap(runningTeam).getStoreLocations();

                World world = runningTeam.getTeamSpawn().getWorld();
                DebugUtil.addMessage("Executing ShopKeeper Skins for team " + runningTeam.getName());

                // Delete existing NPCs
                for (Location shopLocation : storeLocations) {
                    world.getEntities().stream()
                            .filter(e -> (e.getLocation().distance(shopLocation) <= 0.2))
                            .forEach(Entity::remove);
                }

                // Choose random player from the team
                Player player = runningTeam.getConnectedPlayers().get(MathUtil.getRandom(0, runningTeam.getConnectedPlayers().size() - 1));
                String skin = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(player, CosmeticsType.ShopKeeperSkin);
                DebugUtil.addMessage("Selected skin: " + skin);
                // Spawn new NPCs
                for (ShopKeeperSkin skins : StartupUtils.shopKeeperSkinList) {
                    if (skin.equals(skins.getIdentifier())) {
                        try {
                            skins.execute(player, storeLocations);
                        } catch (Exception ignored) {
                        }
                    }
                }


                if (CosmeticsPlugin.getInstance().getHandler().getHandlerType() != HandlerType.BUNGEE) {
                    for (Player p : runningTeam.getConnectedPlayers()) {
                        IHandler handler = CosmeticsPlugin.getInstance().getHandler();
                        handler.getScoreboardUtil().removePlayerScoreboard(p);
                        handler.getScoreboardUtil().giveScoreboard(p, true);
                    }
                }
            }

        }, 40L);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        Entity clicked = event.getRightClicked();

        if (!clicked.hasMetadata("shop_entity_cosmetics")) return;

        Game game = BedwarsAPI.getInstance().getGameOfPlayer(player);

        if (game == null) return;

        RunningTeam team = game.getTeamOfPlayer(player);

        if (team == null) return;

        game.getGameStores().stream()
                .filter(gameStore -> gameStore.getStoreLocation().distance(clicked.getLocation()) <= 0.2)
                .filter(gameStore -> gameStore instanceof GameStore)
                .map(gameStore -> (GameStore) gameStore)
                .forEach(gameStore -> open(gameStore, event, game));

        event.setCancelled(true);
    }

    /**
     * Opens the shop, copied from <a href="https://github.com/ScreamingSandals/BedWars/blob/ver/0.2.x/plugin/src/main/java/org/screamingsandals/bedwars/listener/VillagerListener.java">ScreamingBedWars</a>]
     * @param store the game store instance
     * @param event the interaction event
     * @param game the game instance
     */
    public void open(GameStore store, PlayerInteractEntityEvent event, Game game) {
        BedwarsOpenShopEvent openShopEvent = new BedwarsOpenShopEvent(game,
                event.getPlayer(), store, event.getRightClicked());

        if (Main.getConfigurator().config.getBoolean("disable-opening-stores-of-other-teams")
                && store.getTeam() != null
                && store.getTeam() != ((CurrentTeam) game.getTeamOfPlayer(event.getPlayer())).teamInfo) {
            openShopEvent.setResult(BedwarsOpenShopEvent.Result.DISALLOW_WRONG_TEAM);
        }

        Main.getInstance().getServer().getPluginManager().callEvent(openShopEvent);

        if (openShopEvent.getResult() != BedwarsOpenShopEvent.Result.ALLOW) {
            return;
        }

        Main.openStore(event.getPlayer(), store);
    }

    private boolean enabled() {
        return CosmeticsPlugin.getInstance().getConfig().getBoolean("shopkeeper-skins.enabled");
    }
}
