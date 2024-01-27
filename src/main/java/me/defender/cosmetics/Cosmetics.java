
package me.defender.cosmetics;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.hakan.core.HCore;
import com.tomkeuper.bedwars.api.BedWars;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.cosmetics.category.VictoryDance;
import me.defender.cosmetics.util.StartupUtils;
import me.defender.cosmetics.api.configuration.ConfigUtils;
import me.defender.cosmetics.util.MainMenuUtils;
import me.defender.cosmetics.api.configuration.DefaultsUtils;
import me.defender.cosmetics.util.Utility;
import me.defender.cosmetics.command.MainCommand;
import me.defender.cosmetics.util.config.MainMenuData;
import me.defender.cosmetics.database.IDatabase;
import me.defender.cosmetics.database.PlayerData;
import me.defender.cosmetics.database.PlayerOwnedData;
import me.defender.cosmetics.database.mysql.MySQL;
import me.defender.cosmetics.database.sqlite.SQLite;
import me.defender.cosmetics.support.bedwars.BedWars2023;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Cosmetics extends JavaPlugin
{

    @Setter
    @Getter
    private BedWars bedWars2023API;
    @Getter
    private com.andrei1058.bedwars.api.BedWars bedWars1058API;
    public MainMenuData menuData;
    public static HikariDataSource db;
    @Getter
    public static Connection dbConnection;
    public boolean dependenciesMissing = false;
    @Getter
    static boolean placeholderAPI;
    @Getter
    private IDatabase dataBase;
    @Getter
    private static Cosmetics instance;
    @Getter
    private ProtocolManager protocolManager;
    @Getter
    private HashMap<Integer, Player> entityPlayerHashMap;


    @Override
    public void onEnable() {
        if(!StartupUtils.checkDependencies()){
            getLogger().severe("Cosmetics addon will now disable, make sure you have all dependencies installed!");
            getServer().getPluginManager().disablePlugin(this);
            dependenciesMissing = true;
            return;
        }
        if (StartupUtils.isBw2023) {
            BedWars2023 bedWars2023 = new BedWars2023(this);
            bedWars2023.start();
        } else {
            this.bedWars1058API = Bukkit.getServer().getServicesManager().getRegistration(com.andrei1058.bedwars.api.BedWars.class).getProvider();
        }

        getLogger().info("All dependencies found, continuing with plugin startup.");
        try{
            HCore.initialize(this);
        }catch (IllegalStateException ignored){
            getLogger().severe("BW1058-Cosmetics does not support your server version, please check dev builds or contact the developer for more info!");
            setEnabled(false);
            return;
        }
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        entityPlayerHashMap  = new HashMap<>();
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

        this.menuData = new MainMenuData(this);
        ConfigUtils.addSlotsList();
        getLogger().info("Configuration file successfully loaded.");
        getLogger().info("Loading " + (new BwcAPI().isMySQL() ? "MySQL" : "SQLite") + " database...");
        if(new BwcAPI().isMySQL()){
            dataBase = new MySQL(this);
        }else{
            dataBase = new SQLite(this);
        }
        db = dataBase.getDataSource();
        dbConnection = dataBase.getConnection();

        // Load all the list
        Utility.playerDataList = new HashMap<>();
        Utility.playerOwnedDataList = new HashMap<>();
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

        HCore.asyncScheduler().every(5, TimeUnit.SECONDS).run(() -> {
            try (Connection connection = dataBase.getConnection()){
                connection.createStatement();
            }catch (Exception e){
                dataBase.connect();
            }
        });

    }

    @Override
    public void onDisable() {
        if(dependenciesMissing){
            getLogger().severe("Detected forced disable! plugin will not unload anything!");
            return;
        }
        if(!new BwcAPI().isMySQL()){
            getLogger().info("Saving player data to SQLite database...");
            getLogger().info("Please wait it may take some time!");
            for(PlayerData playerData : Utility.playerDataList.values()){
                playerData.save();
            }
            for (PlayerOwnedData playerOwnedData : Utility.playerOwnedDataList.values()){
                playerOwnedData.save();
            }
            getLogger().info("Player data saved to SQLite database!");
        }
        try {
            db.getConnection().close();
        } catch (SQLException e) {
            getLogger().severe("There was an error while closing connection to database: " + e.getMessage());
        }
    }



    public static HikariDataSource getDB(){
        return db;
    }

    public static void setPlaceholderAPI(boolean placeholderAPI) {
        Cosmetics.placeholderAPI = placeholderAPI;
    }

    public boolean isBw2023() {
        return getBedWars2023API() != null;
    }


}