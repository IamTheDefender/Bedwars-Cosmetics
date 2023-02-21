package me.defender.cosmetics.database;

import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.categories.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.categories.deathcries.DeathCry;
import me.defender.cosmetics.api.categories.finalkilleffects.FinalKillEffect;
import me.defender.cosmetics.api.categories.glyphs.Glyph;
import me.defender.cosmetics.api.categories.islandtoppers.IslandTopper;
import me.defender.cosmetics.api.categories.killmessage.KillMessage;
import me.defender.cosmetics.api.categories.projectiletrails.ProjectileTrail;
import me.defender.cosmetics.api.categories.shopkeeperskins.ShopKeeperSkin;
import me.defender.cosmetics.api.categories.sprays.Spray;
import me.defender.cosmetics.api.categories.victorydances.VictoryDance;
import me.defender.cosmetics.api.categories.woodskins.WoodSkin;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.utils.StartupUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerOwnedData{
    private final UUID uuid;
    private final Connection connection;
    private int bedDestroy;
    private int deathCry;

    private int finalKillEffect;
    private int glyph;
    private int islandTopper;
    private int killMessage;
    private int projectileTrail;
    private int shopkeeperSkin;
    private int spray;
    private int victoryDance;
    private int woodSkin;


    public PlayerOwnedData (UUID uuid) {
        this.uuid = uuid;
        this.connection = Cosmetics.dbConnection;
        load();
    }

    public void load() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM player_owned_data WHERE uuid = ?");
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                bedDestroy = result.getInt("bed_destroy");
                deathCry = result.getInt("death_cry");
                finalKillEffect = result.getInt("final_kill_effect");
                glyph = result.getInt("glyph");
                islandTopper = result.getInt("island_topper");
                killMessage = result.getInt("kill_message");
                projectileTrail = result.getInt("projectile_trail");
                shopkeeperSkin = result.getInt("shopkeeper_skin");
                spray = result.getInt("spray");
                victoryDance = result.getInt("victory_dance");
                woodSkin = result.getInt("wood_skin");
                statement.close();
            }else{
                demo();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void demo(){
        String sql = "INSERT INTO player_owned_data (uuid, bed_destroy, death_cry, final_kill_effect, glyph, island_topper, kill_message, projectile_trail, shopkeeper_skin, spray, victory_dance, wood_skin) " +
        "VALUES (?, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uuid.toString());
            statement.executeUpdate();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE player_owned_data SET bed_destroy = ?, death_cry = ?, final_kill_effect = ?, glyph = ?, island_topper = ?, kill_message = ?, projectile_trail = ?, shopkeeper_skin = ?, spray = ?, victory_dance = ?, wood_skin = ? WHERE uuid = ?;");
            statement.setInt(1, bedDestroy);
            statement.setInt(2, deathCry);
            statement.setInt(3, finalKillEffect);
            statement.setInt(4, glyph);
            statement.setInt(5, islandTopper);
            statement.setInt(6, killMessage);
            statement.setInt(7, projectileTrail);
            statement.setInt(8, shopkeeperSkin);
            statement.setInt(9, spray);
            statement.setInt(10, victoryDance);
            statement.setInt(11, woodSkin);
            statement.setString(12, uuid.toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getBedDestroy() {
        return bedDestroy;
    }

    public void setBedDestroy(int bedDestroy) {
        this.bedDestroy = bedDestroy;
    }

    public int getDeathCry() {
        return deathCry;
    }

    public void setDeathCry(int deathCry) {
        this.deathCry = deathCry;
    }

    public int getFinalKillEffect() {
        return finalKillEffect;
    }

    public void setFinalKillEffect(int finalKillEffect) {
        this.finalKillEffect = finalKillEffect;
    }

    public int getGlyph() {
        return glyph;
    }

    public void setGlyph(int glyph) {
        this.glyph = glyph;
    }

    public int getIslandTopper() {
        return islandTopper;
    }

    public void setIslandTopper(int islandTopper) {
        this.islandTopper = islandTopper;
    }

    public int getKillMessage() {
        return killMessage;
    }

    public void setKillMessage(int killMessage) {
        this.killMessage = killMessage;
    }

    public int getProjectileTrail() {
        return projectileTrail;
    }

    public void setProjectileTrail(int projectileTrail) {
        this.projectileTrail = projectileTrail;
    }

    public int getShopkeeperSkin() {
        return shopkeeperSkin;
    }

    public void setShopkeeperSkin(int shopkeeperSkin) {
        this.shopkeeperSkin = shopkeeperSkin;
    }

    public int getSpray() {
        return spray;
    }

    public void setSpray(int spray) {
        this.spray = spray;
    }

    public int getVictoryDance() {
        return victoryDance;
    }

    public void setVictoryDance(int victoryDance) {
        this.victoryDance = victoryDance;
    }

    public int getWoodSkin() {
        return woodSkin;
    }

    public void setWoodSkin(int woodSkin) {
        this.woodSkin = woodSkin;
    }



    public void updateOwned(){
        setBedDestroy(0);
        setDeathCry(0);
        setFinalKillEffect(0);
        setGlyph(0);
        setIslandTopper(0);
        setKillMessage(0);
        setProjectileTrail(0);
        setShopkeeperSkin(0);
        setSpray(0);
        setVictoryDance(0);
        setWoodSkin(0);
        for (BedDestroy destroy : StartupUtils.bedDestroyList) {
            if (Bukkit.getPlayer(uuid).hasPermission(CosmeticsType.BedBreakEffects.getPermissionFormat() + "." + destroy.getIdentifier())) {
                this.setBedDestroy(this.getBedDestroy() + 1);
            }
        }
        for (DeathCry deathCr : StartupUtils.deathCryList) {
            if (Bukkit.getPlayer(uuid).hasPermission(CosmeticsType.DeathCries.getPermissionFormat() + "." + deathCr.getIdentifier())) {
                this.setDeathCry(this.getDeathCry() + 1);
            }
        }
        for (FinalKillEffect killEffect : StartupUtils.finalKillList) {
            if (Bukkit.getPlayer(uuid).hasPermission(CosmeticsType.FinalKillEffects.getPermissionFormat() + "." + killEffect.getIdentifier())) {
                this.setFinalKillEffect(this.getFinalKillEffect() + 1);
            }
        }
        for (Glyph glyph : StartupUtils.glyphsList) {
            if (Bukkit.getPlayer(uuid).hasPermission(CosmeticsType.Glyphs.getPermissionFormat() + "." + glyph.getIdentifier())) {
                this.setGlyph(this.getGlyph() + 1);
            }
        }
        for (IslandTopper topper : StartupUtils.islandTopperList) {
            if (Bukkit.getPlayer(uuid).hasPermission(CosmeticsType.IslandTopper.getPermissionFormat() + "." + topper.getIdentifier())) {
                this.setIslandTopper(this.getIslandTopper() + 1);
            }
        }
        for (KillMessage message : StartupUtils.killMessageList) {
            if (Bukkit.getPlayer(uuid).hasPermission(CosmeticsType.KillMessage.getPermissionFormat() + "." + message.getIdentifier())) {
                this.setKillMessage(this.getKillMessage() + 1);
            }
        }


        for (ProjectileTrail trail : StartupUtils.projectileTrailList) {
            if (Bukkit.getPlayer(uuid).hasPermission(CosmeticsType.ProjectileTrails.getPermissionFormat() + "." + trail.getIdentifier())) {
                this.setProjectileTrail(this.getProjectileTrail() + 1);
            }
        }

        for (ShopKeeperSkin shopKeeperSkin : StartupUtils.shopKeeperSkinList) {
            if (Bukkit.getPlayer(uuid).hasPermission(CosmeticsType.ShopKeeperSkin.getPermissionFormat() + "." + shopKeeperSkin.getIdentifier())) {
                this.setShopkeeperSkin(this.getShopkeeperSkin() + 1);
            }
        }

        for (Spray spray1 : StartupUtils.sprayList) {
            if (Bukkit.getPlayer(uuid).hasPermission(CosmeticsType.Sprays.getPermissionFormat() + "." + spray1.getIdentifier())) {
                this.setSpray(this.getSpray() + 1);
            }
        }

        for (VictoryDance dance : StartupUtils.victoryDancesList) {
            if (Bukkit.getPlayer(uuid).hasPermission(CosmeticsType.VictoryDances.getPermissionFormat() + "." + dance.getIdentifier())) {
                this.setVictoryDance(this.getVictoryDance() + 1);
            }
        }

        for (WoodSkin skin : StartupUtils.woodSkinsList) {
            if (Bukkit.getPlayer(uuid).hasPermission(CosmeticsType.WoodSkins.getPermissionFormat() + "." + skin.getIdentifier())) {
                this.setWoodSkin(this.getWoodSkin() + 1);
            }
        }
    }
}
