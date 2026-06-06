package xyz.iamthedefender.cosmetics.data.database;

import xyz.iamthedefender.cosmetics.api.database.DatabaseType;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

public class MariaDB extends AbstractSqlDatabase {

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MARIADB;
    }

    @Override
    public String getDisplayName() {
        return "MariaDB";
    }

    @Override
    protected String driverClassName() {
        return "org.mariadb.jdbc.Driver";
    }

    @Override
    protected String jdbcUrl() {
        String host = StartupUtils.getString("database.mariadb.host", null, "localhost");
        String database = StartupUtils.getString("database.mariadb.database", null, "name");
        boolean ssl = StartupUtils.getBoolean("database.mariadb.use-ssl", null, false);
        int port = StartupUtils.getInt("database.mariadb.port", null, 3306);
        return "jdbc:mariadb://" + host + ":" + port + "/" + database + "?useSsl=" + ssl;
    }

    @Override
    protected String username() {
        return StartupUtils.getString("database.mariadb.username", null, "root");
    }

    @Override
    protected String password() {
        return StartupUtils.getString("database.mariadb.password", null, "none");
    }

    @Override
    protected int maximumPoolSize() {
        return StartupUtils.getInt("database.mariadb.max-pool-size", null, 50);
    }

    @Override
    protected long maxLifetime() {
        return StartupUtils.getInt("database.mariadb.max-lifetime", null, Integer.MAX_VALUE);
    }

    @Override
    protected String poolName() {
        return "BW1058Cosmetics-MariaDBPool";
    }
}
