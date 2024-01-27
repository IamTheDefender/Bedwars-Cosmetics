package me.defender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.cosmetics.category.VictoryDance;
import me.defender.cosmetics.category.victorydance.util.UsefulUtilsVD;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.util.Utility;
import me.defender.cosmetics.category.shopkeeperskins.ShopKeeperHandler1058;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class DragonRiderDance extends VictoryDance {
    @Override
    public ItemStack getItem() {
        return XMaterial.DRAGON_EGG.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "dragon-rider";
    }

    @Override
    public String getDisplayName() {
        return "Dragon Rider";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Ride a Dragon capable of", "&7destroying the map.");
    }

    @Override
    public int getPrice() {
        return 100000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.LEGENDARY;
    }

    @Override
    public void execute(Player winner) {
        // spawn the dragon and set the winner as the passenger
        EnderDragon dragon = (EnderDragon) winner.getWorld().spawnEntity(winner.getLocation(), EntityType.ENDER_DRAGON);
        dragon.setPassenger(winner);
        dragon.setNoDamageTicks(Integer.MAX_VALUE);
        // create an armorstand as the fake target
        ArmorStand stand = (ArmorStand) winner.getWorld().spawnEntity(winner.getLocation(), EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setMetadata("FAKE_TARGET", new FixedMetadataValue(Utility.Cosmetics.getInstance(), ""));
        // create a task to move the dragon towards the fake target
        new BukkitRunnable() {
            public void run() {
                Location original = winner.getEyeLocation();
                Vector direction = winner.getEyeLocation().clone().getDirection().normalize().multiply(20);
                Location newLocation = original.add(direction);
                stand.teleport(newLocation);
                for (Block block : UsefulUtilsVD.getBlocksInRadius(dragon.getLocation(), 10, false)) {
                    block.setType(Material.AIR);
                }
                if (!ShopKeeperHandler1058.arenas.containsKey(winner.getWorld().getName())) {
                    this.cancel();
                    stand.remove();
                    dragon.remove();
                }
                if(dragon.getPassenger() != winner){
                    dragon.setPassenger(winner);
                }
                Fireball fireball = winner.getWorld().spawn(original, Fireball.class);
                fireball.setDirection(direction);
            }
        }.runTaskTimer(Utility.Cosmetics.getInstance(), 0L, 1L);
    }
}
