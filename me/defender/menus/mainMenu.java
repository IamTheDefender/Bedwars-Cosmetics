package me.defender.menus;

import com.hakan.core.HCore;
import com.hakan.core.ui.inventory.HInventory;
import me.defender.api.utils.MainMenuUtils;
import me.defender.api.utils.SkullUtil;
import me.defender.api.utils.util;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class mainMenu extends HInventory {

    FileConfiguration config = util.plugin().menuData.getConfig();

    public mainMenu() {
        super("none", "Cosmetics", 6, InventoryType.CHEST);
    }

    @Override
    protected void onOpen(@NotNull Player player) {
        String loc = "Main-Menu";
        String langLoc = "cosmetics.main-menu";
        // why not?
        for(String name : config.getConfigurationSection(loc).getKeys(false)) {
            // Variables
            String material = config.getString(loc + "." + name + ".Material");
            List<String> lore = util.getListLang(player, langLoc + "." + name + ".lore");
            String itemName = util.getMSGLang(player, langLoc + "." + name + ".name");
            String head = config.getString(loc + "." + name + ".Head-Value");
            int slot = config.getInt(loc + "." + name + ".Slot");
            short data = (short) config.getInt(loc + "." + name + ".Data");

            List<String> lores = MainMenuUtils.formatLore(lore, player);

            if(material != null){
                super.setItem(slot, HCore.itemBuilder(Material.valueOf(material)).lores(true, lores).durability(data).name(true, itemName).build(), (e) -> {
                    MainMenuUtils.openMenus((Player) e.getWhoClicked(), name);
                });
            }
            if(head != null){
                super.setItem(slot, HCore.itemBuilder(SkullUtil.makeTextureSkull(head)).name(true, itemName).lores(true, lores).build(), (e) -> {
                    MainMenuUtils.openMenus((Player) e.getWhoClicked(), name);
                });
            }

        }
    }
}
