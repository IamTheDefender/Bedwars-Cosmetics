package me.defender.cosmetics.api.category.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.HCore;
import com.hakan.core.particle.Particle;
import com.hakan.core.particle.type.ParticleType;
import me.defender.cosmetics.api.category.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.MathUtil;
import org.bukkit.Location;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class EggExplosionBedDestroy extends BedDestroy {
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
        return "egg-explosion";
    }

    @Override
    public String getDisplayName() {
        return "Egg Explosion";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Explodes the ground with an", "&7explosion of chickens!");
    }

    @Override
    public int getPrice() {
        return 10000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.COMMON;
    }

    @Override
    public void execute1058(Player player, Location bedLocation, ITeam victimTeam) {
        // Define the center point where the eggs should be spawned
        Location center = bedLocation;
        for (int i = 0; i < MathUtil.getRandom(5, 6); i++) {
            // Choose a random direction vector
            Vector direction = new Vector(MathUtil.getRandom(-1, 1), 0, MathUtil.getRandom(-1, 1)).normalize();

            // Spawn an egg entity in the chosen direction
            Egg egg = player.getWorld().spawn(center, Egg.class);
            egg.setVelocity(direction.multiply(1.5)); // Make the egg fly faster in the chosen direction

            // Spawn particle effects at the egg's location
            for (int j = 0; j < 10; j++) {
                HCore.playParticle(egg.getLocation(), new Particle(ParticleType.SPELL_WITCH, 1, center.toVector()));
                HCore.playParticle(egg.getLocation(), new Particle(ParticleType.SPELL_INSTANT, 1, center.toVector()));
            }

            // Schedule a task to run after 5 seconds to spawn a chicken and despawn the egg
            HCore.syncScheduler().run(() -> {
                egg.getWorld().spawnEntity(egg.getLocation(), EntityType.CHICKEN); // Spawn a chicken entity
                egg.remove(); // Remove the egg entity
            });
        }
    }

    @Override
    public void execute2023(Player player, Location bedLocation, com.tomkeuper.bedwars.api.arena.team.ITeam victimTeam) {
        // Define the center point where the eggs should be spawned
        Location center = bedLocation;
        for (int i = 0; i < MathUtil.getRandom(5, 6); i++) {
            // Choose a random direction vector
            Vector direction = new Vector(MathUtil.getRandom(-1, 1), 0, MathUtil.getRandom(-1, 1)).normalize();

            // Spawn an egg entity in the chosen direction
            Egg egg = player.getWorld().spawn(center, Egg.class);
            egg.setVelocity(direction.multiply(1.5)); // Make the egg fly faster in the chosen direction

            // Spawn particle effects at the egg's location
            for (int j = 0; j < 10; j++) {
                HCore.playParticle(egg.getLocation(), new Particle(ParticleType.SPELL_WITCH, 1, center.toVector()));
                HCore.playParticle(egg.getLocation(), new Particle(ParticleType.SPELL_INSTANT, 1, center.toVector()));
            }

            // Schedule a task to run after 5 seconds to spawn a chicken and despawn the egg
            HCore.syncScheduler().run(() -> {
                egg.getWorld().spawnEntity(egg.getLocation(), EntityType.CHICKEN); // Spawn a chicken entity
                egg.remove(); // Remove the egg entity
            });
        }
    }
}