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
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        Location beforeLocation = player.getLocation().clone();
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

        ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(finalPlayerLocation, EntityType.ARMOR_STAND);
        as.setVisible(false);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
                Integer.MAX_VALUE, 2));

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.equals(player)) continue;

            player1.hidePlayer(player);
        }

        CraftPlayer craftPlayer = (CraftPlayer)player;

        PacketPlayOutCamera cameraPacket = new PacketPlayOutCamera(((CraftArmorStand) as).getHandle());
        PacketPlayOutCamera resetPacket = new PacketPlayOutCamera(craftPlayer.getHandle());
        craftPlayer.getHandle().playerConnection.sendPacket(cameraPacket);

        ShopKeeperSkinsUtils.spawnShopKeeperNPCForPreview(player, finalCosmeticLocation, selected);

        HCore.syncScheduler().after(5, TimeUnit.SECONDS).run(() -> {
            if (!as.isDead()) as.remove();

            craftPlayer.getHandle().playerConnection.sendPacket(resetPacket);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            player.teleport(beforeLocation);

            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (player1.equals(player)) continue;

                player1.showPlayer(player);
            }

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