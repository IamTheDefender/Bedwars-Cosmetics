package xyz.iamthedefender.cosmetics.category.bedbreakeffects.preview;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticPreview;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.BedDestroy;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.support.protocol.PacketEventsBridge;

public class BedDestroyPreview extends CosmeticPreview {


    // TODO
    public BedDestroyPreview() {
        super(CosmeticType.BED_DESTROY);
    }

    @Override
    public void showPreview(Player player, Cosmetics selected, Location previewLocation, Location playerLocation) throws IllegalArgumentException {
        handleLocation(player, playerLocation);

        ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(playerLocation, EntityType.ARMOR_STAND);
        as.setVisible(false);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
                100, 2));

        Runnable onEnd = sendBedBreakEffect(player, previewLocation, (BedDestroy) selected);
        PacketEventsBridge.sendCamera(player, as.getEntityId());

        setOnEnd(player, () -> {
            if (!as.isDead()) as.remove();

            PacketEventsBridge.sendCamera(player, player.getEntityId());
            player.removePotionEffect(PotionEffectType.INVISIBILITY);

            onEnd.run();
        });
    }

    private Runnable sendBedBreakEffect(Player player, Location location, BedDestroy selected) {
        Material old = location.getBlock().getType();
        byte data = location.getBlock().getData();
        player.sendBlockChange(location, XMaterial.RED_BED.parseMaterial(), XMaterial.RED_BED.getData());

        Run.delayed(() -> {
            player.sendBlockChange(location, old, data);

            // Accordingly spawn in the effect, requires further work
            Run.delayed(() -> {
                //selected.execute(player, location, selected.getIdentifier());
            }, 20L);
        }, 20L);

        return () -> {

        };
    }
}
