package xyz.iamthedefender.cosmetics.category.shopkeeperskins;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerTeleportEvent;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.ShopKeeperSkin;
import xyz.iamthedefender.cosmetics.api.event.AbstractListener;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.utils.ShopKeeperSkinsUtils;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AbstractShopKeeperSkin extends AbstractListener {

    public static final Map<String, Boolean> arenas = new HashMap<>();

    public boolean enabled() {
        return StartupUtils.isFeatureEnabled("shopkeeper-skins");
    }

    protected void execute(Player player, Location shopLocation, Location upgradeLocation,
                           Consumer<Player> shopInteraction, Consumer<Player> upgradeInteraction) {
        ShopKeeperSkin skin = getSelectedCosmetic(player, CosmeticType.SHOPKEEPER_SKINS);
        if (skin == null || shopLocation == null || upgradeLocation == null) {
            return;
        }

        World world = shopLocation.getWorld();
        if (world == null) {
            return;
        }

        world.getEntities().stream()
                .filter(entity -> entity instanceof Villager
                        && (isSameLocation(entity.getLocation(), shopLocation) || isSameLocation(entity.getLocation(), upgradeLocation)))
                .forEach(Entity::remove);

        ShopKeeperSkinsUtils.spawnRuntimeShopKeeper(player, shopLocation, skin.getIdentifier(), shopInteraction);
        ShopKeeperSkinsUtils.spawnRuntimeShopKeeper(player, upgradeLocation, skin.getIdentifier(), upgradeInteraction);
    }

    public void handleNpcTeleport(PlayerTeleportEvent event) {
        if (!enabled() || event.getTo() == null || event.getTo().getWorld() == null) {
            return;
        }

        String destinationWorld = event.getTo().getWorld().getName();
        if (!arenas.containsKey(destinationWorld)) {
            return;
        }

        Run.delayed(() -> ShopKeeperSkinsUtils.spawnRuntimeDisplaysForWorld(destinationWorld, event.getPlayer()), 5L);
    }

    public void removeArenaLater(String arenaName) {
        if (!enabled()) {
            return;
        }

        Run.delayed(() -> {
            arenas.remove(arenaName);
            ShopKeeperSkinsUtils.clearRuntimeDisplays(arenaName);
        }, 20L);
    }

    public static boolean canSeeRuntimeShopkeepers(Player player) {
        return player != null && player.isOnline() && player.getWorld() != null;
    }

    private boolean isSameLocation(Location first, Location second) {
        return first.getWorld() != null
                && second.getWorld() != null
                && first.getWorld().equals(second.getWorld())
                && first.distanceSquared(second) <= 0.2D;
    }
}
