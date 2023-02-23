package me.defender.cosmetics.api.categories.deathcries;


import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import me.defender.cosmetics.api.categories.Cosmetics;
import me.defender.cosmetics.api.enums.ConfigType;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.utils.StartupUtils;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.configuration.ConfigUtils;
import me.defender.cosmetics.api.utils.Utility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.defender.cosmetics.api.configuration.ConfigUtils.get;
import static me.defender.cosmetics.api.configuration.ConfigUtils.saveIfNotFound;
import static me.defender.cosmetics.api.utils.Utility.saveIfNotExistsLang;

public abstract class DeathCry extends Cosmetics {

    /**
     * @return the sound of the death cry
     */
    public abstract XSound getSound();
    /**
     * @return the pitch of the sound
     */
    public abstract float getPitch();
    /**
     * @return the volume of the sound
     */
    public abstract float getVolume();

    /**
     * Register the death cry
     * This method should be called when the plugin is enabled.
     */
    @Override
    public void register(){
        // save to config
        String category = "death-cry";
        String configPath = category + "." + getIdentifier() + ".";
        ConfigType type = ConfigType.DEATH_CRIES;
        saveIfNotFound(type, configPath + "price", getPrice());
         saveIfNotFound(type, configPath + "rarity", getRarity().toString());
        if(!XMaterial.matchXMaterial(getItem()).isSupported()) {
            Bukkit.getLogger().severe("The item is not supported! (Information: Category name is " + category + " and item name is " + getIdentifier());
            return;
        }
        if(XMaterial.matchXMaterial(getItem()).isSimilar(XMaterial.PLAYER_HEAD.parseItem())){
            get(type).setItemStack(configPath + "item", getItem(), base64());
        }else{
            get(type).setItemStack(configPath + "item", getItem());
        }
        saveIfNotFound(type, configPath + "pitch", getPitch());
        saveIfNotFound(type, configPath + "volume", getVolume());
        saveIfNotFound(type, configPath + "sound", getSound().name());

        // save to language file
        saveIfNotExistsLang("cosmetics." + configPath + "name", getDisplayName());
        // Format the lore
        List<String> finalLore = new ArrayList<>();
        finalLore.addAll(Arrays.asList("&8Death Cry", ""));
        finalLore.addAll(getLore());
        if(getRarity() != RarityType.NONE){
            finalLore.addAll(Arrays.asList("", "&eRight-Click to preview!", "" ,"&7Rarity: {rarity}","&7Cost: &6{cost}", "", "{status}"));
        }else{
            finalLore.addAll(Arrays.asList("", "&7Rarity: {rarity}","&7Cost: &6{cost}", "", "{status}"));
        }

        saveIfNotExistsLang("cosmetics." + configPath + "lore", finalLore);
        StartupUtils.deathCryList.add(this);
    }

    /**
     * Get the field of the death cry
     * @param fields the field to get
     * @param p the player to get the field for
     * @return the field
     */
    public Object getField(FieldsType fields, Player p){
        String category = "death-cry";
        String configPath = category + "." + getIdentifier() + ".";
        ConfigManager config = ConfigUtils.getDeathCries();
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
            case PITCH:
                return config.getYml().get(configPath + "pitch");
            case VOLUME:
                return config.getYml().get(configPath + "volume");
            case SOUND:
                return XSound.matchXSound(config.getString(configPath + "sound")).get();
            default:
                return null;
        }
    }

    /**
     * Get the default death cry
     * @param player the player to get the default death cry for
     * @return the default death cry
     */
    public static @NotNull DeathCry getDefault(Player player){
        for(DeathCry deathCry : StartupUtils.deathCryList){
            if(deathCry.getField(FieldsType.RARITY, player) == RarityType.NONE){
                return deathCry;
            }
        }

        // This will never return null!
        return null;
    }
}
