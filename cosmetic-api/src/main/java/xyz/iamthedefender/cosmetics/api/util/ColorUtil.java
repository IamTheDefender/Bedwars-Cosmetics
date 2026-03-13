package xyz.iamthedefender.cosmetics.api.util;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ColorUtil {

    public static String translate(@Nullable String string) {
        if (string == null) return null;

        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
