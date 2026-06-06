package xyz.iamthedefender.cosmetics.data.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.bukkit.Bukkit;
import xyz.iamthedefender.cosmetics.api.database.IDatabase;
import xyz.iamthedefender.cosmetics.api.database.PlayerCosmeticsData;
import xyz.iamthedefender.cosmetics.api.database.PlayerOwnedCosmeticsData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public abstract class AbstractSqlDatabase implements IDatabase {

    @Getter
    protected HikariDataSource dataSource;

    protected AbstractSqlDatabase() {
        connect();
        if (dataSource != null) {
            createTable();
        }
    }

    protected abstract String driverClassName();

    protected abstract String jdbcUrl();

    protected abstract String username();

    protected abstract String password();

    protected abstract int maximumPoolSize();

    protected abstract long maxLifetime();

    protected abstract String poolName();

    protected String connectionTestQuery() {
        return null;
    }

    protected String playerDataTableSchema() {
        return "CREATE TABLE IF NOT EXISTS cosmetics_player_data (" +
                "uuid VARCHAR(36) PRIMARY KEY," +
                "bed_destroy VARCHAR(36)," +
                "wood_skin VARCHAR(36)," +
                "victory_dance VARCHAR(36)," +
                "shopkeeper_skin VARCHAR(36)," +
                "glyph VARCHAR(36)," +
                "spray VARCHAR(36)," +
                "projectile_trail VARCHAR(36)," +
                "kill_message VARCHAR(36)," +
                "final_kill_effect VARCHAR(36)," +
                "island_topper VARCHAR(36)," +
                "death_cry VARCHAR(36)" +
                ")";
    }

    protected String ownedDataTableSchema() {
        return "CREATE TABLE IF NOT EXISTS player_owned_data (" +
                "uuid VARCHAR(36) PRIMARY KEY," +
                "bed_destroy INT," +
                "death_cry INT," +
                "final_kill_effect INT," +
                "glyph INT," +
                "island_topper INT," +
                "kill_message INT," +
                "projectile_trail INT," +
                "shopkeeper_skin INT," +
                "spray INT," +
                "victory_dance INT," +
                "wood_skin INT" +
                ")";
    }

    @Override
    public void connect() {
        boolean needConnecting = dataSource == null;
        if (!needConnecting) {
            try (Connection connection = dataSource.getConnection()) {
                connection.createStatement().close();
            } catch (Exception e) {
                needConnecting = true;
            }
        }

        if (!needConnecting) {
            return;
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName());
        config.setJdbcUrl(jdbcUrl());
        config.setUsername(username());
        config.setPassword(password());
        config.setPoolName(poolName());
        config.setMaximumPoolSize(maximumPoolSize());
        config.setMaxLifetime(maxLifetime());

        if (!(this instanceof SQLite)) {
            config.addDataSourceProperty("characterEncoding", "utf8");
            config.addDataSourceProperty("encoding", "UTF-8");
            config.addDataSourceProperty("useUnicode", "true");
            config.addDataSourceProperty("rewriteBatchedStatements", "true");
            config.addDataSourceProperty("jdbcCompliantTruncation", "false");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        }

        String testQuery = connectionTestQuery();
        if (testQuery != null) {
            config.setConnectionTestQuery(testQuery);
        }

        dataSource = new HikariDataSource(config);

        try (Connection ignored = dataSource.getConnection()) {
            // validate
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to connect to " + getDisplayName() + ": " + e.getMessage());
        }
    }

    @Override
    public void createTable() {
        if (dataSource == null) {
            return;
        }
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(playerDataTableSchema());
            statement.executeUpdate(ownedDataTableSchema());
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to create tables for " + getDisplayName() + ": " + e.getMessage());
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void validateConnection() {
        try (Connection connection = getConnection()) {
            connection.createStatement().close();
        } catch (Exception exception) {
            connect();
        }
    }

    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    @Override
    public PlayerCosmeticsData loadPlayerData(UUID uuid) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM cosmetics_player_data WHERE uuid = ?")) {
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                return null;
            }
            return new PlayerCosmeticsData(
                    result.getString("wood_skin"),
                    result.getString("bed_destroy"),
                    result.getString("victory_dance"),
                    result.getString("shopkeeper_skin"),
                    result.getString("glyph"),
                    result.getString("spray"),
                    result.getString("projectile_trail"),
                    result.getString("kill_message"),
                    result.getString("final_kill_effect"),
                    result.getString("island_topper"),
                    result.getString("death_cry")
            );
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to load player-data from " + getDisplayName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public void createPlayerData(UUID uuid, PlayerCosmeticsData data) {
        String sql = "INSERT INTO cosmetics_player_data (uuid, bed_destroy, wood_skin, victory_dance, shopkeeper_skin, glyph, spray, projectile_trail, kill_message, final_kill_effect, island_topper, death_cry) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            statement.setString(2, data.getBedDestroy());
            statement.setString(3, data.getWoodSkin());
            statement.setString(4, data.getVictoryDance());
            statement.setString(5, data.getShopkeeperSkin());
            statement.setString(6, data.getGlyph());
            statement.setString(7, data.getSpray());
            statement.setString(8, data.getProjectileTrail());
            statement.setString(9, data.getKillMessage());
            statement.setString(10, data.getFinalKillEffect());
            statement.setString(11, data.getIslandTopper());
            statement.setString(12, data.getDeathCry());
            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to create player-data in " + getDisplayName() + ": " + e.getMessage());
        }
    }

    @Override
    public void savePlayerData(UUID uuid, PlayerCosmeticsData data) {
        String sql = "UPDATE cosmetics_player_data SET bed_destroy = ?, wood_skin = ?, victory_dance = ?, shopkeeper_skin = ?, glyph = ?, spray = ?, projectile_trail = ?, kill_message = ?, final_kill_effect = ?, island_topper = ?, death_cry = ? WHERE uuid = ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, data.getBedDestroy());
            statement.setString(2, data.getWoodSkin());
            statement.setString(3, data.getVictoryDance());
            statement.setString(4, data.getShopkeeperSkin());
            statement.setString(5, data.getGlyph());
            statement.setString(6, data.getSpray());
            statement.setString(7, data.getProjectileTrail());
            statement.setString(8, data.getKillMessage());
            statement.setString(9, data.getFinalKillEffect());
            statement.setString(10, data.getIslandTopper());
            statement.setString(11, data.getDeathCry());
            statement.setString(12, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to save player-data in " + getDisplayName() + ": " + e.getMessage());
        }
    }

    @Override
    public PlayerOwnedCosmeticsData loadOwnedData(UUID uuid) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM player_owned_data WHERE uuid = ?")) {
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                return null;
            }
            return new PlayerOwnedCosmeticsData(
                    result.getInt("bed_destroy"),
                    result.getInt("death_cry"),
                    result.getInt("final_kill_effect"),
                    result.getInt("glyph"),
                    result.getInt("island_topper"),
                    result.getInt("kill_message"),
                    result.getInt("projectile_trail"),
                    result.getInt("shopkeeper_skin"),
                    result.getInt("spray"),
                    result.getInt("victory_dance"),
                    result.getInt("wood_skin")
            );
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to load owned-data from " + getDisplayName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public void createOwnedData(UUID uuid, PlayerOwnedCosmeticsData data) {
        String sql = "INSERT INTO player_owned_data (uuid, bed_destroy, death_cry, final_kill_effect, glyph, island_topper, kill_message, projectile_trail, shopkeeper_skin, spray, victory_dance, wood_skin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            statement.setInt(2, data.getBedDestroy());
            statement.setInt(3, data.getDeathCry());
            statement.setInt(4, data.getFinalKillEffect());
            statement.setInt(5, data.getGlyph());
            statement.setInt(6, data.getIslandTopper());
            statement.setInt(7, data.getKillMessage());
            statement.setInt(8, data.getProjectileTrail());
            statement.setInt(9, data.getShopkeeperSkin());
            statement.setInt(10, data.getSpray());
            statement.setInt(11, data.getVictoryDance());
            statement.setInt(12, data.getWoodSkin());
            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to create owned-data in " + getDisplayName() + ": " + e.getMessage());
        }
    }

    @Override
    public void saveOwnedData(UUID uuid, PlayerOwnedCosmeticsData data) {
        String sql = "UPDATE player_owned_data SET bed_destroy = ?, death_cry = ?, final_kill_effect = ?, glyph = ?, island_topper = ?, kill_message = ?, projectile_trail = ?, shopkeeper_skin = ?, spray = ?, victory_dance = ?, wood_skin = ? WHERE uuid = ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, data.getBedDestroy());
            statement.setInt(2, data.getDeathCry());
            statement.setInt(3, data.getFinalKillEffect());
            statement.setInt(4, data.getGlyph());
            statement.setInt(5, data.getIslandTopper());
            statement.setInt(6, data.getKillMessage());
            statement.setInt(7, data.getProjectileTrail());
            statement.setInt(8, data.getShopkeeperSkin());
            statement.setInt(9, data.getSpray());
            statement.setInt(10, data.getVictoryDance());
            statement.setInt(11, data.getWoodSkin());
            statement.setString(12, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to save owned-data in " + getDisplayName() + ": " + e.getMessage());
        }
    }
}
