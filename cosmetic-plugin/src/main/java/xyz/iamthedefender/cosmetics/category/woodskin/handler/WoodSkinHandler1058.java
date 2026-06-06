package xyz.iamthedefender.cosmetics.category.woodskin.handler;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.shop.IBuyItem;
import com.andrei1058.bedwars.api.events.shop.ShopBuyEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.category.woodskin.AbstractWoodSkin;
import xyz.iamthedefender.cosmetics.api.util.Utility;

public class WoodSkinHandler1058 extends AbstractWoodSkin {

    @EventHandler
    public void onShopBuy(ShopBuyEvent e) {
        if (!enabled()) return;

        Player p = e.getBuyer();
        IBuyItem item = e.getCategoryContent().getContentTiers().get(0).getBuyItemsList().get(0);
        ItemStack stack = item.getItemStack();

        applyToStack(p, stack);
    }

    @EventHandler
    public void onShopOpen(InventoryOpenEvent e) {
        Inventory inv = e.getInventory();

        Player p = (Player) e.getPlayer();
        IArena arena = BedWars.getAPI().getArenaUtil().getArenaByPlayer(p);
        if (arena == null) return;

        boolean isWoodSkinInventory =
                e.getView().getTitle().equals(Utility.getMSGLang(p, "shop-items-messages.inventory-name"))
                        || e.getView().getTitle().equals(Utility.getMSGLang(p, "shop-items-messages.blocks-category.inventory-name"))
                        || e.getView().getTitle().equals(Utility.getMSGLang(p, "shop-items-messages.quick-buy-add-inventory-name"));

        if(!isWoodSkinInventory) return;

        applyToInventory(p, inv);
    }
}

