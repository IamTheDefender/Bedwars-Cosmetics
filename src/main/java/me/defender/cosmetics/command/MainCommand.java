package me.defender.cosmetics.command;

import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.main.ShopIndex;
import com.andrei1058.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import com.andrei1058.bedwars.shop.quickbuy.QuickBuyElement;
import com.hakan.core.HCore;
import com.hakan.core.command.executors.basecommand.BaseCommand;
import com.hakan.core.command.executors.subcommand.SubCommand;
import com.hakan.core.ui.inventory.InventoryGui;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.api.enums.ConfigType;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.util.StartupUtils;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.util.MainMenuUtils;
import me.defender.cosmetics.api.configuration.ConfigUtils;
import me.defender.cosmetics.menu.MainMenu;
import me.defender.cosmetics.api.util.Utility;
import me.defender.cosmetics.menu.CategoryMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

@BaseCommand(
        name = "bwc",
        description = "Main command for cosmetics addon!",
        usage = "§cUse /bwc help for help!",
        aliases = {"cos", "cosmetics", "cosmetic", "bwcosmetic", "bwcosmetics", "bwcos"}
)

public class MainCommand {

    @SubCommand(
            args = "help",
            permission = "bwcosmetics.help",
            permissionMessage = "§cYou don't have permission to do that!"
    )
    public void helpCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage(ColorUtil.colored("&7-> &6BedWars1058-Cosmetics Addon &7- &cCommands &7<-"));
            p.sendMessage(" ");
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc reload    &8- &eclick for details", "Reloads the all the YAML's"));
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc menu    &8- &eclick for details", "Opens the Main Menu"));
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc km    &8- &eclick for details", "Opens the Kill message GUI"));
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc shopkeeper    &8- &eclick for details", "Opens the ShopKeeperSkin GUI"));
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc sprays    &8- &eclick for details", "Opens the Sprays GUI"));
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc dc    &8- &eclick for details", "Opens the Death Cries GUI"));
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc glyphs    &8- &eclick for details", "Opens the Glyphs GUI"));
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc bbe    &8- &eclick for details", "Opens the Bed Break Effect GUI"));
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc finalke    &8- &eclick for details", "Opens the Final Kill Effect GUI"));
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc pt    &8- &eclick for details", "Opens the Projectile Trails GUI"));
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc vd    &8- &eclick for details", "Opens the Victory Dance GUI"));
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc ws    &8- &eclick for details", "Opens the Wood Skins GUI"));
            p.spigot().sendMessage(Utility.hoverablemsg("&6-> &7/bwc it    &8- &eclick for details", "Opens the Island Toppers GUI"));
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "quickbuy",
            permission = "bwcosmetics.quickbuy",
            permissionMessage = "§cYou don't have permission to do that!"
    )
    public void quickbuyCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage(ColorUtil.colored("&cThis command currently doesn't work! use at your own risk."));
            new PlayerQuickBuyCache(p);
            new ShopCache(p.getUniqueId());
            ShopManager.shop.open(p, PlayerQuickBuyCache.getQuickBuyCache(p.getUniqueId()), true);
            p.setMetadata("bwc_quickbuy", new FixedMetadataValue(Utility.plugin(), true));
            HCore.registerEvent(InventoryCloseEvent.class).limit(1).consume((event -> {
                if(event.getPlayer().hasMetadata("bwc_quickbuy")) {
                    event.getPlayer().removeMetadata("bwc_quickbuy", Utility.plugin());
                    PlayerQuickBuyCache.getQuickBuyCache(event.getPlayer().getUniqueId()).pushChangesToDB();
                    PlayerQuickBuyCache.getQuickBuyCache(event.getPlayer().getUniqueId()).destroy();
                    ShopCache.getShopCache(event.getPlayer().getUniqueId()).destroy();
                }
            }));
        }else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "set",
            permission = "bwcosmetics.admin",
            permissionMessage = "§cYou don't have permission to do that!"
    )
    public void setCommand(CommandSender sender, String[] args) {
        List<String> availableArgs = List.of("FinalKillEffects", "ProjectileTrails", "BedBreakEffects", "Glyphs", "DeathCries", "VictoryDances", "WoodSkins", "Sprays", "KillMessage", "ShopKeeperSkin", "IslandTopper");

        if (args.length < 4) {
            sender.sendMessage(ChatColor.RED + "Usage: /bwc set <arg> <cosmeticID> <player>");
            return;
        }

        String arg = args[1];
        String cosmeticID = args[2];
        String playerName = args[3];

        Player player = Bukkit.getPlayer(playerName);
        if (player == null || !player.isOnline()) {
            sender.sendMessage(ChatColor.RED + "Invalid or offline player: " + playerName);
            return;
        }
        if (!availableArgs.contains(arg)) {
            sender.sendMessage(ChatColor.RED + "Invalid argument: " + String.join(", ", availableArgs));
            return;
        }
        new BwcAPI().setSelectedCosmetic(player, CosmeticsType.valueOf(arg), cosmeticID);
        sender.sendMessage(ColorUtil.colored("&aSuccess! Note, this command will not check if cosmeticsID is valid!"));

    }



    @SubCommand(
            args = "reload",
            permission = "bwcosmetics.reload",
            permissionMessage = "§cYou don't have permission to do that!"
    )
    public void reloadCommand(CommandSender sender, String[] args) {
        sender.sendMessage("§aReloading the YAML's, please wait...");
        StartupUtils.updateConfigs();
        MainMenuUtils.saveLores();
        for(ConfigType configType : ConfigType.values()){
            ConfigUtils.get(configType).reload();
        }
        sender.sendMessage("§aReloaded the YAML's!");
    }


    @SubCommand(
            args = "menu"
    )
    public void menuCommand(CommandSender sender, String[] args) {
        try {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (Utility.isInArena(p))
                    return;
                new MainMenu(p).open(p);
            } else {
                sender.sendMessage(ChatColor.RED + "You need to be in-game!");
            }
        }catch (Exception e){
            e.printStackTrace();
            Bukkit.getLogger().severe("Send this to the developer!");
        }
    }

    @SubCommand(
            args = "km"
    )
    public void kmMenu(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (Utility.isInArena((Player) sender))
                return;
            InventoryGui inv = new CategoryMenu(CosmeticsType.KillMessage);
            inv.open(((Player) sender).getPlayer());
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "shopkeeper"
    )
    public void shopkeeper(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (Utility.isInArena((Player) sender))
                return;
            InventoryGui inv = new CategoryMenu(CosmeticsType.ShopKeeperSkin);
            inv.open(((Player) sender).getPlayer());
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "sprays"
    )
    public void spraysMenu(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (Utility.isInArena((Player) sender))
                return;
            InventoryGui inv = new CategoryMenu(CosmeticsType.Sprays);
            inv.open(((Player) sender).getPlayer());
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "dc"
    )
    public void dcMenu(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (Utility.isInArena((Player) sender))
                return;
            InventoryGui inv = new CategoryMenu(CosmeticsType.DeathCries);
            inv.open(((Player) sender).getPlayer());
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "glyphs"
    )
    public void glyphsMenu(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (Utility.isInArena((Player) sender))
                return;
            InventoryGui inv = new CategoryMenu(CosmeticsType.Glyphs);
            inv.open(((Player) sender).getPlayer());
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "bbe"
    )
    public void bbeMenu(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (Utility.isInArena((Player) sender))
                return;
            InventoryGui inv = new CategoryMenu(CosmeticsType.BedBreakEffects);
            inv.open(((Player) sender).getPlayer());
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "finalke"
    )
    public void finalkeMenu(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (Utility.isInArena((Player) sender))
                return;
            InventoryGui inv = new CategoryMenu(CosmeticsType.FinalKillEffects);
            inv.open(((Player) sender).getPlayer());
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "pt"
    )
    public void ptMenu(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (Utility.isInArena((Player) sender))
                return;
            InventoryGui inv = new CategoryMenu(CosmeticsType.ProjectileTrails);
            inv.open(((Player) sender).getPlayer());
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "vd"
    )
    public void vdMenu(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (Utility.isInArena((Player) sender))
                return;
            InventoryGui inv = new CategoryMenu(CosmeticsType.VictoryDances);
            inv.open(((Player) sender).getPlayer());
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "ws"
    )
    public void wsMenu(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (Utility.isInArena((Player) sender))
                return;
            InventoryGui inv = new CategoryMenu(CosmeticsType.WoodSkins);
            inv.open(((Player) sender).getPlayer());
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "it"
    )
    public void itMenu(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (Utility.isInArena((Player) sender))
                return;
            InventoryGui inv = new CategoryMenu(CosmeticsType.IslandTopper);
            inv.open(((Player) sender).getPlayer());
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }



    @SubCommand(
            args = "setIslandTopperPosition",
            permission = "bwcosmetics.admin"
    )
    public void setIslandTopperPostion(CommandSender sender, String[] args) {
        if(args.length != 2){
            sender.sendMessage(ChatColor.RED + "Command usage, /bwc setIslandTopperPosition TeamName");
            return;
        }
        if(!(sender instanceof Player) ){
            sender.sendMessage(ChatColor.RED + "Sorry but you need to be in-game to do that!");
            return;
        }
        Player p = (Player) sender;
        BwcAPI api = new BwcAPI();
        ISetupSession setupSession = api.getBwAPI().getSetupSession(p.getUniqueId());
        if(setupSession == null){
            sender.sendMessage(ChatColor.RED + "You need to be in setup when you use this command!");
            return;
        }
        String teamName = args[1];
        String configPath = "Team." + teamName;
        ConfigurationSection section = setupSession.getConfig().getYml().getConfigurationSection("Team." + teamName);
        if(section == null){
            sender.sendMessage(ColorUtil.colored("&cYou need to setup teams before you do this command!"));
            return;
        }
        setupSession.getConfig().saveConfigLoc(configPath + ".IslandTopper.location", p.getLocation());
        sender.sendMessage(ChatColor.GREEN + "Done! saved your current location as Island Topper location for team " + teamName );



    }




}


