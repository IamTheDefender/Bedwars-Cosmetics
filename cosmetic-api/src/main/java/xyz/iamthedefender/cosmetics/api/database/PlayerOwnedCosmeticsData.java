package xyz.iamthedefender.cosmetics.api.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerOwnedCosmeticsData {
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
}
