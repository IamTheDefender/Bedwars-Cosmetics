package xyz.iamthedefender.cosmetics.api.util;

import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;

import java.util.List;

public class Messages {

    public static final Message MAIN_MENU_GUI_TITLE = of("title", "&8Cosmetics");
    public static final Message CATEGORY_MENU_TITLE_FORMAT = of("menu.category-title-format", "&8{category}");
    public static final Message FINAL_KILL_SUFFIX = of("final-kill-suffix", "&b&lFINAL KILL!");

    public static final Message SELECTED = of("selected", "&aSELECTED!");
    public static final Message CLICK_TO_SELECT = of("click-to-select", "&eClick to select.");
    public static final Message CLICK_TO_PURCHASE = of("click-to-purchase", "&eClick to purchase.");
    public static final Message LOCKED = of("not-purchase-able", "&cLOCKED.");
    public static final Message NO_COINS = of("no-coins", "&cYou don't have enough coins!");
    public static final Message SPRAY_COOLDOWN = of("spray-msg", "&cYou must wait 3 seconds between spray uses!");
    public static final Message SPRAY_CLICK = of("spray_click", "&eClick!");

    public static final Message GUI_BACK_NAME = of("gui.navigation.back.name", "&aBack");
    public static final Message GUI_BACK_LORE = of("gui.navigation.back.lore", List.of("&7Click to go back."));
    public static final Message GUI_NEXT_NAME = of("gui.navigation.next.name", "&aNext page");
    public static final Message GUI_NEXT_LORE = of("gui.navigation.next.lore", List.of("&7View the next page."));
    public static final Message GUI_PREVIOUS_NAME = of("gui.navigation.previous.name", "&aPrevious page");
    public static final Message GUI_PREVIOUS_LORE = of("gui.navigation.previous.lore", List.of("&7View the previous page."));
    public static final Message GUI_SORT_NAME = of("gui.sort.name", "&eSorted by: &6{sort_mode}");
    public static final Message GUI_SORT_LORE = of("gui.sort.lore", List.of(
            "&7Sorts by rarity: &f{sort_mode}",
            "",
            "&6Next sort: &f{next_sort_mode}",
            "&eLeft click to use!",
            "",
            "&7Owned items first: {owned_first}",
            "&eRight click to toggle!"
    ));
    public static final Message SEARCH_RESULTS_TITLE_FORMAT = of("menu.search-results-title-format", "&8Showing cosmetics for {search}");
    public static final Message SEARCH_RESULTS_EMPTY_QUERY_LABEL = of("menu.search-results-empty-query", "all");
    public static final Message SEARCH_RESULTS_SEARCH_AGAIN_NAME = of("menu.search-results.search-again.name", "&aSearch again");
    public static final Message SEARCH_RESULTS_SEARCH_AGAIN_LORE = of("menu.search-results.search-again.lore", List.of("&7Open the search prompt again."));
    public static final Message SEARCH_RESULTS_NO_RESULTS_NAME = of("menu.search-results.no-results.name", "&cNo cosmetics found");
    public static final Message SEARCH_RESULTS_NO_RESULTS_LORE = of("menu.search-results.no-results.lore", List.of("&7Try a different search query."));

    public static final Message ERROR_NO_COSMETIC_FOUND = of("error.no-cosmetic-found", "No cosmetic found with that ID and category!");

    public static Message of(String path, Object defaultValue) {
        return new Message(path, defaultValue);
    }

    public static Message cosmeticDisplayName(String configPath, String defaultValue) {
        return of(configPath + "name", defaultValue);
    }

    public static Message cosmeticDisplayLore(String configPath, List<String> defaultValue) {
        return of(configPath + "lore", defaultValue);
    }

    public static Message mainMenuItemName(String key, String defaultValue) {
        return of("main-menu." + key + ".name", defaultValue);
    }

    public static Message mainMenuItemLore(String key, List<String> defaultValue) {
        return of("main-menu." + key + ".lore", defaultValue);
    }

    public static Message cosmeticDisplayName(CosmeticType<?> cosmeticType, String id, Player player) {
        String path = cosmeticType.getSectionKey() + "." + id + ".";

        return Messages.cosmeticDisplayName(path, Utility.getMSGLang(player, "cosmetics." + path + "name"));
    }

    public static Message cosmeticDisplayName(CosmeticType<?> cosmeticType, Cosmetics cosmetics, Player player) {
        return cosmeticDisplayName(cosmeticType, cosmetics.getIdentifier(), player);
    }

    public static Message createRarityName(RarityType rarityType) {
        return of("rarity." + rarityType.name().toLowerCase(), rarityType.name());
    }
}
