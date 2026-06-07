package xyz.iamthedefender.cosmetics.api.cosmetics.category;

import com.cryptomorin.xseries.XEntityType;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.*;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigType;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils.get;
import static xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils.saveIfNotFound;

public abstract class ShopKeeperSkin extends Cosmetics {


    private final String category = "shopkeeper-skins";
    ConfigManager config = ConfigUtils.getShopKeeperSkins();
    ConfigType type = ConfigType.SHOP_KEEPER_SKINS;

    /**
     * Register the shopkeeper skin
     * This method should be called when the plugin is enabled.
     */
    @Override
    public void register() {
        // save to config
        String configPath = category + "." + getIdentifier() + ".";
        saveIfNotFound(type, configPath + "price", getPrice());
        saveIfNotFound(type, configPath + "rarity", getRarity().toString());
        if (!XMaterial.matchXMaterial(getItem()).isSupported()) {
            Bukkit.getLogger().severe("The item is not supported! (Information: Category name is " + category + " and item name is " + getIdentifier());
            return;
        }
        if (XMaterial.matchXMaterial(getItem()).isSimilar(XMaterial.PLAYER_HEAD.parseItem())) {
            get(type).setItemStack(configPath + "item", getItem(), base64());
        } else {
            get(type).setItemStack(configPath + "item", getItem());
        }

        List<String> finalLore = new ArrayList<>();
        finalLore.addAll(Arrays.asList("&8ShopKeeper Skins", ""));
        finalLore.addAll(getLore());
        if (getRarity() != RarityType.NONE) {
            finalLore.addAll(Arrays.asList("", "&eRight-Click to preview!", "", "&7Rarity: {rarity}", "&7Cost: &6{cost}", "", "{status}"));
        } else {
            finalLore.addAll(Arrays.asList("", "&7Rarity: {rarity}", "&7Cost: &6{cost}", "", "{status}"));
        }

        ConfigUtils.saveCosmeticDisplayDefaults(type, configPath, getDisplayName(), finalLore);
        CosmeticRegistry.register(CosmeticType.SHOPKEEPER_SKINS, this);
    }

    /**
     * Get the topper's field
     *
     * @param fields the field to get
     * @param p      the player to get the field
     * @return the field
     */
    public <T> T getField(FieldsType field, Player p) {
        String configPath = category + "." + getIdentifier() + ".";

        Object value;
        switch (field) {
            case NAME:
                value = xyz.iamthedefender.cosmetics.api.util.Messages.cosmeticDisplayName(
                        configPath, Utility.getMSGLang(p, "cosmetics." + configPath + "name")
                ).value(p);
                break;
            case PRICE:
                value = config.getInt(configPath + "price");
                break;
            case LORE:
                value = xyz.iamthedefender.cosmetics.api.util.Messages.cosmeticDisplayLore(
                        configPath, Utility.getListLang(p, "cosmetics." + configPath + "lore")
                ).list(p);
                break;
            case RARITY:
                value = RarityType.valueOf(config.getString(configPath + "rarity"));
                break;
            case ITEM_STACK:
                value = config.getItemStack(configPath + "item");
                break;
            case ENTITY_TYPE:
                String raw = config.getString(configPath + field.path());

                if (raw != null) {
                    XEntityType xEntityType = XEntityType.valueOf(raw.toUpperCase());
                    value = xEntityType.get();
                }

            default:
                value = config.get(configPath + field.path());

                if (field.type().isEnum()) {
                    String stored = config.getString(configPath + field.path());

                    if (stored != null)
                        value = Enum.valueOf((Class<? extends Enum>) field.type(), stored);
                }

                break;
        }

        if (value == null) return null;

        return (T) field.getType().cast(value);
    }


    /**
     * Display the shopkeeper skin to the player
     *
     * @param player          the player to display the shopkeeper skin
     * @param shopLocation    the location of the shopkeeper
     * @param upgradeLocation the location of the upgrade shopkeeper
     */
    public void execute(Player player, Location shopLocation, Location upgradeLocation) {
        execute(player, Arrays.asList(shopLocation, upgradeLocation));
    }

    public abstract void execute(Player player, List<Location> spawnLocations);

    /**
     * Get the default shopkeeper skin
     *
     * @param player the player to get the default shopkeeper skin
     * @return the default shopkeeper skin
     */
    public static @NotNull ShopKeeperSkin getDefault(Player player) {
        for (ShopKeeperSkin shopKeeperSkin : Utility.getApi().getShopKeeperSkinList()) {
            if (shopKeeperSkin.getField(FieldsType.RARITY, player) == RarityType.NONE) {
                return shopKeeperSkin;
            }
        }

        // This will never return null!
        return null;
    }

    @Override
    public CosmeticType<?> getCosmeticType() {
        return CosmeticType.SHOPKEEPER_SKINS;
    }
}

