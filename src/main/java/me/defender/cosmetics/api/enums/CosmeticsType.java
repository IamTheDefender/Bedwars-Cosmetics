package me.defender.cosmetics.api.enums;


import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.configuration.ConfigUtils;

import java.util.List;

import static me.defender.cosmetics.api.util.StartupUtils.*;

public enum CosmeticsType {
    FinalKillEffects("Final Kill Effect", ConfigUtils.getFinalKillEffects(), finalKillList, "finalkill-effect", "finalkilleffect"),
    ProjectileTrails("Projectile Trail", ConfigUtils.getProjectileTrails(), projectileTrailList, "projectile-trails", "projectiletrail"),
    BedBreakEffects("Bed Destroy", ConfigUtils.getBedDestroys(), bedDestroyList, "bed-destroy", "beddestroy"),
    Glyphs("Glyph", ConfigUtils.getGlyphs(), glyphsList, "glyph", "glyph"),
    DeathCries("Death Cry", ConfigUtils.getDeathCries(), deathCryList, "death-cry", "deathcry"),
    VictoryDances("Victory Dance", ConfigUtils.getVictoryDances(), victoryDancesList, "victory-dance", "victorydance"),
    WoodSkins("Wood Skin", ConfigUtils.getWoodSkins(), woodSkinsList, "wood-skins", "woodskin"),
    Sprays("Spray", ConfigUtils.getSprays(), sprayList, "sprays", "spray"),
    KillMessage("Kill Message", ConfigUtils.getKillMessages(), killMessageList, "kill-message", "killmessage"),
    ShopKeeperSkin("ShopKeeper Skin", ConfigUtils.getShopKeeperSkins(), shopKeeperSkinList, "shopkeeper-skins", "shopkeeperskin"),
    IslandTopper("Island Topper", ConfigUtils.getIslandToppers(), islandTopperList, "island-topper", "islandtopper");

    private String formatedName;
    private ConfigManager configManager;
    private List<?> itemsList;
    private String sectionKey;
    private String permissionFormat;
    CosmeticsType(String formatedName, ConfigManager configManager, List<?> itemsList, String sectionKey, String permissionFormat){
        this.formatedName = formatedName;
        this.configManager = configManager;
        this.itemsList = itemsList;
        this.sectionKey = sectionKey;
        this.permissionFormat = permissionFormat;
    }


    public String getFormatedName() {
        return formatedName;
    }

    public ConfigManager getConfig() {
        return configManager;
    }

    public List<?> getItemsList(){
        return itemsList;
    }

    public String getSectionKey() {
        return sectionKey;
    }

    public String getPermissionFormat() {
        return permissionFormat;
    }
}
