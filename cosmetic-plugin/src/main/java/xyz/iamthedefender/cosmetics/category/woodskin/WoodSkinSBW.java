package xyz.iamthedefender.cosmetics.category.woodskin;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.api.events.BedwarsItemBoughtEvent;
import org.screamingsandals.bedwars.api.events.BedwarsOpenShopEvent;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.WoodSkin;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WoodSkinSBW implements Listener {

    @EventHandler
    public void onShopBuy(BedwarsItemBoughtEvent e) {
        boolean isWoodSkinsEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("wood-skins.enabled");
        if (!isWoodSkinsEnabled) return;

        Player p = e.getCustomer();
        ItemStack stack = e.getItem();

        if (Utility.isWoodOrLogBlock(stack.getType())) {
            String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(p, CosmeticsType.WoodSkins);
            WoodSkin selectedWoodSkin = find(selected);

            if (selectedWoodSkin == null) return;

            ItemStack configItem = (ItemStack) selectedWoodSkin.getField(FieldsType.ITEM_STACK, p);

            stack.setType(configItem.getType());
            stack.setDurability(configItem.getDurability());
        }
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

        IArena arena = BedWars.getAPI().getArenaUtil().getArenaByPlayer(p);
        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(p, CosmeticsType.WoodSkins);
        WoodSkin selectedWoodSkin = find(selected);

        if (selectedWoodSkin == null) return;

        if (arena == null) return;

        ItemStack item = (ItemStack) selectedWoodSkin.getField(FieldsType.ITEM_STACK, p);

        if (item == null) return;

        for (ItemStack itemStack : inv.getContents()) {
            if (itemStack == null) continue;
            if (itemStack.getType() == XMaterial.AIR.parseMaterial()) continue;
            if (!Utility.isWoodOrLogBlock(itemStack.getType())) continue;

            itemStack.setType(item.getType());
            itemStack.setDurability(item.getDurability());
        }
    }

    private WoodSkin find(String selected) {
        if (selected == null) return null;

        for (WoodSkin woodSkin : StartupUtils.woodSkinsList) {
            if (woodSkin.getIdentifier().equals(selected)) {
                return woodSkin;
            }
        }
        return null;
    }
}
