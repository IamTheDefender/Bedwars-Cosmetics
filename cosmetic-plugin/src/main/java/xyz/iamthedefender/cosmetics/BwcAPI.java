package xyz.iamthedefender.cosmetics;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.iamthedefender.cosmetics.api.CosmeticsAPI;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticPreview;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticRegistry;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.*;
import xyz.iamthedefender.cosmetics.api.database.DatabaseType;
import xyz.iamthedefender.cosmetics.api.database.IDatabase;
import xyz.iamthedefender.cosmetics.api.handler.IHandler;
import xyz.iamthedefender.cosmetics.api.menu.SystemGuiManager;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.api.versionsupport.IVersionSupport;
import xyz.iamthedefender.cosmetics.data.PlayerData;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.List;

public class BwcAPI implements CosmeticsAPI {

    /**
     * Check if MySQL is enabled.
     * @return true if enabled.
     */
    public boolean isMySQL() {
        return StartupUtils.getConfiguredDatabaseType() == DatabaseType.MYSQL;
    }

    @Override
    public IHandler getHandler() {
        return CosmeticsPlugin.getInstance().getHandler();
    }

    @Override
    public SystemGuiManager getSystemGuiManager() {
        return CosmeticsPlugin.getInstance().getSystemGuiManager();
    }

    @Override
    public JavaPlugin getPlugin() {
        return CosmeticsPlugin.getInstance();
    }

    @Override
    public ConfigManager getMenuData() {
        return CosmeticsPlugin.getInstance().menuData;
    }

    @Override
    public List<BedDestroy> getBedDestroyList() {
        return CosmeticRegistry.getByCategory(CosmeticType.BED_DESTROY);
    }

    @Override
    public List<DeathCry> getDeathCryList() {
        return CosmeticRegistry.getByCategory(CosmeticType.DEATH_CRIES);
    }

    @Override
    public List<FinalKillEffect> getFinalKillList() {
        return CosmeticRegistry.getByCategory(CosmeticType.FINAL_KILL_EFFECTS);
    }

    @Override
    public List<ProjectileTrail> getProjectileTrailList() {
        return CosmeticRegistry.getByCategory(CosmeticType.PROJECTILE_TRAILS);
    }

    @Override
    public List<Glyph> getGlyphsList() {
        return CosmeticRegistry.getByCategory(CosmeticType.GLYPHS);
    }

    @Override
    public List<VictoryDance> getVictoryDanceList() {
        return CosmeticRegistry.getByCategory(CosmeticType.VICTORY_DANCES);
    }

    @Override
    public List<WoodSkin> getWoodSkinList() {
        return CosmeticRegistry.getByCategory(CosmeticType.WOOD_SKINS);
    }

    @Override
    public List<Spray> getSprayList() {
        return CosmeticRegistry.getByCategory(CosmeticType.SPRAYS);
    }

    @Override
    public List<KillMessage> getKillMessageList() {
        return CosmeticRegistry.getByCategory(CosmeticType.KILL_MESSAGES);
    }

    @Override
    public List<ShopKeeperSkin> getShopKeeperSkinList() {
        return CosmeticRegistry.getByCategory(CosmeticType.SHOPKEEPER_SKINS);
    }

    @Override
    public List<IslandTopper> getIslandTopperList() {
        return CosmeticRegistry.getByCategory(CosmeticType.ISLAND_TOPPERS);
    }

    @Override
    public IVersionSupport getVersionSupport() {
        return CosmeticsPlugin.getInstance().getVersionSupport();
    }

    @Override
    public List<CosmeticPreview> getPreviewList() {
        return CosmeticsPlugin.getInstance().getPreviewList();
    }

    /**
     * Get the selected cosmetic for a player.
     * @param p   Player
     * @param cos Cosmetic type
     * @return    String
     */
    public String getSelectedCosmetic(Player p, CosmeticType<?> cos){
        if (p == null){
            return null;
        }
        PlayerData playerData = CosmeticsPlugin.getInstance().getPlayerManager().getPlayerData(p.getUniqueId());
        String value = null;
        if (cos == CosmeticType.BED_DESTROY) {
            value = playerData.getBedDestroy();
        } else if (cos == CosmeticType.DEATH_CRIES) {
            value = playerData.getDeathCry();
        } else if (cos == CosmeticType.FINAL_KILL_EFFECTS) {
            value = playerData.getFinalKillEffect();
        } else if (cos == CosmeticType.GLYPHS) {
            value = playerData.getGlyph();
        } else if (cos == CosmeticType.ISLAND_TOPPERS) {
            value = playerData.getIslandTopper();
        } else if (cos == CosmeticType.KILL_MESSAGES) {
            value = playerData.getKillMessage();
        } else if (cos == CosmeticType.PROJECTILE_TRAILS) {
            value = playerData.getProjectileTrail();
        } else if (cos == CosmeticType.SHOPKEEPER_SKINS) {
            value = playerData.getShopkeeperSkin();
        } else if (cos == CosmeticType.SPRAYS) {
            value = playerData.getSpray();
        } else if (cos == CosmeticType.VICTORY_DANCES) {
            value = playerData.getVictoryDance();
        } else if (cos == CosmeticType.WOOD_SKINS) {
            value = playerData.getWoodSkin();
            if (value == null || value.isEmpty()) {
                WoodSkin def = WoodSkin.getDefault(p);
                return def != null ? def.getIdentifier() : "oak-plank";
            }
        }
        return value;
    }

    /**
     * Set the selected cosmetic for a player.
     * @param p     Player.
     * @param cos   Cosmetic type.
     * @param value Cosmetic value.
     */
    public void setSelectedCosmetic(Player p, CosmeticType<?> cos, String value){
        PlayerData playerData = CosmeticsPlugin.getInstance().getPlayerManager().getPlayerData(p.getUniqueId());
        playerData.setSelectedData(cos, value);
        
        Run.async(playerData::save);
    }

    /**
     * Check if the plugin is running on a proxy.
     * @return true if running on a proxy.
     */
    public boolean isProxy(){
        return Bukkit.getPluginManager().getPlugin("BedWarsProxy") != null ||
                Bukkit.getPluginManager().getPlugin("BWProxy2023") != null;
    }

    public IDatabase getDatabase(){
        return CosmeticsPlugin.getInstance().getRemoteDatabase();
    }
}
