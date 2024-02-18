package me.defender.cosmetics.api.cosmetics.category;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.cosmetics.Cosmetics;
import me.defender.cosmetics.api.cosmetics.FieldsType;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.api.handler.ITeamHandler;
import me.defender.cosmetics.util.StartupUtils;
import me.defender.cosmetics.util.Utility;
import me.defender.cosmetics.util.config.ConfigType;
import me.defender.cosmetics.util.config.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.defender.cosmetics.util.Utility.saveIfNotExistsLang;
import static me.defender.cosmetics.util.config.ConfigUtils.get;
import static me.defender.cosmetics.util.config.ConfigUtils.saveIfNotFound;

/**
 * Bed destroy effect.
 * All bed destroy effects must extend this class.
 */
public abstract class BedDestroy extends Cosmetics {

    /**
     * Execute the bed destroy effect.
     * This method will be called when a bed is destroyed.
     *
     * @param player player who destroyed the bed.
     * @param bedLocation location of the bed.
     */
    public abstract void execute(Player player, Location bedLocation, ITeamHandler victimTeam);
    /**
     * Register the bed destroy effect.
     * This method should be called when the plugin is enabled.
     */
    @Override
    public void register(){
        String category = "bed-destroy";
        String configPath = category + "." + getIdentifier() + ".";
        ConfigType type = ConfigType.BED_DESTROYS;
        saveIfNotFound(type, configPath + "price", getPrice());
         saveIfNotFound(type, configPath + "rarity", getRarity().toString());
        String item = null;
        if (!XMaterial.matchXMaterial(getItem()).isSupported()) {
            Bukkit.getLogger().severe("The item is not supported! (Information: Category name is " + category + " and item name is " + getIdentifier());
            return;
        }
        if (XMaterial.matchXMaterial(getItem()).isSimilar(XMaterial.PLAYER_HEAD.parseItem())){
            get(type).setItemStack(configPath + "item", getItem(), base64());
        }else{
            get(type).setItemStack(configPath + "item", getItem());
        }

        // save to language file
        saveIfNotExistsLang("cosmetics." + configPath + "name", getDisplayName());
        // Format the lore
        List<String> finalLore = new ArrayList<>();
        finalLore.addAll(Arrays.asList("&8Bed Destroy", ""));
        finalLore.addAll(getLore());
        finalLore.addAll(Arrays.asList("", "&7Rarity: {rarity}","&7Cost: &6{cost}", "", "{status}"));

        saveIfNotExistsLang("cosmetics." + configPath + "lore", finalLore);
        StartupUtils.bedDestroyList.add(this);
    }

    /**
     * Get the field of this bed destroy effect.
     * This method will be used to get the field of this bed destroy effect.
     *
     * @param fields field to get.
     * @param p player who is viewing the effect.
     * @return field of this bed destroy effect.
     */
    public Object getField(FieldsType fields, Player p){
        String category = "bed-destroy";
        String configPath = category + "." + getIdentifier() + ".";
        ConfigManager config = ConfigUtils.getBedDestroys();
        switch (fields){
            case NAME:
                return Utility.getMSGLang(p, "cosmetics." + configPath + "name");
            case PRICE:
                return config.getInt(configPath + "price");
            case LORE:
                return Utility.getListLang(p, "cosmetics." + configPath + "lore");
            case RARITY:
                return RarityType.valueOf(config.getString(configPath + "rarity"));
            case ITEM_STACK:
                return config.getItemStack(configPath + "item");
            default:
                return null;
        }
    }

    /**
     * Get the default bed destroy effect.
     * This method will be used to get the default bed destroy effect.
     *
     * @param player player who is viewing the effect.
     * @return default bed destroy effect.
     */
    public static @NotNull BedDestroy getDefault(Player player){
        for(BedDestroy bedDestroy : StartupUtils.bedDestroyList){
            if (bedDestroy.getField(FieldsType.RARITY, player) == RarityType.NONE){
                return bedDestroy;
            }
        }

        // This will never return null!
        return null;
    }

}

