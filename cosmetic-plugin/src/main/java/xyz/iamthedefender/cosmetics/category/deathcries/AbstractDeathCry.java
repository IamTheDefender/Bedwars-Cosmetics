package xyz.iamthedefender.cosmetics.category.deathcries;

import com.cryptomorin.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.DeathCry;
import xyz.iamthedefender.cosmetics.api.event.AbstractListener;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.NoSuchElementException;

public class AbstractDeathCry extends AbstractListener {

    public void execute(Player victim) {
        if (!StartupUtils.isCosmeticEnabled(CosmeticType.DEATH_CRIES)) return;

        DeathCry deathCry = getSelectedCosmetic(victim, CosmeticType.DEATH_CRIES);

        if (deathCry == null || deathCry.getField(FieldsType.RARITY, victim) == RarityType.NONE) return;

        try {
            float pitch = Float.parseFloat(String.valueOf(deathCry.getField(FieldsType.PITCH, victim)));
            float volume = Float.parseFloat(String.valueOf(deathCry.getField(FieldsType.VOLUME, victim)));
            XSound sound = (XSound) deathCry.getField(FieldsType.SOUND, victim);
            sound.play(victim.getLocation(), volume, pitch);
        } catch (NoSuchElementException exception) {
            exception.printStackTrace();
            Bukkit.getLogger().severe(deathCry.getIdentifier() + ": unable to find sound with ID: " + deathCry.getField(FieldsType.SOUND, victim));
        }
    }

}
