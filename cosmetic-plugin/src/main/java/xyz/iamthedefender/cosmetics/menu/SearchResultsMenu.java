package xyz.iamthedefender.cosmetics.menu;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.iamthedefender.cosmetics.api.menu.impl.ChestSystemGui;
import xyz.iamthedefender.cosmetics.api.util.ItemBuilder;
import xyz.iamthedefender.cosmetics.api.util.Messages;
import xyz.iamthedefender.cosmetics.menu.data.CosmeticMenuItemData;
import xyz.iamthedefender.cosmetics.menu.data.CosmeticMenuSupport;
import xyz.iamthedefender.cosmetics.util.MainMenuUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchResultsMenu extends ChestSystemGui {

    private static final int ROWS = 6;
    private static final int BACK_SLOT = 49;
    private static final int SEARCH_AGAIN_SLOT = 50;
    private static final int NEXT_SLOT = 47;
    private static final int PREVIOUS_SLOT = 51;
    private static final int NO_RESULTS_SLOT = 22;

    private final String searchQuery;
    private final int page;
    private final List<Integer> slots;

    public SearchResultsMenu(String searchQuery, int page) {
        super(resolveTitle(searchQuery), ROWS);
        this.searchQuery = searchQuery == null ? "" : searchQuery.trim();
        this.page = Math.max(1, page);
        this.slots = new ArrayList<>(CategoryMenu.DEFAULT_SLOTS);
    }

    public SearchResultsMenu(String searchQuery) {
        this(searchQuery, 1);
    }

    @Override
    public void onOpen(@NotNull Player player) {
        clearInventory();

        List<CosmeticMenuItemData> results = search(player);
        setItem(BACK_SLOT, new ItemBuilder(new ItemStack(Material.ARROW))
                .name(Messages.GUI_BACK_NAME.value(player))
                .lore(Messages.GUI_BACK_LORE.list(player))
                .build(), (event) -> new MainMenu((Player) event.getWhoClicked()).open((Player) event.getWhoClicked()));

        ItemStack searchAgainItem = XMaterial.COMPASS.parseItem();
        setItem(SEARCH_AGAIN_SLOT, new ItemBuilder(searchAgainItem == null ? new ItemStack(Material.COMPASS) : searchAgainItem)
                .name(Messages.SEARCH_RESULTS_SEARCH_AGAIN_NAME.value(player))
                .lore(Messages.SEARCH_RESULTS_SEARCH_AGAIN_LORE.list(player))
                .build(), (event) -> MainMenuUtils.openSearch((Player) event.getWhoClicked()));

        if (results.isEmpty()) {
            ItemStack barrier = XMaterial.BARRIER.parseItem();
            setItem(NO_RESULTS_SLOT, new ItemBuilder(barrier == null ? new ItemStack(Material.BARRIER) : barrier)
                    .name(Messages.SEARCH_RESULTS_NO_RESULTS_NAME.value(player))
                    .lore(Messages.SEARCH_RESULTS_NO_RESULTS_LORE.list(player))
                    .build());
            fillEmpty();
            return;
        }

        createPages(results, player);
    }

    @Override
    public void onClose(Player player) {
    }

    private void createPages(List<CosmeticMenuItemData> items, Player player) {
        int itemsPerPage = slots.size();
        int totalPages = Math.max(1, (int) Math.ceil(items.size() / (double) itemsPerPage));
        int currentPage = Math.min(Math.max(page, 1), totalPages);
        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(items.size(), startIndex + itemsPerPage);

        if (currentPage < totalPages) {
            setItem(NEXT_SLOT, new ItemBuilder(new ItemStack(Material.ARROW))
                    .name(Messages.GUI_NEXT_NAME.value(player))
                    .lore(Messages.GUI_NEXT_LORE.list(player))
                    .build(), (event) -> new SearchResultsMenu(searchQuery, currentPage + 1).open((Player) event.getWhoClicked()));
        }

        if (currentPage > 1) {
            setItem(PREVIOUS_SLOT, new ItemBuilder(new ItemStack(Material.ARROW))
                    .name(Messages.GUI_PREVIOUS_NAME.value(player))
                    .lore(Messages.GUI_PREVIOUS_LORE.list(player))
                    .build(), (event) -> new SearchResultsMenu(searchQuery, currentPage - 1).open((Player) event.getWhoClicked()));
        }

        List<CosmeticMenuItemData> pageItems = items.subList(startIndex, endIndex);
        for (CosmeticMenuItemData item : pageItems) {
            int slot = findFirstEmptySlot();
            if (slot == -1) {
                break;
            }

            setItem(slot, CosmeticMenuSupport.createClickableItem(
                    player,
                    item,
                    () -> new SearchResultsMenu(searchQuery, currentPage).open(player)
            ));
        }

        fillEmpty();
    }

    private List<CosmeticMenuItemData> search(Player player) {
        List<CosmeticMenuItemData> items = CosmeticMenuSupport.loadAllItems(player, true);
        String normalizedQuery = normalize(searchQuery);
        if (normalizedQuery.isEmpty()) {
            return items.stream()
                    .sorted(Comparator.comparing(item -> ChatColor.stripColor(item.getCategoryName() + " " + item.getFormattedName())))
                    .collect(Collectors.toList());
        }

        List<String> tokens = tokenize(normalizedQuery);
        Map<CosmeticMenuItemData, Integer> scores = new HashMap<>();
        for (CosmeticMenuItemData item : items) {
            int score = score(item, normalizedQuery, tokens, player);
            if (score > 0) {
                scores.put(item, score);
            }
        }

        return scores.keySet().stream()
                .sorted(Comparator
                        .comparingInt((CosmeticMenuItemData item) -> scores.getOrDefault(item, 0)).reversed()
                        .thenComparing(item -> isSelected(player, item) ? 0 : 1)
                        .thenComparing(item -> player.hasPermission(item.getCosmeticType().getPermissionFormat() + "." + item.getId()) ? 0 : 1)
                        .thenComparing(item -> ChatColor.stripColor(item.getFormattedName()))
                        .thenComparing(CosmeticMenuItemData::getId))
                .collect(Collectors.toList());
    }

    private int score(CosmeticMenuItemData item, String query, List<String> tokens, Player player) {
        int score = 0;
        String id = normalize(item.getId());
        String name = normalize(ChatColor.stripColor(item.getFormattedName()));
        String category = normalize(ChatColor.stripColor(item.getCategoryName()));
        String lore = normalize(item.getLore().stream()
                .map(ChatColor::stripColor)
                .collect(Collectors.joining(" ")));

        score += scoreField(id, query, tokens, 1200, 800, 450);
        score += scoreField(name, query, tokens, 1100, 700, 400);
        score += scoreField(category, query, tokens, 250, 160, 90);
        score += scoreField(lore, query, tokens, 150, 80, 40);

        if (isSelected(player, item)) {
            score += 20;
        }

        return score;
    }

    private int scoreField(String field, String query, List<String> tokens, int exact, int startsWith, int contains) {
        if (field.isEmpty()) {
            return 0;
        }

        int score = 0;
        if (field.equals(query)) {
            score += exact;
        } else if (field.startsWith(query)) {
            score += startsWith;
        } else if (field.contains(query)) {
            score += contains;
        }

        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }

            if (field.equals(token)) {
                score += 200;
            } else if (field.startsWith(token)) {
                score += 120;
            } else if (field.contains(token)) {
                score += 60;
            }

            for (String word : field.split(" ")) {
                if (word.startsWith(token)) {
                    score += 35;
                }
            }
        }
        return score;
    }

    private boolean isSelected(Player player, CosmeticMenuItemData item) {
        return item.getId().equalsIgnoreCase(
                xyz.iamthedefender.cosmetics.CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(player, item.getCosmeticType())
        );
    }

    private int findFirstEmptySlot() {
        for (Integer slot : slots) {
            if (getInventory().getItem(slot) == null) {
                return slot;
            }
        }
        return -1;
    }

    private void fillEmpty() {
        ItemStack fill = XMaterial.BLACK_STAINED_GLASS_PANE.parseItem();
        while (getInventory().firstEmpty() != -1) {
            setItem(getInventory().firstEmpty(), new ItemBuilder(fill == null ? new ItemStack(Material.STAINED_GLASS_PANE) : fill)
                    .name("&r")
                    .build());
        }
    }

    private static String resolveTitle(String query) {
        String search = query == null || query.trim().isEmpty()
                ? Messages.SEARCH_RESULTS_EMPTY_QUERY_LABEL.value(null)
                : query.trim();
        String title = Messages.SEARCH_RESULTS_TITLE_FORMAT.value(null).replace("{search}", search);
        return title.length() > 32 ? title.substring(0, 32) : title;
    }

    private static String normalize(String value) {
        if (value == null) {
            return "";
        }
        String stripped = ChatColor.stripColor(value);
        if (stripped == null) {
            return "";
        }
        return stripped.toLowerCase(Locale.ROOT)
                .replace('_', ' ')
                .replace('-', ' ')
                .replaceAll("[^a-z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private static List<String> tokenize(String normalizedQuery) {
        if (normalizedQuery.isEmpty()) {
            return List.of();
        }
        return List.of(normalizedQuery.split(" "));
    }
}
