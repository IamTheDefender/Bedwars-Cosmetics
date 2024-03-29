package me.defender.cosmetics.category.deathcries;

import com.cryptomorin.xseries.XSound;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.cosmetics.CosmeticsType;
import me.defender.cosmetics.api.cosmetics.FieldsType;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.api.cosmetics.category.DeathCry;
import me.defender.cosmetics.util.DebugUtil;
import me.defender.cosmetics.util.StartupUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.NoSuchElementException;

public class DeathCryHandler2023 implements Listener {

    @EventHandler
    public void onPlayerDeath2023(com.tomkeuper.bedwars.api.events.player.PlayerKillEvent e) {
        Player killed = e.getVictim();
        String selected = Cosmetics.getInstance().getApi().getSelectedCosmetic(killed, CosmeticsType.DeathCries);

        boolean isDeathCriesEnabled = Cosmetics.getInstance().getConfig().getBoolean("death-cries.enabled");
        if (!isDeathCriesEnabled) return;

        for (DeathCry deathCry : StartupUtils.deathCryList) {
            if (deathCry.getIdentifier().equals(selected)) {
                if(deathCry.getField(FieldsType.RARITY, killed) == RarityType.NONE) return;
                try {
                    float pitch = Float.parseFloat(String.valueOf(deathCry.getField(FieldsType.PITCH, killed)));
                    float volume = Float.parseFloat(String.valueOf(deathCry.getField(FieldsType.VOLUME, killed)));
                    XSound sound = (XSound) deathCry.getField(FieldsType.SOUND, killed);
                    sound.play(killed.getLocation(), volume, pitch);
                } catch (NoSuchElementException exception){
                    exception.printStackTrace();
                    Bukkit.getLogger().severe(deathCry.getIdentifier() + "Death cry has invalid sound!");
                }
            }
        }
        DebugUtil.addMessage("Executing " + selected + " Death Cry for " + killed.getDisplayName());
    }
}