package me.defender.cosmetics.FinalKillEffects;

import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import me.defender.cosmetics.BedBreakEffect.ParticleEffect;
import me.defender.cosmetics.VictoryDances.UsefulUtilsVD;
import me.defender.api.utils.util;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class FinalKillEffectsUtil {

    // All the methods for sending the effect!


    public static void squidMissile(final Player p, final Location loc) {
        final Squid squid = (Squid) p.getWorld().spawnEntity(loc, EntityType.SQUID);
        final ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
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
                p.playSound(p.getLocation(), util.egg_pop, 1.0f, 1.0f);
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
        }.runTaskTimer(util.plugin(), 4L, 1L);
    }

    public static void spawnFirework(Player p, Location loc) {

        UsefulUtilsVD.spawnFireWorks(p, 1, Color.RED, Color.GREEN, loc);
    }

    public static void spawnLightStrike(Location loc) {
        loc.getWorld().strikeLightningEffect(loc);
    }

    public static void heartAura(Location loc) {
        ParticleEffect.HEART.display(0.0F, 0.0F, 0.0F, 0.01F, 100, loc, 16.0D);
        ParticleEffect.HEART.display(0.0F, 0.1F, 0.0F, 0.01F, 100, loc, 16.0D);
        ParticleEffect.HEART.display(0.0F, 0.2F, 0.0F, 0.01F, 100, loc, 16.0D);
        ParticleEffect.HEART.display(0.0F, 0.3F, 0.1F, 0.01F, 100, loc, 16.0D);
        ParticleEffect.HEART.display(0.0F, 0.4F, 0.3F, 0.01F, 100, loc, 16.0D);
        ParticleEffect.HEART.display(0.4F, 0.5F, 0.0F, 0.01F, 100, loc, 16.0D);
        ParticleEffect.HEART.display(0.1F, 0.0F, 0.0F, 0.01F, 100, loc, 16.0D);
        ParticleEffect.HEART.display(0.2F, 0.3F, 0.0F, 0.01F, 100, loc, 16.0D);
    }

    public static void burnigShoes(Player p) {
        final Location loc = p.getPlayer().getLocation();
        (new BukkitRunnable() {
            double t = 0.0D;

            public void run() {
                this.t += 0.3D;
                for (double phi = 0.0D; phi <= 6.0D; phi += 1.5D) {
                    double x = 0.11D * (12.5D - this.t) * Math.cos(this.t + phi);
                    double y = 0.23D * this.t;
                    double z = 0.11D * (12.5D - this.t) * Math.sin(this.t + phi);
                    loc.add(x, y, z);
                    ParticleEffect.FLAME.display(0.0F, 0.0F, 0.0F, 0.01F, 100, loc, 16.0D);
                    loc.subtract(x, y, z);
                    if (this.t >= 12.5D) {
                        loc.add(x, y, z);
                        if (phi > Math.PI)
                            cancel();
                    }
                }
            }
        }).runTaskTimer(util.plugin(), 1L, 1L);
    }

    public static void rekt(Player victim, String attacker) {
        ArmorStand stand = (ArmorStand) victim.getWorld().spawnEntity(victim.getEyeLocation(), EntityType.ARMOR_STAND);
        stand.setSmall(true);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setCustomNameVisible(true);
        stand.setCustomName(util.color("&6" + attacker + " &ehas #rekt &6" + victim.getDisplayName()
                + "&ehere"));

        new BukkitRunnable() {
            @Override
            public void run() {
                stand.remove();
            }
        }.runTaskLater(util.plugin(), 200L);
    }

    public static void batCrux(Player victim){
        Location loc = victim.getLocation();
        Bat bat1 = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
        bat1.setVelocity(bat1.getLocation().getDirection().multiply(2).setY(2));


        Bat bat2 = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
        bat2.setVelocity(bat2.getLocation().getDirection().multiply(1).setY(1));


        Bat bat3 = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
        bat3.setVelocity(bat3.getLocation().getDirection().multiply(1).setY(1));


        Bat bat4 = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
        bat4.setVelocity(bat4.getLocation().getDirection().multiply(2).setY(2));



        new BukkitRunnable(){
            @Override
            public void run() {
                bat1.remove();
                bat2.remove();
                bat3.remove();
                bat4.remove();
            }
        }.runTaskLater(util.plugin(), 200L);

    }

    public static void playTornado(final Player user, Location loc) {
        final Location location = loc;

        FastParticle.spawnParticle(user, ParticleType.SMOKE_LARGE, location, 1);
        user.playSound(location, util.Explode, 1.0f, 1.0f);
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
                        final double radius = y * radius_increasement;
                        final double x = Math.cos(Math.toRadians(360 / lines * l + y * 30.0 - this.angle)) * radius;
                        final double z = Math.sin(Math.toRadians(360 / lines * l + y * 30.0 - this.angle)) * radius;
                        ParticleEffect.CLOUD.display(0.0f, 0.0f, 0.0f, 0.01f, 1, location.clone().add(x, y, z), 16.0);
                    }
                }
                ++this.angle;
                if (this.angle == 70) {
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 2L, 0L);
    }

    // 7!



}
