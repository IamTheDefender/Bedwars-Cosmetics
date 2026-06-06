package xyz.iamthedefender.cosmetics.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import xyz.iamthedefender.cosmetics.api.CosmeticsAPI;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticRegistry;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.util.Utility;

public abstract class AbstractListener implements Listener {

    public CosmeticsAPI getAPI() {
        return Utility.getApi();
    }

    public <T extends Cosmetics> T getSelectedCosmetic(Player player, CosmeticType<T> type) {
        String selected = getAPI().getSelectedCosmetic(player, type);

        return selected != null ? CosmeticRegistry.getById(type, selected) : null;
    }

}
