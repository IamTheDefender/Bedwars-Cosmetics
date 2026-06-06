package xyz.iamthedefender.cosmetics.api.cosmetics.category;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

public abstract class IslandTopper extends Cosmetics {

    /**
     * Display the topper
     *
     * @param player the player to display the topper
     * @param topperLocation the location to display the topper
     * @param selected the selected topper
     */
    public abstract void execute(Player player, Location topperLocation, String selected);

    /**
     * Register the topper.
     * This method should be called when the plugin is enabled.
     */
    @Override
    public void register(){
        // save to config
        String category = "island-topper";
        String configPath = category + "." + getIdentifier() + ".";
        ConfigType type = ConfigType.ISLAND_TOPPERS;
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
        finalLore.addAll(Arrays.asList("&8Island Topper", ""));
        finalLore.addAll(getLore());
        finalLore.addAll(Arrays.asList("", "&eRight-Click to preview!", "" ,"&7Rarity: {rarity}","&7Cost: &6{cost}", "", "{status}"));
        ConfigUtils.saveCosmeticDisplayDefaults(type, configPath, getDisplayName(), finalLore);
        CosmeticRegistry.register(CosmeticType.ISLAND_TOPPERS, this);
    }

    /**
     * Get the topper's field
     * @param fields the field to get
     * @param p the player to get the field
     * @return the field
     */
    public Object getField(FieldsType fields, Player p){
        String category = "island-topper";
        String configPath = category + "." + getIdentifier() + ".";
        ConfigManager config = ConfigUtils.getIslandToppers();
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
            default:
                return null;
        }
    }

    /**
     * Get the default topper
     * @param player the player to get the default topper
     * @return the default topper
     */
    public static @NotNull IslandTopper getDefault(Player player){
        for(IslandTopper islandTopper : Utility.getApi().getIslandTopperList()){
            if (islandTopper.getField(FieldsType.RARITY, player) == RarityType.NONE){
                return islandTopper;
            }
        }
        // This will never return null!
        return new IslandTopper() {
            @Override
            public ItemStack getItem() {
                return XMaterial.BARRIER.parseItem();
            }

            @Override
            public String base64() {
                return null;
            }

            @Override
            public String getIdentifier() {
                return "disabled";
            }

            @Override
            public String getDisplayName() {
                return "DISABLED";
            }

            @Override
            public List<String> getLore() {
                return List.of("Island Toppers are DISABLED for some reason!");
            }

            @Override
            public int getPrice() {
                return 0;
            }

            @Override
            public RarityType getRarity() {
                return RarityType.NONE;
            }

            @Override
            public void execute(Player player, Location topperLocation, String selected) {
            }
        };
    }

    @Override
    public CosmeticType<?> getCosmeticType() {
        return CosmeticType.ISLAND_TOPPERS;
    }

}

