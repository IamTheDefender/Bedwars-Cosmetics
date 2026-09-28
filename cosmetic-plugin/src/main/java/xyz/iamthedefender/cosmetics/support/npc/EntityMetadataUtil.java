package xyz.iamthedefender.cosmetics.support.npc;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataType;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class EntityMetadataUtil {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<EntityData<?>> getEntityMetadata(Player player) {
        List<EntityData<?>> result = new ArrayList<>();
        try {
            Object nmsEntity = player.getClass().getMethod("getHandle").invoke(player);

            Object dataWatcher = resolveMethodCall(nmsEntity, "getDataWatcher", "getEntityData", "al", "ah");
            if (dataWatcher == null) return result;

            Object rawItems = resolveMethodCall(dataWatcher, "c", "getNonDefaultValues", "getAll", "d");
            if (rawItems == null) rawItems = resolveMethodCall(dataWatcher, "b", "getAllWatched");

            if (rawItems instanceof Iterable) {
                for (Object item : (Iterable<?>) rawItems) {
                    if (item == null) continue;
                    try {
                        int index = resolveIntMethodCall(item, "a", "id", "getId");
                        if (index == 0) continue;

                        Object value = resolveMethodCall(item, "b", "value", "getValue");
                        if (value == null) continue;

                        EntityDataType<?> type = resolveType(value);
                        if (type != null) {
                            result.add(new EntityData(index, type, value));
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ignored) {
        }

        return result;
    }

    private static Object resolveMethodCall(Object obj, String... methodNames) {
        for (String name : methodNames) {
            try {
                return obj.getClass().getMethod(name).invoke(obj);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private static int resolveIntMethodCall(Object obj, String... methodNames) {
        for (String name : methodNames) {
            try {
                return (int) obj.getClass().getMethod(name).invoke(obj);
            } catch (Exception ignored) {
            }
        }
        return -1;
    }

    private static EntityDataType<?> resolveType(Object value) {
        if (value instanceof Byte) return EntityDataTypes.BYTE;
        if (value instanceof Short) return EntityDataTypes.SHORT;
        if (value instanceof Integer) return EntityDataTypes.INT;
        if (value instanceof Float) return EntityDataTypes.FLOAT;
        if (value instanceof String) return EntityDataTypes.STRING;
        return null;
    }
}