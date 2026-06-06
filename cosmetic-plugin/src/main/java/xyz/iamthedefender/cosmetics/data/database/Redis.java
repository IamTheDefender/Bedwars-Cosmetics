package xyz.iamthedefender.cosmetics.data.database;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import xyz.iamthedefender.cosmetics.api.database.DatabaseType;
import xyz.iamthedefender.cosmetics.api.database.IDatabase;
import xyz.iamthedefender.cosmetics.api.database.PlayerCosmeticsData;
import xyz.iamthedefender.cosmetics.api.database.PlayerOwnedCosmeticsData;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Redis implements IDatabase {

    private JedisPool pool;

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.REDIS;
    }

    @Override
    public String getDisplayName() {
        return "Redis";
    }

    @Override
    public void connect() {
        String host = StartupUtils.getString("database.redis.host", null, "localhost");
        int port = StartupUtils.getInt("database.redis.port", null, 6379);
        String password = StartupUtils.getString("database.redis.password", null, "");
        int database = StartupUtils.getInt("database.redis.database", null, 0);

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(StartupUtils.getInt("database.redis.max-pool-size", null, 50));
        pool = password == null || password.isEmpty()
                ? new JedisPool(config, host, port, 2000, null, database)
                : new JedisPool(config, host, port, 2000, password, database);
    }

    @Override
    public void createTable() {
        // Redis does not need schema creation.
    }

    @Override
    public void validateConnection() {
        try (Jedis jedis = pool.getResource()) {
            jedis.ping();
        } catch (Exception exception) {
            connect();
        }
    }

    @Override
    public void close() {
        if (pool != null) {
            pool.close();
        }
    }

    @Override
    public PlayerCosmeticsData loadPlayerData(UUID uuid) {
        try (Jedis jedis = pool.getResource()) {
            Map<String, String> map = jedis.hgetAll(playerKey(uuid));
            if (map == null || map.isEmpty()) {
                return null;
            }
            return new PlayerCosmeticsData(
                    map.get("wood_skin"),
                    map.get("bed_destroy"),
                    map.get("victory_dance"),
                    map.get("shopkeeper_skin"),
                    map.get("glyph"),
                    map.get("spray"),
                    map.get("projectile_trail"),
                    map.get("kill_message"),
                    map.get("final_kill_effect"),
                    map.get("island_topper"),
                    map.get("death_cry")
            );
        }
    }

    @Override
    public void createPlayerData(UUID uuid, PlayerCosmeticsData data) {
        savePlayerData(uuid, data);
    }

    @Override
    public void savePlayerData(UUID uuid, PlayerCosmeticsData data) {
        try (Jedis jedis = pool.getResource()) {
            jedis.hset(playerKey(uuid), playerMap(data));
        }
    }

    @Override
    public PlayerOwnedCosmeticsData loadOwnedData(UUID uuid) {
        try (Jedis jedis = pool.getResource()) {
            Map<String, String> map = jedis.hgetAll(ownedKey(uuid));
            if (map == null || map.isEmpty()) {
                return null;
            }
            return new PlayerOwnedCosmeticsData(
                    parseInt(map.get("bed_destroy")),
                    parseInt(map.get("death_cry")),
                    parseInt(map.get("final_kill_effect")),
                    parseInt(map.get("glyph")),
                    parseInt(map.get("island_topper")),
                    parseInt(map.get("kill_message")),
                    parseInt(map.get("projectile_trail")),
                    parseInt(map.get("shopkeeper_skin")),
                    parseInt(map.get("spray")),
                    parseInt(map.get("victory_dance")),
                    parseInt(map.get("wood_skin"))
            );
        }
    }

    @Override
    public void createOwnedData(UUID uuid, PlayerOwnedCosmeticsData data) {
        saveOwnedData(uuid, data);
    }

    @Override
    public void saveOwnedData(UUID uuid, PlayerOwnedCosmeticsData data) {
        try (Jedis jedis = pool.getResource()) {
            jedis.hset(ownedKey(uuid), ownedMap(data));
        }
    }

    private String playerKey(UUID uuid) {
        return "bwcosmetics:player:" + uuid;
    }

    private String ownedKey(UUID uuid) {
        return "bwcosmetics:owned:" + uuid;
    }

    private Map<String, String> playerMap(PlayerCosmeticsData data) {
        Map<String, String> map = new HashMap<>();
        map.put("bed_destroy", stringOrEmpty(data.getBedDestroy()));
        map.put("wood_skin", stringOrEmpty(data.getWoodSkin()));
        map.put("victory_dance", stringOrEmpty(data.getVictoryDance()));
        map.put("shopkeeper_skin", stringOrEmpty(data.getShopkeeperSkin()));
        map.put("glyph", stringOrEmpty(data.getGlyph()));
        map.put("spray", stringOrEmpty(data.getSpray()));
        map.put("projectile_trail", stringOrEmpty(data.getProjectileTrail()));
        map.put("kill_message", stringOrEmpty(data.getKillMessage()));
        map.put("final_kill_effect", stringOrEmpty(data.getFinalKillEffect()));
        map.put("island_topper", stringOrEmpty(data.getIslandTopper()));
        map.put("death_cry", stringOrEmpty(data.getDeathCry()));
        return map;
    }

    private Map<String, String> ownedMap(PlayerOwnedCosmeticsData data) {
        Map<String, String> map = new HashMap<>();
        map.put("bed_destroy", Integer.toString(data.getBedDestroy()));
        map.put("death_cry", Integer.toString(data.getDeathCry()));
        map.put("final_kill_effect", Integer.toString(data.getFinalKillEffect()));
        map.put("glyph", Integer.toString(data.getGlyph()));
        map.put("island_topper", Integer.toString(data.getIslandTopper()));
        map.put("kill_message", Integer.toString(data.getKillMessage()));
        map.put("projectile_trail", Integer.toString(data.getProjectileTrail()));
        map.put("shopkeeper_skin", Integer.toString(data.getShopkeeperSkin()));
        map.put("spray", Integer.toString(data.getSpray()));
        map.put("victory_dance", Integer.toString(data.getVictoryDance()));
        map.put("wood_skin", Integer.toString(data.getWoodSkin()));
        return map;
    }

    private int parseInt(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    private String stringOrEmpty(String value) {
        return value == null ? "" : value;
    }
}
