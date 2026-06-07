package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;

import java.util.List;

public class SuperSheepDance extends VictoryDance {

    private static final int SHEEP_COUNT = 6;
    private static final double RADIUS = 3.0;

    @Override
    public ItemStack getItem() {
        return XMaterial.WHITE_WOOL.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "super-sheep";
    }

    @Override
    public String getDisplayName() {
        return "Super Sheep";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7A flock of super sheep surrounds you!");
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
        for (int i = 0; i < SHEEP_COUNT; i++) {
            double angle = (2 * Math.PI / SHEEP_COUNT) * i;
            double offsetX = RADIUS * Math.cos(angle);
            double offsetZ = RADIUS * Math.sin(angle);

            Sheep sheep = winner.getWorld().spawn(
                    winner.getLocation().clone().add(offsetX, 0, offsetZ),
                    Sheep.class
            );
            sheep.setColor(org.bukkit.DyeColor.values()[i % org.bukkit.DyeColor.values().length]);
            sheep.setInvulnerable(true);
            addEntity(winner, sheep);
        }

        addTask(winner, Run.every((r) -> {
//            if (!AbstractShopKeeperSkin.arenas.containsKey(winner.getWorld().getName())) {
//                r.cancel();
//                return;
//            }

            winner.getNearbyEntities(RADIUS + 2, 3, RADIUS + 2).stream()
                    .filter(e -> e instanceof Sheep)
                    .map(e -> (Sheep) e)
                    .forEach(sheep -> {
                        if (sheep.isOnGround()) {
                            sheep.setVelocity(new Vector(
                                    (Math.random() - 0.5) * 0.4,
                                    0.5 + Math.random() * 0.3,
                                    (Math.random() - 0.5) * 0.4
                            ));
                        }
                    });
        }, 10L));
    }
}