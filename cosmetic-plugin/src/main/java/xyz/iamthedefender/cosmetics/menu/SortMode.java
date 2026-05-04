package xyz.iamthedefender.cosmetics.menu;

import lombok.Getter;

@Getter
public enum SortMode {
    RARITY_LOW_HIGH("Lowest rarity first"),
    RARITY_HIGH_LOW("Highest rarity first"),
    A_TO_Z("A to Z"),
    Z_TO_A("Z to A");

    private final String display;

    SortMode(String display) {
        this.display = display;
    }

    public SortMode next() {
        SortMode[] values = values();
        return values[(ordinal() + 1) % values.length];
    }
}
