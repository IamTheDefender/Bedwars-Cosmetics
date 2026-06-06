package xyz.iamthedefender.cosmetics.category.shopkeeperskins.handler;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.events.BedwarsGameEndEvent;
import org.screamingsandals.bedwars.api.events.BedwarsGameStartedEvent;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.GameStore;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.ShopKeeperSkin;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.utils.ShopKeeperSkinsUtils;
import xyz.iamthedefender.cosmetics.util.BedWarsWrapper;
import xyz.iamthedefender.cosmetics.util.CosmeticsUtil;
import xyz.iamthedefender.cosmetics.util.MathUtil;

import java.util.List;
import java.util.stream.Collectors;

public class ShopKeeperHandlerSBW extends AbstractShopKeeperSkin {

    @EventHandler
    public void onGameStateChange(BedwarsGameStartedEvent event) {
        Game game = event.getGame();
        if (!enabled()) {
            return;
        }

        Run.delayed(() -> {
            String worldName = game.getGameWorld().getName();
            arenas.put(worldName, true);
            ShopKeeperSkinsUtils.clearRuntimeDisplays(worldName);

            for (RunningTeam runningTeam : game.getRunningTeams()) {
                if (runningTeam.getConnectedPlayers().isEmpty()) {
                    continue;
                }

                List<org.bukkit.Location> storeLocations = BedWarsWrapper.wrap(runningTeam).getStoreLocations();
                List<GameStore> gameStores = game.getGameStores().stream()
                        .filter(gameStore -> gameStore.getStoreLocation() != null
                                && storeLocations.stream().anyMatch(location -> location.distanceSquared(gameStore.getStoreLocation()) <= 0.5))
                        .map(gameStore -> (GameStore) gameStore)
                        .collect(Collectors.toList());

                if (gameStores.isEmpty()) {
                    continue;
                }

                Player player = runningTeam.getConnectedPlayers().get(MathUtil.getRandom(0, runningTeam.getConnectedPlayers().size() - 1));
                ShopKeeperSkin shopKeeperSkin = CosmeticsUtil.getShopKeeperSkin(player);
                if (shopKeeperSkin == null) {
                    continue;
                }

                for (GameStore gameStore : gameStores) {
                    EntityType entityType = shopKeeperSkin.getField(FieldsType.ENTITY_TYPE, player);
                    if (entityType != null) {
                        gameStore.setEntityType(entityType);
                        gameStore.kill();
                        gameStore.spawn();
                        continue;
                    }

                    gameStore.kill();
                    ShopKeeperSkinsUtils.spawnRuntimeShopKeeper(player, gameStore.getStoreLocation(), shopKeeperSkin.getIdentifier(),
                            viewer -> open(gameStore, viewer, game));
                }
            }
        }, 10L);
    }

    @EventHandler
    public void onGameEnd(BedwarsGameEndEvent event) {
        removeArenaLater(event.getGame().getGameWorld().getName());
    }

    private void open(GameStore store, Player player, Game game) {
        if (Main.getConfigurator().config.getBoolean("disable-opening-stores-of-other-teams")
                && store.getTeam() != null
                && game.getTeamOfPlayer(player) instanceof CurrentTeam
                && store.getTeam() != ((CurrentTeam) game.getTeamOfPlayer(player)).teamInfo) {
            return;
        }

        Main.openStore(player, store);
    }
}
