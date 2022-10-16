package me.defender.menus;

import com.hakan.core.HCore;
import com.hakan.core.ui.inventory.HInventory;
import com.hakan.core.ui.inventory.item.ClickableItem;
import com.hakan.core.ui.inventory.pagination.Pagination;
import me.defender.api.utils.SkullUtil;
import me.defender.api.utils.util;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class islandTopperGUI extends HInventory {


    String title = "Island Toppers";
    String loc = "IslandTopper";
    String perm = "islandtopper";
    FileConfiguration config = util.plugin().islandToppersData.getConfig();

    public islandTopperGUI() {
        super("none", "Island Toppers", 6, InventoryType.CHEST);
    }

    @Override
    protected void onOpen(@NotNull Player player) {
        List<ClickableItem> items = new ArrayList<>();
        // why not?
        for(String name : config.getConfigurationSection(loc).getKeys(false)) {
            // Variables
            String material = config.getString(loc+ "." + name + ".Material");
            java.util.List<String> lore = util.getListLang(player ,loc + "." + name + ".lore");
            String head = config.getString(loc + "." + name + ".Head-Value");
            int price = config.getInt(loc + "." + name + ".Price");
            short data = (short) config.getInt(loc + "." + name + ".Data");

            // Items
            ClickableItem item;

            lore = util.formatLore(lore, name, util.getItemStatus(player, loc, perm + "." + name, name, price), price);
            List<String> lore1 = new ArrayList<>();
            for(String lores : lore){
                lore1.add(lores);
            }
            if(material != null) {
                item = new ClickableItem(HCore.itemBuilder(Material.valueOf(material)).name(true, "&a" + name).lores(true, lore1).durability(data).build(), (e) -> {
                    util.onClick(player, e.getCurrentItem(), loc, this, perm, price);
                });
            }else{
                item = new ClickableItem(HCore.itemBuilder(SkullUtil.makeTextureSkull(head)).name(true, "&a" + name).lores(true, lore1).build(), (e) -> {
                    util.onClick(player, e.getCurrentItem(), loc, this, perm, price);
                });
            }
            items.add(item);
            Pagination pages = new Pagination(this);
            List<Integer> slots = Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34);
            pages.setSlots(slots);
            pages.setItems(items);
            pages.getPage(pages.getCurrentPage()).getItems().forEach((slot, materials) -> {
                super.setItem(slot, materials);
            });
            setItem(49, HCore.itemBuilder(Material.ARROW).name(true, "&aBack").build(), (e) -> {
                util.openMainMenu((Player) e.getWhoClicked());
            });
            util.createPages(pages, this, title);
        }
    }
}
