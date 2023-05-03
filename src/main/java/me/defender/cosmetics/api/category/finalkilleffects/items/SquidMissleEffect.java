package me.defender.cosmetics.api.category.finalkilleffects.items;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import me.defender.cosmetics.api.category.finalkilleffects.FinalKillEffect;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class SquidMissleEffect extends FinalKillEffect {
    @Override
    public ItemStack getItem() {
        return XMaterial.SQUID_SPAWN_EGG.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "squid-missile";
    }

    @Override
    public String getDisplayName() {
        return "Squid Missile";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawn a flying squid at", "&7location of victim's death!");
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
    public void execute(Player killer, Player victim) {
        Location loc = victim.getLocation();
        Squid squid = (Squid) victim.getWorld().spawnEntity(loc, EntityType.SQUID);
         ArmorStand stand = (ArmorStand) victim.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        stand.setGravity(false);
        stand.setPassenger(squid);
        stand.setVisible(false);
        new BukkitRunnable() {
            int i1 = 0;

            public void run() {
                ++this.i1;
                squid.getLocation().setYaw(180.0f);
                stand.eject();
                stand.teleport(stand.getLocation().add(0.0, 0.5, 0.0));
                stand.setPassenger(squid);
                stand.getWorld().spigot().playEffect(stand.getLocation(), Effect.FLAME, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 1, 128);
                victim.playSound(victim.getLocation(), XSound.ENTITY_CHICKEN_EGG.parseSound(), 1.0f, 1.0f);
                if (this.i1 == 13) {
                    final Firework fw = stand.getWorld().spawn(stand.getLocation(), Firework.class);
                    final FireworkMeta fm = fw.getFireworkMeta();
                    fm.addEffect(FireworkEffect.builder().flicker(true).trail(false).with(FireworkEffect.Type.BALL).withColor(Color.BLACK).withFade(Color.BLACK).build());
                    fw.setFireworkMeta(fm);
                }
                if (this.i1 == 25) {
                    stand.remove();
                    squid.remove();
                    this.i1 = 0;
                    this.cancel();
                }
            }
        }.runTaskTimer(Utility.plugin(), 4L, 1L);
    }
}
