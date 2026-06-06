package xyz.iamthedefender.cosmetics.api.cosmetics;

import java.util.*;

@SuppressWarnings("unchecked,unused")
public final class CosmeticRegistry {

    private static <T extends Cosmetics> List<T> items(CosmeticType<T> type) {
        return type == null ? List.of() : type.getItemsList();
    }

    public static <T extends Cosmetics> boolean register(CosmeticType<T> type, T cosmetics) {
        if (type == null || cosmetics == null) {
            return false;
        }
        return items(type).add(cosmetics);
    }

    public static boolean categoryExists(CosmeticType<?> type) {
        return type != null && CosmeticType.values().contains(type);
    }

    public static boolean cosmeticExist(CosmeticType<?> type, String id) {
        if (type == null || id == null) {
            return false;
        }
        return type.getItemsList().stream()
                .filter(Cosmetics.class::isInstance)
                .map(Cosmetics.class::cast)
                .anyMatch(cosmetic -> id.equalsIgnoreCase(cosmetic.getIdentifier()));
    }

    public static <T extends Cosmetics> boolean unregister(CosmeticType<T> type, T cosmetics) {
        if (type == null || cosmetics == null) {
            return false;
        }
        return items(type).remove(cosmetics);
    }

    public static boolean unregister(CosmeticType<?> type) {
        if (type == null) {
            return false;
        }
        List<?> cosmetics = type.getItemsList();
        boolean hadEntries = !cosmetics.isEmpty();
        cosmetics.clear();
        return hadEntries;
    }


    public static <T extends Cosmetics> List<T> getByCategory(CosmeticType<T> type) {
        return new ArrayList<>(items(type));
    }

    public static <T extends Cosmetics> T getById(CosmeticType<T> type, String id) {
        if (type == null || id == null) {
            return null;
        }
        return items(type).stream()
                .filter(cosmetic -> id.equalsIgnoreCase(cosmetic.getIdentifier()))
                .findFirst()
                .orElse(null);
    }

    public static <T extends Cosmetics> Optional<T> getById(CosmeticType<?> type, String id, Class<T> clazz) {
        if (type == null || id == null || clazz == null) {
            return Optional.empty();
        }
        return getByCategory((CosmeticType<? extends Cosmetics>) type).stream()
                .filter(cosmetic -> id.equalsIgnoreCase(cosmetic.getIdentifier()))
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst();
    }


    public static <T extends Cosmetics> Optional<T> getById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return (Optional<T>) getAll().stream()
                .filter(cosmetic -> id.equalsIgnoreCase(cosmetic.getIdentifier()))
                .findFirst();
    }

    public static Set<Cosmetics> getAll() {
        Set<Cosmetics> cosmetics = new LinkedHashSet<>();
        for (CosmeticType<?> type : CosmeticType.values()) {
            cosmetics.addAll((List<? extends Cosmetics>) type.getItemsList());
        }
        return cosmetics;
    }
}
