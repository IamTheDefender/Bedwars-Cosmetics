package xyz.iamthedefender.cosmetics.support.protocol;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerCamera;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerOpenSignEditor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class PacketEventsBridge {

    private PacketEventsBridge() {
    }

    public static void registerListener(PacketListenerCommon listener) {
        PacketEvents.getAPI().getEventManager().registerListener(listener);
    }

    public static void unregisterListener(PacketListenerCommon listener) {
        PacketEvents.getAPI().getEventManager().unregisterListener(listener);
    }

    public static void sendPacket(Player player, PacketWrapper<?> wrapper) {
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, wrapper);
    }

    public static void sendCamera(Player player, int entityId) {
        sendPacket(player, new WrapperPlayServerCamera(entityId));
    }

    public static void destroyEntities(Player player, int... entityIds) {
        sendPacket(player, new WrapperPlayServerDestroyEntities(entityIds));
    }

    public static void openSignEditor(Player player, Location location) {
        sendPacket(player, new WrapperPlayServerOpenSignEditor(
                new Vector3i(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                true
        ));
    }
}
