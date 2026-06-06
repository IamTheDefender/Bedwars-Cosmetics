package xyz.iamthedefender.cosmetics.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticRegistry;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.*;
import xyz.iamthedefender.cosmetics.api.database.PlayerOwnedCosmeticsData;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class PlayerOwnedData {
    private final UUID uuid;
    @Setter
    private int bedDestroy, deathCry, finalKillEffect, glyph, islandTopper, killMessage, projectileTrail, shopkeeperSkin, spray, victoryDance, woodSkin;

    public PlayerOwnedData(UUID uuid) {
        this.uuid = uuid;
        load();
    }

    public void load() {
        PlayerOwnedCosmeticsData data = CosmeticsPlugin.getInstance().getRemoteDatabase().loadOwnedData(uuid);
        if (data == null) {
            demo();
            return;
        }
        bedDestroy = data.getBedDestroy();
        deathCry = data.getDeathCry();
        finalKillEffect = data.getFinalKillEffect();
        glyph = data.getGlyph();
        islandTopper = data.getIslandTopper();
        killMessage = data.getKillMessage();
        projectileTrail = data.getProjectileTrail();
        shopkeeperSkin = data.getShopkeeperSkin();
        spray = data.getSpray();
        victoryDance = data.getVictoryDance();
        woodSkin = data.getWoodSkin();
    }

    public void demo() {
        CosmeticsPlugin.getInstance().getRemoteDatabase().createOwnedData(uuid, toStorageData());
    }

    public void save() {
        CosmeticsPlugin.getInstance().getRemoteDatabase().saveOwnedData(uuid, toStorageData());
    }


    public void updateOwned() {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return;
        }

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
        for (BedDestroy destroy : CosmeticRegistry.getByCategory(CosmeticType.BED_DESTROY)) {
            if (hasPermission(player, CosmeticType.BED_DESTROY, destroy.getIdentifier())) {
                this.setBedDestroy(this.getBedDestroy() + 1);
            }
        }
        for (DeathCry deathCr : CosmeticRegistry.getByCategory(CosmeticType.DEATH_CRIES)) {
            if (hasPermission(player, CosmeticType.DEATH_CRIES, deathCr.getIdentifier())) {
                this.setDeathCry(this.getDeathCry() + 1);
            }
        }
        for (FinalKillEffect killEffect : CosmeticRegistry.getByCategory(CosmeticType.FINAL_KILL_EFFECTS)) {
            if (hasPermission(player, CosmeticType.FINAL_KILL_EFFECTS, killEffect.getIdentifier())) {
                this.setFinalKillEffect(this.getFinalKillEffect() + 1);
            }
        }
        for (Glyph glyph : CosmeticRegistry.getByCategory(CosmeticType.GLYPHS)) {
            if (hasPermission(player, CosmeticType.GLYPHS, glyph.getIdentifier())) {
                this.setGlyph(this.getGlyph() + 1);
            }
        }
        for (IslandTopper topper : CosmeticRegistry.getByCategory(CosmeticType.ISLAND_TOPPERS)) {
            if (hasPermission(player, CosmeticType.ISLAND_TOPPERS, topper.getIdentifier())) {
                this.setIslandTopper(this.getIslandTopper() + 1);
            }
        }
        for (KillMessage message : CosmeticRegistry.getByCategory(CosmeticType.KILL_MESSAGES)) {
            if (hasPermission(player, CosmeticType.KILL_MESSAGES, message.getIdentifier())) {
                this.setKillMessage(this.getKillMessage() + 1);
            }
        }


        for (ProjectileTrail trail : CosmeticRegistry.getByCategory(CosmeticType.PROJECTILE_TRAILS)) {
            if (hasPermission(player, CosmeticType.PROJECTILE_TRAILS, trail.getIdentifier())) {
                this.setProjectileTrail(this.getProjectileTrail() + 1);
            }
        }

        for (ShopKeeperSkin shopKeeperSkin : CosmeticRegistry.getByCategory(CosmeticType.SHOPKEEPER_SKINS)) {
            if (hasPermission(player, CosmeticType.SHOPKEEPER_SKINS, shopKeeperSkin.getIdentifier())) {
                this.setShopkeeperSkin(this.getShopkeeperSkin() + 1);
            }
        }

        for (Spray spray1 : CosmeticRegistry.getByCategory(CosmeticType.SPRAYS)) {
            if (hasPermission(player, CosmeticType.SPRAYS, spray1.getIdentifier())) {
                this.setSpray(this.getSpray() + 1);
            }
        }

        for (VictoryDance dance : CosmeticRegistry.getByCategory(CosmeticType.VICTORY_DANCES)) {
            if (hasPermission(player, CosmeticType.VICTORY_DANCES, dance.getIdentifier())) {
                this.setVictoryDance(this.getVictoryDance() + 1);
            }
        }

        for (WoodSkin skin : CosmeticRegistry.getByCategory(CosmeticType.WOOD_SKINS)) {
            if (hasPermission(player, CosmeticType.WOOD_SKINS, skin.getIdentifier())) {
                this.setWoodSkin(this.getWoodSkin() + 1);
            }
        }
    }

    private boolean hasPermission(Player player, CosmeticType<?> type, String identifier) {
        return player.hasPermission(type.getPermissionFormat() + "." + identifier)
                || player.hasPermission(type.getPermissionFormat() + ".*");
    }

    private PlayerOwnedCosmeticsData toStorageData() {
        return new PlayerOwnedCosmeticsData(
                bedDestroy,
                deathCry,
                finalKillEffect,
                glyph,
                islandTopper,
                killMessage,
                projectileTrail,
                shopkeeperSkin,
                spray,
                victoryDance,
                woodSkin
        );
    }
}
