

package me.defender.cosmetics.util.config;

import me.defender.cosmetics.Cosmetics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class MainMenuData
{
    private final Cosmetics plugin;
    private FileConfiguration datacfg;
    private File cfgfile;
    
    public MainMenuData(final Cosmetics plugin) {
        this.plugin = plugin;
        this.savedefaultcfg();
    }
    
    public void reloadcfg() {
        if (this.cfgfile == null) {
            this.cfgfile = new File(new File(plugin.getHandler().getAddonPath()), "MainMenu.yml");
        }
        this.datacfg = YamlConfiguration.loadConfiguration(this.cfgfile);
        final InputStream defaultStream = this.plugin.getResource("MainMenu.yml");
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
            this.cfgfile = new File(new File(plugin.getHandler().getAddonPath()), "MainMenu.yml");
        }
        if (!this.cfgfile.exists()) {
            this.plugin.saveResource("MainMenu.yml", false);
        }
    }
}
