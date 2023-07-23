package me.defender.cosmetics.menu;

import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.HCore;
import com.hakan.core.ui.inventory.InventoryGui;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.util.MainMenuUtils;
import me.defender.cosmetics.api.util.SkullUtil;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class MainMenu extends InventoryGui {

    FileConfiguration config = Utility.plugin().menuData.getConfig();

    public MainMenu(Player player) {
        super("none", Utility.getMSGLang(player, "cosmetics.gui-title") , 6, InventoryType.CHEST);
    }

    @Override
    public void onOpen(@NotNull Player player) {
        String loc = "Main-Menu";
        String langLoc = "cosmetics.main-menu";
        for(String name : config.getConfigurationSection(loc).getKeys(false)) {
            ItemStack itemStack = ConfigManager.getItemStack(config, config.getString(loc + "." + name + ".item"));
            List<String> lore = Utility.getListLang(player, langLoc + "." + name + ".lore");
            String itemName = Utility.getMSGLang(player, langLoc + "." + name + ".name");
            int slot = config.getInt(loc + "." + name + ".slot");
            List<String> lores = MainMenuUtils.formatLore(lore, player);
            boolean disabled = config.getBoolean(loc + "." + name + ".disabled");

            if(itemStack != null && !disabled){
                super.setItem(slot, HCore.itemBuilder(itemStack).lores(true, lores).name(true, itemName).build(), (e) -> {
                    MainMenuUtils.openMenus((Player) e.getWhoClicked(), name);
                });
            }
        }
        String extrasPath = "Extras.fill-empty.";
        if(config.getBoolean(extrasPath + "enabled")){
            ItemStack stack = ConfigManager.getItemStack(config, config.getString(extrasPath + "item"));
            while (toInventory().firstEmpty() != -1){
                setItem(toInventory().firstEmpty(), HCore.itemBuilder(stack).name(true, "&r").build());
            }
        }
    }
}
