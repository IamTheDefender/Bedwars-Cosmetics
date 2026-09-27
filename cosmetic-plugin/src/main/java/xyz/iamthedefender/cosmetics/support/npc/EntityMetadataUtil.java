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
            Object dataWatcher = nmsEntity.getClass().getMethod("getDataWatcher").invoke(nmsEntity);
            Object rawItems = dataWatcher.getClass().getMethod("c").invoke(dataWatcher);

            if (rawItems instanceof Iterable) {
                for (Object item : (Iterable<?>) rawItems) {
                    if (item == null) continue;
                    try {
                        int index = (int) item.getClass().getMethod("a").invoke(item);
                        Object value = item.getClass().getMethod("b").invoke(item);
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

    private static EntityDataType<?> resolveType(Object value) {
        if (value instanceof Byte) return EntityDataTypes.BYTE;
        if (value instanceof Short) return EntityDataTypes.SHORT;
        if (value instanceof Integer) return EntityDataTypes.INT;
        if (value instanceof Float) return EntityDataTypes.FLOAT;
        if (value instanceof String) return EntityDataTypes.STRING;
        return null;
    }
}