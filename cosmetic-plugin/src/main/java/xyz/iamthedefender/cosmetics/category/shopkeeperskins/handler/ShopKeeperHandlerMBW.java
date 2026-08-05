package xyz.iamthedefender.cosmetics.category.shopkeeperskins.handler;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.arena.Team;
import de.marcely.bedwars.api.event.arena.ArenaPreparingStartEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.events.BedwarsGameEndEvent;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.GameStore;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.utils.ShopKeeperSkinsUtils;
import xyz.iamthedefender.cosmetics.util.BedWarsWrapper;

import java.util.List;

public class ShopKeeperHandlerMBW extends AbstractShopKeeperSkin {

    @EventHandler
    public void onGameStateChange(ArenaPreparingStartEvent event) {
        Arena arena = event.getArena();

        if (!enabled()) {
            return;
        }

        Run.delayed(() -> {
            String worldName = arena.getGameWorldName();
            arenas.put(worldName, true);
            ShopKeeperSkinsUtils.clearRuntimeDisplays(worldName);

            for (Team team : arena.getEnabledTeams()) {
                if (arena.getPlayersInTeam(team).isEmpty()) continue;

                List<Location> storeLocations = BedWarsWrapper.wrap(team, arena).getStoreLocations();


                //TODO: implement this
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
