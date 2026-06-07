package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XEntity;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;
import xyz.iamthedefender.cosmetics.util.EntityUtil;

import java.util.List;

public class ChickenRiderDance extends VictoryDance {

    @Override
    public ItemStack getItem() {
        return XMaterial.EGG.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "chicken-rider";
    }

    @Override
    public String getDisplayName() {
        return "Chicken Rider";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Ride a chicken to victory!");
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
        Chicken chicken = winner.getWorld().spawn(winner.getLocation(), Chicken.class);
        EntityUtil.setAI(chicken, false);

        winner.setVelocity(winner.getVelocity());

        try {
            chicken.addPassenger(winner);
        }catch (NoSuchMethodError e) {
            chicken.setPassenger(winner);
        }

        addEntity(winner, chicken);

        addTask(winner, Run.every((r) -> {
//            if (!AbstractShopKeeperSkin.arenas.containsKey(winner.getWorld().getName())) {
//                winner.leaveVehicle();
//                r.cancel();
//                return;
//            }

            try {
                if (!chicken.getPassengers().contains(winner)) {
                    chicken.addPassenger(winner);
                }
            }catch (NoSuchMethodError e) {
                if (chicken.getPassenger() == null || !chicken.getPassenger().getUniqueId().equals(winner.getUniqueId())) {
                    chicken.setPassenger(winner);
                }
            }
        }, 5L));
    }
}