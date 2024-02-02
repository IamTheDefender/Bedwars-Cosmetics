
package me.defender.cosmetics;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.hakan.core.HCore;
import lombok.Getter;
import me.defender.cosmetics.api.CosmeticsAPI;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.cosmetics.category.VictoryDance;
import me.defender.cosmetics.api.database.DatabaseType;
import me.defender.cosmetics.api.handler.IHandler;
import me.defender.cosmetics.data.manager.PlayerManager;
import me.defender.cosmetics.support.bedwars.handler.bedwars1058.BW1058Handler;
import me.defender.cosmetics.support.bedwars.handler.bedwars1058.BW1058ProxyHandler;
import me.defender.cosmetics.support.bedwars.handler.bedwars2023.BW2023Handler;
import me.defender.cosmetics.support.bedwars.handler.bedwars2023.BW2023ProxyHandler;
import me.defender.cosmetics.util.StartupUtils;
import me.defender.cosmetics.util.config.ConfigUtils;
import me.defender.cosmetics.util.MainMenuUtils;
import me.defender.cosmetics.util.config.DefaultsUtils;
import me.defender.cosmetics.command.MainCommand;
import me.defender.cosmetics.util.config.MainMenuData;
import me.defender.cosmetics.api.database.IDatabase;
import me.defender.cosmetics.data.PlayerData;
import me.defender.cosmetics.data.PlayerOwnedData;
import me.defender.cosmetics.data.database.MySQL;
import me.defender.cosmetics.data.database.SQLite;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class Cosmetics extends JavaPlugin {
    @Getter
    public ConfigManager menuData;
    public boolean dependenciesMissing = false;
    @Getter
    static boolean placeholderAPI;
    @Getter
    private IDatabase remoteDatabase;
    @Getter
    private static Cosmetics instance;
    @Getter
    private ProtocolManager protocolManager;
    @Getter
    private HashMap<Integer, Player> entityPlayerHashMap;

    @Getter
    private PlayerManager playerManager;
    @Getter
    private CosmeticsAPI api;
    @Getter
    private Economy economy;
    @Getter
    private IHandler handler;

    @Override
    public void onEnable() {
        try{
            HCore.initialize(this);
        }catch (IllegalStateException ignored){
            getLogger().severe("BW1058-Cosmetics does not support your server version, please check dev builds or contact the developer for more info!");
            setEnabled(false);
            dependenciesMissing = true;
            return;
        }
        instance = this;
        api = new BwcAPI();
        if(!StartupUtils.checkDependencies()){
            getLogger().severe("Cosmetics addon will now disable, make sure you have all dependencies installed!");
            getServer().getPluginManager().disablePlugin(this);
            dependenciesMissing = true;
            return;
        }
        handler = (api.isProxy() ? (StartupUtils.isBw2023 ? new BW2023ProxyHandler() : new BW1058ProxyHandler()) : (StartupUtils.isBw2023 ? new BW2023Handler() : new BW1058Handler()));
        handler.register();


        getLogger().info("All dependencies found, continuing with plugin startup.");
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        economy = rsp.getProvider();
        protocolManager = ProtocolLibrary.getProtocolManager();
        entityPlayerHashMap  = new HashMap<>();
        playerManager = new PlayerManager();
        StartupUtils.addEntityHideListener();
        // Download Glyphs
        StartupUtils.downloadGlyphs();
        ConfigUtils.getBedDestroys().save();
        ConfigUtils.getDeathCries().save();
        ConfigUtils.getFinalKillEffects().save();
        ConfigUtils.getGlyphs().save();
        ConfigUtils.getIslandToppers().save();
        ConfigUtils.getKillMessages().save();
        ConfigUtils.getProjectileTrails().save();
        ConfigUtils.getShopKeeperSkins().save();
        ConfigUtils.getSprays().save();
        ConfigUtils.getVictoryDances().save();
        ConfigUtils.getWoodSkins().save();
        ConfigUtils.getMainConfig().save();
        ConfigUtils.addExtrasToLang();

        this.menuData = new ConfigManager(this, "MainMenu", getHandler().getAddonPath());
        ConfigUtils.addSlotsList();
        getLogger().info("Configuration file successfully loaded.");
        getLogger().info("Loading " + (api.isMySQL() ? "MySQL" : "SQLite") + " database...");
        if(api.isMySQL()){
            remoteDatabase = new MySQL(this);
        }else{
            remoteDatabase = new SQLite(this);
        }

        // Load all the list
        StartupUtils.loadLists();
        getLogger().info("Cosmetics list successfully loaded.");

        getLogger().info("Saving data to configs...");
        MainMenuUtils.saveLores();
        StartupUtils.updateConfigs();
        getLogger().info("Creating folders...");
        StartupUtils.createFolders();
        getLogger().info("Registering event listeners...");
        StartupUtils.registerEvents();
        getLogger().info("Registering command to HCore...");
        HCore.registerCommands(new MainCommand(this));
        getLogger().info("Loading data from resources in jar...");
        DefaultsUtils defaultsUtils = new DefaultsUtils();
        defaultsUtils.saveAllDefaults();
        getLogger().info("Loading cosmetics...");
        StartupUtils.loadCosmetics();
        getLogger().info("Addon have been loaded and enabled!");
        // This is a check to make sure victory dance config doesn't have any issues.
        VictoryDance.getDefault(null);

        HCore.asyncScheduler().every(5L).run(() -> {
            try (Connection connection = remoteDatabase.getConnection()){
                connection.createStatement();
            }catch (Exception e){
                remoteDatabase.connect();
            }
        });

        HCore.asyncScheduler().every(5 * 20L).run(() -> {
            for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                getPlayerManager().getPlayerOwnedData(onlinePlayer.getUniqueId()).updateOwned();
            }
        });

    }

    @Override
    public void onDisable() {
        if(dependenciesMissing){
            getLogger().severe("Detected forced disable! plugin will not unload anything!");
            return;
        }
        if(remoteDatabase.getDatabaseType() == DatabaseType.SQLITE){
            getLogger().info("Saving player data to SQLite database...");
            getLogger().info("Please wait it may take some time!");
            for(PlayerData playerData : getPlayerManager().getPlayerDataHashMap().values()){
                playerData.save();
            }
            for (PlayerOwnedData playerOwnedData : getPlayerManager().getPlayerOwnedDataHashMap().values()) {
                playerOwnedData.save();
            }
            getLogger().info("Player data saved to SQLite database!");
        }
        try {
            remoteDatabase.getConnection().close();
        } catch (SQLException e) {
            getLogger().severe("There was an error while closing connection to database: " + e.getMessage());
        }
    }

    public static void setPlaceholderAPI(boolean placeholderAPI) {
        Cosmetics.placeholderAPI = placeholderAPI;
    }




}