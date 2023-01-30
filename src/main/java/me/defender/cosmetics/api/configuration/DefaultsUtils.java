package me.defender.cosmetics.api.configuration;

import me.defender.cosmetics.api.enums.ConfigType;
import me.defender.cosmetics.api.utils.Utility;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;

public class DefaultsUtils {

    /**
     * This method will save default values from the
     * plugin jar yml for example, from Glyph.yml to
     * the file located in the plugin folder, example
     * resources/Glyph.yml -> pluginFolder/Categories/Glyph.yml
     */
    public void saveAllDefaults(){
        for(ConfigType type : ConfigType.values()){
            ConfigManager config = ConfigUtils.get(type);
            InputStream defaultConfigStream = Utility.plugin().getResource(type.getFileName() + ".yml");
            if(defaultConfigStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream));
                config.addDefaults(defaultConfig);
                config.save();
            }
            config.reload();
            config.save();
        }
    }

}
