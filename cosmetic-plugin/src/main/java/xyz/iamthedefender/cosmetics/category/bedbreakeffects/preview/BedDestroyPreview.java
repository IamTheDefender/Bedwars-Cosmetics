package xyz.iamthedefender.cosmetics.category.bedbreakeffects.preview;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticPreview;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.BedDestroy;
import xyz.iamthedefender.cosmetics.api.util.Run;

public class BedDestroyPreview extends CosmeticPreview {


    // TODO
    public BedDestroyPreview() {
        super(CosmeticsType.BedBreakEffects);
    }

    @Override
    public void showPreview(Player player, Cosmetics selected, Location previewLocation, Location playerLocation) throws IllegalArgumentException {
        handleLocation(player, playerLocation);

        ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(playerLocation, EntityType.ARMOR_STAND);
        as.setVisible(false);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
                100, 2));

        Runnable onEnd = sendBedBreakEffect(player, previewLocation, (BedDestroy) selected);

        PacketContainer cameraPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CAMERA);
        cameraPacket.getIntegers().write(0, as.getEntityId());

        PacketContainer resetPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CAMERA);
        resetPacket.getIntegers().write(0, player.getEntityId());
        CosmeticsPlugin.getInstance().getProtocolManager().sendServerPacket(player, cameraPacket);

        setOnEnd(player, () -> {
            if (!as.isDead()) as.remove();

            CosmeticsPlugin.getInstance().getProtocolManager().sendServerPacket(player, resetPacket);
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
