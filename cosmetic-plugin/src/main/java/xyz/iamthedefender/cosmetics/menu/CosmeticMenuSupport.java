package xyz.iamthedefender.cosmetics.menu;

import com.cryptomorin.xseries.XSound;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.event.CosmeticPurchaseEvent;
import xyz.iamthedefender.cosmetics.api.menu.ClickableItem;
import xyz.iamthedefender.cosmetics.api.util.Constants;
import xyz.iamthedefender.cosmetics.api.util.ItemBuilder;
import xyz.iamthedefender.cosmetics.api.util.Message;
import xyz.iamthedefender.cosmetics.api.util.Messages;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;
import xyz.iamthedefender.cosmetics.util.StringUtils;
import xyz.iamthedefender.cosmetics.util.VaultUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CosmeticMenuSupport {

    public static List<CosmeticMenuItemData> loadCategoryItems(Player player, CosmeticType<?> type) {
        return loadCategoryItems(player, type, false);
    }

    public static List<CosmeticMenuItemData> loadCategoryItems(Player player, CosmeticType<?> type, boolean includeCategoryLore) {
        List<CosmeticMenuItemData> items = new ArrayList<>();
        if (type == null) {
            return items;
        }

        ConfigurationSection section = type.getConfig().getYml().getConfigurationSection(type.getSectionKey());
        if (section == null) {
            return items;
        }

        for (String id : section.getKeys(false)) {
            CosmeticMenuItemData item = loadItem(player, type, id, includeCategoryLore);
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

    public static List<CosmeticMenuItemData> loadAllItems(Player player, boolean includeCategoryLore) {
        List<CosmeticMenuItemData> items = new ArrayList<>();
        for (CosmeticType<?> type : CosmeticType.values()) {
            items.addAll(loadCategoryItems(player, type, includeCategoryLore));
        }
        return items;
    }

    public static CosmeticMenuItemData loadItem(Player player, CosmeticType<?> type, String id, boolean includeCategoryLore) {
        if (type == null || id == null) {
            return null;
        }

        ConfigManager configManager = type.getConfig();
        FileConfiguration config = configManager.getYml();
        String path = type.getSectionKey() + "." + id + ".";

        ItemStack stack = configManager.getItemStack(path + "item");
        if (stack == null || config.getBoolean(path + "disabled")) {
            return null;
        }

        String rarityValue = config.getString(path + "rarity");
        if (rarityValue == null) {
            return null;
        }

        int price = config.getInt(path + "price");
        RarityType rarity = RarityType.valueOf(rarityValue.toUpperCase());
        String categoryName = resolveCategoryName(player, type);
        String formattedName = Messages.cosmeticDisplayName(path, Utility.getMSGLang(player, "cosmetics." + path + "name")).value(player);
        List<String> lore = Messages.cosmeticDisplayLore(path, Utility.getListLang(player, "cosmetics." + path + "lore")).list(player);

        List<String> formattedLore = StringUtils.formatLore(
                new ArrayList<>(lore),
                formattedName,
                price,
                getItemStatus(player, type, id, price),
                rarity.getChatColor() + rarity.toString()
        );

        if (includeCategoryLore) {
            List<String> loreWithCategory = new ArrayList<>();
            loreWithCategory.add("&8Category: &7" + categoryName);
            loreWithCategory.add("");
            loreWithCategory.addAll(formattedLore);
            formattedLore = loreWithCategory;
        }

        return new CosmeticMenuItemData(type, id, categoryName, stack, price, rarity, formattedName, formattedLore);
    }

    public static ClickableItem createClickableItem(Player player, CosmeticMenuItemData itemData, Runnable refreshAction) {
        ClickStatus status = handleLeftClick(player, itemData, true, null);
        ItemStack stack = itemData.getItemStack().clone();
        String colorCode = status == ClickStatus.INSUFFICIENT_FUNDS || status == ClickStatus.NOT_PURCHASABLE ? "&c" : "&a";

        if (status == ClickStatus.ALREADY_SELECTED) {
            stack.addUnsafeEnchantment(Enchantment.LUCK, 1);
        }

        ItemStack display = new ItemBuilder(stack)
                .name(colorCode + itemData.getFormattedName())
                .lore(itemData.getLore())
                .itemFlag(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES)
                .build();

        return new ClickableItem(display, (event) -> {
            if (event.getClick() == ClickType.RIGHT) {
                previewClick(player, itemData, refreshAction);
                return;
            }

            if (event.getClick() == ClickType.LEFT) {
                handleLeftClick(player, itemData, false, refreshAction);
            }
        });
    }

    public static String getItemStatus(Player player, CosmeticType<?> type, String id, int price) {
        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(player, type);
        if (selected.equals(id)) {
            return Messages.SELECTED.value(player);
        }

        if (player.hasPermission(type.getPermissionFormat() + "." + id)) {
            return Messages.CLICK_TO_SELECT.value(player);
        }

        if (type.getConfig().getString(type.getSectionKey() + "." + id + ".purchase-able") != null
                && !type.getConfig().getBoolean(type.getSectionKey() + "." + id + ".purchase-able")) {
            return Messages.LOCKED.value(player);
        }

        if (CosmeticsPlugin.getInstance().getEconomy().getBalance(player) >= price) {
            return Messages.CLICK_TO_PURCHASE.value(player);
        }

        return Messages.LOCKED.value(player);
    }

    public static ClickStatus handleLeftClick(Player player, CosmeticMenuItemData itemData, boolean onlyCheck, Runnable refreshAction) {
        CosmeticType<?> type = itemData.getCosmeticType();
        String id = itemData.getId();
        int price = itemData.getPrice();
        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(player, type);
        String permissionFormat = type.getPermissionFormat();
        Economy economy = VaultUtils.getEconomy();
        Permission permission = VaultUtils.getPermissions();

        if (selected.equals(id)) {
            DebugUtil.addMessage("Cosmetic " + id + " is already selected");
            return ClickStatus.ALREADY_SELECTED;
        }

        if (!player.hasPermission(permissionFormat + "." + id)) {
            DebugUtil.addMessage("Cosmetic " + id + " is not unlocked (No permission)");

            if (!type.getConfig().getBoolean(type.getSectionKey() + "." + id + ".purchase-able", true)) {
                DebugUtil.addMessage("Cosmetic " + id + " is not purchase-able");
                return ClickStatus.NOT_PURCHASABLE;
            }

            if (economy == null || economy.getBalance(Bukkit.getOfflinePlayer(player.getUniqueId())) < price) {
                DebugUtil.addMessage("Cosmetic " + id + " is not purchase-able (no coins)");
                if (onlyCheck) {
                    return ClickStatus.INSUFFICIENT_FUNDS;
                }

                player.playSound(player.getLocation(), XSound.ENTITY_ENDERMAN_TELEPORT.parseSound(), 1.0f, 1.0f);
                return ClickStatus.INSUFFICIENT_FUNDS;
            }

            if (onlyCheck) {
                DebugUtil.addMessage("Cosmetic " + id + " is purchase-able");
                return ClickStatus.PURCHASABLE;
            }

            CosmeticPurchaseEvent event = new CosmeticPurchaseEvent(player, type);
            Bukkit.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                DebugUtil.addMessage("Cosmetic " + id + " is not purchase-able (event cancelled)");
                return ClickStatus.PURCHASE_CANCELLED;
            }

            if (permission != null) {
                permission.playerAdd(player, permissionFormat + "." + id);
            }

            CosmeticsPlugin.getInstance().getApi().setSelectedCosmetic(player, type, id);
            economy.withdrawPlayer(player, price);
            player.playSound(player.getLocation(), XSound.ENTITY_VILLAGER_YES.parseSound(), 1.0f, 1.0f);
            if (refreshAction != null) {
                refreshAction.run();
            }

            DebugUtil.addMessage("Selected " + id + " for " + type + " and paid " + price + " coins");
            return ClickStatus.PURCHASED_AND_SELECTED;
        }

        if (onlyCheck) {
            return ClickStatus.UNLOCKED;
        }

        DebugUtil.addMessage("Selected " + id + " for " + type);
        CosmeticsPlugin.getInstance().getApi().setSelectedCosmetic(player, type, id);
        XSound.ENTITY_VILLAGER_YES.play(player);
        if (refreshAction != null) {
            refreshAction.run();
        }
        return ClickStatus.SELECTED;
    }

    public static void previewClick(Player player, CosmeticMenuItemData itemData, Runnable refreshAction) {
        Cosmetics cosmetics = CosmeticsPlugin.findCosmetic(itemData.getId(), itemData.getCosmeticType());
        if (cosmetics == null) {
            return;
        }

        if (cosmetics.getRarity() == RarityType.NONE) {
            XSound.ENTITY_VILLAGER_NO.play(player, 1.0f, 1.0f);
            return;
        }

        Location playerLocation = StartupUtils.getPlayerLocation();
        Location previewLocation = StartupUtils.getCosmeticLocation();
        AtomicBoolean found = new AtomicBoolean(false);

        CosmeticsPlugin.getInstance().getPreviewList().stream()
                .filter(preview -> preview.getType() == itemData.getCosmeticType())
                .findAny()
                .ifPresent(preview -> {
                    preview.showPreview(player, cosmetics, previewLocation, playerLocation);
                    found.set(true);
                });

        if (!found.get()) {
            handleLeftClick(player, itemData, false, refreshAction);
        }
    }

    private static String resolveCategoryName(Player player, CosmeticType<?> cosmeticType) {
        Message defaultTitle = Messages.of(
                cosmeticType.name().toLowerCase() + Constants.Key.COSMETICS_MENU_TITLES_SUFFIX,
                cosmeticType.getFormatedName()
        );
        return defaultTitle.value(player);
    }

    public enum ClickStatus {
        ALREADY_SELECTED,
        UNLOCKED,
        PURCHASABLE,
        INSUFFICIENT_FUNDS,
        NOT_PURCHASABLE,
        PURCHASE_CANCELLED,
        PURCHASED_AND_SELECTED,
        SELECTED
    }
}
