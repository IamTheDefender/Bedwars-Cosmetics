package xyz.iamthedefender.cosmetics.menu;

import com.cryptomorin.xseries.XItemStack;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.menu.impl.ChestSystemGui;
import xyz.iamthedefender.cosmetics.api.util.ColorUtil;
import xyz.iamthedefender.cosmetics.api.util.ItemBuilder;
import xyz.iamthedefender.cosmetics.api.util.Messages;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.util.MainMenuUtils;

import java.util.List;

public class MainMenu extends ChestSystemGui {

    public MainMenu(Player player) {
        super(resolveTitle(), resolveRows());
    }

    @Override
    public void onOpen(@NotNull Player player) {
        String langLoc = "cosmetics.main-menu";
        FileConfiguration config = CosmeticsPlugin.getInstance().getMenuData().getYml();
        String itemsPath = config.contains("main-menu.items") ? "main-menu.items" : "Main-Menu";

        ConfigurationSection section = config.getConfigurationSection(itemsPath);
        if (section == null) {
            return;
        }

        for(String name : section.getKeys(false)) {
            try {
                String itemPath = itemsPath + "." + name + ".";
                ItemStack itemStack = ConfigManager.getItemStack(config, itemPath + "item");
                List<String> lore = Messages.mainMenuItemLore(name, Utility.getListLang(player, langLoc + "." + name + ".lore")).list(player);
                String itemName = Messages.mainMenuItemName(name, Utility.getMSGLang(player, langLoc + "." + name + ".name")).value(player);
                int slot = config.getInt(itemPath + "slot");
                List<String> lores = MainMenuUtils.formatLore(lore, player);
                boolean disabled = config.getBoolean(itemPath + "disabled");

                // Translate for XItemStack
                ConfigurationSection configurationSection = new MemoryConfiguration();
                configurationSection.set("lore", lores);
                configurationSection.set("name", itemName);

                if (itemStack != null && !disabled) {

                    super.setItem(slot, XItemStack.edit(itemStack, configurationSection, s -> s, null), (e) -> {
                        MainMenuUtils.handleItemClick((Player) e.getWhoClicked(), name);
                    });
                }
            }catch (Exception exception){
                Bukkit.getLogger().warning("There was an error with main menu item: " + name);
                throw new RuntimeException(exception);
            }
        }
        String extrasPath = config.contains("main-menu.fill-empty.enabled") ? "main-menu.fill-empty." : "Extras.fill-empty.";
        if (config.getBoolean(extrasPath + "enabled")){
            ItemStack stack = ConfigManager.getItemStack(config, extrasPath + "item");
            while (getInventory().firstEmpty() != -1){
                setItem(getInventory().firstEmpty(), new ItemBuilder(stack).name("&r").build());
            }
        }
    }

    @Override
    public void onClose(Player player) {

    }

    private static int resolveRows() {
        FileConfiguration config = CosmeticsPlugin.getInstance().getMenuData().getYml();
        if (config.contains("main-menu.layout.rows")) {
            return Math.min(6, Math.max(1, config.getInt("main-menu.layout.rows")));
        }
        return 6;
    }

    private static String resolveTitle() {
        return ColorUtil.translate(Messages.MAIN_MENU_GUI_TITLE.value(null));
    }
}
