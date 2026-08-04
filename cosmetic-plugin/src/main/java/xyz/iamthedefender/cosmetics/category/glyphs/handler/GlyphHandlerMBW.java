package xyz.iamthedefender.cosmetics.category.glyphs.handler;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.game.spawner.Spawner;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;
import xyz.iamthedefender.cosmetics.category.glyphs.AbstractGlyph;

import java.util.Arrays;

public class GlyphHandlerMBW extends AbstractGlyph {

    @EventHandler
    public void onGenCollectSBW(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItem();

        Arena arena = de.marcely.bedwars.api.BedwarsAPI.getGameAPI().getArenaByPlayer(player);

        if (arena == null) return;

        if (item.getItemStack().getType() != Material.DIAMOND && item.getItemStack().getType() != Material.EMERALD)
            return;

        Spawner spawner = arena.getSpawners().stream().filter(itemSpawner -> itemSpawner.exists() &&
                        Arrays.stream(itemSpawner.getDropType().getConfigDroppingMaterials()).anyMatch(someItem -> someItem.getType() == item.getItemStack().getType()))
                .sorted((o1, o2) -> o1.getLocation().distance(player.getLocation()) > o2.getLocation().distance(player.getLocation()) ? 1 : -1)
                .findFirst().orElse(null);

        if (spawner == null) return;

        Location loc = spawner.getLocation().toLocation(player.getWorld());

        execute(player, loc.add(0, 8, 0).subtract(2, 0, 0));
    }

}
