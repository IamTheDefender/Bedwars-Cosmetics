package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;

import java.util.List;
import java.util.Random;

public class ExplodingBunniesDance extends VictoryDance {

    private static final Random RANDOM = new Random();

    @Override
    public ItemStack getItem() {
        return XMaterial.RABBIT_FOOT.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "exploding-bunnies";
    }

    @Override
    public String getDisplayName() {
        return "Exploding Bunnies";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Bunnies explode out from under you!");
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
        addTask(winner, Run.every((r) -> {
//            if (!AbstractShopKeeperSkin.arenas.containsKey(winner.getWorld().getName())) {
//                r.cancel();
//                return;
//            }

            Rabbit.Type[] types = Rabbit.Type.values();
            Rabbit rabbit = winner.getWorld().spawn(winner.getLocation(), Rabbit.class);
            rabbit.setRabbitType(types[RANDOM.nextInt(types.length)]);
            rabbit.setInvulnerable(true);

            double angle = RANDOM.nextDouble() * 2 * Math.PI;
            rabbit.setVelocity(new Vector(
                    Math.cos(angle) * (0.4 + RANDOM.nextDouble() * 0.4),
                    0.6 + RANDOM.nextDouble() * 0.5,
                    Math.sin(angle) * (0.4 + RANDOM.nextDouble() * 0.4)
            ));

            addEntity(winner, rabbit);
        }, 8L));
    }
}