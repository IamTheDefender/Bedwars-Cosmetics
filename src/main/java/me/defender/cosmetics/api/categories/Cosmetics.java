package me.defender.cosmetics.api.categories;

import me.defender.cosmetics.api.enums.RarityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Represents an abstract category of cosmetics.
 */
public abstract class Cosmetics {

    /**
     * Get the item stack associated with this bed destroy effect.
     * This item stack will be used to display the effect in the shop.
     *
     * @return item stack associated with this bed destroy effect.
     */
    public abstract ItemStack getItem();

    /**
     * Get the base64 texture of the head.
     * This is only used if the item stack is a player head.
     *
     * @return base64 texture of the head.
     */
    public abstract String base64();

    /**
     * Get the identifier of this bed destroy effect.
     * This identifier will be used to save the effect in the config.
     *
     * @return identifier of this bed destroy effect.
     */
    public abstract String getIdentifier();

    /**
     * Get the display name of this bed destroy effect.
     * This display name will be used when displaying the effect in the shop.
     *
     * @return display name of this bed destroy effect.
     */
    public abstract String getDisplayName();

    /**
     * Get the lore of this bed destroy effect.
     * This lore will be used when displaying the effect in the shop.
     *
     * @return lore of this bed destroy effect.
     */
    public abstract List<String> getLore();

    /**
     * Get the price of this bed destroy effect.
     * This price will be used when displaying the effect in the shop.
     *
     * @return price of this bed destroy effect.
     */
    public abstract int getPrice();

    /**
     * Get the rarity of this bed destroy effect.
     * This rarity will be used when displaying the effect in the shop.
     *
     * @return rarity of this bed destroy effect.
     */
    public abstract RarityType getRarity();
}
