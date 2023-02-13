package me.defender.cosmetics.api.utils;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.HCore;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkullUtil {
    private static final Map<String, ItemStack> skullCache = new HashMap<>();

    public static ItemStack makeTextureSkull(String base64Texture) {
        return HCore.skullBuilder(base64Texture).build();
    }
}
