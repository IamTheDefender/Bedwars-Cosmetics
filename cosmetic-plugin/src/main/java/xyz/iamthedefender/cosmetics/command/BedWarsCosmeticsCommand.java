package xyz.iamthedefender.cosmetics.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.ConditionFailedException;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticRegistry;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.handler.ISetupSession;
import xyz.iamthedefender.cosmetics.api.menu.SystemGui;
import xyz.iamthedefender.cosmetics.api.util.ColorUtil;
import xyz.iamthedefender.cosmetics.api.util.Messages;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigType;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;
import xyz.iamthedefender.cosmetics.menu.CategoryMenu;
import xyz.iamthedefender.cosmetics.menu.MainMenu;
import xyz.iamthedefender.cosmetics.util.MainMenuUtils;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

@CommandAlias("bwc|bedwarscosmetics|cosmetics|cos|bwcos|bwcosmetic|bwcosmetics")
@Description("Main command for the cosmetics plugin.")
public class BedWarsCosmeticsCommand extends BaseCommand {

    private final CosmeticsPlugin plugin = CosmeticsPlugin.getInstance();

    @Subcommand("help")
    @CommandPermission("bwcosmetics.help")
    @CatchUnknown
    @HelpCommand
    public void helpCommand(CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("set cosmetic")
    @CommandPermission("bwcosmetics.admin")
    @Description("Manually set a cosmetic for an user")
    @CommandCompletion("@cosmeticTypes @cosmetics @players")
    public void setCosmetic(CommandSender sender, CosmeticType<?> cosmeticType, String cosmeticID, OnlinePlayer onlinePlayer) {
        Player player = onlinePlayer.getPlayer();

        if (!(CosmeticRegistry.cosmeticExist(cosmeticType, cosmeticID))) {
            throw new ConditionFailedException(Messages.ERROR_NO_COSMETIC_FOUND.value(sender instanceof Player ? (Player) sender : null));
        }

        plugin.getApi().setSelectedCosmetic(player, cosmeticType, cosmeticID);
        sender.sendMessage(ColorUtil.translate(String.format("&6Successfully set the selected cosmetic for %s to %s for category %s",
                player.getName(),
                cosmeticID,
                cosmeticType.getFormattedName()
        )));
    }

    @Subcommand("reload")
    @CommandPermission("bwcosmetics.reload")
    @Description("Reload the plugin configuration files")
    public void reloadCommand(CommandSender sender) {
        sender.sendMessage(ColorUtil.translate("&eReloading configuration files, please wait up to 60 seconds.."));
        StartupUtils.updateConfigs();
        MainMenuUtils.saveLores();

        for (ConfigType configType : ConfigType.values()) {
            ConfigUtils.get(configType).reload();
        }

        sender.sendMessage(ColorUtil.translate("&aSuccess! &6Report any issues to the developer."));
    }


    @Subcommand("menu")
    @Description("Open the main menu or the menu for a category")
    @CommandCompletion("@cosmeticTypes")
    public void menuCommand(Player player, @Optional CosmeticType<?> cosmeticType) {
        if (Utility.isInArena(player)) throw new ConditionFailedException("You cannot do that while in a game!");

        if (cosmeticType != null) {
            openMenu(player, cosmeticType);
            return;
        }

        new MainMenu(player).open(player);
    }

    @Subcommand("set islandtopperpos")
    @CommandPermission("bwcosmetics.admin")
    @Description("Set IslandTopper position for a team in an arena")
    public void onSetIslandTopperPosition(Player player, String teamName) {
        ISetupSession setupSession = plugin.getHandler().getSetupSession(player.getUniqueId());

        if (setupSession == null) {
            player.sendMessage(ChatColor.RED + "You need to be in setup when you use this command!");
            return;
        }

        ConfigurationSection section = setupSession.getConfig().getConfigurationSection("Team." + teamName);
        if (section == null) {
            player.sendMessage(ColorUtil.translate("&cYou need to setup teams before you do this command!"));
            return;
        }

        String configPath = "Team." + teamName;
        setupSession.saveConfigLoc(configPath + ".IslandTopper.location", player.getLocation());

        ArmorStand hologramStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, 3, 0), EntityType.ARMOR_STAND);
        hologramStand.setGravity(false);
        hologramStand.setVisible(false);
        hologramStand.setCustomNameVisible(true);

        ChatColor color = getColorForTeam(section, teamName);

        hologramStand.setCustomName(color + teamName + " " + ChatColor.GOLD + "ISLAND TOPPER SET" + ChatColor.GRAY + " (HOLOGRAM WILL DISAPPEAR IN 5 SECONDS)");

        Run.delayed(hologramStand::remove, 5 * 20);
        player.sendMessage(ChatColor.GREEN + "Done! saved your current location as Island Topper location for team " + teamName);
    }

    @Subcommand("set previewloc")
    @CommandPermission("bwcosmetics.admin")
    @Description("Set preview location, where preview will be spawned at")
    public void onSetPreviewLocation(Player player) {
        saveLocation(player, "preview.locations.cosmetic", "cosmetic-preview.cosmetic-location");
        player.sendMessage(ChatColor.GREEN + "Done! saved your current location as preview location.");
    }

    @Subcommand("set playerloc")
    @CommandPermission("bwcosmetics.admin")
    @Description("Set player preview location, player will be teleported to this during previews")
    public void onSetPlayerLocation(Player player) {
        saveLocation(player, "preview.locations.player", "cosmetic-preview.player-location");
        player.sendMessage(ChatColor.GREEN + "Done! saved your current location as player location for preview.");
    }

    @Subcommand("testvd")
    public void testVd(Player player, String id) {
        VictoryDance victoryDance = CosmeticRegistry.getById(CosmeticType.VICTORY_DANCES, id);

        if (victoryDance == null) {
            throw new ConditionFailedException("No victory dance with ID: " + id);
        }

        victoryDance.execute(player);

        player.sendMessage("Started execution of " + victoryDance.getDisplayName());

        Run.delayed(() -> victoryDance.stopExecution(player), 10 * 20L);
    }

    private void openMenu(Player player, CosmeticType<?> CosmeticType) {
        if (Utility.isInArena(player)) {
            player.sendMessage(ChatColor.RED + "You cannot do that while in a game!");
            return;
        }

        String title = CosmeticType.getFormatedName();
        if (CosmeticsPlugin.isPlaceholderAPI()) {
            title = PlaceholderAPI.setPlaceholders(player, title);
        }

        xyz.iamthedefender.cosmetics.data.PlayerData data = CosmeticsPlugin.getInstance().getPlayerManager().getPlayerData(player.getUniqueId());
        SystemGui inv = new CategoryMenu(CosmeticType, title, 1, data.getSortMode(), data.isOwnedFirst());
        inv.open(player);
    }


    private ChatColor getColorForTeam(ConfigurationSection section, String teamName) {
        if (teamName.equalsIgnoreCase("Pink")) {
            return ChatColor.LIGHT_PURPLE;
        }

        try {
            return ChatColor.valueOf(section.getString("Color"));
        } catch (Exception e) {
            return ChatColor.GOLD;
        }
    }

    private void saveLocation(Player player, String primaryPath, String legacyPath) {
        Location location = player.getLocation();
        ConfigManager config = ConfigUtils.getMainConfig();
        writeLocation(config, primaryPath, location);
        writeLocation(config, legacyPath, location);
        config.save();
        config.reload();
    }

    private void writeLocation(ConfigManager config, String path, Location location) {
        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getX());
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getZ());
        config.set(path + ".yaw", location.getYaw());
        config.set(path + ".pitch", location.getPitch());
    }

}
