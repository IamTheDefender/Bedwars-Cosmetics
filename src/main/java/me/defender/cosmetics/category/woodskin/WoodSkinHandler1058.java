

package me.defender.cosmetics.category.woodskin;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.events.shop.ShopBuyEvent;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.XTag;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.cosmetics.category.WoodSkin;
import me.defender.cosmetics.api.cosmetics.CosmeticsType;
import me.defender.cosmetics.util.StartupUtils;
import me.defender.cosmetics.util.Utility;
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

import java.io.File;

import static me.defender.cosmetics.util.Utility.plugin;

public class WoodSkinHandler1058 implements Listener
{

	@EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) return;

        boolean isWoodSkinsEnabled = Cosmetics.getInstance().getConfig().getBoolean("wood-skins.enabled");
        if (!isWoodSkinsEnabled) return;

        if(!XTag.PLANKS.isTagged(XMaterial.matchXMaterial(e.getBlock().getType())) || !XTag.LOGS.isTagged(XMaterial.matchXMaterial(e.getBlock().getType()))) return;
        String selected = new BwcAPI().getSelectedCosmetic(e.getPlayer(), CosmeticsType.WoodSkins);
        for(WoodSkin woodSkin : StartupUtils.woodSkinsList){
            if(woodSkin.getIdentifier().equals(selected)){
                ItemStack stack = woodSkin.woodSkin();
                byte data = Byte.parseByte(String.valueOf(stack.getDurability()));
                Material type = stack.getType();
                if(e.getBlock().getType() != type) e.getBlock().setType(type);
                if(e.getBlock().getData() != data) e.getBlock().setData(data);
            }
        }
    }


    @EventHandler
    public void onBuy1058(ShopBuyEvent e) {

        boolean isWoodSkinsEnabled = Cosmetics.getInstance().getConfig().getBoolean("wood-skins.enabled");
        if (!isWoodSkinsEnabled) return;

        Player p = e.getBuyer();
        if (e.getCategoryContent().getItemStack(p).getType() == Material.WOOD) {
             String selected = new BwcAPI().getSelectedCosmetic(p, CosmeticsType.WoodSkins);
            
            BedWars bedwarsAPI = Cosmetics.getInstance().getBedWars1058API();
            String iso =  bedwarsAPI.getLangIso(p);
            String msg = bedwarsAPI.getLanguageByIso(iso).getString("shop-new-purchase");
            String prefix = bedwarsAPI.getLanguageByIso(iso).getString("prefix");
            String nopemsg = bedwarsAPI.getLanguageByIso(iso).getString("shop-insuff-money");
            
            
            File sounds = new File(Bukkit.getPluginManager().getPlugin("BedWars1058").getDataFolder(), "sounds.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(sounds);
            int cost = bedwarsAPI.getConfigs().getShopConfig().getInt("blocks-category.category-content.wood.content-tiers.tier1.tier-settings.cost");
            String currency = bedwarsAPI.getConfigs().getShopConfig().getString("blocks-category.category-content.wood.content-tiers.tier1.tier-settings.currency");
            Material m = bedwarsAPI.getShopUtil().getCurrency(currency);
            int have = bedwarsAPI.getShopUtil().calculateMoney(p, m);
            String cost1 = cost + "";
            		
            if(have < cost) {
         	   p.sendMessage(ColorUtil.colored(nopemsg).replace("{prefix}", prefix).replace("{currency}", currency).replace("{amount}", cost1));
         	   return;
            }
            bedwarsAPI.getShopUtil().takeMoney(p, m, cost);

            WoodSkin skin = null;
            for(WoodSkin woodSkin : StartupUtils.woodSkinsList){
                if(woodSkin.getIdentifier().equals(selected)){
                    skin = woodSkin;
                }
            }
            if(skin == null) return;
            ItemStack stack = skin.getItem().clone();
            stack.setAmount(16);
            p.getInventory().addItem(stack);

            Sound soundz = null;
            Sound.valueOf(config.getString("shop-bought.sound"));
            assert XSound.ENTITY_LIGHTNING_BOLT_THUNDER.parseSound() != null;
            soundz = Sound.valueOf(config.getString("shop-bought.sound", XSound.ENTITY_LIGHTNING_BOLT_THUNDER.parseSound().toString()));

            p.playSound(p.getLocation(),soundz , config.getInt("shop-bought.volume"), config.getInt("shop-bought.pitch"));
            p.sendMessage(ColorUtil.colored(msg.replace("{item}", "Wood").replace("{prefix}", prefix)));
            e.setCancelled(true);
        }
    }
}