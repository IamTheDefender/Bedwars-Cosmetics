package xyz.iamthedefender.cosmetics.api.cosmetics;


import lombok.Getter;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.*;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class CosmeticType<T> {

    private static final List<CosmeticType<?>> REGISTRY = new ArrayList<>();


    public static final CosmeticType<FinalKillEffect> FINAL_KILL_EFFECTS = register(
            "Final Kill Effect", ConfigUtils.getFinalKillEffects(),
            Utility.getApi().getFinalKillList(), "finalkill-effect", "finalkilleffect",
            FinalKillEffect.class, "FinalKill-Effects", "FinalKillEffects");

    public static final CosmeticType<ProjectileTrail> PROJECTILE_TRAILS = register(
            "Projectile Trail", ConfigUtils.getProjectileTrails(),
            Utility.getApi().getProjectileTrailList(), "projectile-trails", "projectiletrail",
            ProjectileTrail.class, "Projectile-Trails", "ProjectileTrails");

    public static final CosmeticType<BedDestroy> BED_DESTROY = register(
            "Bed Destroy", ConfigUtils.getBedDestroys(),
            Utility.getApi().getBedDestroyList(), "bed-destroy", "beddestroy",
            BedDestroy.class, "Bed-Destroys", "BedBreakEffects");

    public static final CosmeticType<Glyph> GLYPHS = register(
            "Glyph", ConfigUtils.getGlyphs(),
            Utility.getApi().getGlyphsList(), "glyph", "glyph",
            Glyph.class, "Glyphs", "Glyphs");

    public static final CosmeticType<DeathCry> DEATH_CRIES = register(
            "Death Cry", ConfigUtils.getDeathCries(),
            Utility.getApi().getDeathCryList(), "death-cry", "deathcry",
            DeathCry.class, "Death-Cries", "DeathCries");

    public static final CosmeticType<VictoryDance> VICTORY_DANCES = register(
            "Victory Dance", ConfigUtils.getVictoryDances(),
            Utility.getApi().getVictoryDanceList(), "victory-dance", "victorydance",
            VictoryDance.class, "Victory-Dances", "VictoryDances");

    public static final CosmeticType<WoodSkin> WOOD_SKINS = register(
            "Wood Skin", ConfigUtils.getWoodSkins(),
            Utility.getApi().getWoodSkinList(), "wood-skins", "woodskin",
            WoodSkin.class, "WoodSkins", "WoodSkins");

    public static final CosmeticType<Spray> SPRAYS = register(
            "Spray", ConfigUtils.getSprays(),
            Utility.getApi().getSprayList(), "sprays", "spray",
            Spray.class, "Sprays", "Sprays");

    public static final CosmeticType<KillMessage> KILL_MESSAGES = register(
            "Kill Message", ConfigUtils.getKillMessages(),
            Utility.getApi().getKillMessageList(), "kill-message", "killmessage",
            KillMessage.class, "Kill-Messages", "KillMessages");

    public static final CosmeticType<ShopKeeperSkin> SHOPKEEPER_SKINS = register(
            "ShopKeeper Skin", ConfigUtils.getShopKeeperSkins(),
            Utility.getApi().getShopKeeperSkinList(), "shopkeeper-skins", "shopkeeperskin",
            ShopKeeperSkin.class, "ShopKeeperSkins", "ShopKeeperSkins");

    public static final CosmeticType<IslandTopper> ISLAND_TOPPERS = register(
            "Island Topper", ConfigUtils.getIslandToppers(),
            Utility.getApi().getIslandTopperList(), "island-topper", "islandtopper",
            IslandTopper.class, "Island-Toppers", "IslandToppers");


    private final String internalName;
    private final String formattedName;
    private final ConfigManager configManager;
    private final List<T> itemsList;
    private final String sectionKey;
    private final String permissionFormat;
    private final Class<T> cosmeticsClass;
    private final String menuKey;

    public CosmeticType(String internalName, String formattedName, ConfigManager configManager, List<T> itemsList,
                        String sectionKey, String permissionFormat, Class<T> cosmeticsClass, String menuKey) {
        this.internalName = internalName;
        this.formattedName = formattedName;
        this.configManager = configManager;
        this.itemsList = itemsList;
        this.sectionKey = sectionKey;
        this.permissionFormat = permissionFormat;
        this.cosmeticsClass = cosmeticsClass;
        this.menuKey = menuKey;
    }

    private static <T> CosmeticType<T> register(String formattedName, ConfigManager configManager,
                                                List<T> itemsList, String sectionKey,
                                                String permissionFormat, Class<T> cosmeticsClass,
                                                String menuKey, String internalName) {
        CosmeticType<T> type = new CosmeticType<>(internalName, formattedName, configManager, itemsList,
                sectionKey, permissionFormat, cosmeticsClass, menuKey);
        REGISTRY.add(type);
        return type;
    }

    public static List<CosmeticType<?>> values() {
        return Collections.unmodifiableList(REGISTRY);
    }

    public static CosmeticType<?> fromName(String name) {
        return REGISTRY.stream()
                .filter(type -> type.menuKey.equalsIgnoreCase(name)
                        || type.internalName.equalsIgnoreCase(name)
                        || type.sectionKey.equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public ConfigManager getConfig() {
        return configManager;
    }

    public String name() {
        return internalName;
    }

    @Deprecated
    public String getFormatedName() {
        return formattedName;
    }

    @Override
    public String toString() {
        return internalName;
    }
}
