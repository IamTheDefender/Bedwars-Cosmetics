package me.defender.cosmetics.api.category.finalkilleffects.items;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.category.finalkilleffects.FinalKillEffect;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.Location;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class BatCruxEffect extends FinalKillEffect {
    @Override
    public ItemStack getItem() {
        return XMaterial.BAT_SPAWN_EGG.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "batcrux";
    }

    @Override
    public String getDisplayName() {
        return "Batcrux";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns many bats at", "&7location of victim!");
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
        Location loc = victim.getLocation();
        Bat bat1 = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
        bat1.setVelocity(bat1.getLocation().getDirection().multiply(2).setY(2));


        Bat bat2 = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
        bat2.setVelocity(bat2.getLocation().getDirection().multiply(1).setY(1));


        Bat bat3 = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
        bat3.setVelocity(bat3.getLocation().getDirection().multiply(1).setY(1));


        Bat bat4 = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
        bat4.setVelocity(bat4.getLocation().getDirection().multiply(2).setY(2));



        new BukkitRunnable(){
            @Override
            public void run() {
                bat1.remove();
                bat2.remove();
                bat3.remove();
                bat4.remove();
            }
        }.runTaskLater(Utility.plugin(), 200L);

    }
}
