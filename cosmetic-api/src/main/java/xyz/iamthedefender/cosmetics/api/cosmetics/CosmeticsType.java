package xyz.iamthedefender.cosmetics.api.cosmetics;


import lombok.Getter;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.*;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;

import java.util.List;

@Getter
public enum CosmeticsType {
    FinalKillEffects("Final Kill Effect", ConfigUtils.getFinalKillEffects(), Utility.getApi().getFinalKillList(), "finalkill-effect", "finalkilleffect", FinalKillEffect.class),
    ProjectileTrails("Projectile Trail", ConfigUtils.getProjectileTrails(), Utility.getApi().getProjectileTrailList(), "projectile-trails", "projectiletrail", ProjectileTrail.class),
    BedBreakEffects("Bed Destroy", ConfigUtils.getBedDestroys(), Utility.getApi().getBedDestroyList(), "bed-destroy", "beddestroy", BedDestroy.class),
    Glyphs("Glyph", ConfigUtils.getGlyphs(), Utility.getApi().getGlyphsList(), "glyph", "glyph", Glyph.class),
    DeathCries("Death Cry", ConfigUtils.getDeathCries(), Utility.getApi().getDeathCryList(), "death-cry", "deathcry", DeathCry.class),
    VictoryDances("Victory Dance", ConfigUtils.getVictoryDances(), Utility.getApi().getVictoryDanceList(), "victory-dance", "victorydance", VictoryDance.class),
    WoodSkins("Wood Skin", ConfigUtils.getWoodSkins(), Utility.getApi().getWoodSkinList(), "wood-skins", "woodskin", WoodSkin.class),
    Sprays("Spray", ConfigUtils.getSprays(), Utility.getApi().getSprayList(), "sprays", "spray", Spray.class),
    KillMessages("Kill Message", ConfigUtils.getKillMessages(), Utility.getApi().getKillMessageList(), "kill-message", "killmessage", KillMessage.class),
    ShopKeeperSkins("ShopKeeper Skin", ConfigUtils.getShopKeeperSkins(), Utility.getApi().getShopKeeperSkinList(), "shopkeeper-skins", "shopkeeperskin", ShopKeeperSkin.class),
    IslandToppers("Island Topper", ConfigUtils.getIslandToppers(), Utility.getApi().getIslandTopperList(), "island-topper", "islandtopper", IslandTopper.class);

    private final String formatedName;
    private final ConfigManager configManager;
    private final List<?> itemsList;
    private final String sectionKey;
    private final String permissionFormat;
    private final Class<?> cosmeticsClass;

    CosmeticsType(String formatedName, ConfigManager configManager, List<?> itemsList, String sectionKey, String permissionFormat, Class<?> cosmeticsClass) {
        this.formatedName = formatedName;
        this.configManager = configManager;
        this.itemsList = itemsList;
        this.sectionKey = sectionKey;
        this.permissionFormat = permissionFormat;
        this.cosmeticsClass = cosmeticsClass;
    }

    public ConfigManager getConfig() {
        return configManager;
    }

    /**
     * Resolves a Main-Menu config key (e.g. {@code Victory-Dances}, {@code Bed-Destroys})
     * or an enum/section name to a {@link CosmeticsType}.
     * <p>
     * Earlier versions only compared the raw key to {@link #name()}, so hyphenated
     * Main-Menu entries never opened a category menu.
     */
    public static CosmeticsType fromName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        // Exact Main-Menu.yml keys used by openMenus(...)
        switch (name) {
            case "Sprays":
                return Sprays;
            case "Projectile-Trails":
                return ProjectileTrails;
            case "FinalKill-Effects":
                return FinalKillEffects;
            case "Kill-Messages":
                return KillMessages;
            case "Glyphs":
                return Glyphs;
            case "Bed-Destroys":
                return BedBreakEffects;
            case "WoodSkins":
            case "Wood-Skins":
                return WoodSkins;
            case "Victory-Dances":
                return VictoryDances;
            case "Island-Toppers":
            case "IslandToppers":
                return IslandToppers;
            case "ShopKeeperSkins":
                return ShopKeeperSkins;
            case "Death-Cries":
                return DeathCries;
            default:
                break;
        }

        String compact = name.replace("-", "").replace("_", "");
        String normalized = normalizeKey(name);

        for (CosmeticsType type : values()) {
            if (type.name().equalsIgnoreCase(name) || type.name().equalsIgnoreCase(compact)) {
                return type;
            }
            if (normalizeKey(type.name()).equals(normalized)
                    || normalizeKey(type.sectionKey).equals(normalized)
                    || normalizeKey(type.formatedName).equals(normalized)) {
                return type;
            }
        }
        return null;
    }

    private static String normalizeKey(String s) {
        String out = s.toLowerCase().replaceAll("[^a-z0-9]", "");
        // Bed-Destroys -> beddestroy, death-cry -> deathcry (singularize trailing s only)
        if (out.endsWith("s") && out.length() > 1) {
            out = out.substring(0, out.length() - 1);
        }
        return out;
    }

}
