package me.defender.cosmetics.menu;

import com.andrei1058.bedwars.BedWars;
import com.cryptomorin.xseries.XSound;
import com.hakan.core.HCore;
import com.hakan.core.ui.inventory.InventoryGui;
import com.hakan.core.ui.inventory.item.ClickableItem;
import com.hakan.core.ui.inventory.pagination.Pagination;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.category.deathcries.preview.DeathCryPreview;
import me.defender.cosmetics.api.category.killmessage.preview.KillMessagePreview;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.event.CosmeticPurchaseEvent;
import me.defender.cosmetics.api.util.StringUtils;
import me.defender.cosmetics.api.util.VaultUtils;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.util.Utility;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CategoryMenu extends InventoryGui {

    ConfigManager config;
    CosmeticsType cosmeticsType;
    String title;
    List<Integer> slots;

    public CategoryMenu(CosmeticsType type) {
        super(type.getFormatedName(), type.getFormatedName(), 6, InventoryType.CHEST);
        this.config = type.getConfig();
        this.cosmeticsType = type;
        title = type.getFormatedName();
        slots = config.getYml().getIntegerList("slots");
        if(slots.isEmpty()){
            slots = Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34);
        }
    }

    @Override
    public void onOpen(@NotNull Player player) {
        toInventory().clear();
        List<ClickableItem> items = new ArrayList<>();
        ConfigManager configManager = cosmeticsType.getConfig();
        ConfigurationSection section = config.getYml().getConfigurationSection(cosmeticsType.getSectionKey());
        if(section == null) return;
        Map<ClickableItem, RarityType> rarityMap = new HashMap<>();

        // Set up the items
        for(String id : section.getKeys(false)) {
            // set the variables
            String path = cosmeticsType.getSectionKey() + "." + id + ".";

            ItemStack stack = configManager.getItemStack(path + "item");
            int price = config.getInt(path + "price");
            RarityType rarity = RarityType.valueOf(config.getString(path + "rarity").toUpperCase());
            // From language file
            String formattedName = Utility.getMSGLang(player, "cosmetics." + path + "name");
            List<String> lore = Utility.getListLang(player ,"cosmetics." + path + "lore");
            lore = StringUtils.formatLore(lore, formattedName, price, getItemStatus(player, cosmeticsType, id, price), rarity.getChatColor() + rarity.toString());


            // Items
            ClickableItem item = null;
            List<String> lore1 = new ArrayList<>(lore);
            if(stack != null) {
                String colorCode = "&a";
                int returnValue = onClick(player, cosmeticsType, price, id, true);
                if(returnValue == 2){
                    colorCode = "&c";
                }
                if(returnValue == 0){
                    stack.addUnsafeEnchantment(Enchantment.LUCK, 1);
                }
                item = new ClickableItem(HCore.itemBuilder(stack).addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES).name(true, colorCode + formattedName).lores(true, lore1).build(), (e) -> {
                    if(e.getClick() == ClickType.RIGHT){
                        previewClick(player, cosmeticsType, id, price);
                    }else if (e.getClick() == ClickType.LEFT){
                        onClick(player, cosmeticsType, price, id, false);
                    }
                });
            }
            if(item != null) {
                items.add(item);
                rarityMap.put(item, rarity);
            }
        }
        // Page system
        Pagination pages = new Pagination(this);
        pages.setSlots(slots);
        pages.setItems(items);
        addItemsAccordingToRarity(rarityMap);
        if(Utility.plugin().getConfig().getBoolean("BackItemInCosmeticsMenu")) {
            setItem(49, HCore.itemBuilder(Material.ARROW).name(true, "&aBack").build(), (e) -> {
                Utility.openMainMenu((Player) e.getWhoClicked());
            });
        }
        Utility.createPages(pages, this, title);
    }


    public int findFirstEmptySlot(Inventory inventory) {
        for (Integer slot : slots) {
            if (inventory.getItem(slot) == null) {
                return slot;
            }
        }
        return -1;
    }
    
    public boolean isFull(Inventory inventory){
        return findFirstEmptySlot(inventory) == -1;
    }

    public void addItemsAccordingToRarity(Map<ClickableItem, RarityType> rarityMap){
        List<ClickableItem> noneItems = new ArrayList<>();
        List<ClickableItem> commonItems = new ArrayList<>();
        List<ClickableItem> rareItems = new ArrayList<>();
        List<ClickableItem> epicItems = new ArrayList<>();
        List<ClickableItem> legendaryItems = new ArrayList<>();

        for (Map.Entry<ClickableItem, RarityType> entry : rarityMap.entrySet()) {
            ClickableItem item = entry.getKey();
            RarityType rarity = entry.getValue();
            switch (rarity) {
                case NONE:
                    noneItems.add(item);
                    break;
                case COMMON:
                    commonItems.add(item);
                    break;
                case RARE:
                    rareItems.add(item);
                    break;
                case EPIC:
                    epicItems.add(item);
                    break;
                case LEGENDARY:
                    legendaryItems.add(item);
                    break;
            }
        }
        noneItems.sort(Comparator.comparing((ClickableItem item) -> ChatColor.stripColor(item.getItem().getItemMeta().getDisplayName())));
        commonItems.sort(Comparator.comparing((ClickableItem item) -> ChatColor.stripColor(item.getItem().getItemMeta().getDisplayName())));
        rareItems.sort(Comparator.comparing((ClickableItem item) -> ChatColor.stripColor(item.getItem().getItemMeta().getDisplayName())));
        epicItems.sort(Comparator.comparing((ClickableItem item) -> ChatColor.stripColor(item.getItem().getItemMeta().getDisplayName())));
        legendaryItems.sort(Comparator.comparing((ClickableItem item) -> ChatColor.stripColor(item.getItem().getItemMeta().getDisplayName())));



        for (ClickableItem clickableItem : noneItems) {
            if(!isFull(toInventory())) {
                super.setItem(findFirstEmptySlot(toInventory()), clickableItem);
            }
        }

        for (ClickableItem clickableItem : commonItems) {
            if(!isFull(toInventory())) {
                super.setItem(findFirstEmptySlot(toInventory()), clickableItem);
            }
        }

        for (ClickableItem clickableItem : rareItems) {
            if(!isFull(toInventory())) {
                super.setItem(findFirstEmptySlot(toInventory()), clickableItem);
            }
        }

        for (ClickableItem clickableItem : epicItems) {
            if(!isFull(toInventory())) {
                super.setItem(findFirstEmptySlot(toInventory()), clickableItem);
            }
        }

        for (ClickableItem clickableItem : legendaryItems) {
            if(!isFull(toInventory())) {
                super.setItem(findFirstEmptySlot(toInventory()), clickableItem);
            }
        }

    }


    public String getItemStatus(Player p, CosmeticsType type, String unformattedName, int price){
        String selected = new BwcAPI().getSelectedCosmetic(p, type);
        if(selected.equals(unformattedName)){
            return ColorUtil.colored(Utility.getMSGLang(p, "cosmetics.selected"));
        }

        if(p.hasPermission(type.getPermissionFormat() + "." + unformattedName)){
            return ColorUtil.colored(Utility.getMSGLang(p, "cosmetics.click-to-select"));
        }

        if(type.getConfig().getString(type.getSectionKey() + "." + unformattedName + ".purchase-able") != null){
            boolean purchaseAble = type.getConfig().getBoolean(type.getSectionKey() + "." + unformattedName + ".purchase-able");
            if(!purchaseAble){
                return ColorUtil.colored(Utility.getMSGLang(p, "cosmetics.not-purchase-able"));
            }
        }

        if(new BwcAPI().getEco().getBalance(p) >= price){
            return ColorUtil.colored(Utility.getMSGLang(p, "cosmetics.click-to-purchase"));
        }

        return ColorUtil.colored(Utility.getMSGLang(p, "cosmetics.no-coins"));
    }

    public int onClick(Player p, CosmeticsType type, int price, String id, boolean isOnlyForCheck) {
        BwcAPI api = new BwcAPI();
        String selected = api.getSelectedCosmetic(p, type);
        String permissionFormat = type.getPermissionFormat();
        Economy eco = VaultUtils.getEconomy();
        Permission perm = VaultUtils.getPermissions();
        // If the player did not have the item selected.
        if(!selected.equals(id)) {
            // Select
            if (p.hasPermission(permissionFormat + "." + id)) {
                if (isOnlyForCheck) return 0;
                api.setSelectedCosmetic(p, type, id);
                XSound.ENTITY_VILLAGER_YES.play(p);
                HCore.getInventoryByPlayer(p).open(p);

                // Can't purchase, cuz locked
            } else if(type.getConfig().getString(type.getSectionKey() + "." + id + ".purchase-able") != null){
                boolean purchaseAble = type.getConfig().getBoolean(type.getSectionKey() + "." + id + ".purchase-able");
                if(!purchaseAble){
                    XSound.ENTITY_VILLAGER_NO.play(p);
                }
                // Purchase
        } else if (eco != null && eco.getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= price) {
                if(isOnlyForCheck) return 1;
                CosmeticPurchaseEvent event = new CosmeticPurchaseEvent(p, type);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled())
                    return -1;
                // they bought the cosmetic and re-opened the GUI
                if (perm != null) {
                    perm.playerAdd(p, permissionFormat + "." + id);
                }
                api.setSelectedCosmetic(p, type, id);
                eco.withdrawPlayer(p, price);
                p.playSound(p.getLocation(), XSound.ENTITY_VILLAGER_YES.parseSound(), 1.0f, 1.0f);
                HCore.getInventoryByPlayer(p).open(p);
                // Don't have money and is purchasable
            } else {
                if(isOnlyForCheck) return 2;
                p.playSound(p.getLocation(), XSound.ENTITY_ENDERMAN_TELEPORT.parseSound(), 1.0f, 1.0f);
            }
        }
        return -1;
    }

    public void previewClick(Player player, CosmeticsType type, String id, int price){
        switch (type){
            case KillMessage:
                new KillMessagePreview().sendPreviewMessage(player, id);
                break;
            case DeathCries:
                new DeathCryPreview().preview(player, id);
                break;

            default:
                onClick(player,type, price, id, false);
                break;
        }
    }

}
