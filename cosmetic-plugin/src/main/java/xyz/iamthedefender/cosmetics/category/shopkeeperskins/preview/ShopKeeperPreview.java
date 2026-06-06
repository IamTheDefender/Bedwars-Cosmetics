package xyz.iamthedefender.cosmetics.category.shopkeeperskins.preview;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticPreview;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.utils.ShopKeeperSkinsUtils;
import xyz.iamthedefender.cosmetics.support.protocol.PacketEventsBridge;

public class ShopKeeperPreview extends CosmeticPreview {

    public ShopKeeperPreview() {
        super(CosmeticType.SHOPKEEPER_SKINS);
    }

    @Override
    public void showPreview(Player player, Cosmetics selected, Location previewLocation, Location playerLocation) throws IllegalArgumentException {
        handleLocation(player, playerLocation);

        ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(playerLocation, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setBasePlate(false);
        as.setSmall(false); // Normal size
        as.teleport(playerLocation);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 80, 2));
        
        // Small delay for client sync
        Run.delayed(() -> {
            if (player.isOnline() && !as.isDead()) {
                PacketEventsBridge.sendCamera(player, as.getEntityId());
            }
        }, 2L);

        Runnable cleanup = ShopKeeperSkinsUtils.spawnShopKeeperNPCForPreview(player, previewLocation, selected.getIdentifier());

        setOnEnd(player, () -> {
            if (!as.isDead()) as.remove();

            PacketEventsBridge.sendCamera(player, player.getEntityId());
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            cleanup.run();
        });
    }
}
