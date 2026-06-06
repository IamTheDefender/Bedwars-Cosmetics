package xyz.iamthedefender.cosmetics.support.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.CosmeticsAPI;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticRegistry;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.data.PlayerOwnedData;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

public class CosmeticsPlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "bwc";
    }

    @Override
    public @NotNull String getAuthor() {
        return "IamTheDefender";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.2";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String placeholder) {
        CosmeticsAPI api = CosmeticsPlugin.getInstance().getApi();
        PlayerOwnedData ownedData = CosmeticsPlugin.getInstance().getPlayerManager().getPlayerOwnedData(player.getUniqueId());
        switch (placeholder.toLowerCase()) {
            case "selected_dc":
                return api.getSelectedCosmetic(player, CosmeticType.DEATH_CRIES);
            case "selected_vd":
                return api.getSelectedCosmetic(player, CosmeticType.VICTORY_DANCES);
            case "selected_spray":
                return api.getSelectedCosmetic(player, CosmeticType.SPRAYS);
            case "selected_km":
                return api.getSelectedCosmetic(player, CosmeticType.KILL_MESSAGES);
            case "selected_skin":
                return api.getSelectedCosmetic(player, CosmeticType.SHOPKEEPER_SKINS);
            case "selected_ws":
                return api.getSelectedCosmetic(player, CosmeticType.WOOD_SKINS);
            case "selected_glyph":
                return api.getSelectedCosmetic(player, CosmeticType.GLYPHS);
            case "selected_bbe":
                return api.getSelectedCosmetic(player, CosmeticType.BED_DESTROY);
            case "selected_pt":
                return api.getSelectedCosmetic(player, CosmeticType.PROJECTILE_TRAILS);
            case "selected_finalkill":
                return api.getSelectedCosmetic(player, CosmeticType.FINAL_KILL_EFFECTS);
            case "selected_islandtopper":
                return api.getSelectedCosmetic(player, CosmeticType.ISLAND_TOPPERS);
            case "owned_vd":
                return String.valueOf(ownedData.getVictoryDance());
            case "owned_ws":
                return String.valueOf(ownedData.getWoodSkin());
            case "owned_shopkeeper":
                return String.valueOf(ownedData.getShopkeeperSkin());
            case "owned_pt":
                return String.valueOf(ownedData.getProjectileTrail());
            case "owned_km":
                return String.valueOf(ownedData.getKillMessage());
            case "owned_gly":
                return String.valueOf(ownedData.getGlyph());
            case "owned_finalkill":
                return String.valueOf(ownedData.getFinalKillEffect());
            case "owned_dc":
                return String.valueOf(ownedData.getDeathCry());
            case "owned_bbe":
                return String.valueOf(ownedData.getBedDestroy());
            case "owned_spray":
                return String.valueOf(ownedData.getSpray());
            case "owned_island":
                return String.valueOf(ownedData.getIslandTopper());
            case "total_vd":
                return CosmeticRegistry.getByCategory(CosmeticType.VICTORY_DANCES).size() + "";
            case "total_bbe":
                return CosmeticRegistry.getByCategory(CosmeticType.BED_DESTROY).size() + "";
            case "total_dc":
                return CosmeticRegistry.getByCategory(CosmeticType.DEATH_CRIES).size() + "";
            case "total_finalkill":
                return CosmeticRegistry.getByCategory(CosmeticType.FINAL_KILL_EFFECTS).size() + "";
            case "total_shopkeeper":
                return CosmeticRegistry.getByCategory(CosmeticType.SHOPKEEPER_SKINS).size() + "";
            case "total_ws":
                return CosmeticRegistry.getByCategory(CosmeticType.WOOD_SKINS).size() + "";
            case "total_km":
                return CosmeticRegistry.getByCategory(CosmeticType.KILL_MESSAGES).size() + "";
            case "total_pt":
                return CosmeticRegistry.getByCategory(CosmeticType.PROJECTILE_TRAILS).size() + "";
            case "total_spray":
                return CosmeticRegistry.getByCategory(CosmeticType.SPRAYS).size() + "";
            case "total_island":
                return CosmeticRegistry.getByCategory(CosmeticType.ISLAND_TOPPERS).size() + "";
            default:
                return null;
        }
    }
}
