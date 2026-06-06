package xyz.iamthedefender.cosmetics.category.sprays;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.Spray;
import xyz.iamthedefender.cosmetics.api.event.AbstractListener;
import xyz.iamthedefender.cosmetics.api.util.Messages;
import xyz.iamthedefender.cosmetics.category.sprays.util.SpraysUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

public class AbstractSpray extends AbstractListener {

    public boolean enabled() {
        return StartupUtils.isFeatureEnabled("sprays");
    }

    public boolean canApply(ItemFrame itemFrame) {
        if (itemFrame.getItem() == null) return false;

        XMaterial material = XMaterial.matchXMaterial(itemFrame.getItem());
        return material == XMaterial.AIR || material == XMaterial.MAP || material == XMaterial.FILLED_MAP;
    }

    public void execute(Player player, ItemFrame itemFrame) {
        Spray spray = getSelectedCosmetic(player, CosmeticType.SPRAYS);
        if (spray != null) spray.execute(player, itemFrame);
    }

    public void markSprayFrames(World world) {
        for (final Entity entity : world.getEntities()) {
            if (entity.getType() != EntityType.ITEM_FRAME) continue;

            ItemFrame itemFrame = (ItemFrame) entity;
            if (itemFrame.getItem() != null && itemFrame.getItem().getType() != Material.AIR) continue;

            SpraysUtil.markSprayFrame(itemFrame);
            ArmorStand stand = (ArmorStand) entity.getWorld().spawnEntity(entity.getLocation().subtract(0.0, 0.9, 0.0), EntityType.ARMOR_STAND);
            stand.setVisible(false);
            stand.setGravity(false);
            stand.setCustomName(Messages.SPRAY_CLICK.value(null));
            stand.setMetadata("HOLO_ITEM_FRAME", new FixedMetadataValue(CosmeticsPlugin.getInstance(), ""));
            stand.setCustomNameVisible(true);
            stand.setMarker(true);
            stand.setSmall(true);
        }
    }
}
