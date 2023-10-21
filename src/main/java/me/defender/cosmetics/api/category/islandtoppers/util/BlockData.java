package me.defender.cosmetics.api.category.islandtoppers.util;

import org.bukkit.Material;

public class BlockData {

    private final Material material;
    private final byte data;

    public BlockData(Material material, byte data) {
        this.material = material;
        this.data = data;
    }

    public Material getMaterial() {
        return material;
    }

    public byte getData() {
        return data;
    }
}