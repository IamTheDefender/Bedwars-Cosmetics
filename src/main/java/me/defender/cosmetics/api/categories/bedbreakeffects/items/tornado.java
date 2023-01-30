package me.defender.cosmetics.api.categories.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XSound;
import com.hakan.core.HCore;
import com.hakan.core.particle.Particle;
import me.defender.cosmetics.api.categories.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.utils.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class tornado extends BedDestroy {
    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.STRING);
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "tornado";
    }

    @Override
    public String getDisplayName() {
        return "Tornado";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Spawns a tornado at the","&7location of bed.");
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
    public void execute(Player player, Location bedLocation, ITeam victimTeam) {
        player.playSound(bedLocation, XSound.ENTITY_GENERIC_EXPLODE.parseSound(), 1.0f, 1.0f);
        new BukkitRunnable() {
            int angle = 0;

            public void run() {
                final int max_height = 7;
                final double max_radius = 3.0;
                final int lines = 4;
                final double height_increasement = 0.25;
                final double radius_increasement = max_radius / max_height;
                for (int l = 0; l < lines; ++l) {
                    for (double y = 0.0; y < max_height; y += height_increasement) {
                        double radius = y * radius_increasement;
                        double x = Math.cos(Math.toRadians(360 / lines * l + y * 30.0 - this.angle)) * radius;
                        double z = Math.sin(Math.toRadians(360 / lines * l + y * 30.0 - this.angle)) * radius;
                        HCore.playParticle(bedLocation.clone().add(x,y,z), new Particle(com.hakan.core.particle.type.ParticleType.CLOUD, 1, 0.01, new Vector(0.0f, 0.0f, 0.0f)));
                    }
                }
                ++this.angle;
                if (this.angle == 70) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Utility.plugin(), 2L, 0L);
    }
}
