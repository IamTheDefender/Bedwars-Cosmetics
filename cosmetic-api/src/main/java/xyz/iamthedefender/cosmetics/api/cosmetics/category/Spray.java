package xyz.iamthedefender.cosmetics.api.cosmetics.category;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.ItemFrame;
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

public abstract class Spray extends Cosmetics {
    private final String category = "sprays";
    ConfigManager config = ConfigUtils.getSprays();
    ConfigType type = ConfigType.SPRAYS;

    /**
     * Register the spray
     * This method should be called when the plugin is enabled.
     */
    @Override
    public void register(){
        // save to config
        String configPath = category + "." + getIdentifier() + ".";
        saveIfNotFound(type, configPath + "price", getPrice());
         saveIfNotFound(type, configPath + "rarity", getRarity().toString());
        if (!XMaterial.matchXMaterial(getItem()).isSupported()) {
            Bukkit.getLogger().severe("The item is not supported! (Information: Category name is " + category + " and item name is " + getIdentifier());
            return;
        }
        if (XMaterial.matchXMaterial(getItem()).isSimilar(XMaterial.PLAYER_HEAD.parseItem())){
            get(type).setItemStack(configPath + "item", getItem(), base64());
        }else{
            get(type).setItemStack(configPath + "item", getItem());
        }

        List<String> finalLore = new ArrayList<>();
        finalLore.addAll(Arrays.asList("&8Sprays", ""));
        finalLore.addAll(getLore());
        finalLore.addAll(Arrays.asList("", "&eRight-Click to preview!", "" ,"&7Rarity: {rarity}","&7Cost: &6{cost}", "", "{status}"));
        ConfigUtils.saveCosmeticDisplayDefaults(type, configPath, getDisplayName(), finalLore);
        CosmeticRegistry.register(CosmeticType.SPRAYS, this);
    }

    /**
     * Get the topper's field
     * @param fields the field to get
     * @param p the player to get the field
     * @return the field
     */
    public Object getField(FieldsType fields, Player p){
        String configPath = category + "." + getIdentifier() + ".";

        switch (fields){
            case NAME:
                return xyz.iamthedefender.cosmetics.api.util.Messages.cosmeticDisplayName(
                        configPath, Utility.getMSGLang(p, "cosmetics." + configPath + "name")
                ).value(p);
            case PRICE:
                return config.getInt(configPath + "price");
            case LORE:
                return xyz.iamthedefender.cosmetics.api.util.Messages.cosmeticDisplayLore(
                        configPath, Utility.getListLang(p, "cosmetics." + configPath + "lore")
                ).list(p);
            case RARITY:
                return RarityType.valueOf(config.getString(configPath + "rarity"));
            case ITEM_STACK:
                return config.getItemStack(configPath + "item");
            case FILE:
                return get(type).getString(configPath + "file");
            case URL:
                return get(type).getString(configPath + "url");
            default:
                return null;
        }
    }

    /**
     * Use the spray
     *
     * @param player the player to use the spray
     * @param frame the frame to use the spray
     */
    public abstract void execute(Player player, ItemFrame frame);

    /**
     * Get the default spray
     * @param player the player to get the default spray
     * @return the default spray
     */
    public static @NotNull Spray getDefault(Player player){
        for(Spray spray : Utility.getApi().getSprayList()){
            if (spray.getField(FieldsType.RARITY, player) == RarityType.NONE){
                return spray;
            }
        }

        // This will never return null!
        return null;
    }

    @Override
    public CosmeticType<?> getCosmeticType() {
        return CosmeticType.SPRAYS;
    }
}
