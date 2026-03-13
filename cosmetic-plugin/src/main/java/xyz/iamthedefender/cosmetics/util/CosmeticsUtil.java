package xyz.iamthedefender.cosmetics.util;

import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.ShopKeeperSkin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CosmeticsUtil {

    public static ShopKeeperSkin getShopKeeperSkin(Player player) {
        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(player, CosmeticsType.ShopKeeperSkins);

        List<ShopKeeperSkin> shopKeeperSkins = getShopKeeperSkins(player);

        if (shopKeeperSkins.isEmpty()) return ShopKeeperSkin.getDefault(player);

        ShopKeeperSkin selectedSkin = shopKeeperSkins.stream().filter(shopKeeperSkin -> shopKeeperSkin.getIdentifier().equals(selected)).findFirst().orElse(null);

        if (selectedSkin != null && selectedSkin.getField(FieldsType.RARITY, null) == RarityType.RANDOM) {
            List<ShopKeeperSkin> filteredSkins = shopKeeperSkins.stream().filter(shopKeeperSkin -> shopKeeperSkin.getField(FieldsType.RARITY, null) != RarityType.RANDOM).collect(Collectors.toList());

            // ThreadLocalRandom#current#nextInt is used instead of Random because this method may or may not be called in an async context
            selectedSkin = filteredSkins.get(ThreadLocalRandom.current().nextInt(filteredSkins.size()));
        }

        return selectedSkin;
    }

    public static List<ShopKeeperSkin> getShopKeeperSkins(Player player) {
        List<ShopKeeperSkin> shopKeeperSkins = new ArrayList<>();

        for (ShopKeeperSkin shopKeeperSkin : StartupUtils.shopKeeperSkinList) {
            if (player.hasPermission(CosmeticsType.ShopKeeperSkins.getPermissionFormat() + "." + shopKeeperSkin.getIdentifier())) {
                shopKeeperSkins.add(shopKeeperSkin);
            }
        }

        return shopKeeperSkins;
    }

}
