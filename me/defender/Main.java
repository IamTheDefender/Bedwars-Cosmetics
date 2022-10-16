 

package me.defender;

import com.andrei1058.bedwars.BedWars;
import com.hakan.core.HCore;
import me.defender.api.BwcAPI;
import me.defender.command.MainCommand;
import me.defender.api.utils.MainMenuUtils;
import me.defender.configs.MainMenuData;
import me.defender.database.mysql.Database;
import me.defender.support.placeholders.Placeholders;
import me.defender.configs.BedBreakEffectData;
import me.defender.configs.DeathCriesData;
import me.defender.configs.FinalKillEffectData;
import me.defender.cosmetics.Glyphs.Glyphs;
import me.defender.configs.GlyphsData;
import me.defender.configs.IslandToppersData;
import me.defender.configs.KillMessagesData;
import me.defender.configs.PlayerData;
import me.defender.configs.ProjectileEffectsData;
import me.defender.cosmetics.ShopKeeperSkins.ShopKeepersData;
import me.defender.configs.SpraysData;
import me.defender.cosmetics.Sprays.SpraysUtil;
import me.defender.cosmetics.VictoryDances.VictoryDancesUtil;
import me.defender.configs.VictoryDancesData;
import me.defender.configs.WoodSkinsData;
import me.defender.api.utils.util;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;

public class Main extends JavaPlugin
{
    public GlyphsData glyconf;
    public VictoryDancesData vdconf;
    public PlayerData playerData;
    public KillMessagesData killmessagecfg;
    public SpraysData spraysdata;
    public ShopKeepersData shopkeeperdata;
    public WoodSkinsData woodskindata;
    public DeathCriesData dcdata;
    public BedBreakEffectData bbedata;
    public ProjectileEffectsData pedata;
    public HashMap<Player, ArmorStand> stands;
    public FinalKillEffectData finalkilldata;
    public IslandToppersData islandToppersData;
    public MainMenuData menuData;
    public Database db;
    
    public Main() {
        this.stands = new HashMap<>();
    }
    
    public Database getDb() {
        return this.db;
    }

    public Permission perms = null;

    
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("BedWars1058") == null) {
            if (!new BwcAPI().isProxy()) {
                getServer().getConsoleSender().sendMessage("§8[§dBedwars1058-Cosmetics§8] Can't find BedWarsProxy or BedWars1058!");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
        }

        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            getServer().getConsoleSender().sendMessage("§8[§dBedwars1058-Cosmetics§8] §cYou need Vault to run this plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;

        }
        if (Bukkit.getPluginManager().getPlugin("Citizens") == null) {
            getServer().getConsoleSender().sendMessage("§8[§dBedwars1058-Cosmetics§8] §cYou need Citizens to run this plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;

        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders(this).register();
        }

        ServerManager.downloadGlyphs();
        if (this.getConfig().getBoolean("MySQL.Enabled")) {
            this.db = new Database();
            this.getServer().getConsoleSender().sendMessage("§8[§dBedwars1058-Cosmetics§8] §aTrying to connect to MySQL database!");
            try {
                this.db.setupDB();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
        this.getServer().getConsoleSender().sendMessage("§8[§dBedwars1058-Cosmetics§8] §aTrying to load YAML's!");
        this.pedata = new ProjectileEffectsData(this);
        this.killmessagecfg = new KillMessagesData(this);
        this.playerData = new PlayerData(this);
        this.spraysdata = new SpraysData(this);
        this.shopkeeperdata = new ShopKeepersData(this);
        this.woodskindata = new WoodSkinsData(this);
        this.dcdata = new DeathCriesData(this);
        this.vdconf = new VictoryDancesData(this);
        this.glyconf = new GlyphsData(this);
        this.bbedata = new BedBreakEffectData(this);
        this.finalkilldata = new FinalKillEffectData(this);
        this.islandToppersData = new IslandToppersData(this);
        this.menuData = new MainMenuData(this);
        this.saveDefaultConfig();
        util.addToList();
        MainMenuUtils.saveLores();
        ServerManager.updateConfigs();
        ServerManager.createSpraysFolder();
        ServerManager.createIslandTopperFolder();
        ServerManager.downloadHCore();
        this.getServer().getConsoleSender().sendMessage("§8[§dBedwars1058-Cosmetics§8] §aTrying to load HashMaps!");
        Glyphs.glyphs = new HashMap<Location, Integer>();
        SpraysUtil.cooldown = new HashMap<String, Long>();
        VictoryDancesUtil.toystickplayer = new HashMap<String, Boolean>();
        setupPermissions();
        this.getServer().getConsoleSender().sendMessage("§8[§dBedwars1058-Cosmetics§8] §aTrying to register events..");
        ServerManager.registerEvents();
        this.getServer().getConsoleSender().sendMessage("§8[§dBedwars1058-Cosmetics§8] §aEvents are registered.");
        this.getServer().getConsoleSender().sendMessage("§8[§dBedwars1058-Cosmetics§8] §aTrying to register commands..");
        HCore.registerCommands(new MainCommand());
        this.getServer().getConsoleSender().sendMessage("§8[§dBedwars1058-Cosmetics§8] §aCommands are registered.");
        this.getServer().getConsoleSender().sendMessage("§8[§dBedwars1058-Cosmetics§8] §aPlugin is enabled!");
        this.getServer().getConsoleSender().sendMessage("§8[§dBedwars1058-Cosmetics§8] §aThis plugin is coded by IamTheDefender#4081");

        HCore.registerEvent(EntityDismountEvent.class).consume((e) -> {
           if(e.getDismounted() instanceof  Player){
               Player p = (Player) e.getDismounted();
               if(BedWars.getAPI().getArenaUtil().getArenaByPlayer(p) != null){
                   e.setCancelled(true);
               }
           }
        });

        Bukkit.getScheduler().runTaskLater(this, () -> ServerManager.createHeightValue(), 110l);

    }

    
    public void onDisable() {
       if(new BwcAPI().isMySQL()) {
    	   try {
			db.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
       }
    }
    
    public static void downloadFile(final URL url, final String outputFileName) throws IOException {
    	try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(outputFileName));
            }
        }

    public boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    }



