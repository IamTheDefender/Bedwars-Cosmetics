package xyz.iamthedefender.cosmetics.api.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Messages {

    public static final Entry MAIN_MENU_GUI_TITLE = new Entry(
            "main_menu.title",
            "Cosmetics"
    );

    public static final Entry SPRAY_CLICK = of(
            "spray_click",
            "&eClick!"
    );

    public static Entry of(String path, String defaultValue) {
        return new Entry(path, defaultValue);
    }

    public static class Entry {
        private final String path;
        private final String defaultValue;

        public Entry(String path, String defaultValue) {
            this.path = "cosmetics." + path;
            this.defaultValue = defaultValue;
        }

        public String path() {
            return path;
        }

        public String value(Player player) {
            String found = Utility.getMSGLang(player, path);

            if (found == null) {
                found = defaultValue;
                Utility.saveIfNotExistsLang(path, defaultValue);
            }

            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                try {
                    PlaceholderAPI.setPlaceholders(player, found);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    Utility.getPlugin().getLogger().warning("Failed to set placeholders.");
                }
            }

            return ColorUtil.translate(found);
        }
    }
}
