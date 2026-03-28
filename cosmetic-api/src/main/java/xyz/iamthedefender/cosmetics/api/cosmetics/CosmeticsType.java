package xyz.iamthedefender.cosmetics.api.cosmetics;


import lombok.Getter;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.*;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;

import java.util.List;

@Getter
public enum CosmeticsType {
    FinalKillEffects("Final Kill Effect", ConfigUtils.getFinalKillEffects(), Utility.getApi().getFinalKillList(), "finalkill-effect", "finalkilleffect", FinalKillEffect.class, "FinalKill-Effects"),
    ProjectileTrails("Projectile Trail", ConfigUtils.getProjectileTrails(), Utility.getApi().getProjectileTrailList(), "projectile-trails", "projectiletrail", ProjectileTrail.class, "Projectile-Trails"),
    BedBreakEffects("Bed Destroy", ConfigUtils.getBedDestroys(), Utility.getApi().getBedDestroyList(), "bed-destroy", "beddestroy", BedDestroy.class, "Bed-Destroys"),
    Glyphs("Glyph", ConfigUtils.getGlyphs(), Utility.getApi().getGlyphsList(), "glyph", "glyph", Glyph.class, "Glyphs"),
    DeathCries("Death Cry", ConfigUtils.getDeathCries(), Utility.getApi().getDeathCryList(), "death-cry", "deathcry", DeathCry.class, "Death-Cries"),
    VictoryDances("Victory Dance", ConfigUtils.getVictoryDances(), Utility.getApi().getVictoryDanceList(), "victory-dance", "victorydance", VictoryDance.class, "Victory-Dances"),
    WoodSkins("Wood Skin", ConfigUtils.getWoodSkins(), Utility.getApi().getWoodSkinList(), "wood-skins", "woodskin", WoodSkin.class, "WoodSkins"),
    Sprays("Spray", ConfigUtils.getSprays(), Utility.getApi().getSprayList(), "sprays", "spray", Spray.class, "Sprays"),
    KillMessages("Kill Message", ConfigUtils.getKillMessages(), Utility.getApi().getKillMessageList(), "kill-message", "killmessage", KillMessage.class, "Kill-Messages"),
    ShopKeeperSkins("ShopKeeper Skin", ConfigUtils.getShopKeeperSkins(), Utility.getApi().getShopKeeperSkinList(), "shopkeeper-skins", "shopkeeperskin", ShopKeeperSkin.class, "ShopKeeperSkins"),
    IslandToppers("Island Topper", ConfigUtils.getIslandToppers(), Utility.getApi().getIslandTopperList(), "island-topper", "islandtopper", IslandTopper.class, "Island-Toppers");

    private final String formatedName;
    private final ConfigManager configManager;
    private final List<?> itemsList;
    private final String sectionKey;
    private final String permissionFormat;
    private final Class<?> cosmeticsClass;
    private final String menuKey;

    CosmeticsType(String formatedName, ConfigManager configManager, List<?> itemsList, String sectionKey, String permissionFormat, Class<?> cosmeticsClass, String menuKey) {
        this.formatedName = formatedName;
        this.configManager = configManager;
        this.itemsList = itemsList;
        this.sectionKey = sectionKey;
        this.permissionFormat = permissionFormat;
        this.cosmeticsClass = cosmeticsClass;
        this.menuKey = menuKey;
    }

    public ConfigManager getConfig() {
        return configManager;
    }

    public static CosmeticsType fromName(String name) {
        for (CosmeticsType type : values()) {
            if (type.menuKey.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}