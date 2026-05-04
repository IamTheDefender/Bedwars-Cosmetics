package xyz.iamthedefender.cosmetics.menu;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.CosmeticsAPI;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.event.CosmeticPurchaseEvent;
import xyz.iamthedefender.cosmetics.api.menu.ClickableItem;
import xyz.iamthedefender.cosmetics.api.menu.impl.ChestSystemGui;
import xyz.iamthedefender.cosmetics.api.util.ColorUtil;
import xyz.iamthedefender.cosmetics.api.util.ItemBuilder;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.data.PlayerData;
import xyz.iamthedefender.cosmetics.menu.SortMode;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;
import xyz.iamthedefender.cosmetics.util.StringUtils;
import xyz.iamthedefender.cosmetics.util.VaultUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class CategoryMenu extends ChestSystemGui {

    ConfigManager config;
    CosmeticsType cosmeticsType;
    String title;
    List<Integer> slots;
    int page;
    SortMode sortMode;
    boolean ownedFirst;

    public CategoryMenu(CosmeticsType type, String title, int page, SortMode sortMode, boolean ownedFirst) {
        super(title, 6);
        this.config = type.getConfig();
        this.cosmeticsType = type;
        this.title = title;
        String list = config.getString("slots");
        list = list.replace("[", "").replace("]", "");
        List<Integer> integerList = new ArrayList<>();
        for (String s : list.split("\\s*,\\s*")) {
            integerList.add(Integer.parseInt(s));
        }
        slots = integerList;
        if (slots.isEmpty()) {
            slots = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);
        }
        this.page = page;
        this.sortMode = sortMode;
        this.ownedFirst = ownedFirst;
    }

    public CategoryMenu(CosmeticsType type, String title, int page) {
        this(type, title, page, SortMode.RARITY_LOW_HIGH, false);
    }

    public CategoryMenu(CosmeticsType type, String title) {
        this(type, title, 1, SortMode.RARITY_LOW_HIGH, false);
    }

    @Override
    public void onOpen(@NotNull Player player) {
        CosmeticsAPI api = CosmeticsPlugin.getInstance().getApi();
        // Stop any active previews for this player
        api.getPreviewList().forEach(preview -> preview.stopPreview(player));

        ConfigManager configManager = cosmeticsType.getConfig();
        ConfigurationSection section = config.getYml().getConfigurationSection(cosmeticsType.getSectionKey());

        if (section == null) return;

        clearInventory();
        Map<ClickableItem, RarityType> rarityMap = new LinkedHashMap<>();
        Map<ClickableItem, String> idMap = new LinkedHashMap<>();

        for (String id : section.getKeys(false)) {
            String path = cosmeticsType.getSectionKey() + "." + id + ".";

            ItemStack stack = configManager.getItemStack(path + "item");
            int price = config.getInt(path + "price");
            RarityType rarity = RarityType.valueOf(config.getString(path + "rarity").toUpperCase());

            String formattedName = Utility.getMSGLang(player, "cosmetics." + path + "name");
            List<String> lore = Utility.getListLang(player, "cosmetics." + path + "lore");
            lore = StringUtils.formatLore(lore, formattedName, price, getItemStatus(player, cosmeticsType, id, price), rarity.getChatColor() + rarity.toString());
            boolean disabled = config.getBoolean(path + "disabled");

            ClickableItem item = null;
            List<String> lore1 = new ArrayList<>(lore);

            if (stack != null && !disabled) {
                ClickStatus status = onClick(player, cosmeticsType, price, id, true);

                String colorCode = status == ClickStatus.INSUFFICIENT_FUNDS || status == ClickStatus.NOT_PURCHASABLE ? "&c" : "&a";

                if (status == ClickStatus.ALREADY_SELECTED) {
                    stack.addUnsafeEnchantment(Enchantment.LUCK, 1);
                }

                item = new ClickableItem(new ItemBuilder(stack)
                        .name(colorCode + formattedName)
                        .lore(lore1)
                        .itemFlag(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES)
                        .build(), (e) -> {

                    if (e.getClick() == ClickType.RIGHT) {
                        previewClick(player, cosmeticsType, id, price);
                        return;
                    }

                    if (e.getClick() == ClickType.LEFT) {
                        onClick(player, cosmeticsType, price, id, false);
                    }
                });
            }

            if (item != null) {
                rarityMap.put(item, rarity);
                idMap.put(item, id);
            }
        }

        if (CosmeticsPlugin.getInstance().getConfig().getBoolean("BackItemInCosmeticsMenu")) {
            setItem(49, new ItemBuilder().material(Material.ARROW).name("&aBack").build(), (e) -> new MainMenu((Player) e.getWhoClicked()).open((Player) e.getWhoClicked()));
        }

        setItem(50, buildSortItem(), (e) -> {
            PlayerData data = CosmeticsPlugin.getInstance().getPlayerManager().getPlayerData(player.getUniqueId());
            if (e.getClick() == ClickType.RIGHT) {
                data.setOwnedFirst(!ownedFirst);
                new CategoryMenu(cosmeticsType, title, page, sortMode, !ownedFirst).open(player);
            } else if (e.getClick() == ClickType.LEFT) {
                SortMode next = sortMode.next();
                data.setSortMode(next);
                new CategoryMenu(cosmeticsType, title, page, next, ownedFirst).open(player);
            }
        });

        createPages(rarityMap, player, idMap);
    }

    @Override
    public void onClose(Player player) {
        Utility.getApi().getPreviewList().forEach(preview -> {
            if (!preview.isProgrammaticClose(player)) {
                preview.stopPreview(player);
            }
        });
    }

    public void createPages(Map<ClickableItem, RarityType> rarityMap, Player player, Map<ClickableItem, String> itemIdMap) {
        List<ClickableItem> items = sortItems(rarityMap, player, itemIdMap);

        int itemsPerPage = slots.size();
        int totalPages = (items.size() / itemsPerPage) + 1;
        int itemStartIndex = (page - 1) * itemsPerPage;
        int itemEndIndex = Math.min(items.size(), itemStartIndex + itemsPerPage);

        List<ClickableItem> pageItems = items.subList(itemStartIndex, itemEndIndex);

        if (page < totalPages) {
            setItem(47, new ItemBuilder().material(Material.ARROW).name("&aNext page").build(), (e) -> new CategoryMenu(cosmeticsType, title, page + 1, sortMode, ownedFirst).open((Player) e.getWhoClicked()));
        }

        if (page > 1) {
            setItem(51, new ItemBuilder().material(Material.ARROW).name("&aPrevious page").build(), (e) -> new CategoryMenu(cosmeticsType, title, page - 1, sortMode, ownedFirst).open((Player) e.getWhoClicked()));
        }

        Map<ClickableItem, RarityType> pageRarityMap = pageItems.stream()
                .collect(Collectors.toMap(c -> c, rarityMap::get, (a, b) -> a, LinkedHashMap::new));

        placeItems(pageRarityMap);

        String extrasPath = "Extras.fill-empty.";
        boolean extrasEnabled = config.getBoolean(extrasPath + "enabled");

        if (!extrasEnabled) return;

        ItemStack stack = ConfigManager.getItemStack(config.getYml(), extrasPath + "item");
        while (getInventory().firstEmpty() != -1) {
            setItem(getInventory().firstEmpty(), new ItemBuilder(stack).name("&r").build());
        }
    }

    private List<ClickableItem> sortItems(Map<ClickableItem, RarityType> rarityMap, Player player, Map<ClickableItem, String> itemIdMap) {
        CosmeticsAPI api = CosmeticsPlugin.getInstance().getApi();

        // 1. Always put 'NONE' rarity item first
        Comparator<ClickableItem> nonePriority = Comparator.comparing(item -> rarityMap.get(item) == RarityType.NONE ? 0 : 1);

        // 2. Put SELECTED item next
        String selectedId = api.getSelectedCosmetic(player, cosmeticsType);
        Comparator<ClickableItem> selectedPriority = Comparator.comparing(item -> {
            String id = itemIdMap.get(item);
            return id != null && id.equals(selectedId) ? 0 : 1;
        });

        // 3. Determine base sorting based on sortMode
        Comparator<ClickableItem> baseComparator;
        Comparator<ClickableItem> nameComparator = Comparator.comparing(item -> ChatColor.stripColor(item.getItemStack().getItemMeta().getDisplayName()));
        Comparator<ClickableItem> idComparator = Comparator.comparing(item -> itemIdMap.getOrDefault(item, ""));

        if (sortMode == SortMode.RARITY_HIGH_LOW) {
            baseComparator = Comparator.comparing((ClickableItem item) -> rarityMap.get(item)).thenComparing(nameComparator).thenComparing(idComparator);
        } else if (sortMode == SortMode.RARITY_LOW_HIGH) {
            baseComparator = Comparator.comparing((ClickableItem item) -> rarityMap.get(item), Comparator.reverseOrder()).thenComparing(nameComparator).thenComparing(idComparator);
        } else if (sortMode == SortMode.A_TO_Z) {
            baseComparator = nameComparator.thenComparing(idComparator);
        } else {
            baseComparator = nameComparator.reversed().thenComparing(idComparator);
        }

        // 4. Handle 'Owned First' toggle
        Comparator<ClickableItem> finalComparator;
        if (ownedFirst) {
            Comparator<ClickableItem> owned = Comparator.comparing(item -> {
                if (rarityMap.get(item) == RarityType.NONE) return 0; // None is always 'owned'
                String id = itemIdMap.get(item);
                if (id == null) return 1;
                return player.hasPermission(cosmeticsType.getPermissionFormat() + "." + id) ? 0 : 1;
            });
            // Chain: None First -> Selected -> Owned -> Sort Mode
            finalComparator = nonePriority.thenComparing(selectedPriority).thenComparing(owned).thenComparing(baseComparator);
        } else {
            // Chain: None First -> Selected -> Sort Mode
            finalComparator = nonePriority.thenComparing(selectedPriority).thenComparing(baseComparator);
        }

        return new ArrayList<>(rarityMap.keySet()).stream()
                .sorted(finalComparator)
                .collect(Collectors.toList());
    }

    public int findFirstEmptySlot(Inventory inventory) {
        for (Integer slot : slots) {
            if (inventory.getItem(slot) == null) {
                return slot;
            }
        }
        return -1;
    }

    public boolean isFull(Inventory inventory) {
        return findFirstEmptySlot(inventory) == -1;
    }

    public void placeItems(Map<ClickableItem, RarityType> rarityMap) {
        rarityMap.forEach((item, rarity) -> {
            int slot = findFirstEmptySlot(getInventory());
            if (slot == -1) return;
            setItem(slot, item);
        });
    }


    public String getItemStatus(Player p, CosmeticsType type, String unformattedName, int price) {
        CosmeticsAPI api = CosmeticsPlugin.getInstance().getApi();
        String selected = api.getSelectedCosmetic(p, type);
        if (selected.equals(unformattedName)) {
            return ColorUtil.translate(Utility.getMSGLang(p, "cosmetics.selected"));
        }

        if (p.hasPermission(type.getPermissionFormat() + "." + unformattedName)) {
            return ColorUtil.translate(Utility.getMSGLang(p, "cosmetics.click-to-select"));
        }

        if (type.getConfig().getString(type.getSectionKey() + "." + unformattedName + ".purchase-able") != null) {
            boolean purchaseAble = type.getConfig().getBoolean(type.getSectionKey() + "." + unformattedName + ".purchase-able");
            if (!purchaseAble) {
                return ColorUtil.translate(Utility.getMSGLang(p, "cosmetics.not-purchase-able"));
            }
        }

        if (CosmeticsPlugin.getInstance().getEconomy().getBalance(p) >= price) {
            return ColorUtil.translate(Utility.getMSGLang(p, "cosmetics.click-to-purchase"));
        }

        return ColorUtil.translate(Utility.getMSGLang(p, "cosmetics.not-purchase-able"));
    }

    public ClickStatus onClick(Player p, CosmeticsType type, int price, String id, boolean isOnlyForCheck) {
        CosmeticsAPI api = CosmeticsPlugin.getInstance().getApi();
        String selected = api.getSelectedCosmetic(p, type);
        String permissionFormat = type.getPermissionFormat();
        Economy eco = VaultUtils.getEconomy();
        Permission perm = VaultUtils.getPermissions();

        if (selected.equals(id)) {
            DebugUtil.addMessage("Cosmetic " + id + " is already selected");
            return ClickStatus.ALREADY_SELECTED;
        }

        if (!p.hasPermission(permissionFormat + "." + id)) {
            DebugUtil.addMessage("Cosmetic " + id + " is not unlocked (No permission)");

            if (!type.getConfig().getBoolean(type.getSectionKey() + "." + id + ".purchase-able", true)) {
                DebugUtil.addMessage("Cosmetic " + id + " is not purchase-able");
                return ClickStatus.NOT_PURCHASABLE;
            }

            if (eco == null || eco.getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) < price) {
                DebugUtil.addMessage("Cosmetic " + id + " is not purchase-able (no coins)");
                if (isOnlyForCheck) {
                    return ClickStatus.INSUFFICIENT_FUNDS;
                }

                p.playSound(p.getLocation(), XSound.ENTITY_ENDERMAN_TELEPORT.parseSound(), 1.0f, 1.0f);
                return ClickStatus.INSUFFICIENT_FUNDS;
            }

            if (isOnlyForCheck) {
                DebugUtil.addMessage("Cosmetic " + id + " is purchase-able");
                return ClickStatus.PURCHASABLE;
            }

            CosmeticPurchaseEvent event = new CosmeticPurchaseEvent(p, type);
            Bukkit.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                DebugUtil.addMessage("Cosmetic " + id + " is not purchase-able (event cancelled)");
                return ClickStatus.PURCHASE_CANCELLED;
            }

            if (perm != null) perm.playerAdd(p, permissionFormat + "." + id);

            api.setSelectedCosmetic(p, type, id);
            eco.withdrawPlayer(p, price);
            p.playSound(p.getLocation(), XSound.ENTITY_VILLAGER_YES.parseSound(), 1.0f, 1.0f);
            new CategoryMenu(cosmeticsType, title, page, sortMode, ownedFirst).open(p);

            DebugUtil.addMessage("Selected " + id + " for " + type + " and paid " + price + " coins");
            return ClickStatus.PURCHASED_AND_SELECTED;
        }

        if (isOnlyForCheck) return ClickStatus.UNLOCKED;

        DebugUtil.addMessage("Selected " + id + " for " + type);
        api.setSelectedCosmetic(p, type, id);
        XSound.ENTITY_VILLAGER_YES.play(p);
        new CategoryMenu(cosmeticsType, title, page, sortMode, ownedFirst).open(p);
        return ClickStatus.SELECTED;
    }

    public void previewClick(Player player, CosmeticsType type, String id, int price) {
        if (id == null || id.equalsIgnoreCase("Back") || id.equalsIgnoreCase("Balance")) {
            return;
        }

        Cosmetics cosmetics = CosmeticsPlugin.findCosmetic(id, type);

        if (cosmetics == null) return;

        if (cosmetics.getRarity() == RarityType.NONE) {
            XSound.ENTITY_VILLAGER_NO.play(player, 1.0f, 1.0f);
            return;
        }

        Location playerLocation = StartupUtils.getPlayerLocation();
        Location previewLocation = StartupUtils.getCosmeticLocation();

        AtomicBoolean found = new AtomicBoolean(false);
        CosmeticsPlugin.getInstance().getPreviewList().stream().filter(preview -> preview.getType() == type).findAny().ifPresent(cosmeticPreview -> {
            cosmeticPreview.showPreview(player, cosmetics, previewLocation, playerLocation);
            found.set(true);
        });

        if (found.get()) return;

        onClick(player, type, price, id, false);
    }

    private ItemStack buildSortItem() {
        String ownedTag = ownedFirst ? "&aYes" : "&cNo";

        return new ItemBuilder()
                .material(Material.HOPPER)
                .name("&eSorted by: &6" + sortMode.getDisplay())
                .lore(
                        "&7Sorts by rarity: &f" + sortMode.getDisplay(),
                        "",
                        "&6Next sort: &f" + sortMode.next().getDisplay(),
                        "&eLeft click to use!",
                        "",
                        "&7Owned items first: " + ownedTag,
                        "&eRight click to toggle!"
                )
                .build();
    }

    enum ClickStatus {
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