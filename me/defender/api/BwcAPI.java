package me.defender.api;

import com.andrei1058.bedwars.api.BedWars;
import me.defender.cosmetics.KillMessage.KillMessagesUtil;
import me.defender.api.utils.util;
import me.defender.api.enums.Cosmetics;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static me.defender.api.utils.util.plugin;

public class BwcAPI {
    private final Boolean enabledMySQL = plugin().getConfig().getBoolean("MySQL.Enabled");



    public Boolean isMySQL() {
        return enabledMySQL;
    }

    public String getSelectedCosmetic(Player p, Cosmetics cos){
        String selected = plugin().playerData.getConfig().getString(util.getUUID(p) + "." + cos.toString());
        return selected;
    }

    public void setSelectedCosmetic(Player p, Cosmetics cos, String to){
        util.savePlayerData(p, cos.toString(), to);
    }

    public Economy getEco(){
        final RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        Economy econ = rsp.getProvider();
        return econ;
    }

    public BedWars getBwAPI(){
        BedWars bedwarsAPI = Bukkit.getServicesManager().getRegistration(BedWars .class).getProvider();
        return bedwarsAPI;
    }

    public Boolean isProxy(){
        return Bukkit.getPluginManager().getPlugin("BedWarsProxy") != null;
    }




}
