package me.defender.cosmetics.api;

import me.defender.cosmetics.api.cosmetics.CosmeticsType;
import me.defender.cosmetics.api.database.IDatabase;
import me.defender.cosmetics.api.handler.IHandler;
import org.bukkit.entity.Player;

public interface CosmeticsAPI {
    IDatabase getDatabase();
    String getSelectedCosmetic(Player player, CosmeticsType type);
    void setSelectedCosmetic(Player player, CosmeticsType type, String value);
    boolean isProxy();
    boolean isMySQL();

    IHandler getHandler();
}
