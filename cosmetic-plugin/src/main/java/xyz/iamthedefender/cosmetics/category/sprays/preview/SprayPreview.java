package xyz.iamthedefender.cosmetics.category.sprays.preview;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.cryptomorin.xseries.XSound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticPreview;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.Spray;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.sprays.util.SpraysUtil;

public class SprayPreview extends CosmeticPreview {

    private ItemFrame frame;
    private PacketAdapter adapter;

    public SprayPreview() {
        super(CosmeticsType.Sprays);
    }

    @Override
    public void showPreview(Player player, Cosmetics selected, Location previewLocation, Location playerLocation) throws IllegalArgumentException {
        if (!(selected instanceof Spray)) return;

        Spray spray = (Spray) selected;

        if (spray.getField(FieldsType.RARITY, player) == RarityType.NONE) {
            XSound.ENTITY_VILLAGER_NO.play(player, 1.0f, 1.0f);
            return;
        }

        if (previewLocation == null || playerLocation == null) {
            throw new IllegalArgumentException("Preview location or Player location is not set!");
        }

        handleLocation(player, playerLocation);

        ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(playerLocation, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setBasePlate(false);
        as.setSmall(false);
        as.teleport(playerLocation);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 80, 2));

        sendFrame(player, selected, previewLocation);

        PacketContainer cameraPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CAMERA);
        cameraPacket.getIntegers().write(0, as.getEntityId());

        PacketContainer resetPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CAMERA);
        resetPacket.getIntegers().write(0, player.getEntityId());
        
        Run.delayed(() -> {
            if (player.isOnline() && !as.isDead()) {
                CosmeticsPlugin.getInstance().getProtocolManager().sendServerPacket(player, cameraPacket);
            }
        }, 2L);


        setOnEnd(player, () -> {
            if (!as.isDead()) as.remove();

            if (frame != null && !frame.isDead()) {
                frame.setItem(new ItemStack(Material.AIR));
                frame.remove();
            }
            if (adapter != null) {
                CosmeticsPlugin.getInstance().getProtocolManager().removePacketListener(adapter);
            }
            CosmeticsPlugin.getInstance().getProtocolManager().sendServerPacket(player, resetPacket);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        });
    }

    public void sendFrame(Player player, Cosmetics selected, Location previewLocation) {
        Location loc = previewLocation.clone();
        loc.setPitch(0);
        
        BlockFace face = getCardinalDirection(loc);
        
        BlockFace opposite = face.getOppositeFace();
        Location supportBlock = loc.clone().add(opposite.getModX(), opposite.getModY(), opposite.getModZ());
        supportBlock.getBlock().setType(Material.BARRIER);
        
        frame = (ItemFrame) loc.getWorld().spawnEntity(loc, EntityType.ITEM_FRAME);

        adapter = new PacketAdapter(CosmeticsPlugin.getInstance(), PacketType.Play.Server.SPAWN_ENTITY) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacket().getIntegers().read(0) == frame.getEntityId() &&
                !event.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        };

        CosmeticsPlugin.getInstance().getProtocolManager().addPacketListener(adapter);
        frame.setFacingDirection(face, true);
        SpraysUtil.spawnSprays(player, frame, true, (Spray) selected);

        XSound.ENTITY_SILVERFISH_HURT.play(player, 10f, 10f);

        Run.every((r) -> {
            if(frame == null || frame.isDead() || !frame.isValid()){
                if (supportBlock.getBlock().getType() == Material.BARRIER) {
                    supportBlock.getBlock().setType(Material.AIR);
                }
                r.cancel();
            }
        }, 10L);
    }

    public static BlockFace getCardinalDirection(Location location) {
        double yaw = location.getYaw();

        if (yaw < 0) {
            yaw += 360;
        }

        if (yaw >= 315 || yaw < 45) {
            return BlockFace.SOUTH;
        } else if (yaw >= 45 && yaw < 135) {
            return BlockFace.WEST;
        } else if (yaw >= 135 && yaw < 225) {
            return BlockFace.NORTH;
        } else {
            return BlockFace.EAST;
        }
    }
}