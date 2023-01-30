package me.defender.cosmetics.api.configuration;


import me.defender.cosmetics.api.enums.ConfigType;
import me.defender.cosmetics.api.utils.Utility;

import java.util.Objects;


public class ConfigUtils {

    public static ConfigManager getBedDestroys() {
        return new ConfigManager(Utility.plugin(), "BedBreakEffect", Utility.plugin().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getDeathCries() {
        return new ConfigManager(Utility.plugin(), "DeathCries", Utility.plugin().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getFinalKillEffects() {
        return new ConfigManager(Utility.plugin(), "FinalKillEffects", Utility.plugin().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getGlyphs() {
        return new ConfigManager(Utility.plugin(), "Glyphs", Utility.plugin().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getIslandToppers() {
        return new ConfigManager(Utility.plugin(), "IslandToppers", Utility.plugin().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getKillMessages() {
        return new ConfigManager(Utility.plugin(), "KillMessages", Utility.plugin().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getProjectileTrails() {
        return new ConfigManager(Utility.plugin(), "ProjectileTrails", Utility.plugin().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getShopKeeperSkins() {
        return new ConfigManager(Utility.plugin(), "ShopKeeperSkins", Utility.plugin().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getSprays() {
        return new ConfigManager(Utility.plugin(), "Sprays", Utility.plugin().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getVictoryDances() {
        return new ConfigManager(Utility.plugin(), "VictoryDances", Utility.plugin().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getWoodSkins() {
        return new ConfigManager(Utility.plugin(), "WoodSkins", Utility.plugin().getDataFolder().getPath() + "/Categories");
    }


    public static ConfigManager get(ConfigType configType){
        if(configType == ConfigType.Main_Config){
            return getMainConfig();
        }
        return new ConfigManager(Utility.plugin(), configType.getFileName(), Utility.plugin().getDataFolder().getPath() + "/Categories");
    }

    public static ConfigManager getMainConfig(){
        return new ConfigManager(Utility.plugin(), "config", Utility.plugin().getDataFolder().getPath());
    }

    public static void saveIfNotFound(ConfigType configType, String path ,Object data){
        if(Objects.isNull(get(configType).getYml().get(path))){
            get(configType).set(path, data);
            get(configType).save();
            get(configType).reload();
        }
    }


}
