package xyz.iamthedefender.cosmetics.data.database;

import xyz.iamthedefender.cosmetics.api.database.DatabaseType;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

public class PostgreSQL extends AbstractSqlDatabase {

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.POSTGRESQL;
    }

    @Override
    public String getDisplayName() {
        return "PostgreSQL";
    }

    @Override
    protected String driverClassName() {
        return "org.postgresql.Driver";
    }

    @Override
    protected String jdbcUrl() {
        String host = StartupUtils.getString("database.postgresql.host", null, "localhost");
        String database = StartupUtils.getString("database.postgresql.database", null, "name");
        boolean ssl = StartupUtils.getBoolean("database.postgresql.use-ssl", null, false);
        int port = StartupUtils.getInt("database.postgresql.port", null, 5432);
        return "jdbc:postgresql://" + host + ":" + port + "/" + database + "?ssl=" + ssl;
    }

    @Override
    protected String username() {
        return StartupUtils.getString("database.postgresql.username", null, "postgres");
    }

    @Override
    protected String password() {
        return StartupUtils.getString("database.postgresql.password", null, "none");
    }

    @Override
    protected int maximumPoolSize() {
        return StartupUtils.getInt("database.postgresql.max-pool-size", null, 50);
    }

    @Override
    protected long maxLifetime() {
        return StartupUtils.getInt("database.postgresql.max-lifetime", null, Integer.MAX_VALUE);
    }

    @Override
    protected String poolName() {
        return "BW1058Cosmetics-PostgreSQLPool";
    }
}
