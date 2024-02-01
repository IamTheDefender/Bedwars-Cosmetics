package me.defender.cosmetics.util.config;


import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.cosmetics.CosmeticsType;
import me.defender.cosmetics.util.Utility;

import java.util.Arrays;
import java.util.Objects;

public class ConfigUtils {

    public static ConfigManager getBedDestroys() {
        return new ConfigManager(Cosmetics.getInstance(), "BedBreakEffect", Cosmetics.getInstance().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getDeathCries() {
        return new ConfigManager(Cosmetics.getInstance(), "DeathCries", Cosmetics.getInstance().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getFinalKillEffects() {
        return new ConfigManager(Cosmetics.getInstance(), "FinalKillEffects", Cosmetics.getInstance().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getGlyphs() {
        return new ConfigManager(Cosmetics.getInstance(), "Glyphs", Cosmetics.getInstance().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getIslandToppers() {
        return new ConfigManager(Cosmetics.getInstance(), "IslandToppers", Cosmetics.getInstance().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getKillMessages() {
        return new ConfigManager(Cosmetics.getInstance(), "KillMessages", Cosmetics.getInstance().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getProjectileTrails() {
        return new ConfigManager(Cosmetics.getInstance(), "ProjectileTrails", Cosmetics.getInstance().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getShopKeeperSkins() {
        return new ConfigManager(Cosmetics.getInstance(), "ShopKeeperSkins", Cosmetics.getInstance().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getSprays() {
        return new ConfigManager(Cosmetics.getInstance(), "Sprays", Cosmetics.getInstance().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getVictoryDances() {
        return new ConfigManager(Cosmetics.getInstance(), "VictoryDances", Cosmetics.getInstance().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getWoodSkins() {
        return new ConfigManager(Cosmetics.getInstance(), "WoodSkins", Cosmetics.getInstance().getDataFolder().getPath() + "/Categories");
    }


    public static ConfigManager get(ConfigType configType){
        if(configType == ConfigType.Main_Config){
            return getMainConfig();
        }
        return new ConfigManager(Cosmetics.getInstance(), configType.getFileName(), Cosmetics.getInstance().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getMainConfig(){
        return new ConfigManager(Cosmetics.getInstance(), "config", Cosmetics.getInstance().getDataFolder().getPath());
    }

    public static void saveIfNotFound(ConfigType configType, String path ,Object data){
        if(Objects.isNull(get(configType).getYml().get(path))){
            get(configType).set(path, data);
            get(configType).save();
            get(configType).reload();
        }
    }

    public static void addSlotsList(){
        for (CosmeticsType cosmeticsType : CosmeticsType.values()){
            cosmeticsType.getConfig().getYml().addDefault("slots", Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34).toString());

            cosmeticsType.getConfig().save();
        }
    }

    public static void addExtrasToLang(){
        Utility.saveIfNotExistsLang("Cosmetics.not-purchase-able", "&cLOCKED");
    }
}