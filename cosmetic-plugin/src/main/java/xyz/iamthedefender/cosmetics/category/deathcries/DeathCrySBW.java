package xyz.iamthedefender.cosmetics.category.deathcries;

import com.cryptomorin.xseries.XSound;
import com.tomkeuper.bedwars.api.events.player.PlayerKillEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerKilledEvent;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.DeathCry;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.NoSuchElementException;

public class DeathCrySBW implements Listener {

    @EventHandler
    public void onPlayerDeathSBW(BedwarsPlayerKilledEvent event) {
        Player killed = event.getPlayer();
        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(killed, CosmeticsType.DeathCries);

        boolean isDeathCriesEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("death-cries.enabled");
        if (!isDeathCriesEnabled) return;

        for (DeathCry deathCry : StartupUtils.deathCryList) {
            if (deathCry.getIdentifier().equals(selected)) {

                try {
                    float pitch = Float.parseFloat(String.valueOf(deathCry.getField(FieldsType.PITCH, killed)));
                    float volume = Float.parseFloat(String.valueOf(deathCry.getField(FieldsType.VOLUME, killed)));
                    XSound sound = (XSound) deathCry.getField(FieldsType.SOUND, killed);
                    sound.play(killed.getLocation(), volume, pitch);
                } catch (NoSuchElementException exception){
                    exception.printStackTrace();
                    Bukkit.getLogger().severe(deathCry.getIdentifier() + "Death cry has invalid sound!");
                } finally {
                    try {
                        // Reflective call — method exists only on ScreamingBedWars 0.2.41+
                        event.getClass().getMethod("setPlaySound", boolean.class).invoke(event, false);
                    } catch (Throwable throwable) {
                        CosmeticsPlugin.getInstance().getLogger().severe("Failed to disable default sounds from ScreamingBedWars, please make sure you are using ScreamingBedWars 0.2.41-SNAPSHOT or higher!");
                    }
                }
            }
        }
        DebugUtil.addMessage("Executing " + selected + " Death Cry for " + killed.getDisplayName());
    }

}
