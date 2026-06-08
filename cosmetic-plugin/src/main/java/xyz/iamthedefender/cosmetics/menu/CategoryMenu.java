package xyz.iamthedefender.cosmetics.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.menu.impl.ChestSystemGui;
import xyz.iamthedefender.cosmetics.api.util.ItemBuilder;
import xyz.iamthedefender.cosmetics.api.util.Messages;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.data.PlayerData;
import xyz.iamthedefender.cosmetics.menu.data.CosmeticMenuItemData;
import xyz.iamthedefender.cosmetics.menu.data.CosmeticMenuSupport;
import xyz.iamthedefender.cosmetics.menu.data.SortMode;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryMenu extends ChestSystemGui {

    static final List<Integer> DEFAULT_SLOTS = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);

    private final ConfigManager config;
    private final CosmeticType<?> cosmeticType;
    private final String title;
    private final List<Integer> slots;
    private final int page;
    private final SortMode sortMode;
    private final boolean ownedFirst;

    public CategoryMenu(CosmeticType<?> type, String title, int page, SortMode sortMode, boolean ownedFirst) {
        super(title, Math.min(6, Math.max(1, type.getConfig().contains("gui.layout.rows") ? type.getConfig().getInt("gui.layout.rows") : 6)));
        this.config = type.getConfig();
        this.cosmeticType = type;
        this.title = title;
        this.slots = resolveSlots(config);
        this.page = page;
        this.sortMode = sortMode;
        this.ownedFirst = ownedFirst;
    }

    public CategoryMenu(CosmeticType<?> type, String title, int page) {
        this(type, title, page, SortMode.RARITY_LOW_HIGH, false);
    }

    public CategoryMenu(CosmeticType<?> type, String title) {
        this(type, title, 1, SortMode.RARITY_LOW_HIGH, false);
    }

    @Override
    public void onOpen(@NotNull Player player) {
        CosmeticsPlugin.getInstance().getApi().getPreviewList().forEach(preview -> preview.stopPreview(player));
        clearInventory();

        List<CosmeticMenuItemData> items = CosmeticMenuSupport.loadCategoryItems(player, cosmeticType);

        if (StartupUtils.isBackItemEnabledInCategoryMenu() && config.getBoolean("gui.navigation.back.enabled", true)) {
            ItemStack stack = ConfigManager.getItemStack(config.getYml(), "gui.navigation.back.item");
            int backSlot = config.getInt("gui.navigation.back.slot");
            setItem(backSlot, new ItemBuilder(stack == null ? new ItemStack(Material.ARROW) : stack)
                    .name(Messages.GUI_BACK_NAME.value(player))
                    .lore(Messages.GUI_BACK_LORE.list(player))
                    .build(), (event) -> new MainMenu((Player) event.getWhoClicked()).open((Player) event.getWhoClicked()));
        }

        setItem(config.getInt("gui.sort.slot"), buildSortItem(), (event) -> {
            PlayerData data = CosmeticsPlugin.getInstance().getPlayerManager().getPlayerData(player.getUniqueId());
            if (event.getClick() == ClickType.RIGHT) {
                data.setOwnedFirst(!ownedFirst);
                new CategoryMenu(cosmeticType, title, page, sortMode, !ownedFirst).open(player);
            } else if (event.getClick() == ClickType.LEFT) {
                SortMode next = sortMode.next();
                data.setSortMode(next);
                new CategoryMenu(cosmeticType, title, page, next, ownedFirst).open(player);
            }
        });

        createPages(sortItems(items, player), player);
    }

    @Override
    public void onClose(Player player) {
        Utility.getApi().getPreviewList().forEach(preview -> {
            if (!preview.isProgrammaticClose(player)) {
                preview.stopPreview(player);
            }
        });
    }

    private void createPages(List<CosmeticMenuItemData> items, Player player) {
        int itemsPerPage = slots.size();
        int totalPages = Math.max(1, (int) Math.ceil(items.size() / (double) itemsPerPage));
        int currentPage = Math.min(Math.max(page, 1), totalPages);
        int itemStartIndex = (currentPage - 1) * itemsPerPage;
        int itemEndIndex = Math.min(items.size(), itemStartIndex + itemsPerPage);

        if (currentPage < totalPages && config.getBoolean("gui.navigation.next.enabled", true)) {
            ItemStack nextItem = ConfigManager.getItemStack(config.getYml(), "gui.navigation.next.item");
            setItem(config.getInt("gui.navigation.next.slot"), new ItemBuilder(nextItem == null ? new ItemStack(Material.ARROW) : nextItem)
                    .name(Messages.GUI_NEXT_NAME.value(player))
                    .lore(Messages.GUI_NEXT_LORE.list(player))
                    .build(), (event) -> new CategoryMenu(cosmeticType, title, currentPage + 1, sortMode, ownedFirst).open((Player) event.getWhoClicked()));
        }

        if (currentPage > 1 && config.getBoolean("gui.navigation.previous.enabled", true)) {
            ItemStack previousItem = ConfigManager.getItemStack(config.getYml(), "gui.navigation.previous.item");
            setItem(config.getInt("gui.navigation.previous.slot"), new ItemBuilder(previousItem == null ? new ItemStack(Material.ARROW) : previousItem)
                    .name(Messages.GUI_PREVIOUS_NAME.value(player))
                    .lore(Messages.GUI_PREVIOUS_LORE.list(player))
                    .build(), (event) -> new CategoryMenu(cosmeticType, title, currentPage - 1, sortMode, ownedFirst).open((Player) event.getWhoClicked()));
        }

        List<CosmeticMenuItemData> pageItems = items.subList(itemStartIndex, itemEndIndex);
        for (CosmeticMenuItemData item : pageItems) {
            int slot = findFirstEmptySlot(getInventory());
            if (slot == -1) {
                break;
            }

            setItem(slot, CosmeticMenuSupport.createClickableItem(
                    player,
                    item,
                    () -> new CategoryMenu(cosmeticType, title, currentPage, sortMode, ownedFirst).open(player)
            ));
        }

        String extrasPath = config.contains("gui.fill-empty.enabled") ? "gui.fill-empty." : "Extras.fill-empty.";
        if (!config.getBoolean(extrasPath + "enabled")) {
            return;
        }

        ItemStack stack = ConfigManager.getItemStack(config.getYml(), extrasPath + "item");
        while (getInventory().firstEmpty() != -1) {
            setItem(getInventory().firstEmpty(), new ItemBuilder(stack).name("&r").build());
        }
    }

    private List<CosmeticMenuItemData> sortItems(List<CosmeticMenuItemData> items, Player player) {
        Comparator<CosmeticMenuItemData> nonePriority = Comparator.comparing(item -> item.getRarity() == RarityType.NONE ? 0 : 1);
        Comparator<CosmeticMenuItemData> nameComparator = Comparator.comparing(item -> ChatColor.stripColor(item.getFormattedName()));
        Comparator<CosmeticMenuItemData> idComparator = Comparator.comparing(CosmeticMenuItemData::getId);
        Comparator<CosmeticMenuItemData> baseComparator;

        if (sortMode == SortMode.RARITY_HIGH_LOW) {
            baseComparator = Comparator.comparing(CosmeticMenuItemData::getRarity);
        } else if (sortMode == SortMode.RARITY_LOW_HIGH) {
            baseComparator = Comparator.comparing(CosmeticMenuItemData::getRarity, Comparator.reverseOrder());
        } else if (sortMode == SortMode.A_TO_Z) {
            baseComparator = nameComparator.thenComparing(idComparator);
        } else {
            baseComparator = nameComparator.reversed().thenComparing(idComparator);
        }

        Comparator<CosmeticMenuItemData> comparator = nonePriority.thenComparing(baseComparator);
        if (ownedFirst) {
            Comparator<CosmeticMenuItemData> ownedComparator = Comparator.comparing(item -> {
                if (item.getRarity() == RarityType.NONE) {
                    return 0;
                }
                return player.hasPermission(item.getCosmeticType().getPermissionFormat() + "." + item.getId()) ? 0 : 1;
            });
            comparator = nonePriority.thenComparing(ownedComparator).thenComparing(baseComparator);
        }

        return items.stream().sorted(comparator).collect(Collectors.toList());
    }

    public int findFirstEmptySlot(Inventory inventory) {
        for (Integer slot : slots) {
            if (inventory.getItem(slot) == null) {
                return slot;
            }
        }
        return -1;
    }

    private ItemStack buildSortItem() {
        String ownedTag = ownedFirst ? "&aYes" : "&cNo";
        ItemStack configuredItem = ConfigManager.getItemStack(config.getYml(), "gui.sort.item");
        List<String> lore = Messages.GUI_SORT_LORE.list(null).stream()
                .map(line -> line.replace("{sort_mode}", sortMode.getDisplay()))
                .map(line -> line.replace("{next_sort_mode}", sortMode.next().getDisplay()))
                .map(line -> line.replace("{owned_first}", ownedTag))
                .collect(Collectors.toList());
        String name = Messages.GUI_SORT_NAME.value(null)
                .replace("{sort_mode}", sortMode.getDisplay());

        return new ItemBuilder(configuredItem == null ? new ItemStack(Material.HOPPER) : configuredItem)
                .name(name)
                .lore(lore)
                .build();
    }

    private static List<Integer> resolveSlots(ConfigManager config) {
        List<Integer> configuredSlots = new ArrayList<>(config.getIntegerList("gui.layout.item-slots"));
        if (!configuredSlots.isEmpty()) {
            return configuredSlots;
        }

        String legacy = config.getString("slots");
        if (legacy == null || legacy.trim().isEmpty()) {
            return DEFAULT_SLOTS;
        }

        List<Integer> parsed = new ArrayList<>();
        String cleaned = legacy.replace("[", "").replace("]", "");
        for (String slot : cleaned.split("\\s*,\\s*")) {
            if (slot.isEmpty()) {
                continue;
            }
            try {
                parsed.add(Integer.parseInt(slot));
            } catch (NumberFormatException ignored) {
            }
        }
        return parsed.isEmpty() ? DEFAULT_SLOTS : parsed;
    }
}
