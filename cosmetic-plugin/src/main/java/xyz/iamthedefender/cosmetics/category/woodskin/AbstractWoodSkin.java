package xyz.iamthedefender.cosmetics.category.woodskin;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.WoodSkin;
import xyz.iamthedefender.cosmetics.api.event.AbstractListener;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

public class AbstractWoodSkin extends AbstractListener {

    public boolean enabled() {
        return StartupUtils.isFeatureEnabled("wood-skins");
    }

    public void applyToStack(Player player, ItemStack stack) {
        if (stack == null || !Utility.isWoodOrLogBlock(stack.getType())) return;

        WoodSkin selectedWoodSkin = getSelectedCosmetic(player, CosmeticType.WOOD_SKINS);
        if (selectedWoodSkin == null) return;

        ItemStack configItem = (ItemStack) selectedWoodSkin.getField(FieldsType.ITEM_STACK, player);
        if (configItem == null) return;

        stack.setType(configItem.getType());
        stack.setDurability(configItem.getDurability());
    }

    public void applyToInventory(Player player, Inventory inventory) {
        WoodSkin selectedWoodSkin = getSelectedCosmetic(player, CosmeticType.WOOD_SKINS);
        if (selectedWoodSkin == null) return;

        ItemStack item = (ItemStack) selectedWoodSkin.getField(FieldsType.ITEM_STACK, player);
        if (item == null) return;

        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null) continue;
            if (itemStack.getType() == XMaterial.AIR.parseMaterial()) continue;
            if (!Utility.isWoodOrLogBlock(itemStack.getType())) continue;

            itemStack.setType(item.getType());
            itemStack.setDurability(item.getDurability());
        }
    }
}
