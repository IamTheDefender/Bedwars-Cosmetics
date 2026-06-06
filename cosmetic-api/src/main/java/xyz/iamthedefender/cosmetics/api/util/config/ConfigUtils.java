package xyz.iamthedefender.cosmetics.api.util.config;


import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.util.ColorUtil;
import xyz.iamthedefender.cosmetics.api.util.Messages;
import xyz.iamthedefender.cosmetics.api.util.Utility;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ConfigUtils {

    private static final EnumMap<ConfigType, ConfigManager> CONFIG_CACHE = new EnumMap<>(ConfigType.class);
    private static final List<Integer> DEFAULT_CATEGORY_SLOTS = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);

    private static ConfigManager getCachedConfig(ConfigType configType, String dir) {
        return CONFIG_CACHE.computeIfAbsent(configType, type ->
                new ConfigManager(Utility.getPlugin(), type.getFileName(), dir));
    }

    public static ConfigManager getBedDestroys() {
        return get(ConfigType.BED_DESTROYS);
    }

    public static ConfigManager getDeathCries() {
        return get(ConfigType.DEATH_CRIES);
    }

    public static ConfigManager getFinalKillEffects() {
        return get(ConfigType.FINAL_KILL_EFFECTS);
    }

    public static ConfigManager getGlyphs() {
        return get(ConfigType.GLYPHS);
    }

    public static ConfigManager getIslandToppers() {
        return get(ConfigType.ISLAND_TOPPERS);
    }

    public static ConfigManager getKillMessages() {
        return get(ConfigType.KILL_MESSAGES);
    }

    public static ConfigManager getProjectileTrails() {
        return get(ConfigType.PROJECTILE_TRAILS);
    }

    public static ConfigManager getShopKeeperSkins() {
        return get(ConfigType.SHOP_KEEPER_SKINS);
    }

    public static ConfigManager getSprays() {
        return get(ConfigType.SPRAYS);
    }

    public static ConfigManager getVictoryDances() {
        return get(ConfigType.VICTORY_DANCES);
    }

    public static ConfigManager getWoodSkins() {
        return get(ConfigType.WOOD_SKINS);
    }


    public static ConfigManager get(ConfigType configType){
        if (configType == ConfigType.Main_Config){
            return getMainConfig();
        }
        if (configType == ConfigType.Main_Menu){
            return Utility.getApi().getMenuData();
        }
        return getCachedConfig(configType, Utility.getApi().getHandler().getAddonPath() + "/Categories");
    }

    public static ConfigManager getMainConfig(){
        return getCachedConfig(ConfigType.Main_Config, Utility.getApi().getHandler().getAddonPath());
    }

    public static void saveIfNotFound(ConfigType configType, String path ,Object data){
        if (Objects.isNull(get(configType).getYml().get(path))){
            get(configType).set(path, data);
            get(configType).save();
            get(configType).reload();
        }
    }

    public static void saveIfNotFound(ConfigManager config, String path, Object data) {
        if (Objects.isNull(config.getYml().get(path))) {
            config.set(path, data);
            config.save();
            config.reload();
        }
    }

    public static void saveCosmeticDisplayDefaults(ConfigType configType, String configPath, String displayName, List<String> lore) {
        ConfigManager config = get(configType);
        String configuredName = config.getString(configPath + "display.name", displayName);
        List<String> configuredLore = config.contains(configPath + "display.lore")
                ? config.getYml().getStringList(configPath + "display.lore")
                : lore;
        Messages.cosmeticDisplayName(configPath, configuredName).saveIfMissing();
        Messages.cosmeticDisplayLore(configPath, configuredLore).saveIfMissing();
    }

    public static String getConfiguredString(ConfigManager config, String configPath, Supplier<String> fallback) {
        if (config.contains(configPath)) {
            return ColorUtil.translate(config.getString(configPath, ""));
        }
        return fallback.get();
    }

    public static List<String> getConfiguredStringList(ConfigManager config, String configPath, Supplier<List<String>> fallback) {
        if (config.contains(configPath)) {
            return config.getYml().getStringList(configPath).stream()
                    .map(ColorUtil::translate)
                    .collect(Collectors.toList());
        }
        return fallback.get();
    }

    public static void addSlotsList(){
        for (CosmeticType<?> cosmeticType : CosmeticType.values()){
            ConfigManager config = cosmeticType.getConfig();
            if (!config.contains("gui.layout.item-slots")) {
                config.getYml().set("gui.layout.item-slots", DEFAULT_CATEGORY_SLOTS);
            }
            if (!config.contains("gui.layout.rows")) {
                config.getYml().set("gui.layout.rows", 6);
            }
            if (!config.contains("gui.fill-empty.enabled")) {
                config.getYml().set("gui.fill-empty.enabled", true);
            }
            if (!config.contains("gui.fill-empty.item")) {
                config.getYml().set("gui.fill-empty.item", "BLACK_STAINED_GLASS_PANE:0");
            }
            if (!config.contains("gui.navigation.back.enabled")) {
                config.getYml().set("gui.navigation.back.enabled", true);
            }
            if (!config.contains("gui.navigation.back.slot")) {
                config.getYml().set("gui.navigation.back.slot", 49);
            }
            if (!config.contains("gui.navigation.back.item")) {
                config.getYml().set("gui.navigation.back.item", "ARROW:0");
            }
            if (!config.contains("gui.navigation.next.enabled")) {
                config.getYml().set("gui.navigation.next.enabled", true);
            }
            if (!config.contains("gui.navigation.next.slot")) {
                config.getYml().set("gui.navigation.next.slot", 47);
            }
            if (!config.contains("gui.navigation.next.item")) {
                config.getYml().set("gui.navigation.next.item", "ARROW:0");
            }
            if (!config.contains("gui.navigation.previous.enabled")) {
                config.getYml().set("gui.navigation.previous.enabled", true);
            }
            if (!config.contains("gui.navigation.previous.slot")) {
                config.getYml().set("gui.navigation.previous.slot", 51);
            }
            if (!config.contains("gui.navigation.previous.item")) {
                config.getYml().set("gui.navigation.previous.item", "ARROW:0");
            }
            if (!config.contains("gui.sort.slot")) {
                config.getYml().set("gui.sort.slot", 50);
            }
            if (!config.contains("gui.navigation.back.name")) {
                config.getYml().set("gui.navigation.back.name", "&aBack");
            }
            if (!config.contains("gui.navigation.back.lore")) {
                config.getYml().set("gui.navigation.back.lore", List.of("&7Click to go back."));
            }
            if (!config.contains("gui.navigation.next.name")) {
                config.getYml().set("gui.navigation.next.name", "&aNext page");
            }
            if (!config.contains("gui.navigation.next.lore")) {
                config.getYml().set("gui.navigation.next.lore", List.of("&7View the next page."));
            }
            if (!config.contains("gui.navigation.previous.name")) {
                config.getYml().set("gui.navigation.previous.name", "&aPrevious page");
            }
            if (!config.contains("gui.navigation.previous.lore")) {
                config.getYml().set("gui.navigation.previous.lore", List.of("&7View the previous page."));
            }
            if (!config.contains("gui.sort.item")) {
                config.getYml().set("gui.sort.item", "HOPPER:0");
            }
            config.save();
        }
    }

    public static void addExtrasToLang(){
        for (CosmeticType<?> cosmeticType : CosmeticType.values()) {
            ConfigManager config = cosmeticType.getConfig();
            Messages.of("not-purchase-able", config.getString("messages.status.locked", "&cLOCKED.")).saveIfMissing();
            Messages.of("gui.navigation.back.name", config.getString("gui.navigation.back.name", "&aBack")).saveIfMissing();
            Messages.of("gui.navigation.back.lore", config.contains("gui.navigation.back.lore")
                    ? config.getYml().getStringList("gui.navigation.back.lore")
                    : List.of("&7Click to go back.")).saveIfMissing();
            Messages.of("gui.navigation.next.name", config.getString("gui.navigation.next.name", "&aNext page")).saveIfMissing();
            Messages.of("gui.navigation.next.lore", config.contains("gui.navigation.next.lore")
                    ? config.getYml().getStringList("gui.navigation.next.lore")
                    : List.of("&7View the next page.")).saveIfMissing();
            Messages.of("gui.navigation.previous.name", config.getString("gui.navigation.previous.name", "&aPrevious page")).saveIfMissing();
            Messages.of("gui.navigation.previous.lore", config.contains("gui.navigation.previous.lore")
                    ? config.getYml().getStringList("gui.navigation.previous.lore")
                    : List.of("&7View the previous page.")).saveIfMissing();
            Messages.of("gui.sort.name", config.getString("gui.sort.name", "&eSorted by: &6{sort_mode}")).saveIfMissing();
            Messages.of("gui.sort.lore", config.contains("gui.sort.lore")
                    ? config.getYml().getStringList("gui.sort.lore")
                    : List.of(
                    "&7Sorts by rarity: &f{sort_mode}",
                    "",
                    "&6Next sort: &f{next_sort_mode}",
                    "&eLeft click to use!",
                    "",
                    "&7Owned items first: {owned_first}",
                    "&eRight click to toggle!"
            )).saveIfMissing();
        }
    }
}
