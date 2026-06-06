package xyz.iamthedefender.cosmetics.support.npc;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.player.Equipment;
import com.github.retrooper.packetevents.protocol.player.EquipmentSlot;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.support.protocol.PacketEventsBridge;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
public abstract class PacketNpc {

    private static final byte SNEAKING_FLAG = 0x02;

    private final int entityId;
    private final UUID entityUuid;
    private final Set<UUID> viewers = ConcurrentHashMap.newKeySet();
    private final List<EntityData<?>> metadata;
    private Location location;
    private ItemStack mainHand;
    private boolean sneaking;
    private Consumer<Player> interactionHandler;

    protected PacketNpc(int entityId, UUID entityUuid, Location location, List<EntityData<?>> metadata) {
        this.entityId = entityId;
        this.entityUuid = entityUuid;
        this.location = location.clone();
        this.metadata = metadata == null ? new ArrayList<>() : cloneMetadata(metadata);
        applyFlags();
    }

    public PacketNpc mainHand(ItemStack itemStack) {
        this.mainHand = itemStack == null ? null : itemStack.clone();
        sendEquipment();
        return this;
    }

    public PacketNpc sneaking(boolean sneaking) {
        this.sneaking = sneaking;
        applyFlags();
        sendMetadata();
        return this;
    }

    public PacketNpc interaction(Consumer<Player> interactionHandler) {
        this.interactionHandler = interactionHandler;
        return this;
    }

    public void spawn(Collection<? extends Player> players) {
        for (Player player : players) {
            spawn(player);
        }
    }

    public abstract void spawn(Player player);

    public abstract void despawn(Player player);

    public void destroy() {
        for (UUID viewerId : List.copyOf(viewers)) {
            despawn(Bukkit.getPlayer(viewerId));
        }
        viewers.clear();
    }

    public void teleport(Location location) {
        this.location = location.clone();
        for (Player viewer : resolveViewers(viewers)) {
            sendTeleport(viewer);
            sendHeadRotation(viewer);
        }
    }

    public void lookAt(Location target) {
        if (target == null || target.getWorld() == null || location.getWorld() == null
                || !target.getWorld().equals(location.getWorld())) {
            return;
        }
        Location updated = location.clone();
        updated.setDirection(target.toVector().subtract(updated.toVector()));
        teleport(updated);
    }

    // -- metadata flag helpers --

    private void applyFlags() {
        byte flags = getByteMetadata(0);
        if (sneaking) {
            flags |= SNEAKING_FLAG;
        } else {
            flags &= ~SNEAKING_FLAG;
        }
        setByteMetadata(0, flags);
    }

    private byte getByteMetadata(int index) {
        for (EntityData<?> entityData : metadata) {
            if (entityData.getIndex() == index && entityData.getValue() instanceof Byte) {
                return (Byte) entityData.getValue();
            }
        }
        return 0;
    }

    private void setByteMetadata(int index, byte value) {
        for (EntityData<?> entityData : metadata) {
            if (entityData.getIndex() == index) {
                @SuppressWarnings("unchecked")
                EntityData<Byte> byteData = (EntityData<Byte>) entityData;
                byteData.setValue(value);
                return;
            }
        }
        metadata.add(new EntityData<>(index, EntityDataTypes.BYTE, value));
    }

    // -- packet helpers --

    protected void sendMetadata() {
        for (Player viewer : resolveViewers(viewers)) {
            sendMetadata(viewer);
        }
    }

    protected void sendMetadata(Player player) {
        PacketEventsBridge.sendPacket(player, new WrapperPlayServerEntityMetadata(entityId, metadata));
    }

    protected void sendEquipment() {
        for (Player viewer : resolveViewers(viewers)) {
            sendEquipment(viewer);
        }
    }

    protected void sendEquipment(Player player) {
        if (mainHand == null || mainHand.getType() == org.bukkit.Material.AIR) {
            return;
        }
        PacketEventsBridge.sendPacket(player, new WrapperPlayServerEntityEquipment(
                entityId,
                List.of(new Equipment(EquipmentSlot.MAIN_HAND, SpigotConversionUtil.fromBukkitItemStack(mainHand)))
        ));
    }

    protected void sendTeleport(Player player) {
        PacketEventsBridge.sendPacket(player, new WrapperPlayServerEntityTeleport(
                entityId,
                toPacketVector(location),
                location.getYaw(),
                location.getPitch(),
                true
        ));
    }

    protected void sendHeadRotation(Player player) {
        PacketEventsBridge.sendPacket(player, new WrapperPlayServerEntityHeadLook(entityId, location.getYaw()));
    }

    protected void sendDestroy(Player player) {
        PacketEventsBridge.sendPacket(player, new WrapperPlayServerDestroyEntities(entityId));
    }

    protected List<Player> resolveViewers(Set<UUID> viewers) {
        return viewers.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .filter(Player::isOnline)
                .collect(Collectors.toList());
    }

    protected Vector3d toPacketVector(Location location) {
        return new Vector3d(location.getX(), location.getY(), location.getZ());
    }

    /** Converts a Bukkit {@link Location} to the PacketEvents {@link com.github.retrooper.packetevents.protocol.world.Location}. */
    protected com.github.retrooper.packetevents.protocol.world.Location toPacketLocation(Location location) {
        return new com.github.retrooper.packetevents.protocol.world.Location(
                location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch()
        );
    }

    private List<EntityData<?>> copyMetadata(Player player) {
        return cloneMetadata(SpigotConversionUtil.getEntityMetadata(player));
    }

    private List<EntityData<?>> cloneMetadata(List<EntityData<?>> source) {
        List<EntityData<?>> copied = new ArrayList<>(source.size());
        for (EntityData<?> entityData : source) {
            copied.add(copyEntityData(entityData));
        }
        return copied;
    }

    private EntityData<?> copyEntityData(EntityData<?> entityData) {
        return new EntityData(entityData.getIndex(), entityData.getType(), entityData.getValue());
    }

}