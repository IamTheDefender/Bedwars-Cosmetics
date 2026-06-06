package xyz.iamthedefender.cosmetics.support.npc;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.support.npc.impl.PacketEntityNpc;
import xyz.iamthedefender.cosmetics.support.npc.impl.PacketPlayerNpc;
import xyz.iamthedefender.cosmetics.support.protocol.PacketEventsBridge;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PacketNpcManager {

    private final AtomicInteger entityIds = new AtomicInteger(300_000);
    private final Map<Integer, PacketNpc> npcs = new ConcurrentHashMap<>();
    private final SkinFovTracker skinFovTracker;

    public PacketNpcManager(Plugin plugin) {
        skinFovTracker = new SkinFovTracker(plugin);

        PacketEventsBridge.registerListener(new PacketListenerAbstract() {
            @Override
            public void onPacketReceive(com.github.retrooper.packetevents.event.PacketReceiveEvent event) {
                if (event.getPacketType() != PacketType.Play.Client.INTERACT_ENTITY) {
                    return;
                }

                WrapperPlayClientInteractEntity wrapper = new WrapperPlayClientInteractEntity(event);
                PacketNpc npc = npcs.get(wrapper.getEntityId());
                if (npc == null || npc.getInteractionHandler() == null) {
                    return;
                }

                event.setCancelled(true);
                Run.sync(() -> npc.getInteractionHandler().accept(event.getPlayer()));
            }
        });
    }

    public PacketNpc createClone(Player metadataSource, Player skinSource, Location location) {
        int entityId = entityIds.incrementAndGet();
        UUID uuid = UUID.randomUUID();
        String profileName = createProfileName(entityId);
        UserProfile profile = new UserProfile(uuid, profileName, extractTextureProperties(skinSource));
        PacketPlayerNpc npc = new PacketPlayerNpc(entityId, profile, location, copyMetadata(metadataSource));
        npcs.put(entityId, npc);
        skinFovTracker.track(npc);
        return npc;
    }

    public PacketPlayerNpc createNamedClone(Player metadataSource, Player skinSource, String profileName, Location location) {
        int entityId = entityIds.incrementAndGet();
        UUID uuid = UUID.randomUUID();
        UserProfile profile = new UserProfile(uuid, profileName, extractTextureProperties(skinSource));

        // NOTE: for testing purposes the name in tab/tag is disabled.. Don't think about it too much
        PacketPlayerNpc npc = new PacketPlayerNpc(entityId, profile, location, copyMetadata(metadataSource), false);
        npcs.put(entityId, npc);
        skinFovTracker.track(npc);
        return npc;
    }

    public PacketNpc createSkinned(Player metadataSource, String textureValue, String textureSignature, Location location) {
        int entityId = entityIds.incrementAndGet();
        String profileName = createProfileName(entityId);
        List<TextureProperty> properties = List.of(new TextureProperty("textures", textureValue, textureSignature));
        UserProfile profile = new UserProfile(UUID.randomUUID(), profileName, properties);
        PacketPlayerNpc npc = new PacketPlayerNpc(entityId, profile, location, copyMetadata(metadataSource));
        npcs.put(entityId, npc);
        skinFovTracker.track(npc);
        return npc;
    }

    public PacketPlayerNpc createNamedSkinned(Player metadataSource, String profileName, String textureValue, String textureSignature, Location location) {
        int entityId = entityIds.incrementAndGet();
        List<TextureProperty> properties = List.of(new TextureProperty("textures", textureValue, textureSignature));
        UserProfile profile = new UserProfile(UUID.randomUUID(), profileName, properties);
        PacketPlayerNpc npc = new PacketPlayerNpc(entityId, profile, location, copyMetadata(metadataSource), false);
        npcs.put(entityId, npc);
        skinFovTracker.track(npc);
        return npc;
    }

    public PacketNpc createEntity(org.bukkit.entity.EntityType entityType, Location location) {
        int entityId = entityIds.incrementAndGet();
        PacketEntityNpc npc = new PacketEntityNpc(
                entityId,
                UUID.randomUUID(),
                entityType,
                location,
                new ArrayList<>()
        );
        npcs.put(entityId, npc);
        return npc;
    }

    public void destroy(PacketNpc npc) {
        if (npc == null) {
            return;
        }

        if (npc instanceof PacketPlayerNpc) {
            skinFovTracker.untrack((PacketPlayerNpc) npc);
        }

        npc.destroy();
        npcs.remove(npc.getEntityId());
    }

    public boolean destroyed(PacketNpc npc) {
        return !npcs.containsKey(npc.getEntityId());
    }

    public void shutdown() {
        skinFovTracker.cancel();
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

    @SuppressWarnings({"rawtypes", "unchecked"})
    private EntityData<?> copyEntityData(EntityData<?> entityData) {
        return new EntityData(entityData.getIndex(), entityData.getType(), entityData.getValue());
    }

    private List<TextureProperty> extractTextureProperties(Player player) {
        try {
            Object profile = resolveProfile(player);
            if (profile == null) {
                return List.of();
            }

            Object propertyMap = profile.getClass().getMethod("getProperties").invoke(profile);
            Object textures = propertyMap.getClass().getMethod("get", Object.class).invoke(propertyMap, "textures");
            if (!(textures instanceof Iterable)) {
                return List.of();
            }

            List<TextureProperty> properties = new ArrayList<>();
            for (Object property : (Iterable<?>) textures) {
                Method getName = property.getClass().getMethod("getName");
                Method getValue = property.getClass().getMethod("getValue");
                Method getSignature = property.getClass().getMethod("getSignature");
                Object signature = getSignature.invoke(property);
                properties.add(new TextureProperty(
                        String.valueOf(getName.invoke(property)),
                        String.valueOf(getValue.invoke(property)),
                        signature == null ? null : String.valueOf(signature)
                ));
            }
            return properties;
        } catch (Exception ignored) {
            return List.of();
        }
    }

    private Object resolveProfile(Player player) throws Exception {
        try {
            return player.getClass().getMethod("getProfile").invoke(player);
        } catch (NoSuchMethodException ignored) {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            return handle.getClass().getMethod("getProfile").invoke(handle);
        }
    }

    private String createProfileName(int entityId) {
        return "npc-" + Integer.toHexString(entityId);
    }
}
