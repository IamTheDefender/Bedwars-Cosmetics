package xyz.iamthedefender.cosmetics.api.cosmetics;

import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public enum FieldsType {
    FILE(String.class),
    ITEM_STACK(ItemStack.class),
    LORE(List.class),
    NAME(String.class),
    PITCH(Float.class),
    PRICE(Double.class),
    RARITY(RarityType.class),
    SOUND(Sound.class),
    URL(String.class),
    VOLUME(Float.class),

    ENTITY_TYPE(EntityType.class, "entity-type"),
    SKIN_VALUE(String.class, "skin-value"),
    SKIN_SIGN(String.class, "skin-sign"),
    MIRROR(Boolean.class, "mirror");

    private final Class<?> type;
    private final @Nullable String path;

    FieldsType(Class<?> type, @Nullable String path) {
        this.type = type;
        this.path = path;
    }

    FieldsType(Class<?> type) {
        this(type, null);
    }

    public Class<?> type() {
        return this.type;
    }

    public @Nullable String path() {
        return this.path;
    }

}
