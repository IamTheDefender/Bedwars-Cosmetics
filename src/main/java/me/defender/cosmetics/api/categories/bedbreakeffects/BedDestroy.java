package me.defender.cosmetics.api.categories.bedbreakeffects;

import com.andrei1058.bedwars.api.arena.team.ITeam;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.categories.Cosmetics;
import me.defender.cosmetics.api.enums.ConfigType;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.utils.StartupUtils;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.configuration.ConfigUtils;
import me.defender.cosmetics.api.utils.Utility;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.defender.cosmetics.api.configuration.ConfigUtils.get;
import static me.defender.cosmetics.api.configuration.ConfigUtils.saveIfNotFound;
import static me.defender.cosmetics.api.utils.Utility.saveIfNotExistsLang;

public abstract class BedDestroy extends Cosmetics {

    public abstract ItemStack getItem();
    public abstract String base64();
    public abstract String getIdentifier();
    public abstract String getDisplayName();
    public abstract List<String> getLore();
    public abstract int getPrice();
    public abstract RarityType getRarity();

    public abstract void execute(Player player, Location bedLocation, ITeam victimTeam);

    public void register(){
        // save to config
        String category = "bed-destroy";
        String configPath = category + "." + getIdentifier() + ".";
        ConfigType type = ConfigType.BED_DESTROYS;
        saveIfNotFound(type, configPath + "price", getPrice());
         saveIfNotFound(type, configPath + "rarity", getRarity().toString());
        String item = null;
        assert XMaterial.PLAYER_HEAD.parseItem() != null;
        if(XMaterial.matchXMaterial(getItem()).isSimilar(XMaterial.PLAYER_HEAD.parseItem())){
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

    public static @NotNull BedDestroy getDefault(Player player){
        for(BedDestroy bedDestroy : StartupUtils.bedDestroyList){
            if(bedDestroy.getField(FieldsType.RARITY, player) == RarityType.NONE){
                return bedDestroy;
            }
        }

        // This will never return null!
        return null;
    }

}

