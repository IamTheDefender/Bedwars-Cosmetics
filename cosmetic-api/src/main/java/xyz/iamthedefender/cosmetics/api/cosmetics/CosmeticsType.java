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

    public static CosmeticsType fromName(String name) {
        for (CosmeticsType type : values()) {
            if (type.name().replace("-", "").replace("_", "").equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

}
