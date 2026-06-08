package xyz.iamthedefender.cosmetics.menu.data;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;

import java.util.List;

@Getter
public class CosmeticMenuItemData {

    private final CosmeticType<?> cosmeticType;
    private final String id;
    private final String categoryName;
    private final ItemStack itemStack;
    private final int price;
    private final RarityType rarity;
    private final String formattedName;
    private final List<String> lore;

    public CosmeticMenuItemData(CosmeticType<?> cosmeticType, String id, String categoryName, ItemStack itemStack,
                                int price, RarityType rarity, String formattedName, List<String> lore) {
        this.cosmeticType = cosmeticType;
        this.id = id;
        this.categoryName = categoryName;
        this.itemStack = itemStack;
        this.price = price;
        this.rarity = rarity;
        this.formattedName = formattedName;
        this.lore = lore;
    }
}
