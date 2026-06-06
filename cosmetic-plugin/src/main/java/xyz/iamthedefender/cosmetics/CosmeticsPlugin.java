
package xyz.iamthedefender.cosmetics;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import xyz.iamthedefender.cosmetics.api.CosmeticsAPI;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticPreview;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticRegistry;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.database.DatabaseType;
import xyz.iamthedefender.cosmetics.api.database.IDatabase;
import xyz.iamthedefender.cosmetics.api.handler.HandlerType;
import xyz.iamthedefender.cosmetics.api.handler.IHandler;
import xyz.iamthedefender.cosmetics.api.handler.IWorldEditHandler;
import xyz.iamthedefender.cosmetics.api.menu.SystemGuiManager;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigType;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;
import xyz.iamthedefender.cosmetics.api.util.config.DefaultsUtils;
import xyz.iamthedefender.cosmetics.api.versionsupport.IVersionSupport;
import xyz.iamthedefender.cosmetics.command.BedWarsCosmeticsCommand;
import xyz.iamthedefender.cosmetics.data.PlayerData;
import xyz.iamthedefender.cosmetics.data.PlayerOwnedData;
import xyz.iamthedefender.cosmetics.data.database.MariaDB;
import xyz.iamthedefender.cosmetics.data.database.MongoDB;
import xyz.iamthedefender.cosmetics.data.database.MySQL;
import xyz.iamthedefender.cosmetics.data.database.PostgreSQL;
import xyz.iamthedefender.cosmetics.data.database.Redis;
import xyz.iamthedefender.cosmetics.data.database.SQLite;
import xyz.iamthedefender.cosmetics.data.manager.PlayerManager;
import xyz.iamthedefender.cosmetics.support.bedwars.handler.bedwars1058.BW1058Handler;
import xyz.iamthedefender.cosmetics.support.bedwars.handler.bedwars1058.BW1058ProxyHandler;
import xyz.iamthedefender.cosmetics.support.bedwars.handler.bedwars2023.BW2023Handler;
import xyz.iamthedefender.cosmetics.support.bedwars.handler.bedwars2023.BW2023ProxyHandler;
import xyz.iamthedefender.cosmetics.support.bedwars.handler.screamingBedwars.ScreamingBedWarsHandler;
import xyz.iamthedefender.cosmetics.util.MainMenuUtils;
import xyz.iamthedefender.cosmetics.util.Metrics;
import xyz.iamthedefender.cosmetics.util.StartupUtils;
import xyz.iamthedefender.cosmetics.util.version.VersionSupportUtil;
import xyz.iamthedefender.cosmetics.support.protocol.PacketEventsBootstrap;
import xyz.iamthedefender.cosmetics.support.npc.PacketNpcManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class CosmeticsPlugin extends JavaPlugin {

    public ConfigManager menuData;
    private PlayerManager playerManager;
    private Metrics metrics;

    public boolean dependenciesMissing = false;
    @Getter
    static boolean placeholderAPI;

    @Getter
    private static CosmeticsPlugin instance;

    private PacketNpcManager packetNpcManager;

    private HashMap<Integer, Player> entityPlayerHashMap;

    private CosmeticsAPI api;
    private Economy economy;
    private IHandler handler;
    private IVersionSupport versionSupport;
    private IDatabase remoteDatabase;

    private SystemGuiManager systemGuiManager;

    private List<CosmeticPreview> previewList;

    private IWorldEditHandler worldEditHandler;

    @Override
    public void onLoad() {
        getLogger().info("Checking if PacketEvents is installed and enabled...");

        if (!PacketEventsBootstrap.ensureInstalledAndEnabled(this)) {
            getLogger().severe("PacketEvents could not be installed or enabled automatically. Please manually download and install it.");
            getServer().getPluginManager().disablePlugin(this);
            dependenciesMissing = true;
            return;
        }

    }

    @Override
    public void onEnable() {
        instance = this;

        String[] banner = {
                " ▄   ▄▄▄▄                                             ",
                "  ▀██████▀                            █▄               ",
                "    ██                 ▄             ▄██▄▀▀            ",
                "    ██     ▄███▄ ▄██▀█ ███▄███▄ ▄█▀█▄ ██ ██ ▄███▀ ▄██▀█",
                "    ██     ██ ██ ▀███▄ ██ ██ ██ ██▄█▀ ██ ██ ██    ▀███▄",
                "    ▀█████▄▀███▀█▄▄██▀▄██ ██ ▀█▄▀█▄▄▄▄██▄██▄▀███▄█▄▄██▀",
                ""
        };

        for (String line : banner) {
            Bukkit.getLogger().info(line);
        }

        api = new BwcAPI();
        previewList = new ArrayList<>();
        Bukkit.getServicesManager().register(CosmeticsAPI.class, api, this, ServicePriority.Highest);
        systemGuiManager = new SystemGuiManager(this);

        if (!StartupUtils.checkDependencies()) {
            getLogger().severe("Cosmetics addon will now disable, make sure you have all dependencies installed before reporting this as a bug.");
            getServer().getPluginManager().disablePlugin(this);
            dependenciesMissing = true;
            return;
        }

        handler = findHandler();
        StartupUtils.loadLibraries();

        versionSupport = StartupUtils.getVersionSupport();
        if (versionSupport == null) {
            getLogger().severe("Could not find a version support for " + VersionSupportUtil.getVersion());
            setEnabled(false);
            dependenciesMissing = true;
            return;
        }

        worldEditHandler = StartupUtils.getWorldEditHandler();
        if (worldEditHandler == null) {
            getLogger().severe("Could not find a world edit handler for " + VersionSupportUtil.getVersion());
            setEnabled(false);
            dependenciesMissing = true;
            return;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            getLogger().severe("Looks like no vault-supported economy plugins are installed! Refer to the guide, as this is required for the cosmetic addon to work!");
            getServer().getPluginManager().disablePlugin(this);
            dependenciesMissing = true;
            return;
        }

        economy = rsp.getProvider();
        packetNpcManager = new PacketNpcManager(this);
        entityPlayerHashMap = new HashMap<>();
        playerManager = new PlayerManager();

        StartupUtils.addEntityHideListener();
        StartupUtils.addPreviewInteractionListener();
        StartupUtils.addWindowClickListener();

        StartupUtils.downloadGlyphs();
        this.menuData = new ConfigManager(this, "MainMenu", getHandler().getAddonPath());

        for (ConfigType configType : ConfigType.values()) {
            Optional.ofNullable(ConfigUtils.get(configType)).ifPresent(ConfigManager::save);
        }

        ConfigUtils.addExtrasToLang();
        DefaultsUtils.saveAllDefaults();
        StartupUtils.unzipSpray();
        MainMenuUtils.saveLores();
        StartupUtils.updateConfigs();
        StartupUtils.createFolders();
        ConfigUtils.addSlotsList();

        remoteDatabase = createDatabase();
        registerSchedulers();
        handler.register();

        StartupUtils.registerEvents();

        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.getCommandContexts()
                .registerContext(CosmeticType.class, (c) -> CosmeticType.fromName(c.popFirstArg()));
        commandManager.getCommandCompletions()
                        .registerAsyncCompletion("cosmeticTypes", (c) -> CosmeticType.values().stream().map(CosmeticType::name).collect(Collectors.toList()));
        commandManager.getCommandCompletions()
                        .registerAsyncCompletion("cosmetics", c -> {
                            CosmeticType<Cosmetics> type = c.getContextValue(CosmeticType.class);
                            return CosmeticRegistry.getByCategory(type).stream()
                                    .map(Cosmetics::getIdentifier)
                                    .collect(Collectors.toList());
                        });


        commandManager.enableUnstableAPI("help");
        commandManager.registerCommand(new BedWarsCosmeticsCommand());

        StartupUtils.loadCosmetics();
        StartupUtils.convertSpraysURLs();

        metrics = new Metrics(this, 21340);

        getLogger().info("BedWars Cosmetics v" + getDescription().getVersion() + " loaded. "
                + "Backend: " + remoteDatabase.getDisplayName() + ", "
                + "Version support: " + versionSupport.getClass().getSimpleName());
    }

    private void registerSchedulers() {
        Run.everyAsync(remoteDatabase::validateConnection, 5L);

        Run.every(() -> {
            for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                getPlayerManager().getPlayerOwnedData(onlinePlayer.getUniqueId()).updateOwned();
            }
        }, 5 * 20L);
    }

    private IHandler findHandler() {

        if (StartupUtils.isPluginEnabled("BedWars")) {
            try {
                return new ScreamingBedWarsHandler();
            }catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new RuntimeException("Failed to find a valid BedWars plugin, are you using a supported BedWars plugin?");
            }
        }

        return api.isProxy() ? (StartupUtils.BW2023 ? new BW2023ProxyHandler() : new BW1058ProxyHandler()) : (StartupUtils.BW2023 ? new BW2023Handler() : new BW1058Handler());
    }

    @Override
    public void onDisable() {
        if (dependenciesMissing){
            getLogger().severe("Detected forced disable! plugin will not unload anything!");
            return;
        }

        if (remoteDatabase != null && remoteDatabase.getDatabaseType() == DatabaseType.SQLITE){
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
           if (remoteDatabase != null){
               remoteDatabase.close();
           }
        } catch (Exception e) {
            getLogger().severe("There was an error while closing storage backend, this may have cause data loss! Make sure to backup every so often: " + e.getMessage());
        }

        if(metrics != null) metrics.shutdown();
    }

    @Override
    public FileConfiguration getConfig() {
        return ConfigUtils.getMainConfig().getYml();
    }

    @Override
    public void reloadConfig() {
        ConfigUtils.getMainConfig().reload();
    }

    @Override
    public void saveConfig() {
        ConfigUtils.getMainConfig().save();
    }

    public static void setPlaceholderAPI(boolean placeholderAPI) {
        CosmeticsPlugin.placeholderAPI = placeholderAPI;
    }

    /**
     * Find cosmetic by id
     * @param cosmeticId case-sensitive cosmetic id
     * @param CosmeticType<?> cosmetic type
     * @return null if not found or else the {@link Cosmetics} object
     */
    public static @Nullable Cosmetics findCosmetic(String cosmeticId, CosmeticType<?> CosmeticType) {
        CosmeticsAPI cosmeticsAPI = instance.getApi();

        List<Cosmetics> cosmetics = new ArrayList<>();
        cosmetics.addAll(cosmeticsAPI.getBedDestroyList());
        cosmetics.addAll(cosmeticsAPI.getDeathCryList());
        cosmetics.addAll(cosmeticsAPI.getFinalKillList());
        cosmetics.addAll(cosmeticsAPI.getProjectileTrailList());
        cosmetics.addAll(cosmeticsAPI.getGlyphsList());
        cosmetics.addAll(cosmeticsAPI.getVictoryDanceList());
        cosmetics.addAll(cosmeticsAPI.getWoodSkinList());
        cosmetics.addAll(cosmeticsAPI.getSprayList());
        cosmetics.addAll(cosmeticsAPI.getKillMessageList());
        cosmetics.addAll(cosmeticsAPI.getShopKeeperSkinList());
        cosmetics.addAll(cosmeticsAPI.getIslandTopperList());

        return cosmetics.stream().filter(cosmetic -> cosmetic.getIdentifier().equals(cosmeticId) && cosmetic.getCosmeticType() == CosmeticType).findFirst().orElse(null);
    }

    private IDatabase createDatabase() {
        DatabaseType databaseType = StartupUtils.getConfiguredDatabaseType();
        if (handler.getHandlerType() == HandlerType.BUNGEE && databaseType == DatabaseType.SQLITE) {
            getLogger().severe("You cannot use SQLite in Bungee mode!");
            getServer().getPluginManager().disablePlugin(this);
            throw new IllegalStateException("SQLite is not supported in Bungee mode.");
        }

        getLogger().info("Selected storage backend: " + databaseType.name());
        IDatabase database;
        switch (databaseType) {
            case MYSQL:
                database = new MySQL();
                break;
            case MARIADB:
                database = new MariaDB();
                break;
            case POSTGRESQL:
                database = new PostgreSQL();
                break;
            case MONGODB:
                database = new MongoDB();
                break;
            case REDIS:
                database = new Redis();
                break;
            case SQLITE:
            default:
                database = new SQLite();
                break;
        }
        database.connect();
        database.createTable();
        return database;
    }



}
