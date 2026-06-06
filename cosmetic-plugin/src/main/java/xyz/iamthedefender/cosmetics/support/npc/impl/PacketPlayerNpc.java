package xyz.iamthedefender.cosmetics.support.npc.impl;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.npc.NPC;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityAnimation;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfo;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTeams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.support.npc.PacketNpc;
import xyz.iamthedefender.cosmetics.support.protocol.PacketEventsBridge;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PacketPlayerNpc extends PacketNpc {

    private final NPC nativeNpc;
    private final Set<UUID> confirmedSkinViewers = ConcurrentHashMap.newKeySet();
    private final boolean keepTabListed;

    public PacketPlayerNpc(int entityId, UserProfile profile, Location location, List<EntityData<?>> metadata) {
        this(entityId, profile, location, metadata, false);
    }

    public PacketPlayerNpc(int entityId, UserProfile profile, Location location, List<EntityData<?>> metadata, boolean keepTabListed) {
        super(entityId, profile.getUUID(), location, metadata);
        this.keepTabListed = keepTabListed;

        nativeNpc = new NPC(
                profile,
                entityId,
                GameMode.SURVIVAL,
                null,
                NamedTextColor.WHITE,
                Component.empty(),
                null
        );
        nativeNpc.setLocation(toPacketLocation(location));
    }

    @Override
    public void spawn(Player player) {
        if (player == null || !player.isOnline()
                || !player.getWorld().equals(getLocation().getWorld())
                || !getViewers().add(player.getUniqueId())) {
            return;
        }

        nativeNpc.spawn(PacketEvents.getAPI().getPlayerManager().getChannel(player));
        sendSkinSequence(player);

        sendMetadata(player);
        sendEquipment(player);
        sendHeadRotation(player);

        PacketEventsBridge.sendPacket(player, buildHideNameTagPacket());
    }

    public void spawnWithVisibleTabName(Player player) {
        if (player == null || !player.isOnline()
                || !player.getWorld().equals(getLocation().getWorld())
                || !getViewers().add(player.getUniqueId())) {
            return;
        }

        nativeNpc.spawn(PacketEvents.getAPI().getPlayerManager().getChannel(player));
        sendSkinSequence(player);

      //  sendMetadata(player);
        sendEquipment(player);
        sendHeadRotation(player);

        PacketEventsBridge.sendPacket(player, buildHideNameTagPacket());
    }

    public void confirmAndRefreshSkin(Player player) {
        confirmedSkinViewers.add(player.getUniqueId());
        refreshSkin(player);
    }

    public void refreshSkin(Player player) {
        if (keepTabListed) {
            return;
        }

        PacketEventsBridge.sendPacket(player, buildShowTabListPacket());

        Run.delayed(() -> {
            if (!getViewers().contains(player.getUniqueId()) || !player.isOnline()) {
                return;
            }
            PacketEventsBridge.sendPacket(player, buildHideTabListPacket());
        }, 20L); //10L is too short, it's a rush condition, sometimes it works, sometimes it does not..
    }

    public boolean hasSkinConfirmed(UUID playerId) {
        return confirmedSkinViewers.contains(playerId);
    }

    @Override
    public void despawn(Player player) {
        if (player == null || !getViewers().remove(player.getUniqueId())) {
            return;
        }

        confirmedSkinViewers.remove(player.getUniqueId());
        PacketEventsBridge.sendPacket(player, buildHideNameTagPacket());
        PacketEventsBridge.sendPacket(player, buildHideTabListPacket());
        nativeNpc.despawn(PacketEvents.getAPI().getPlayerManager().getChannel(player));

    }

    @Override
    public void teleport(Location location) {
        nativeNpc.setLocation(toPacketLocation(location));
        super.teleport(location);
    }

    private void sendSkinSequence(Player player) {
        Object channel = PacketEvents.getAPI().getPlayerManager().getChannel(player);

        nativeNpc.spawn(channel);

        Run.delayed(() -> {
            if (!getViewers().contains(player.getUniqueId()) || !player.isOnline()) {
                return;
            }
            if (keepTabListed) {
                return;
            }
            PacketEventsBridge.sendPacket(player, buildHideTabListPacket());
        }, 15L);
    }

    public void swingArm(Player player) {
        WrapperPlayServerEntityAnimation packet = new WrapperPlayServerEntityAnimation(
                getEntityId(),
                WrapperPlayServerEntityAnimation.EntityAnimationType.SWING_MAIN_ARM
        );

        PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
    }


    private PacketWrapper<?> buildShowTabListPacket() {
        if (PacketEvents.getAPI()
                .getServerManager().getVersion().isOlderThan(ServerVersion.V_1_19_3)) {
            WrapperPlayServerPlayerInfo.PlayerData playerData = new WrapperPlayServerPlayerInfo.PlayerData(
                    nativeNpc.getTabName(),
                    nativeNpc.getProfile(),
                    nativeNpc.getGameMode(),
                    nativeNpc.getDisplayPing()
            );
            return new WrapperPlayServerPlayerInfo(
                    WrapperPlayServerPlayerInfo.Action.ADD_PLAYER, List.of(playerData)
            );
        }

        EnumSet<WrapperPlayServerPlayerInfoUpdate.Action> actions = EnumSet.of(
                WrapperPlayServerPlayerInfoUpdate.Action.ADD_PLAYER,
                WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_LISTED,
                WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_GAME_MODE,
                WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_LATENCY,
                WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_DISPLAY_NAME
        );

        WrapperPlayServerPlayerInfoUpdate.PlayerInfo info =
                new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(
                        nativeNpc.getProfile(),
                        true,
                        nativeNpc.getDisplayPing(),
                        nativeNpc.getGameMode(),
                        nativeNpc.getTabName(),
                        null
                );

        return new WrapperPlayServerPlayerInfoUpdate(actions, List.of(info));
    }

    private PacketWrapper<?> buildHideTabListPacket() {
        if (PacketEvents.getAPI()
                .getServerManager().getVersion().isOlderThan(ServerVersion.V_1_19_3)) {
            WrapperPlayServerPlayerInfo.PlayerData playerData = new WrapperPlayServerPlayerInfo.PlayerData(
                    nativeNpc.getTabName(),
                    nativeNpc.getProfile(),
                    nativeNpc.getGameMode(),
                    nativeNpc.getDisplayPing()
            );

            return new WrapperPlayServerPlayerInfo(WrapperPlayServerPlayerInfo.Action.REMOVE_PLAYER, List.of(playerData));
        }

        EnumSet<WrapperPlayServerPlayerInfoUpdate.Action> actions = EnumSet.of(
                WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_LISTED
        );

        WrapperPlayServerPlayerInfoUpdate.PlayerInfo info =
                new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(
                        nativeNpc.getProfile(),
                        false,
                        0,
                        GameMode.SURVIVAL,
                        Component.text("npc"),
                        null
                );

        return new WrapperPlayServerPlayerInfoUpdate(actions, List.of(info));
    }

    private WrapperPlayServerTeams buildHideNameTagPacket() {
        WrapperPlayServerTeams.ScoreBoardTeamInfo teamInfo = new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                Component.empty(),
                Component.empty(),
                Component.empty(),
                WrapperPlayServerTeams.NameTagVisibility.NEVER,
                WrapperPlayServerTeams.CollisionRule.NEVER,
                NamedTextColor.WHITE,
                WrapperPlayServerTeams.OptionData.NONE
        );
        return new WrapperPlayServerTeams(
                nativeNpc.getTeamName(),
                WrapperPlayServerTeams.TeamMode.UPDATE,
                Optional.of(teamInfo),
                List.of(nativeNpc.getProfile().getName())
        );
    }
}
