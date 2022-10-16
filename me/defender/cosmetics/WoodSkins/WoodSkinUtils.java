// 
// @author IamTheDefender
// 

package me.defender.cosmetics.WoodSkins;

import org.bukkit.inventory.ItemStack;
import com.sk89q.worldedit.blocks.ItemType;
import org.bukkit.entity.Player;

public class WoodSkinUtils
{
    public static void stackItem(final Player player) {
        final ItemStack[] items = player.getInventory().getContents();
        final int len = items.length;
        int affected = 0;
        for (int i = 0; i < len; ++i) {
            final ItemStack item = items[i];
            if (item != null && item.getAmount() > 0) {
                if (item.getMaxStackSize() == 1) {
                    final int max = item.getMaxStackSize();
                    if (item.getAmount() < max) {
                        int needed = max - item.getAmount();
                        for (int j = i + 1; j < len; ++j) {
                            final ItemStack item2 = items[j];
                            if (item2 != null && item2.getAmount() > 0) {
                                if (item.getMaxStackSize() == 1) {
                                    if (item2.getTypeId() == item.getTypeId() && (!ItemType.usesDamageValue(item.getTypeId()) || item.getDurability() == item2.getDurability()) && ((item.getItemMeta() == null && item2.getItemMeta() == null) || (item.getItemMeta() != null && item.getItemMeta().equals(item2.getItemMeta())))) {
                                        if (item2.getAmount() > needed) {
                                            item.setAmount(max);
                                            item2.setAmount(item2.getAmount() - needed);
                                            break;
                                        }
                                        items[j] = null;
                                        item.setAmount(item.getAmount() + item2.getAmount());
                                        needed = max - item.getAmount();
                                        ++affected;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (affected > 0) {
            player.getInventory().setContents(items);
        }
    }
}
