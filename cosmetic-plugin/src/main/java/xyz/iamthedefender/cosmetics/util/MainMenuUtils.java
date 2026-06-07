

package xyz.iamthedefender.cosmetics.util;

import de.rapha149.signgui.SignGUI;
import de.rapha149.signgui.exception.SignGUIVersionException;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.CosmeticsAPI;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticRegistry;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.util.*;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;
import xyz.iamthedefender.cosmetics.data.PlayerOwnedData;
import xyz.iamthedefender.cosmetics.menu.CategoryMenu;
import xyz.iamthedefender.cosmetics.menu.SearchResultsMenu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainMenuUtils {

    public static void saveLores() {
        ConfigManager config = CosmeticsPlugin.getInstance().getMenuData();

        List<String> sprays = Arrays.asList("&7Select a spray to show off all", "&7over the place! Sprays slot can", "&7be found on every spawn islands", "&7and some center islands.", "", "&7Unlocked:&a {ownedSpray}", "&7Currently Selected:", "{spray}", "", "&eClick to view.");
        saveMainMenuItemDefaults(config, "Sprays", "&aSprays", sprays);

        List<String> pts = Arrays.asList("&7Change your projectile particle", "&7trail effects.", "", "&7Unlocked:&a {ownedpt}", "&7Currently Selected:", "{projectile}", "", "&eClick to view.");
        saveMainMenuItemDefaults(config, "Projectile-Trails", "&aProjectile Trails", pts);

        List<String> finalke = Arrays.asList("&7A selection of various effects", "&7to chosse from that will trigger", "&7whenever you final kill an", "&7enemy!", "", "&7Unlocked:&a {ownedfinalkill}", "&7Currently Selected:", "&a" + "{finalkill}", "", "&eClick to view.");
        saveMainMenuItemDefaults(config, "FinalKill-Effects", "&aFinal Kill Effects", finalke);

        List<String> km = Arrays.asList("&7Select a Kill Message package to", "&7replace chat messages when you", "&7kill players.", "", "&7Unlocked:&a {ownedkm}", "&7Currently Selected:", "{killmsg}", "", "&eClick to view.");
        saveMainMenuItemDefaults(config, "Kill-Messages", "&aKill Messages", km);

        List<String> gly = Arrays.asList("&7Select a Glyph image which will", "&7appear when picking up diamonds and", "&7emeralds!", "", "&7Unlocked:&a {ownedgly}", "&7Currently Selected:", "{glyphs}", "", "&eClick to view.");
        saveMainMenuItemDefaults(config, "Glyphs", "&aGlyphs", gly);

        List<String> bbe = Arrays.asList("&7Select from various Bed Destroy", "&7effects, which will occur when you", "&7break a bed!", "", "&7Unlocked:&a {ownedbbe}", "&7Currently Selected:", "{bedbreak}", "", "&eClick to view.");
        saveMainMenuItemDefaults(config, "Bed-Destroys", "&aBed Destroys", bbe);

        List<String> ws = Arrays.asList("&7Change the skin of wood", "&7in-game.", "", "&7Unlocked:&a {ownedws}", "&7Currently Selected:", "{woodskin}", "", "&eClick to view.");
        saveMainMenuItemDefaults(config, "WoodSkins", "&aWood Skins", ws);

        List<String> vd = Arrays.asList("&7Celebrate by gloating and", "&7showing off to other players", "&7whenever you win!", "", "&7Unlocked:&a {ownedvd}", "&7Currently Selected:", "{victory}", "", "&eClick to view.");
        saveMainMenuItemDefaults(config, "Victory-Dances", "&aVictory Dances", vd);

        List<String> islandtoppers = Arrays.asList("&7Select an Island Topper to", "&7decorate your island with! In", "&7Doubles and Teams Mode a random", "&7player's choice from each team is choosen.", "", "&7Unlocked:&a {ownedit}", "&7Currently Selected:", "&a{islandtopper}", "", "&eClick to select.");
        saveMainMenuItemDefaults(config, "Island-Toppers", "&aIsland Toppers", islandtoppers);

        List<String> shopkeepers = Arrays.asList("&7Select from various ShopKeeper", "&7skin, which will replace how the", "&7ShopKeeper look in-game! In", "&7Doubles and Teams Mode a random", "&7player's choice from each team", "&7is choosen.", "", "&7Unlocked:&a {ownedshopkeeper}", "&7Currently Selected:", "{shopkeeper}", "", "&eClick to view.");
        saveMainMenuItemDefaults(config, "ShopKeeperSkins", "&aShopKeeperSkins", shopkeepers);

        List<String> dc = Arrays.asList("&7Let others know just how salty", "&7your tears are every time you", "&7die with these death cries!", "", "&7Unlocked:&a {owneddc}", "&7Currently Selected:", "{deathcries}", "", "&eClick to view.");
        saveMainMenuItemDefaults(config, "Death-Cries", "&aDeath Cries", dc);

        saveMainMenuItemDefaults(config, "Back", "&aBack", List.of("&7Click to go back!"));

        saveMainMenuItemDefaults(config, "Balance", "&6Balance", List.of("&7Balance: &6%vault_eco_balance_formatted%"));
        saveMainMenuItemDefaults(config, "Search", "&aSearch", List.of("&7Find cosmetics across every", "&7category.", "", "&eClick to search."));
        ConfigUtils.saveIfNotFound(config, "main-menu.title", "&8Cosmetics");

        ConfigurationSection section = config.getYml().getConfigurationSection(config.contains("main-menu.items") ? "main-menu.items" : "Main-Menu");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                saveMainMenuItemDefaults(config, key, "&cName not set!", List.of("&cLore not set!"));
            }
        }
        config.save();
        config.reload();
    }

    private static void saveMainMenuItemDefaults(ConfigManager config, String key, String name, List<String> lore) {
        String itemsPath = config.contains("main-menu.items." + key) ? "main-menu.items." : "Main-Menu.";
        String displayPath = itemsPath + key + ".display.";
        Messages.mainMenuItemName(key, config.getString(displayPath + "name", name)).saveIfMissing();
        Messages.mainMenuItemLore(key, config.contains(displayPath + "lore")
                ? config.getYml().getStringList(displayPath + "lore")
                : lore).saveIfMissing();
    }

    private static final Map<String, CosmeticType> SELECTED_PLACEHOLDERS = Map.ofEntries(
            Map.entry("{islandtopper}", CosmeticType.ISLAND_TOPPERS),
            Map.entry("{spray}", CosmeticType.SPRAYS),
            Map.entry("{killmsg}", CosmeticType.KILL_MESSAGES),
            Map.entry("{shopkeeper}", CosmeticType.SHOPKEEPER_SKINS),
            Map.entry("{woodskin}", CosmeticType.WOOD_SKINS),
            Map.entry("{victory}", CosmeticType.VICTORY_DANCES),
            Map.entry("{deathcries}", CosmeticType.DEATH_CRIES),
            Map.entry("{glyphs}", CosmeticType.GLYPHS),
            Map.entry("{bedbreak}", CosmeticType.BED_DESTROY),
            Map.entry("{projectile}", CosmeticType.PROJECTILE_TRAILS),
            Map.entry("{finalkill}", CosmeticType.FINAL_KILL_EFFECTS)
    );

    private static final Map<String, Function<PlayerOwnedData, String>> OWNED_PLACEHOLDERS = Map.ofEntries(
            Map.entry("{ownedSpray}", d -> formatOwnedProgress(d.getSpray(), CosmeticRegistry.getByCategory(CosmeticType.SPRAYS).size())),
            Map.entry("{ownedpt}", d -> formatOwnedProgress(d.getProjectileTrail(), CosmeticRegistry.getByCategory(CosmeticType.PROJECTILE_TRAILS).size())),
            Map.entry("{ownedfinalkill}", d -> formatOwnedProgress(d.getFinalKillEffect(), CosmeticRegistry.getByCategory(CosmeticType.FINAL_KILL_EFFECTS).size())),
            Map.entry("{ownedkm}", d -> formatOwnedProgress(d.getKillMessage(), CosmeticRegistry.getByCategory(CosmeticType.KILL_MESSAGES).size())),
            Map.entry("{ownedgly}", d -> formatOwnedProgress(d.getGlyph(), CosmeticRegistry.getByCategory(CosmeticType.GLYPHS).size())),
            Map.entry("{ownedbbe}", d -> formatOwnedProgress(d.getBedDestroy(), CosmeticRegistry.getByCategory(CosmeticType.BED_DESTROY).size())),
            Map.entry("{ownedws}", d -> formatOwnedProgress(d.getWoodSkin(), CosmeticRegistry.getByCategory(CosmeticType.WOOD_SKINS).size())),
            Map.entry("{ownedvd}", d -> formatOwnedProgress(d.getVictoryDance(), CosmeticRegistry.getByCategory(CosmeticType.VICTORY_DANCES).size())),
            Map.entry("{ownedit}", d -> formatOwnedProgress(d.getIslandTopper(), CosmeticRegistry.getByCategory(CosmeticType.ISLAND_TOPPERS).size())),
            Map.entry("{ownedshopkeeper}", d -> formatOwnedProgress(d.getShopkeeperSkin(), CosmeticRegistry.getByCategory(CosmeticType.SHOPKEEPER_SKINS).size())),
            Map.entry("{owneddc}", d -> formatOwnedProgress(d.getDeathCry(), CosmeticRegistry.getByCategory(CosmeticType.DEATH_CRIES).size()))
    );

    public static List<String> formatLore(List<String> lores, Player p) {
        CosmeticsAPI api = CosmeticsPlugin.getInstance().getApi();
        PlayerOwnedData owned = CosmeticsPlugin.getInstance().getPlayerManager().getPlayerOwnedData(p.getUniqueId());

        try {
            return lores.stream()
                    .map(s -> {
                        for (Map.Entry<String, CosmeticType> entry : SELECTED_PLACEHOLDERS.entrySet())
                            s = s.replace(entry.getKey(), "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, entry.getValue())));
                        for (Map.Entry<String, Function<PlayerOwnedData, String>> entry : OWNED_PLACEHOLDERS.entrySet())
                            s = s.replace(entry.getKey(), entry.getValue().apply(owned));
                        return s;
                    })
                    .map(s -> applyPlaceholders(p, s))
                    .map(ColorUtil::translate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to format cosmetics main menu lore: " + e.getMessage());
            return lores;
        }
    }

    public static void handleItemClick(Player p, String name) {
        String title;

        if (name.equalsIgnoreCase("Back")) {
            String command = CosmeticsPlugin.getInstance().menuData.getString("main-menu.items.Back.action.command");
            if (command == null) {
                command = CosmeticsPlugin.getInstance().menuData.getString("Main-Menu.Back.custom-command");
            }
            if (command == null) {
                p.closeInventory();
            } else {
                Bukkit.dispatchCommand(p, command);
            }
            return;
        }

        if (name.equalsIgnoreCase("Search")) {
            openSearch(p);
            return;
        }

        if (name.equalsIgnoreCase("Balance")) {
            return;
        }

        CosmeticType<?> cosmeticType = CosmeticType.fromName(name);

        if (cosmeticType == null) {
            CosmeticsPlugin.getInstance().getLogger()
                    .warning("Failed to find a cosmetic type with ID: " + name);
            return;
        }

        Message defaultTitle = Messages.of(
                cosmeticType.name().toLowerCase() + Constants.Key.COSMETICS_MENU_TITLES_SUFFIX, cosmeticType.getFormatedName()
        );
        title = ColorUtil.translate(Messages.CATEGORY_MENU_TITLE_FORMAT.value(p).replace("{category}", defaultTitle.value(p)));

        xyz.iamthedefender.cosmetics.data.PlayerData data = CosmeticsPlugin.getInstance().getPlayerManager().getPlayerData(p.getUniqueId());
        new CategoryMenu(cosmeticType, title, 1, data.getSortMode(), data.isOwnedFirst()).open(p);

    }


    public static void openSearch(Player player) {
        player.closeInventory();
        try {
            SignGUI signGUI = SignGUI.builder()
                    .setLines("", "^^^^^^^^^", "Type search query", "above")
                    .setHandler((somePlayer, result) -> {
                        Run.sync(() -> new SearchResultsMenu(result.getLine(0), 1)
                                .open(player));

                        return Collections.emptyList();
                    })
                    .build();

            signGUI.open(player);
        } catch (SignGUIVersionException e) {
            e.printStackTrace();
        }


    }

    private static String formatOwnedProgress(int owned, int total) {
        if (total <= 0) {
            return "&a" + owned + "/0 &8(0%)";
        }
        int percent = (int) Math.round((owned * 100.0D) / total);
        return "&a" + owned + "/" + total + " &8(" + percent + "%)";
    }

    private static String applyPlaceholders(Player player, String value) {
        return CosmeticsPlugin.isPlaceholderAPI() ? PlaceholderAPI.setPlaceholders(player, value) : value;
    }
}
