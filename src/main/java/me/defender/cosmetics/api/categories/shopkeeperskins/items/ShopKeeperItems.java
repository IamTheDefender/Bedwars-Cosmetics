package me.defender.cosmetics.api.categories.shopkeeperskins.items;

import me.defender.cosmetics.api.categories.shopkeeperskins.ShopKeeperSkin;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.utils.StringUtils;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.categories.shopkeeperskins.utils.ShopKeeperSkinsUtils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopKeeperItems {

    public void registerItems(){
        ConfigurationSection section = CosmeticsType.ShopKeeperSkin.getConfig().getYml().getConfigurationSection(CosmeticsType.ShopKeeperSkin.getSectionKey());
        if(section == null) return;
        ConfigManager config = CosmeticsType.ShopKeeperSkin.getConfig();
        for(String id : section.getKeys(false)){
            String path = CosmeticsType.ShopKeeperSkin.getSectionKey() + "." + id + ".";
            ShopKeeperSkin shopKeeperSkin = new ShopKeeperSkin() {
                @Override
                public ItemStack getItem() {
                    return config.getItemStack(path + "item");
                }

                @Override
                public String base64() {
                    return null;
                }

                @Override
                public String getIdentifier() {
                    return id;
                }

                @Override
                public String getDisplayName() {
                    return StringUtils.replaceHyphensAndCaptalizeFirstLetter(id);
                }

                @Override
                public List<String> getLore() {
                    if(getRarity() == RarityType.NONE){
                        return List.of("&7Selecting this option disables your", "&7ShopKeeper Skin.");
                    }
                    return List.of("&7Select " + getDisplayName()  + " as your", "&7shopkeeper skin!");
                }

                @Override
                public int getPrice() {
                    return config.getInt(path + "price");
                }

                @Override
                public RarityType getRarity() {
                    return RarityType.valueOf(config.getString(path + "rarity").toUpperCase());
                }

                @Override
                public void execute(Player player, Location shopLocation, Location upgradeLocation) {
                    ShopKeeperSkinsUtils.spawnShopKeeperNPC(player,shopLocation, upgradeLocation);
                }
            };
            shopKeeperSkin.register();
        }
    }
}
