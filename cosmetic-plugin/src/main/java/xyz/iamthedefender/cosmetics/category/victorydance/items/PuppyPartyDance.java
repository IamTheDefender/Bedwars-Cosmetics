package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PuppyPartyDance extends VictoryDance {

    private static final int WOLF_COUNT = 6;
    private static final double RADIUS = 3.0;
    private static final Random RANDOM = new Random();

    @Override
    public ItemStack getItem() {
        return XMaterial.BONE.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "puppy-party";
    }

    @Override
    public String getDisplayName() {
        return "Puppy Party";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7A pack of puppies celebrates with you!");
    }

    @Override
    public int getPrice() {
        return 7500;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.RARE;
    }

    @Override
    public void execute(Player winner) {
        List<Wolf> wolves = new ArrayList<>();

        for (int i = 0; i < WOLF_COUNT; i++) {
            double angle = (2 * Math.PI / WOLF_COUNT) * i;
            Wolf wolf = winner.getWorld().spawn(
                    winner.getLocation().clone().add(
                            RADIUS * Math.cos(angle), 0, RADIUS * Math.sin(angle)
                    ),
                    Wolf.class
            );
            wolf.setTamed(true);
            wolf.setOwner(winner);
            wolf.setInvulnerable(true);
            wolf.setSitting(false);
            wolves.add(wolf);
            addEntity(winner, wolf);
        }

        final long[] tick = {0};

        addTask(winner, Run.every((r) -> {
//            if (!AbstractShopKeeperSkin.arenas.containsKey(winner.getWorld().getName())) {
//                r.cancel();
//                return;
//            }

            tick[0]++;
            double angle = (tick[0] * Math.PI / 20.0);

            for (int i = 0; i < wolves.size(); i++) {
                Wolf wolf = wolves.get(i);
                if (!wolf.isValid()) continue;

                double wolfAngle = angle + (2 * Math.PI / WOLF_COUNT) * i;
                double targetX = winner.getLocation().getX() + RADIUS * Math.cos(wolfAngle);
                double targetZ = winner.getLocation().getZ() + RADIUS * Math.sin(wolfAngle);

                double dx = targetX - wolf.getLocation().getX();
                double dz = targetZ - wolf.getLocation().getZ();
                wolf.setVelocity(new Vector(dx * 0.3, 0, dz * 0.3));

                if (tick[0] % 15 == (i * 3L) % 15) {
                    wolf.setVelocity(wolf.getVelocity().setY(0.4));
                }
            }
        }, 1L));
    }
}