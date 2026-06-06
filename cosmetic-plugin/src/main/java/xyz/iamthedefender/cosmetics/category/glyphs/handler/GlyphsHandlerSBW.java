package xyz.iamthedefender.cosmetics.category.glyphs.handler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.api.game.ItemSpawner;
import xyz.iamthedefender.cosmetics.category.glyphs.AbstractGlyph;

public class GlyphsHandlerSBW extends AbstractGlyph {

    @EventHandler
    public void onGenCollectSBW(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItem();

        if (!Main.isPlayerInGame(player)) return;

        Game game = Main.getPlayerGameProfile(player).getGame();

        if (game == null) return;

        if (item.getItemStack().getType() != Material.DIAMOND && item.getItemStack().getType() != Material.EMERALD)
            return;

        ItemSpawner spawner = game.getItemSpawners().stream().filter(itemSpawner -> itemSpawner.getItemSpawnerType().getMaterial() == item.getItemStack().getType())
                .sorted((o1, o2) -> o1.getLocation().distance(player.getLocation()) > o2.getLocation().distance(player.getLocation()) ? 1 : -1)
                .findFirst().orElse(null);

        if (spawner == null) return;

        Location loc = spawner.getLocation().clone();

        execute(player, loc.add(0, 8, 0).subtract(2, 0, 0));
    }

}


