package xyz.iamthedefender.cosmetics.api.cosmetics;

import org.bukkit.ChatColor;
import xyz.iamthedefender.cosmetics.api.util.Messages;
import xyz.iamthedefender.cosmetics.api.util.Utility;

import java.util.Objects;

public enum RarityType {
    LEGENDARY(ChatColor.GOLD),
    EPIC(ChatColor.DARK_PURPLE),
    RARE(ChatColor.AQUA),
    COMMON(ChatColor.GREEN),
    RANDOM(ChatColor.GREEN),
    NONE(ChatColor.GREEN);

    private final ChatColor color;
    RarityType(ChatColor color) {
        this.color = color;
    }

    public ChatColor getChatColor() {
        String storedColor = Messages.of("rarity-color." + name(), color).value(null);

        if (storedColor != null) {
            try {
                return Objects.requireNonNull(ChatColor.getByChar(storedColor.replace(ChatColor.COLOR_CHAR + "", "")));
            } catch (NullPointerException e) {
                Utility.getPlugin().getLogger().warning("Failed to parse chat color " + storedColor + " for rarity: " + name());
                return color;
            }
        }

        return color;
    }


}
