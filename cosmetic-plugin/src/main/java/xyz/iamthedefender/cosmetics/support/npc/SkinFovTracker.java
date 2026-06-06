package xyz.iamthedefender.cosmetics.support.npc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.iamthedefender.cosmetics.support.npc.impl.PacketPlayerNpc;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SkinFovTracker extends BukkitRunnable {

    private static final double FIELD_OF_VIEW_DEGREES = 180;
    private static final double DEFAULT_VIEW_DISTANCE = 80;

    private final Map<Integer, PacketPlayerNpc> trackedNpcs = new ConcurrentHashMap<>();

    public SkinFovTracker(Plugin plugin) {
        runTaskTimer(plugin, 7L, 7L);
    }

    public void track(PacketPlayerNpc npc) {
        trackedNpcs.put(npc.getEntityId(), npc);
    }

    public void untrack(PacketPlayerNpc npc) {
        trackedNpcs.remove(npc.getEntityId());
    }

    @Override
    public void run() {
        if (trackedNpcs.isEmpty()) {
            return;
        }

        for (PacketPlayerNpc npc : trackedNpcs.values()) {
            Location npcLoc = npc.getLocation();
            if (npcLoc == null || npcLoc.getWorld() == null) {
                continue;
            }

            for (UUID viewerId : npc.getViewers()) {
                Player player = Bukkit.getPlayer(viewerId);
                if (player == null || !player.isOnline()) {
                    continue;
                }

                if (npc.hasSkinConfirmed(viewerId)) {
                    continue;
                }

                if (isInFov(player, npcLoc)) {
                    npc.confirmAndRefreshSkin(player);
                }
            }
        }
    }

    private boolean isInFov(Player player, Location npcLoc) {
        Location playerLoc = player.getLocation();

        if (!playerLoc.getWorld().equals(npcLoc.getWorld())) {
            return false;
        }

        if (playerLoc.distance(npcLoc) > DEFAULT_VIEW_DISTANCE) {
            return false;
        }

        double deltaX = npcLoc.getX() - playerLoc.getX();
        double deltaZ = npcLoc.getZ() - playerLoc.getZ();

        double npcAngleDeg = Math.toDegrees(Math.atan2(deltaX, deltaZ));
        float playerYaw = normalizeYaw(playerLoc.getYaw());
        float npcYaw = (float) normalizeAngle(npcAngleDeg);

        float diff = Math.abs(playerYaw - npcYaw);
        if (diff > 180.0f) {
            diff = 360.0f - diff;
        }

        return diff <= FIELD_OF_VIEW_DEGREES;
    }

    private float normalizeYaw(float yaw) {
        yaw = yaw % 360.0f;
        if (yaw < 0) yaw += 360.0f;
        return yaw;
    }

    private double normalizeAngle(double angle) {
        angle = angle % 360.0;
        if (angle < 0) angle += 360.0;
        return angle;
    }
}