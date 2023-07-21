package me.defender.cosmetics.api.category.shopkeeperskins.preview;

import com.cryptomorin.xseries.XSound;
import com.hakan.core.HCore;
import com.hakan.core.ui.inventory.InventoryGui;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.api.category.shopkeeperskins.ShopKeeperSkin;
import me.defender.cosmetics.api.category.shopkeeperskins.utils.ShopKeeperSkinsUtils;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.StartupUtils;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class ShopKeeperPreview {

    public void sendPreviewShopKeeperSkin(Player player, String selected, InventoryGui gui){
        for (ShopKeeperSkin shopKeeperSkin : StartupUtils.shopKeeperSkinList) {
            if (shopKeeperSkin.getIdentifier().equals(selected)){
                if (shopKeeperSkin.getField(FieldsType.RARITY, player) == RarityType.NONE) {
                    gui.open(player);
                    XSound.ENTITY_VILLAGER_NO.play(player, 1.0f, 1.0f);
                    return;
                }
            }
        }
        Location beforeLocation = player.getLocation();
        float walkSpeed = player.getWalkSpeed();
        player.closeInventory();
        Location cosmeticLocation = null, playerLocation = null;

        try {
             cosmeticLocation = getCosmeticLocation();
             playerLocation = getPlayerLocation();
        }catch (Exception exception){
            exception.printStackTrace();
            player.sendMessage(ColorUtil.colored("&cEither Preview location or Player location is not set! Contact the admin."));
        }

        if(cosmeticLocation == null || playerLocation == null) return;

        Location finalPlayerLocation = playerLocation;
        Location finalCosmeticLocation = cosmeticLocation;
        HCore.syncScheduler().run(() -> {
            player.teleport(finalPlayerLocation);
            ShopKeeperSkinsUtils.spawnShopKeeperNPCForPreview(player, finalCosmeticLocation, selected);
            player.setWalkSpeed(0);
        });

        HCore.syncScheduler().after(5, TimeUnit.SECONDS).run(() -> {
            player.teleport(beforeLocation);
            player.setWalkSpeed(walkSpeed);
            gui.open(player);
        });
    }

    private Location getCosmeticLocation() {
        World world = Bukkit.getWorld(Utility.plugin().getConfig().getString("cosmetic-preview.cosmetic-location.world"));
        double x = Utility.plugin().getConfig().getDouble("cosmetic-preview.cosmetic-location.x");
        double y = Utility.plugin().getConfig().getDouble("cosmetic-preview.cosmetic-location.y");
        double z = Utility.plugin().getConfig().getDouble("cosmetic-preview.cosmetic-location.z");
        float yaw = (float) Utility.plugin().getConfig().getDouble("cosmetic-preview.cosmetic-location.yaw");
        float pitch = (float) Utility.plugin().getConfig().getDouble("cosmetic-preview.cosmetic-location.pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }

    private Location getPlayerLocation() {
        World world = Bukkit.getWorld(Utility.plugin().getConfig().getString("cosmetic-preview.player-location.world"));
        double x = Utility.plugin().getConfig().getDouble("cosmetic-preview.player-location.x");
        double y = Utility.plugin().getConfig().getDouble("cosmetic-preview.player-location.y");
        double z = Utility.plugin().getConfig().getDouble("cosmetic-preview.player-location.z");
        float yaw = (float) Utility.plugin().getConfig().getDouble("cosmetic-preview.player-location.yaw");
        float pitch = (float) Utility.plugin().getConfig().getDouble("cosmetic-preview.player-location.pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }
}