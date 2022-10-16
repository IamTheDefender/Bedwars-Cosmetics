// 
// @author IamTheDefender
// 

package me.defender.cosmetics.WoodSkins;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.events.shop.ShopBuyEvent;

import me.defender.api.utils.util;

public class WoodSkins implements Listener
{
    @SuppressWarnings("deprecation")
	@EventHandler
    public void onBlockPlace(final BlockPlaceEvent e) {
        String selected = util.plugin().playerData.getConfig().getString(util.getUUID(e.getPlayer()) + ".WoodSkins");

        if (e.isCancelled()) {
            return;
        }
        if (e.getBlock().getType() == Material.WOOD && e.getBlock().getType() == Material.LOG && e.getBlock().getType() == Material.LOG_2) {
            if (e.getItemInHand().getAmount() <= 1) {
                final byte woodskin = (byte)util.plugin().woodskindata.getConfig().getInt("WoodSkins." + selected + ".Item-ID");
                if (selected.replace("-", "").contains("SpruceLog")) {
                    if (e.getBlock().getType() != Material.LOG || e.getBlock().getData() != woodskin) {
                        e.getBlock().setType(Material.LOG);
                        e.getBlock().setData(woodskin);
                    }
                }
                else if (selected.replace("-", "").contains("SpruceLog")) {
                    if (e.getBlock().getType() != Material.LOG || e.getBlock().getData() != woodskin) {
                        e.getBlock().setType(Material.LOG);
                        e.getBlock().setData(woodskin);
                    }
                }
                else if (selected.replace("-", "").contains("BirchLog")) {
                    if (e.getBlock().getType() != Material.LOG || e.getBlock().getData() != woodskin) {
                        e.getBlock().setType(Material.LOG);
                        e.getBlock().setData(woodskin);
                    }
                }
                else if (selected.replace("-", "").contains("JungleLog")) {
                    if (e.getBlock().getType() != Material.LOG || e.getBlock().getData() != woodskin) {
                        e.getBlock().setType(Material.LOG);
                        e.getBlock().setData(woodskin);
                    }
                }
                else if (selected.replace("-", "").contains("AcaciaLog")) {
                    if (e.getBlock().getType() != Material.LOG_2 || e.getBlock().getData() != woodskin) {
                        e.getBlock().setType(Material.LOG_2);
                        e.getBlock().setData(woodskin);
                    }
                }
                else if (selected.replace("-", "").contains("DarkOakLog")) {
                    if (e.getBlock().getType() != Material.LOG_2 || e.getBlock().getData() != woodskin) {
                        e.getBlock().setType(Material.LOG_2);
                        e.getBlock().setData(woodskin);
                    }
                }
                else if (e.getBlock().getData() != woodskin) {
                    e.getBlock().setData(woodskin);
                }
            }
            else {
                final int amount = e.getItemInHand().getAmount() - 1;
                final short woodskin2 = (short)util.plugin().woodskindata.getConfig().getInt("WoodSkins." + selected + ".Item-ID");
                if (selected.replace("-", "").contains("OakLog")) {
                    if (e.getBlock().getType() != Material.LOG || e.getBlock().getData() != woodskin2) {
                        e.getBlock().setType(Material.LOG);
                        e.getBlock().setData((byte)woodskin2);
                        e.getItemInHand().setAmount(amount);
                        e.getItemInHand().setType(Material.LOG);
                        e.getItemInHand().setDurability(woodskin2);
                    }
                }
                else if (selected.replace("-", "").contains("SpruceLog")) {
                    if (e.getBlock().getType() != Material.LOG || e.getBlock().getData() != woodskin2) {
                        e.getBlock().setType(Material.LOG);
                        e.getBlock().setData((byte)woodskin2);
                        e.getItemInHand().setAmount(amount);
                        e.getItemInHand().setType(Material.LOG);
                        e.getItemInHand().setDurability(woodskin2);
                    }
                }
                else if (selected.replace("-", "").contains("BirchLog")) {
                    if (e.getBlock().getType() != Material.LOG || e.getBlock().getData() != woodskin2) {
                        e.getBlock().setType(Material.LOG);
                        e.getBlock().setData((byte)woodskin2);
                        e.getItemInHand().setAmount(amount);
                        e.getItemInHand().setType(Material.LOG);
                        e.getItemInHand().setDurability(woodskin2);
                    }
                }
                else if (selected.replace("-", "").contains("JungleLog")) {
                    if (e.getBlock().getType() != Material.LOG || e.getBlock().getData() != woodskin2) {
                        e.getBlock().setType(Material.LOG);
                        e.getBlock().setData((byte)woodskin2);
                        e.getItemInHand().setAmount(amount);
                        e.getItemInHand().setType(Material.LOG);
                        e.getItemInHand().setDurability(woodskin2);
                    }
                }
                else if (selected.replace("-", "").contains("AcaciaLog")) {
                    if (e.getBlock().getType() != Material.LOG_2 || e.getBlock().getData() != woodskin2) {
                        e.getBlock().setType(Material.LOG_2);
                        e.getBlock().setData((byte)woodskin2);
                        e.getItemInHand().setAmount(amount);
                        e.getItemInHand().setType(Material.LOG_2);
                        e.getItemInHand().setDurability(woodskin2);
                    }
                }
                else if (selected.replace("-", "").contains("DarkOakLog")) {
                    if (e.getBlock().getType() != Material.LOG_2 || e.getBlock().getData() != woodskin2) {
                        e.getBlock().setType(Material.LOG_2);
                        e.getBlock().setData((byte)woodskin2);
                        e.getItemInHand().setType(Material.LOG_2);
                        e.getItemInHand().setAmount(amount);
                        e.getItemInHand().setDurability(woodskin2);
                    }
                }
                else if (e.getBlock().getType() != Material.WOOD || e.getBlock().getData() != woodskin2) {
                    e.getBlock().setData((byte)woodskin2);
                    e.getItemInHand().setAmount(amount);
                    e.getItemInHand().setDurability(woodskin2);
                }
            }
        }
    }


    @EventHandler
    public void onBuy(final ShopBuyEvent e) {
        final Player p = e.getBuyer();
        if (e.getCategoryContent().getItemStack(p).getType() == Material.WOOD) {
             String selected = util.plugin().playerData.getConfig().getString(util.getUUID(p) + ".WoodSkins");
            final short woodskin = (short)util.plugin().woodskindata.getConfig().getInt("WoodSkins." + selected + ".Item-ID");
            
            BedWars bedwarsAPI = Bukkit.getServicesManager().getRegistration(BedWars .class).getProvider();
            String iso =  bedwarsAPI.getLangIso(p);
            String msg = bedwarsAPI.getLanguageByIso(iso).getString("shop-new-purchase");
            String prefix = bedwarsAPI.getLanguageByIso(iso).getString("prefix");
            String nopemsg = bedwarsAPI.getLanguageByIso(iso).getString("shop-insuff-money");
            
            
            File sounds = new File(Bukkit.getPluginManager().getPlugin("BedWars1058").getDataFolder(), "sounds.yml");
            final FileConfiguration config = YamlConfiguration.loadConfiguration(sounds);
            int cost = bedwarsAPI.getConfigs().getShopConfig().getInt("blocks-category.category-content.wood.content-tiers.tier1.tier-settings.cost");
            String currency = bedwarsAPI.getConfigs().getShopConfig().getString("blocks-category.category-content.wood.content-tiers.tier1.tier-settings.currency");
            Material m = bedwarsAPI.getShopUtil().getCurrency(currency);
            int have = bedwarsAPI.getShopUtil().calculateMoney(p, m);
            String cost1 = cost + "";
            		
            if(have < cost) {
         	   p.sendMessage(util.color(nopemsg).replace("{prefix}", prefix).replace("{currency}", currency).replace("{amount}", cost1));
         	   return;
            }
            bedwarsAPI.getShopUtil().takeMoney(p, m, cost);
                        
                            if (selected.replace("-", "").contains("OakLog")) {
                                p.getInventory().addItem(new ItemStack(Material.LOG, 16, woodskin));
                            }
                            else if (selected.replace("-", "").contains("SpruceLog")) {
                            	 p.getInventory().addItem(new ItemStack(Material.LOG, 16, woodskin));
                            }
                            else if (selected.replace("-", "").contains("BirchLog")) {
                            	 p.getInventory().addItem(new ItemStack(Material.LOG, 16, woodskin));
                            }
                            else if (selected.replace("-", "").contains("JungleLog")) {
                            	 p.getInventory().addItem(new ItemStack(Material.LOG, 16, woodskin));
                            }
                            else if (selected.replace("-", "").contains("AcaciaLog")) {
                            	 p.getInventory().addItem(new ItemStack(Material.LOG_2, 16, woodskin));
                            }
                            else if (selected.replace("-", "").contains("DarkOakLog")) {
                            	p.getInventory().addItem(new ItemStack(Material.LOG_2, 16, woodskin));
                            }
                            else {
                            
                            	p.getInventory().addItem(new ItemStack(Material.WOOD, 16, woodskin));
                            }
                            
                      
                       p.playSound(p.getLocation(), Sound.valueOf(config.getString("shop-bought.sound")), config.getInt("shop-bought.volume"), config.getInt("shop-bought.pitch"));
                       p.sendMessage(util.color(msg.replace("{item}", "Wood").replace("{prefix}", prefix)));
                       e.setCancelled(true);
                        
                        }
                    }
            
            
    
    
    
    public ItemStack compress(List<ItemStack> stacks)
    {
        ItemStack stack = new ItemStack(stacks.size() == 0 ? Material.AIR : stacks.get(0).getType()); // If the amount of Items in your list is empty the Material type will default to air
        for(ItemStack stack1 : stacks)
        {
            if(stack.getAmount() >= 64) break; // If the amount of items in the stack is over 64 or at 64 we're going to break out of the loop
            stack.setAmount((stack.getAmount() + stack1.getAmount()) >= 64 ? 64 : stack.getAmount() + stack1.getAmount()); // Increases the amount of items in the stack by the amount of items in the stack we're looping through
        }
        return stack; // finally return the stack with the compressed items
    }
}
