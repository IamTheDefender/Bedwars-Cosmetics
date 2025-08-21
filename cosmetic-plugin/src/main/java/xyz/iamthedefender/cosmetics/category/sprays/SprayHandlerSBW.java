package xyz.iamthedefender.cosmetics.category.sprays;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.screamingsandals.bedwars.api.events.BedwarsGameStartEvent;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.Spray;
import xyz.iamthedefender.cosmetics.api.util.ColorUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.function.Predicate;

public class SprayHandlerSBW implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent e) {

        boolean isSpraysEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("sprays.enabled");
        if (!isSpraysEnabled) return;

        Player p = e.getPlayer();

        if (!(e.getRightClicked() instanceof ItemFrame)) {
            return;
        }

        ItemFrame itemFrame = (ItemFrame) e.getRightClicked();
        if (itemFrame.getItem() == null) return;

        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(p, CosmeticsType.Sprays);
        XMaterial material = XMaterial.matchXMaterial(itemFrame.getItem());

        Predicate<XMaterial> allowed = m -> m == XMaterial.AIR || m == XMaterial.MAP || m == XMaterial.FILLED_MAP;

        if (!allowed.test(material)) {
            return;
        }

        for (Spray spray : StartupUtils.sprayList) {
            if (spray.getIdentifier().equals(selected)) {
                spray.execute(p, itemFrame);
            }
        }

        e.setCancelled(true);
    }


    @EventHandler
    public void onGameStart(BedwarsGameStartEvent event) {

        boolean isSpraysEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("sprays.enabled");
        if (!isSpraysEnabled) return;

        for (final Entity e : event.getGame().getGameWorld().getEntities()) {
            if (e.getType() != EntityType.ITEM_FRAME) continue;

            ItemFrame itemFrame = (ItemFrame) e;

            if (itemFrame.getItem() != null && itemFrame.getItem().getType() != Material.AIR) continue;

            ArmorStand stand = (ArmorStand) e.getWorld().spawnEntity(e.getLocation().subtract(0.0, 0.9, 0.0), EntityType.ARMOR_STAND);
            stand.setVisible(false);
            stand.setGravity(false);
            stand.setCustomName(ColorUtil.translate("&eClick!"));
            stand.setMetadata("HOLO_ITEM_FRAME", new FixedMetadataValue(CosmeticsPlugin.getInstance(), ""));
            stand.setCustomNameVisible(true);
            stand.setMarker(true);
            stand.setSmall(true);
        }
    }

}
