package xyz.iamthedefender.cosmetics.data.database;

import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.database.DatabaseType;

public class SQLite extends AbstractSqlDatabase {

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.SQLITE;
    }

    @Override
    public String getDisplayName() {
        return "SQLite";
    }

    @Override
    protected String driverClassName() {
        return "org.sqlite.JDBC";
    }

    @Override
    protected String jdbcUrl() {
        return "jdbc:sqlite:" + CosmeticsPlugin.getInstance().getHandler().getAddonPath() + "/cosmeticsData.db";
    }

    @Override
    protected String username() {
        return "";
    }

    @Override
    protected String password() {
        return "";
    }

    @Override
    protected int maximumPoolSize() {
        return 1;
    }

    @Override
    protected long maxLifetime() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected String connectionTestQuery() {
        return "SELECT 1";
    }

    @Override
    protected String poolName() {
        return "COSMETICS-SQLITE";
    }

    @Override
    protected String playerDataTableSchema() {
        return "CREATE TABLE IF NOT EXISTS cosmetics_player_data (" +
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
                ")";
    }

    @Override
    protected String ownedDataTableSchema() {
        return "CREATE TABLE IF NOT EXISTS player_owned_data (" +
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
                ")";
    }


}
