package me.defender.cosmetics.api.categories.deathcries.preview;

import com.cryptomorin.xseries.XSound;
import me.defender.cosmetics.api.categories.deathcries.DeathCry;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.configuration.ConfigUtils;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.utils.StartupUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DeathCryPreview {


    public void preview(Player player, String previewID){
        for(DeathCry cry : StartupUtils.deathCryList){
            if(cry.getIdentifier().equals(previewID)){
                if(cry.getField(FieldsType.RARITY, player) == null || cry.getField(FieldsType.RARITY, player) == RarityType.NONE) return;
                float pitch = Float.parseFloat(String.valueOf(cry.getField(FieldsType.PITCH, player)));
                float volume = Float.parseFloat(String.valueOf(cry.getField(FieldsType.VOLUME, player)));
                try {
                    XSound sound = (XSound) cry.getField(FieldsType.SOUND, player);
                    sound.play(player, volume, pitch);
                }catch (IllegalArgumentException | NullPointerException e){
                    Bukkit.getLogger().severe("Looks like there is an error with the sound for " + cry.getIdentifier() + " death cry: " + e.getMessage());
                }
                return;
            }
        }
    }
}
