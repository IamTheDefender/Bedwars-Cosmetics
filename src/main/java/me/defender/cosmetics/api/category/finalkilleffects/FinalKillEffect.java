package me.defender.cosmetics.api.category.finalkilleffects;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.category.Cosmetics;
import me.defender.cosmetics.api.enums.ConfigType;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.StartupUtils;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.configuration.ConfigUtils;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.defender.cosmetics.api.configuration.ConfigUtils.get;
import static me.defender.cosmetics.api.configuration.ConfigUtils.saveIfNotFound;
import static me.defender.cosmetics.api.util.Utility.saveIfNotExistsLang;

public abstract class FinalKillEffect extends Cosmetics {
    /**
     * Execute the final kill effect
     * This method should be called when a player kills another player.
     * And the victim has no bed left.
     *
     * @param killer player who killed the victim.
     * @param victim player who was killed.
     */
    public abstract void execute(Player killer, Player victim);

    /**
     * Register the final kill effect.
     * This method should be called when the plugin is enabled.
     */
    @Override
    public void register(){
        // save to config
        String category = "finalkill-effect";
        String configPath = category + "." + getIdentifier() + ".";
        ConfigType type = ConfigType.FINAL_KILL_EFFECTS;
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
        finalLore.addAll(Arrays.asList("&8Final Kill Effects", ""));
        finalLore.addAll(getLore());
        finalLore.addAll(Arrays.asList("", "&7Rarity: {rarity}","&7Cost: &6{cost}", "", "{status}"));

        saveIfNotExistsLang("cosmetics." + configPath + "lore", finalLore);
        StartupUtils.finalKillList.add(this);
    }

    /**
     * Get the field of the final kill effect.
     * @param fields the field you want to get.
     * @param p the player who is viewing the final kill effect.
     * @return the field of the final kill effect.
     */
    public Object getField(FieldsType fields, Player p){
        String category = "finalkill-effect";
        String configPath = category + "." + getIdentifier() + ".";
        ConfigManager config = ConfigUtils.getFinalKillEffects();
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
     * Get the default final kill effect.
     * @param player the player who is viewing the final kill effect.
     * @return the default final kill effect.
     */
    public static @NotNull FinalKillEffect getDefault(Player player){
        for(FinalKillEffect finalKillEffect : StartupUtils.finalKillList){
            if(finalKillEffect.getField(FieldsType.RARITY, player) == RarityType.NONE){
                return finalKillEffect;
            }
        }

        // This will never return null!
        return null;
    }
}
