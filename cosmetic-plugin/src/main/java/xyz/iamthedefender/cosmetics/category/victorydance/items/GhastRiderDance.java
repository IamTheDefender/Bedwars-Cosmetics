package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;

import java.util.List;

public class GhastRiderDance extends VictoryDance {

    @Override
    public ItemStack getItem() {
        return XMaterial.GHAST_TEAR.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "ghast-rider";
    }

    @Override
    public String getDisplayName() {
        return "Ghast Rider";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Ride a ghast from the nether!");
    }

    @Override
    public int getPrice() {
        return 15000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.LEGENDARY;
    }

    @Override
    public void execute(Player winner) {
        Ghast ghast = winner.getWorld().spawn(winner.getLocation(), Ghast.class);
        ghast.setAI(false);
        ghast.setInvulnerable(true);
        ghast.addPassenger(winner);
        addEntity(winner, ghast);

        addTask(winner, Run.every((r) -> {
            if (!AbstractShopKeeperSkin.arenas.containsKey(winner.getWorld().getName())) {
                winner.leaveVehicle();
                r.cancel();
                return;
            }

            if (!ghast.getPassengers().contains(winner)) {
                ghast.addPassenger(winner);
            }
        }, 5L));
    }
}