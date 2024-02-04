package me.defender.cosmetics.data.manager;

import lombok.Getter;
import me.defender.cosmetics.data.PlayerData;
import me.defender.cosmetics.data.PlayerOwnedData;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class PlayerManager {

    private final HashMap<UUID, PlayerData> playerDataHashMap;
    private final HashMap<UUID, PlayerOwnedData> playerOwnedDataHashMap;

    public PlayerManager() {
        playerDataHashMap = new HashMap<>();
        playerOwnedDataHashMap = new HashMap<>();

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
        if(!playerOwnedDataHashMap.containsKey(uuid)){
            PlayerOwnedData playerOwnedData = new PlayerOwnedData(uuid);
            playerOwnedData.load();
            playerOwnedDataHashMap.put(uuid, playerOwnedData);
        }
        return playerOwnedDataHashMap.get(uuid);
    }
}
