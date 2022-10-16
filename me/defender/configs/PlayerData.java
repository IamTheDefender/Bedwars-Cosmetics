// 
// @author IamTheDefender
// 

package me.defender.configs;

import java.io.IOException;
import java.util.logging.Level;
import java.io.InputStream;
import org.bukkit.configuration.Configuration;
import java.io.Reader;
import java.io.InputStreamReader;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import me.defender.Main;

public class PlayerData
{
    private final Main plugin;
    private FileConfiguration datacfg;
    private File cfgfile;
    
    public PlayerData(final Main plugin) {
        this.plugin = plugin;
        this.savedefaultcfg();
    }
    
    public void reloadcfg() {
        if (this.cfgfile == null) {
            this.cfgfile = new File(this.plugin.getDataFolder(), "PlayerData.yml");
        }
        this.datacfg = YamlConfiguration.loadConfiguration(this.cfgfile);
        final InputStream defaultStream = this.plugin.getResource("PlayerData.yml");
        if (defaultStream != null) {
            final YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.datacfg.setDefaults(defaultConfig);
        }
    }
    
    public FileConfiguration getConfig() {
        if (this.datacfg == null) {
            this.reloadcfg();
        }
        return this.datacfg;
    }
    
    public void savecfg() {
        if (this.datacfg == null || this.cfgfile == null) {
            return;
        }
        try {
            this.getConfig().save(this.cfgfile);
        }
        catch (final IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not save the data file! contact the dev!" + this.cfgfile, e);
        }
    }
    
    public void savedefaultcfg() {
        if (this.cfgfile == null) {
            this.cfgfile = new File(this.plugin.getDataFolder(), "PlayerData.yml");
        }
        if (!this.cfgfile.exists()) {
            this.plugin.saveResource("PlayerData.yml", false);
        }
    }
}
