package me.defender.cosmetics.database.sqlite;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.defender.cosmetics.database.IDatabase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite implements IDatabase {

    private final JavaPlugin plugin;
    public HikariDataSource dataSource;
    public Connection connection;
    public SQLite(JavaPlugin plugin){
        this.plugin = plugin;
        connect();
        createTable();
    }


    public void connect(){
        if(dataSource == null){
            String jdbcUrl = "jdbc:sqlite:" + plugin.getDataFolder().getPath() + "/playerData.db";
            try{
                Class.forName("org.sqlite.JDBC");
            }catch (ClassNotFoundException e) {
               Bukkit.getLogger().severe("SQLite class not found! SQLite is not supported!");
            }
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername("");
            config.setPassword("");
            config.setConnectionTestQuery("SELECT 1");
            config.setConnectionTimeout(500000);
            config.setMaximumPoolSize(100);
            config.setPoolName("COSMETICS-SQLITE");
            dataSource = new HikariDataSource(config);
            try {
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                Bukkit.getLogger().severe("There was an error getting the connection for database! error: " + e.getMessage());
            }
        }
    }

    public void createTable(){
        if(dataSource != null){
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS cosmetics_player_data (" +
                        "uuid TEXT PRIMARY KEY," +
                        "bed_destroy TEXT," +
                        "wood_skin TEXT," +
                        "victory_dance TEXT," +
                        "shopkeeper_skin TEXT," +
                        "glyph TEXT," +
                        "spray TEXT," +
                        "projectile_trail TEXT," +
                        "kill_message TEXT," +
                        "final_kill_effect TEXT," +
                        "island_topper TEXT," +
                        "death_cry TEXT" +
                        ")");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS player_owned_data (" +
                        "uuid TEXT PRIMARY KEY," +
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

    public Connection getConnection() {
        return connection;
    }
}
