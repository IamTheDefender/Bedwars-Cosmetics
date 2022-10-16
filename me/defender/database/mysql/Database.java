// 
// @author IamTheDefender
// 

package me.defender.database.mysql;

import com.zaxxer.hikari.HikariDataSource;
import me.defender.database.mysql.model.PlayerData;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import org.bukkit.entity.Player;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;
import me.defender.api.utils.util;
import java.sql.Connection;

public class Database
{
    private Connection connection;
    
    public Connection getConnection() throws SQLException {
        if (this.connection != null) {
            return this.connection;
        }
        String port = util.plugin().getConfig().getString("MySQL.Port");
        final String ip = util.plugin().getConfig().getString("MySQL.IP");
        if (util.plugin().getConfig().getString("MySQL.Port") == "0") {
            port = "3306";
        }
         String db_name = util.plugin().getConfig().getString("MySQL.Database-Name");
         String user = util.plugin().getConfig().getString("MySQL.Username");
         String password = util.plugin().getConfig().getString("MySQL.Password");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPoolName("Bw1058-Cosmetics");
        dataSource.setMaximumPoolSize(10);
        dataSource.setMaxLifetime(1800 * 1000);
        dataSource.setConnectionTimeout(48384000);
        dataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        dataSource.addDataSourceProperty("serverName", ip);
        dataSource.addDataSourceProperty("port", port);
        dataSource.addDataSourceProperty("databaseName", db_name);
        dataSource.addDataSourceProperty("user", user);
        dataSource.addDataSourceProperty("password", password);
        this.connection = dataSource.getConnection();
        return this.connection;
    }
    
    public void setupDB() throws SQLException {
        final Statement statement = this.getConnection().createStatement();
        final String sql = "CREATE TABLE IF NOT EXISTS player_data(name varchar(200), deathcries varchar(16), sprays varchar(16), killmessages varchar(16) ,shopkeeperskins varchar(16), woodskins varchar(16), glyphs varchar(16), victorydances varchar(16), finalkilleffect varchar(16), bedbreakeffects varchar(16), projectileTrails varchar(16), islandtopper varchar(16))";
        statement.execute(sql);
        statement.close();
    }
    
    public String getDeathcries(final Player p) {
        try {
            final PreparedStatement ps = this.getConnection().prepareStatement("SELECT deathcries FROM player_data WHERE name=?");
            ps.setString(1, util.getUUID(p));
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("deathcries");
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }

        return "User Not in DB!";
    }
    
    public String getSprays(final Player p) {
        try {
            final PreparedStatement ps = this.getConnection().prepareStatement("SELECT sprays FROM player_data WHERE name=?");
            ps.setString(1, util.getUUID(p));
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("sprays");
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return "User Not in DB!";
    }
    
    public String getVictorydances(final Player p) {
        try {
            final PreparedStatement ps = this.getConnection().prepareStatement("SELECT victorydances FROM player_data WHERE name=?");
            ps.setString(1, util.getUUID(p));
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("victorydances");
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return "User Not in DB!";
    }
    
    public String getGlyphs(final Player p) {
        try {
            final PreparedStatement ps = this.getConnection().prepareStatement("SELECT glyphs FROM player_data WHERE name=?");
            ps.setString(1, util.getUUID(p));
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("glyphs");
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return "User Not in DB!";
    }
    
    public String getWoodskins(final Player p) {
        try {
            final PreparedStatement ps = this.getConnection().prepareStatement("SELECT woodskins FROM player_data WHERE name=?");
            ps.setString(1, util.getUUID(p));
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("woodskins");
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return "User Not in DB!";
    }
    
    public String getKillMessages(final Player p) {
        try {
            final PreparedStatement ps = this.getConnection().prepareStatement("SELECT killmessages FROM player_data WHERE name=?");
            ps.setString(1, util.getUUID(p));
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("killmessages");
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return "User Not in DB!";
    }

    public String getFinalKillEffect(final Player p) {
        try {
            final PreparedStatement ps = this.getConnection().prepareStatement("SELECT finalkilleffect FROM player_data WHERE name=?");
            ps.setString(1, util.getUUID(p));
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("finalkilleffect");
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return "User Not in DB!";
    }
    
    public String getShopKeeperSkins(final Player p) {
        try {
            final PreparedStatement ps = this.getConnection().prepareStatement("SELECT shopkeeperskins FROM player_data WHERE name=?");
            ps.setString(1, util.getUUID(p));
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("shopkeeperskins");
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return "User Not in DB!";
    }
    
    public String getBedBreakEffects(final Player p) {
        try {
            final PreparedStatement ps = this.getConnection().prepareStatement("SELECT bedbreakeffects FROM player_data WHERE name=?");
            ps.setString(1, util.getUUID(p));
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("bedbreakeffects");
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return "User Not in DB!";
    }
    
    
    public String getProjectileTrails(final Player p) {
        try {
            final PreparedStatement ps = this.getConnection().prepareStatement("SELECT projectileTrails FROM player_data WHERE name=?");
            ps.setString(1, util.getUUID(p));
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("projectileTrails");
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return "User Not in DB!";
    }
    public String getIslandTopper(Player p) {
        try {
            final PreparedStatement ps = this.getConnection().prepareStatement("SELECT islandtopper FROM player_data WHERE name=?");
            ps.setString(1, util.getUUID(p));
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("islandtopper");
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return "User Not in DB!";
    }
    
    public PlayerData findPlayerDataByName(final String name) throws SQLException {
        final PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM player_data WHERE name = ?");
        statement.setString(1, name);
        final ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            final PlayerData playerData = new PlayerData(resultSet.getString("name"), resultSet.getString("deathcries"), resultSet.getString("sprays"), resultSet.getString("shopkeeperskins"), resultSet.getString("woodskins"), resultSet.getString("glyphs"), resultSet.getString("victorydances"), resultSet.getString("killmessages"));
            statement.close();
            return playerData;
        }
        statement.close();
        return null;
    }
    
    public void createPlayerData(final PlayerData playerData) throws SQLException {
        final PreparedStatement statement = this.getConnection().prepareStatement("INSERT INTO player_data(name, deathcries, sprays, killmessages, shopkeeperskins, woodskins, glyphs, victorydances, bedbreakeffects, projectileTrails, finalkilleffect, islandtopper) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, playerData.getName());
        statement.setString(2, playerData.getDeathcries());
        statement.setString(3, playerData.getSprays());
        statement.setString(4, playerData.getKillmessages());
        statement.setString(5, playerData.getShopkeeperskins());
        statement.setString(6, playerData.getWoodskins());
        statement.setString(7, playerData.getGlyphs());
        statement.setString(8, playerData.getVictorydances());
        statement.setString(9, "None");
        statement.setString(10, "None");
        statement.setString(11, "None");
        statement.setString(12, "None");
        statement.executeUpdate();
        statement.close();
    }
    
    public void updateDeathcries(final String to, final Player p) {
        try {
            final PreparedStatement ps = util.plugin().getDb().getConnection().prepareStatement("UPDATE player_data set deathcries=? WHERE name=?");
            ps.setString(1, to);
            ps.setString(2, util.getUUID(p));
            ps.executeUpdate();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateSprays(final String to, final Player p) {
        try {
            final PreparedStatement ps = util.plugin().getDb().getConnection().prepareStatement("UPDATE player_data set sprays=? WHERE name=?");
            ps.setString(1, to);
            ps.setString(2, util.getUUID(p));
            ps.executeUpdate();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateKillMessages(final String to, final Player p) {
        try {
            final PreparedStatement ps = util.plugin().getDb().getConnection().prepareStatement("UPDATE player_data set killmessages=? WHERE name=?");
            ps.setString(1, to);
            ps.setString(2, util.getUUID(p));
            ps.executeUpdate();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateShopKeeperSkins(final String to, final Player p) {
        try {
            final PreparedStatement ps = util.plugin().getDb().getConnection().prepareStatement("UPDATE player_data set shopkeeperskins=? WHERE name=?");
            ps.setString(1, to);
            ps.setString(2, util.getUUID(p));
            ps.executeUpdate();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFinalKillEffect(final String to, final Player p) {
        try {
            final PreparedStatement ps = util.plugin().getDb().getConnection().prepareStatement("UPDATE player_data set finalkilleffect=? WHERE name=?");
            ps.setString(1, to);
            ps.setString(2, util.getUUID(p));
            ps.executeUpdate();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateWoodskins(final String to, final Player p) {
        try {
            final PreparedStatement ps = util.plugin().getDb().getConnection().prepareStatement("UPDATE player_data set woodskins=? WHERE name=?");
            ps.setString(1, to);
            ps.setString(2, util.getUUID(p));
            ps.executeUpdate();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateGlyphs(final String to, final Player p) {
        try {
            final PreparedStatement ps = util.plugin().getDb().getConnection().prepareStatement("UPDATE player_data set glyphs=? WHERE name=?");
            ps.setString(1, to);
            ps.setString(2, util.getUUID(p));
            ps.executeUpdate();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateVictoryDances(final String to, final Player p) {
        try {
            final PreparedStatement ps = util.plugin().getDb().getConnection().prepareStatement("UPDATE player_data set victorydances=? WHERE name=?");
            ps.setString(1, to);
            ps.setString(2, util.getUUID(p));
            ps.executeUpdate();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateBedbreakeffects(final String to, final Player p) {
        try {
            final PreparedStatement ps = util.plugin().getDb().getConnection().prepareStatement("UPDATE player_data set bedbreakeffects=? WHERE name=?");
            ps.setString(1, to);
            ps.setString(2, util.getUUID(p));
            ps.executeUpdate();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateProjectileTrails(final String to, final Player p) {
        try {
            final PreparedStatement ps = util.plugin().getDb().getConnection().prepareStatement("UPDATE player_data set projectileTrails=? WHERE name=?");
            ps.setString(1, to);
            ps.setString(2, util.getUUID(p));
            ps.executeUpdate();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateIslandTopper( String to,  Player p) {
        try {
            final PreparedStatement ps = util.plugin().getDb().getConnection().prepareStatement("UPDATE player_data set islandtopper=? WHERE name=?");
            ps.setString(1, to);
            ps.setString(2, util.getUUID(p));
            ps.executeUpdate();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }
}
