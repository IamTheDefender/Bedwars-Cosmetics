package xyz.iamthedefender.cosmetics.category.woodskin.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.api.events.BedwarsItemBoughtEvent;
import org.screamingsandals.bedwars.api.events.BedwarsOpenShopEvent;
import xyz.iamthedefender.cosmetics.api.handler.IArenaHandler;
import xyz.iamthedefender.cosmetics.category.woodskin.AbstractWoodSkin;
import xyz.iamthedefender.cosmetics.util.BedWarsWrapper;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WoodSkinSBW extends AbstractWoodSkin {

    @EventHandler
    public void onShopBuy(BedwarsItemBoughtEvent e) {
        if (!enabled()) return;

        Player p = e.getCustomer();
        ItemStack stack = e.getItem();

        applyToStack(p, stack);
    }

    private final Set<UUID> shopOpening = ConcurrentHashMap.newKeySet();

    @EventHandler
    public void onShopPreOpen(BedwarsOpenShopEvent e) {
        Player p = e.getPlayer();
        shopOpening.add(p.getUniqueId());
    }

    @EventHandler
    public void onShopOpen(InventoryOpenEvent e) {
        Inventory inv = e.getInventory();

        if (!shopOpening.contains(e.getPlayer().getUniqueId())) return;

        Player p = (Player) e.getPlayer();

        shopOpening.remove(p.getUniqueId());

        IArenaHandler arena = BedWarsWrapper.wrap(p);
        if (arena == null) return;

        applyToInventory(p, inv);
    }
}

