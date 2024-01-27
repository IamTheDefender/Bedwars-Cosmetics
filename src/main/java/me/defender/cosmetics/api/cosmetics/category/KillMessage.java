package me.defender.cosmetics.api.cosmetics.category;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.cosmetics.Cosmetics;
import me.defender.cosmetics.util.config.ConfigType;
import me.defender.cosmetics.api.cosmetics.FieldsType;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.util.StartupUtils;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.util.config.ConfigUtils;
import me.defender.cosmetics.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.defender.cosmetics.util.config.ConfigUtils.get;
import static me.defender.cosmetics.util.config.ConfigUtils.saveIfNotFound;
import static me.defender.cosmetics.util.Utility.saveIfNotExistsLang;

public abstract class KillMessage extends Cosmetics {

    private final String category = "kill-message";
    ConfigManager config = ConfigUtils.getKillMessages();
    ConfigType type = ConfigType.KILL_MESSAGES;

    /**
     * Register the kill message
     * This method should be called when the plugin is enabled.
     */
    @Override
    public void register(){
        // save to config
        String configPath = category + "." + getIdentifier() + ".";
        saveIfNotFound(type, configPath + "price", getPrice());
         saveIfNotFound(type, configPath + "rarity", getRarity().toString());
        String item = null;
        if(!XMaterial.matchXMaterial(getItem()).isSupported()) {
            Bukkit.getLogger().severe("The item is not supported! (Information: Category name is " + category + " and item name is " + getIdentifier());
            return;
        }
        if(XMaterial.matchXMaterial(getItem()).isSimilar(XMaterial.PLAYER_HEAD.parseItem())){
            get(type).setItemStack(configPath + "item", getItem(), base64());
        }else{
            get(type).setItemStack(configPath + "item", getItem());
        }

        // save to language file
        saveIfNotExistsLang("cosmetics." + configPath + "name", getDisplayName());
        // Format the lore
        List<String> finalLore = new ArrayList<>();
        finalLore.addAll(Arrays.asList("&8Kill Message", ""));
        finalLore.addAll(getLore());
        if(getRarity() != RarityType.NONE){
            finalLore.addAll(Arrays.asList("", "&eRight-Click to preview!", "" ,"&7Rarity: {rarity}","&7Cost: &6{cost}", "", "{status}"));
        }else{
            finalLore.addAll(Arrays.asList("", "&7Rarity: {rarity}","&7Cost: &6{cost}", "", "{status}"));
        }

        saveIfNotExistsLang("cosmetics." + configPath + "lore", finalLore);
        StartupUtils.killMessageList.add(this);

        Utility.saveIfNotExistsLang("cosmetics." + category + ".preview.message",
                Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬", "",
                        "&e                                   &l&nCHAT MESSAGES:", "" ,"%message%", "", "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
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
     * Display the kill message
     *
     * @param player the player to display the kill message
     * @return the kill message
     */
    public abstract String execute(Player player);


    /**
     * Get default kill message
     * @param player the player to get the default kill message
     * @return the default kill message
     */
    public static @NotNull KillMessage getDefault(Player player){
        for(KillMessage killMessage : StartupUtils.killMessageList){
            if(killMessage.getField(FieldsType.RARITY, player) == RarityType.NONE){
                return killMessage;
            }
        }

        // This will never return null!
        return null;
    }
}
