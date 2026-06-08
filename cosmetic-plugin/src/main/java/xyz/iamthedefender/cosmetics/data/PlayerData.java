package xyz.iamthedefender.cosmetics.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.database.PlayerCosmeticsData;
import xyz.iamthedefender.cosmetics.menu.data.SortMode;
import xyz.iamthedefender.cosmetics.util.DebugUtil;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@ToString
@EqualsAndHashCode
public class PlayerData {

    private final UUID uuid;

    @Getter
    private final Map<CosmeticType<?>, String> selectedData = new ConcurrentHashMap<>();

    @Setter
    private SortMode sortMode = SortMode.RARITY_LOW_HIGH;
    @Setter
    private boolean ownedFirst = false;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        load();
    }

    public void load() {
        PlayerCosmeticsData data = CosmeticsPlugin.getInstance().getRemoteDatabase().loadPlayerData(uuid);
        if (data == null) {
            return;
        }

        setSelectedData(CosmeticType.BED_DESTROY, data.getBedDestroy());
        setSelectedData(CosmeticType.WOOD_SKINS, data.getWoodSkin());
        setSelectedData(CosmeticType.VICTORY_DANCES, data.getVictoryDance());
        setSelectedData(CosmeticType.SHOPKEEPER_SKINS, data.getShopkeeperSkin());
        setSelectedData(CosmeticType.GLYPHS, data.getGlyph());
        setSelectedData(CosmeticType.PROJECTILE_TRAILS, data.getProjectileTrail());
        setSelectedData(CosmeticType.KILL_MESSAGES, data.getKillMessage());
        setSelectedData(CosmeticType.FINAL_KILL_EFFECTS, data.getFinalKillEffect());
        setSelectedData(CosmeticType.ISLAND_TOPPERS, data.getIslandTopper());
        setSelectedData(CosmeticType.DEATH_CRIES, data.getDeathCry());
        setSelectedData(CosmeticType.SPRAYS, data.getSpray());
    }

    public void setSelectedData(@NotNull CosmeticType<?> type, @NotNull String identifier) {
        if (identifier == null) return;

        selectedData.put(type, identifier);
    }

    public @Nullable String getSelected(CosmeticType<?> type) {
        return  selectedData.get(type);
    }


    public void createData() {
        CosmeticsPlugin.getInstance().getRemoteDatabase().createPlayerData(uuid, toStorageData());
    }

    public void save() {
        DebugUtil.addMessage("Saving player-data for " + uuid.toString());
        CosmeticsPlugin.getInstance().getRemoteDatabase().savePlayerData(uuid, toStorageData());
    }

    public boolean exists() {
        if (getWoodSkin() == null) {
            return false;

        } else return !getWoodSkin().equals("Demo");
    }

    private PlayerCosmeticsData toStorageData() {
        return new PlayerCosmeticsData(
                getWoodSkin(),
                getBedDestroy(),
                getVictoryDance(),
                getShopkeeperSkin(),
                getGlyph(),
                getSpray(),
                getProjectileTrail(),
                getKillMessage(),
                getFinalKillEffect(),
                getIslandTopper(),
                getDeathCry()
        );
    }

    public String getFinalKillEffect() {
        return getSelected(CosmeticType.FINAL_KILL_EFFECTS);
    }

    public String getProjectileTrail() {
        return getSelected(CosmeticType.PROJECTILE_TRAILS);
    }

    public String getBedDestroy() {
        return getSelected(CosmeticType.BED_DESTROY);
    }

    public String getGlyph() {
        return getSelected(CosmeticType.GLYPHS);
    }

    public String getDeathCry() {
        return getSelected(CosmeticType.DEATH_CRIES);
    }

    public String getVictoryDance() {
        return getSelected(CosmeticType.VICTORY_DANCES);
    }

    public String getWoodSkin() {
        return getSelected(CosmeticType.WOOD_SKINS);
    }

    public String getSpray() {
        return getSelected(CosmeticType.SPRAYS);
    }

    public String getKillMessage() {
        return getSelected(CosmeticType.KILL_MESSAGES);
    }

    public String getShopkeeperSkin() {
        return getSelected(CosmeticType.SHOPKEEPER_SKINS);
    }

    public String getIslandTopper() {
        return getSelected(CosmeticType.ISLAND_TOPPERS);
    }

}
