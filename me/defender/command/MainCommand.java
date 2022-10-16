package me.defender.command;

import com.hakan.core.command.executors.base.BaseCommand;
import com.hakan.core.command.executors.sub.SubCommand;
import com.hakan.core.ui.inventory.HInventory;
import me.defender.api.BwcAPI;
import me.defender.api.utils.MainMenuUtils;
import me.defender.menus.*;
import me.defender.ServerManager;
import me.defender.api.utils.util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

import static me.defender.api.utils.util.plugin;

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
    public void helpCommand(CommandSender sender, String[] args){
        if(sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage(util.color("&7? &6BedWars1058-Cosmetics Addon &7- &cCommands"));
            p.sendMessage(" ");
            p.spigot().sendMessage(util.hoverablemsg("&6? &7/bwc reload    &8- &eclick for details", "Reloads the all the YAML's"));
            p.spigot().sendMessage(util.hoverablemsg("&6? &7/bwc menu    &8- &eclick for details", "Opens the Main Menu"));
            p.spigot().sendMessage(util.hoverablemsg("&6? &7/bwc km    &8- &eclick for details", "Opens the Kill message GUI"));
            p.spigot().sendMessage(util.hoverablemsg("&6? &7/bwc shopkeeper    &8- &eclick for details", "Opens the ShopKeeperSkin GUI"));
            p.spigot().sendMessage(util.hoverablemsg("&6? &7/bwc sprays    &8- &eclick for details", "Opens the Sprays GUI"));
            p.spigot().sendMessage(util.hoverablemsg("&6? &7/bwc dc    &8- &eclick for details", "Opens the Death Cries GUI"));
            p.spigot().sendMessage(util.hoverablemsg("&6? &7/bwc glyphs    &8- &eclick for details", "Opens the Glyphs GUI"));
            p.spigot().sendMessage(util.hoverablemsg("&6? &7/bwc bbe    &8- &eclick for details", "Opens the Bed Break Effect GUI"));
            p.spigot().sendMessage(util.hoverablemsg("&6? &7/bwc finalke    &8- &eclick for details", "Opens the Final Kill Effect GUI"));
            p.spigot().sendMessage(util.hoverablemsg("&6? &7/bwc pt    &8- &eclick for details", "Opens the Projectile Trails GUI"));
            p.spigot().sendMessage(util.hoverablemsg("&6? &7/bwc vd    &8- &eclick for details", "Opens the Victory Dance GUI"));
            p.spigot().sendMessage(util.hoverablemsg("&6? &7/bwc ws    &8- &eclick for details", "Opens the Wood Skins GUI"));
        }else{
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "reload",
            permission = "bwcosmetics.reload",
            permissionMessage = "§cYou don't have permission to do that!"
    )
    public void reloadCommand(CommandSender sender, String[] args){
        sender.sendMessage("§aReloading the YAML's, please wait...");
        ServerManager.updateConfigs();
        util.addToList();
        MainMenuUtils.saveLores();

        plugin().spraysdata.reloadcfg();
        plugin().vdconf.reloadcfg();
        plugin().shopkeeperdata.reloadcfg();
        plugin().pedata.reloadcfg();
        plugin().killmessagecfg.reloadcfg();
        plugin().glyconf.reloadcfg();
        plugin().dcdata.reloadcfg();
        plugin().finalkilldata.reloadcfg();
        plugin().bbedata.reloadcfg();
        plugin().woodskindata.reloadcfg();
        plugin().reloadConfig();
        plugin().playerData.reloadcfg();
        plugin().menuData.reloadcfg();
        sender.sendMessage("§aReloaded the YAML's!");
    }


    @SubCommand(
            args = "menu"
    )
    public void menuCommand(CommandSender sender, String[] args){
        if(sender instanceof  Player){
            Player p = (Player) sender;
            if(util.isInArena(p))
                return;
            new mainMenu().open(p);
        }else{
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "km"
    )
    public void kmMenu(CommandSender sender, String[] args){
        if(sender instanceof  Player){
            if(util.isInArena((Player) sender))
                return;
            HInventory inv = new killMessageGUI();
            inv.open(((Player) sender).getPlayer());
        }else{
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "shopkeeper"
    )
    public void shopkeeper(CommandSender sender, String[] args){
        if(sender instanceof  Player){
            if(util.isInArena((Player) sender))
                return;
            HInventory inv = new shopKeeperGUI();
                    inv.open(((Player) sender).getPlayer());
        }else{
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "sprays"
    )
    public void spraysMenu(CommandSender sender, String[] args){
        if(sender instanceof  Player){
            if(util.isInArena((Player) sender))
                return;
            HInventory inv = new spraysGUI();
            inv.open(((Player) sender).getPlayer());
        }else{
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "dc"
    )
    public void dcMenu(CommandSender sender, String[] args){
        if(sender instanceof  Player){
            if(util.isInArena((Player) sender))
                return;
            HInventory inv = new deathCries();
            inv.open(((Player) sender).getPlayer());
        }else{
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "glyphs"
    )
    public void glyphsMenu(CommandSender sender, String[] args){
        if(sender instanceof  Player){
            if(util.isInArena((Player) sender))
                return;
            HInventory inv = new glyphsGUI();
            inv.open(((Player) sender).getPlayer());
        }else{
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "bbe"
    )
    public void bbeMenu(CommandSender sender, String[] args){
        if(sender instanceof  Player){
            if(util.isInArena((Player) sender))
                return;
            HInventory inv = new bedBreakEffectsGUI();
            inv.open(((Player) sender).getPlayer());
        }else{
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "finalke"
    )
    public void finalkeMenu(CommandSender sender, String[] args){
        if(sender instanceof  Player){
            if(util.isInArena((Player) sender))
                return;
            HInventory inv = new finalKillEffects();
            inv.open(((Player) sender).getPlayer());
        }else{
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "pt"
    )
    public void ptMenu(CommandSender sender, String[] args){
        if(sender instanceof  Player){
            if(util.isInArena((Player) sender))
                return;
            HInventory inv = new projectileTrailsGUI();
            inv.open(((Player) sender).getPlayer());
        }else{
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "vd"
    )
    public void vdMenu(CommandSender sender, String[] args){
        if(sender instanceof  Player){
            if(util.isInArena((Player) sender))
                return;
            HInventory inv = new victoryDanceGUI();
            inv.open(((Player) sender).getPlayer());
        }else{
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "ws"
    )
    public void wsMenu(CommandSender sender, String[] args){
        if(sender instanceof  Player){
            if(util.isInArena((Player) sender))
                return;
            HInventory inv = new woodSkinsGUI();
            inv.open(((Player) sender).getPlayer());
        }else{
            sender.sendMessage(ChatColor.RED + "You need to be in-game!");
        }
    }

    @SubCommand(
            args = "createHeight",
            permission = "bwcosmetics.createHeight"
    )
    public void height(CommandSender sender, String[] args){
        ServerManager.createHeightValue();
    }


    @SubCommand(
            args = "forceCreateTable",
            permission = "bwcosmetics.forceTable"
    )
    public void forceTable(CommandSender sender, String[] args){
        if(new BwcAPI().isMySQL()){
            try {
                plugin().db.setupDB();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            sender.sendMessage(ChatColor.GREEN + "Done!");
        }
    }

}


