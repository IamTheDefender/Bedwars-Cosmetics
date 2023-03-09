package me.defender.cosmetics.api.category.victorydances.items;

import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.HCore;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.api.category.victorydances.VictoryDance;
import me.defender.cosmetics.api.category.shopkeeperskins.ShopKeeperHandler;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class witherRider extends VictoryDance {
    @Override
    public ItemStack getItem() {
        return XMaterial.NETHER_STAR.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "wither-rider";
    }

    @Override
    public String getDisplayName() {
        return "Wither Rider";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Ride a wither straight from the", "&7nether - click to shoot Wither", "&7skulls.");
    }

    @Override
    public int getPrice() {
        return 25000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.EPIC;
    }

    @Override
    public void execute(Player winner) {
        Wither wither = (Wither) winner.getWorld().spawnEntity(winner.getLocation(), EntityType.WITHER);
        wither.setPassenger(winner);
        wither.setMetadata("VD", new FixedMetadataValue(Utility.plugin(), ""));
        wither.setCustomName(ColorUtil.colored("&a" + winner.getName() + "'s Wither!"));
        wither.setNoDamageTicks(Integer.MAX_VALUE);
        new BukkitRunnable() {
            public void run() {
                if (ShopKeeperHandler.arenas.containsKey(winner.getWorld().getName())) {
                    if(wither.getPassenger() != winner){
                        wither.setPassenger(winner);
                    }
                    final Vector direction = winner.getEyeLocation().clone().getDirection().normalize().multiply(0.5);
                    wither.setVelocity(direction);
                    wither.setTarget(null);
                }
                else {
                    wither.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer(Utility.plugin(), 0L, 1L);


        // Event
        HCore.registerEvent(PlayerInteractEvent.class).consume((event -> {
            Player player = event.getPlayer();
            if (player.getVehicle() instanceof Wither) {
                final WitherSkull witherSkull = player.getWorld().spawn(player.getEyeLocation().clone().add(player.getEyeLocation().getDirection().normalize().multiply(2)), WitherSkull.class);
                witherSkull.setShooter(player);
                witherSkull.setVelocity(player.getEyeLocation().clone().getDirection().normalize().multiply(3));
            }
        }));
    }
}
