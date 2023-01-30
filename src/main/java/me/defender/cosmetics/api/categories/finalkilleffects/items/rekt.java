package me.defender.cosmetics.api.categories.finalkilleffects.items;

import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.api.categories.finalkilleffects.FinalKillEffect;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.utils.Utility;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class rekt extends FinalKillEffect {
    @Override
    public ItemStack getItem() {
        return XMaterial.OAK_SIGN.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "rekt";
    }

    @Override
    public String getDisplayName() {
        return "Rekt";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns a rekt sign at the", "&7location of victim");
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
    public void execute(Player killer, Player victim) {
        ArmorStand stand = (ArmorStand) victim.getWorld().spawnEntity(victim.getEyeLocation(), EntityType.ARMOR_STAND);
        stand.setSmall(true);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setCustomNameVisible(true);
        stand.setCustomName(ColorUtil.colored("&6" + killer.getDisplayName() + " &ehas #rekt &6" + victim.getDisplayName()
                + "&ehere"));

        new BukkitRunnable() {
            @Override
            public void run() {
                stand.remove();
            }
        }.runTaskLater(Utility.plugin(), 200L);
    }
}
