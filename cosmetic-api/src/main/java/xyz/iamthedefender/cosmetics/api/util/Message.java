package xyz.iamthedefender.cosmetics.api.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Message {

    private final String path;
    private final Object defaultValue;

    public Message(String path, Object defaultValue) {
        this.path = path.startsWith("cosmetics.") ? path : "cosmetics." + path;
        this.defaultValue = defaultValue;
    }

    public String path() {
        return path;
    }

    public Object defaultValue() {
        return defaultValue;
    }

    public void saveIfMissing() {
        Utility.saveIfNotExistsLang(path, defaultValue);
    }

    public String value(Player player) {
        saveIfMissing();
        String found;

        try {
            found = Utility.getMSGLang(player, path);
        } catch (NullPointerException exception) {
            found = null;
        }

        if (found == null) {
            found = String.valueOf(defaultValue);
        }

        if (player != null && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            try {
                found = PlaceholderAPI.setPlaceholders(player, found);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                Utility.getPlugin().getLogger().warning("Failed to set placeholders.");
            }
        }

        return ColorUtil.translate(found);
    }

    public List<String> list(Player player) {
        saveIfMissing();
        List<String> found = Utility.getListLang(player, path);
        if (found == null || found.isEmpty()) {
            if (defaultValue instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<String> fallback = (List<String>) defaultValue;
                return fallback.stream().map(ColorUtil::translate).collect(Collectors.toList());
            }
            return List.of();
        }
        return found;
    }
}
