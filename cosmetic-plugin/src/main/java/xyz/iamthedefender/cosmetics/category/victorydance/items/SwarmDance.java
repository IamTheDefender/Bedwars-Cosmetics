package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;

import java.util.List;
import java.util.Random;

public class SwarmDance extends VictoryDance {

    private static final int BAT_COUNT = 12;
    private static final Random RANDOM = new Random();

    @Override
    public ItemStack getItem() {
        return XMaterial.LEATHER.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "swarm";
    }

    @Override
    public String getDisplayName() {
        return "Swarm";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7A swarm of bats circles you!");
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
    public void execute(Player winner) {
        for (int i = 0; i < BAT_COUNT; i++) {
            Bat bat = winner.getWorld().spawn(
                    winner.getLocation().clone().add(
                            (RANDOM.nextDouble() - 0.5) * 4,
                            RANDOM.nextDouble() * 3,
                            (RANDOM.nextDouble() - 0.5) * 4
                    ),
                    Bat.class
            );
            bat.setInvulnerable(true);
            addEntity(winner, bat);
        }

        addTask(winner, Run.every((r) -> {
//            if (!AbstractShopKeeperSkin.arenas.containsKey(winner.getWorld().getName())) {
//                r.cancel();
//                return;
//            }

            winner.getNearbyEntities(6, 5, 6).stream()
                    .filter(e -> e instanceof Bat)
                    .map(e -> (Bat) e)
                    .forEach(bat -> {
                        double angle = RANDOM.nextDouble() * 2 * Math.PI;
                        bat.setVelocity(new Vector(
                                Math.cos(angle) * 0.3,
                                (RANDOM.nextDouble() - 0.5) * 0.2,
                                Math.sin(angle) * 0.3
                        ));
                    });
        }, 10L));
    }
}