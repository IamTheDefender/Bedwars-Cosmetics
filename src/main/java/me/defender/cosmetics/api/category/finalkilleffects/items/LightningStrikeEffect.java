package me.defender.cosmetics.api.category.finalkilleffects.items;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.HCore;
import me.defender.cosmetics.api.category.finalkilleffects.FinalKillEffect;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.Location;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class LightningStrikeEffect extends FinalKillEffect {
    @Override
    public ItemStack getItem() {
        return XMaterial.STRING.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "lightning-strike";
    }

    @Override
    public String getDisplayName() {
        return "Lightning Strike";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns a thunder strike at", "&7location of victim!");
    }

    @Override
    public int getPrice() {
        return 5000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.COMMON;
    }

    @Override
    public void execute(Player killer, Player victim, Location location, boolean onlyVictim) {
        if (!onlyVictim) {
            location.getWorld().strikeLightningEffect(location);
        } else {
            // Create a non-NMS lightning entity
            LightningStrike lightning = location.getWorld().strikeLightningEffect(location);
            Utility.entityForPlayerOnly(lightning, victim);
        }
    }
}