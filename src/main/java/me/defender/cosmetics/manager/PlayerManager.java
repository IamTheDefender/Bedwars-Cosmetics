package me.defender.cosmetics.manager;

import me.defender.cosmetics.database.PlayerData;
import me.defender.cosmetics.database.PlayerOwnedData;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private final HashMap<UUID, PlayerData> playerDataHashMap;
    private final HashMap<UUID, PlayerOwnedData> playerOwnedDataHashMap;

    public PlayerManager() {
        playerDataHashMap = new HashMap<>();
        playerOwnedDataHashMap = new HashMap<>();

    }

    public HashMap<UUID, PlayerData> getPlayerDataHashMap() {
        return (HashMap<UUID, PlayerData>) Collections.unmodifiableMap(playerDataHashMap);
    }

    public HashMap<UUID, PlayerOwnedData> getPlayerOwnedDataHashMap() {
        return (HashMap<UUID, PlayerOwnedData>) Collections.unmodifiableMap(playerOwnedDataHashMap);
    }

    public void addPlayerData(PlayerData playerData) {
        playerDataHashMap.put(playerData.getUuid(), playerData);
    }

    public void addPlayerOwnedData(PlayerOwnedData playerOwnedData) {
        playerOwnedDataHashMap.put(playerOwnedData.getUuid(), playerOwnedData);
    }

    public PlayerData getPlayerData(UUID uuid) {
        if (!playerDataHashMap.containsKey(uuid)) {
            PlayerData playerData = new PlayerData(uuid);
            playerData.load();
            playerDataHashMap.put(uuid, playerData);
        }
        return playerDataHashMap.get(uuid);
    }

    public PlayerOwnedData getPlayerOwnedData(UUID uuid) {
        return playerOwnedDataHashMap.get(uuid);
    }
}
