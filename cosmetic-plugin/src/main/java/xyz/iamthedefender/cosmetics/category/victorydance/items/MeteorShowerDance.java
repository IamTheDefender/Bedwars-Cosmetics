package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;

import java.util.List;
import java.util.Random;

public class MeteorShowerDance extends VictoryDance {

    private static final double RADIUS = 6.0;
    private static final double SPAWN_HEIGHT = 15.0;
    private static final Random RANDOM = new Random();

    @Override
    public ItemStack getItem() {
        return XMaterial.FIRE_CHARGE.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "meteor-shower";
    }

    @Override
    public String getDisplayName() {
        return "Meteor Shower";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Meteors will rain from the sky.");
    }

    @Override
    public int getPrice() {
        return 8000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.RARE;
    }

    @Override
    public void execute(Player winner) {
        addTask(winner, Run.every((r) -> {
            if (!AbstractShopKeeperSkin.arenas.containsKey(winner.getWorld().getName())) {
                r.cancel();
                return;
            }


            double angle = RANDOM.nextDouble() * 2 * Math.PI;
            double offsetX = RADIUS * Math.cos(angle);
            double offsetZ = RADIUS * Math.sin(angle);

            Location base = winner.getLocation();
            Location spawnLoc = base.clone().add(offsetX, SPAWN_HEIGHT, offsetZ);

            double dx = spawnLoc.getX() - base.getX();
            double dz = spawnLoc.getZ() - base.getZ();
            double dy = -SPAWN_HEIGHT;

            double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
            Vector velocity = new Vector(dx / length, dy / length, dz / length).multiply(0.9);

            Fireball fireball = winner.getWorld().spawn(spawnLoc, Fireball.class);
            fireball.setDirection(velocity);
            fireball.setIsIncendiary(false);
            fireball.setYield(0f);
            fireball.setShooter(winner);

            addEntity(winner, fireball);
        }, 4L));
    }

}