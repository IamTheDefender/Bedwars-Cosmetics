package xyz.iamthedefender.cosmetics.support.npc.impl;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnLivingEntity;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.support.npc.PacketNpc;
import xyz.iamthedefender.cosmetics.support.npc.PacketNpcManager;
import xyz.iamthedefender.cosmetics.support.protocol.PacketEventsBridge;

import java.util.List;
import java.util.UUID;

public class PacketEntityNpc extends PacketNpc {

    private final org.bukkit.entity.EntityType bukkitEntityType;
    private final com.github.retrooper.packetevents.protocol.entity.type.EntityType entityType;
    private final boolean livingEntity;

    public PacketEntityNpc(int entityId, UUID entityUuid, org.bukkit.entity.EntityType bukkitEntityType,
                           Location location, List<EntityData<?>> metadata) {
        super(entityId, entityUuid, location, metadata);
        this.bukkitEntityType = bukkitEntityType;
        this.entityType = SpigotConversionUtil.fromBukkitEntityType(bukkitEntityType);
        this.livingEntity = bukkitEntityType.isAlive();
    }

    @Override
    public void spawn(Player player) {
        if (player == null || !player.isOnline()
                || !player.getWorld().equals(getLocation().getWorld())
                || !getViewers().add(player.getUniqueId())) {
            return;
        }

        if (livingEntity) {
            PacketEventsBridge.sendPacket(player, new WrapperPlayServerSpawnLivingEntity(
                    getEntityId(),
                    getEntityUuid(),
                    entityType,
                    toPacketVector(getLocation()),
                    getLocation().getYaw(),
                    getLocation().getPitch(),
                    getLocation().getPitch(),
                    Vector3d.zero(),
                    getMetadata()
            ));
        } else {
            PacketEventsBridge.sendPacket(player, new WrapperPlayServerSpawnEntity(
                    getEntityId(),
                    getEntityUuid(),
                    entityType,
                    SpigotConversionUtil.fromBukkitLocation(getLocation()),
                    getLocation().getYaw(),
                    0,
                    Vector3d.zero()
            ));
            sendMetadata(player);
        }

        sendEquipment(player);
        sendHeadRotation(player);
    }

    @Override
    public void despawn(Player player) {
        if (player == null || !getViewers().remove(player.getUniqueId())) {
            return;
        }
        sendDestroy(player);
    }
}
