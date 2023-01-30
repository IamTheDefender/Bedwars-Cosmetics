package me.defender.cosmetics.api.categories.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.api.categories.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.enums.RarityType;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class hologram extends BedDestroy {
    @Override
    public ItemStack getItem() {
        return new ItemStack(XMaterial.matchXMaterial("STICK").get().parseMaterial());
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "hologram";
    }

    @Override
    public String getDisplayName() {
        return "Hologram";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns a hologram at the","&7location of bed");
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
    public void execute(Player player, Location bedLocation, ITeam victimTeam) {
        ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(bedLocation.add(0,1,0), EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setCustomName(ColorUtil.colored("&c" + victimTeam + "'s Bed was destroyed by " + player.getDisplayName()));
        stand.setCustomNameVisible(true);
    }
}
