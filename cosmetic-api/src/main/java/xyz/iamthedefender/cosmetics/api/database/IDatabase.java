package xyz.iamthedefender.cosmetics.api.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.util.UUID;

public interface IDatabase {

    DatabaseType getDatabaseType();

    void connect();
    void createTable();

    default String getDisplayName() {
        return getDatabaseType().name();
    }

    default HikariDataSource getDataSource() {
        return null;
    }

    default Connection getConnection() {
        throw new UnsupportedOperationException("This database backend does not expose JDBC connections.");
    }

    default void validateConnection() {
        connect();
    }

    default void close() {
    }

    PlayerCosmeticsData loadPlayerData(UUID uuid);

    void createPlayerData(UUID uuid, PlayerCosmeticsData data);

    void savePlayerData(UUID uuid, PlayerCosmeticsData data);

    PlayerOwnedCosmeticsData loadOwnedData(UUID uuid);

    void createOwnedData(UUID uuid, PlayerOwnedCosmeticsData data);

    void saveOwnedData(UUID uuid, PlayerOwnedCosmeticsData data);
}

