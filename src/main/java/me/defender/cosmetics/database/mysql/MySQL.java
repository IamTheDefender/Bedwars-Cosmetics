package me.defender.cosmetics.database.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.defender.cosmetics.database.IDatabase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL implements IDatabase {

    public HikariDataSource dataSource;
    @Getter
    public Connection connection;
    private final JavaPlugin plugin;

    public MySQL(JavaPlugin plugin){
        this.plugin = plugin;
        connect();
        if(dataSource == null){
            Bukkit.getLogger().severe("There was an issue connecting to your MySQL database!");
            return;
        }
        createTable();
    }

    public void connect(){
        if(dataSource == null){
            String host = plugin.getConfig().getString("mysql.host");
            String database = plugin.getConfig().getString("mysql.database");
            String username = plugin.getConfig().getString("mysql.username");
            String password = plugin.getConfig().getString("mysql.password");
            int port = plugin.getConfig().getInt("mysql.port", 3306);
            int maxpoolsize = plugin.getConfig().getInt("mysql.maxpoolsize", 50);

            HikariConfig config = new HikariConfig();

            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true" );
            config.setPoolName("BW1058Cosmetics-MySQLPool");
            config.setMaximumPoolSize(maxpoolsize);
            config.setMaxLifetime(1800000);
            config.setUsername(username);
            config.setPassword(password);
            config.addDataSourceProperty("characterEncoding", "utf8");
            config.addDataSourceProperty("encoding", "UTF-8");
            config.addDataSourceProperty("useUnicode", "true");
            config.addDataSourceProperty("rewriteBatchedStatements", "true");
            config.addDataSourceProperty("jdbcCompliantTruncation", "false");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            dataSource = new HikariDataSource(config);
            try {
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                Bukkit.getLogger().severe("There was an issue while getting connection for the database! error: " + e.getMessage());
            }
        }
    }



    public void createTable(){
        if(dataSource != null){
            try {
                connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS cosmetics_player_data (" +
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
                        ")");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS player_owned_data (" +
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
                        ")");
            } catch (SQLException e) {
                Bukkit.getLogger().severe("Failed to create player-data table: " + e.getMessage());
            }
        }
    }

}
