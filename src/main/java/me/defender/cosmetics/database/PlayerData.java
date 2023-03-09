package me.defender.cosmetics.database;

import com.zaxxer.hikari.HikariDataSource;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.BwcAPI;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private String bedDestroy;
    private String woodSkin;
    private String victoryDance;
    private String shopkeeperSkin;
    private String glyph;
    private String spray;
    private String projectileTrail;
    private String killMessage;
    private String finalKillEffect;
    private String islandTopper;
    private String deathCry;
    private final Connection connection;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        connection = Cosmetics.getDbConnection();
        load();
    }



    public void load() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM cosmetics_player_data WHERE uuid = ?");
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                bedDestroy = result.getString("bed_destroy");
                woodSkin = result.getString("wood_skin");
                victoryDance = result.getString("victory_dance");
                shopkeeperSkin = result.getString("shopkeeper_skin");
                glyph = result.getString("glyph");
                spray = result.getString("spray");
                projectileTrail = result.getString("projectile_trail");
                killMessage = result.getString("kill_message");
                finalKillEffect = result.getString("final_kill_effect");
                islandTopper = result.getString("island_topper");
                deathCry = result.getString("death_cry");
            }
            statement.close();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to load player-data: " + e.getMessage());
        }
    }

    public void createData(){
        String sql = "INSERT INTO cosmetics_player_data (uuid, bed_destroy, wood_skin, victory_dance, shopkeeper_skin, glyph, spray, projectile_trail, kill_message, final_kill_effect, island_topper, death_cry) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uuid.toString());
            statement.setString(2, bedDestroy);
            statement.setString(3, woodSkin);
            statement.setString(4, victoryDance);
            statement.setString(5, shopkeeperSkin);
            statement.setString(6, glyph);
            statement.setString(7, spray);
            statement.setString(8, projectileTrail);
            statement.setString(9, killMessage);
            statement.setString(10, finalKillEffect);
            statement.setString(11, islandTopper);
            statement.setString(12, deathCry);
            statement.executeUpdate();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE cosmetics_player_data SET bed_destroy = ?, wood_skin = ?, victory_dance = ?, shopkeeper_skin = ?, glyph = ?, spray = ?, projectile_trail = ?, kill_message = ?, final_kill_effect = ?, island_topper = ?, death_cry = ? WHERE uuid = ?");
            statement.setString(1, bedDestroy);
            statement.setString(2, woodSkin);
            statement.setString(3, victoryDance);
            statement.setString(4, shopkeeperSkin);
            statement.setString(5, glyph);
            statement.setString(6, spray);
            statement.setString(7, projectileTrail);
            statement.setString(8, killMessage);
            statement.setString(9, finalKillEffect);
            statement.setString(10, islandTopper);
            statement.setString(11, deathCry);
            statement.setString(12, uuid.toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to save player-data: " + e.getMessage());
        }
    }


    public boolean exists() {
       if(getWoodSkin() == null){
           return false;
       }else return !getWoodSkin().equals("Demo");
    }



    public String getBedDestroy() {
        return bedDestroy;
    }

    public void setBedDestroy(String bedDestroy) {
        this.bedDestroy = bedDestroy;
    }

    public String getWoodSkin() {
        return woodSkin;
    }

    public void setWoodSkin(String woodSkin) {
        this.woodSkin = woodSkin;
    }

    public String getVictoryDance() {
        return victoryDance;
    }

    public void setVictoryDance(String victoryDance) {
        this.victoryDance = victoryDance;
    }

    public String getShopkeeperSkin() {
        return shopkeeperSkin;
    }

    public void setShopkeeperSkin(String shopkeeperSkin) {
        this.shopkeeperSkin = shopkeeperSkin;
    }

    public String getGlyph() {
        return glyph;
    }

    public void setGlyph(String glyph) {
        this.glyph = glyph;
    }

    public String getSpray() {
        return spray;
    }

    public void setSpray(String spray) {
        this.spray = spray;
    }

    public String getProjectileTrail() {
        return projectileTrail;
    }

    public void setProjectileTrail(String projectileTrail) {
        this.projectileTrail = projectileTrail;
    }

    public String getKillMessage() {
        return killMessage;
    }

    public void setKillMessage(String killMessage) {
        this.killMessage = killMessage;
    }

    public String getFinalKillEffect() {
        return finalKillEffect;
    }

    public void setFinalKillEffect(String finalKillEffect) {
        this.finalKillEffect = finalKillEffect;
    }

    public String getIslandTopper() {
        return islandTopper;
    }

    public void setIslandTopper(String islandTopper) {
        this.islandTopper = islandTopper;
    }

    public String getDeathCry() {
        return deathCry;
    }

    public void setDeathCry(String deathCry) {
        this.deathCry = deathCry;
    }
}
