package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;

import java.util.List;
import java.util.Random;

public class BlizzardDance extends VictoryDance {

    private static final double RADIUS = 5.0;
    private static final double SPAWN_HEIGHT = 8.0;
    private static final Random RANDOM = new Random();

    @Override
    public ItemStack getItem() {
        return XMaterial.SNOWBALL.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "blizzard";
    }

    @Override
    public String getDisplayName() {
        return "Blizzard";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7A raging blizzard surrounds you!");
    }

    @Override
    public int getPrice() {
        return 6000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.RARE;
    }

    @Override
    public void execute(Player winner) {
        addTask(winner, Run.every((r) -> {
//            if (!AbstractShopKeeperSkin.arenas.containsKey(winner.getWorld().getName())) {
//                r.cancel();
//                return;
//            }

            for (int i = 0; i < 3; i++) {
                double angle = RANDOM.nextDouble() * 2 * Math.PI;
                double offsetX = (RANDOM.nextDouble() * RADIUS) * Math.cos(angle);
                double offsetZ = (RANDOM.nextDouble() * RADIUS) * Math.sin(angle);

                Location spawnLoc = winner.getLocation().clone().add(offsetX, SPAWN_HEIGHT, offsetZ);

                Snowball snowball = winner.getWorld().spawn(spawnLoc, Snowball.class);
                snowball.setVelocity(new Vector(
                        (RANDOM.nextDouble() - 0.5) * 0.3,
                        -0.8,
                        (RANDOM.nextDouble() - 0.5) * 0.3
                ));
                snowball.setShooter(winner);

                addEntity(winner, snowball);
            }
        }, 2L));
    }
}