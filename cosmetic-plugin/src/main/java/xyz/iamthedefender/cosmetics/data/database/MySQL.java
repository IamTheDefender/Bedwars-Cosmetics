package xyz.iamthedefender.cosmetics.data.database;

import xyz.iamthedefender.cosmetics.api.database.DatabaseType;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

public class MySQL extends AbstractSqlDatabase {

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }

    @Override
    public String getDisplayName() {
        return "MySQL";
    }

    @Override
    protected String driverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    protected String jdbcUrl() {
        String host = StartupUtils.getString("database.mysql.host", "mysql.host", "localhost");
        String database = StartupUtils.getString("database.mysql.database", "mysql.database", "name");
        boolean ssl = StartupUtils.getBoolean("database.mysql.use-ssl", "mysql.useSSL", false);
        int port = StartupUtils.getInt("database.mysql.port", "mysql.port", 3306);
        return "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&enabledTLSProtocols=TLSv1.2&useSSL=" + ssl + "&allowPublicKeyRetrieval=true";
    }

    @Override
    protected String username() {
        return StartupUtils.getString("database.mysql.username", "mysql.username", "root");
    }

    @Override
    protected String password() {
        return StartupUtils.getString("database.mysql.password", "mysql.password", "none");
    }

    @Override
    protected int maximumPoolSize() {
        return StartupUtils.getInt("database.mysql.max-pool-size", "mysql.maxpoolsize", 50);
    }

    @Override
    protected long maxLifetime() {
        return StartupUtils.getInt("database.mysql.max-lifetime", "mysql.maxlifetime", Integer.MAX_VALUE);
    }

    @Override
    protected String poolName() {
        return "BW1058Cosmetics-MySQLPool";
    }
}
