// 
// @author IamTheDefender
// 

package me.defender.cosmetics.BedBreakEffect;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import me.defender.api.utils.util;
import me.defender.cosmetics.VictoryDances.UsefulUtilsVD;

public class BedBreakEffectUtils
{
    public static void squidMissile(final Player p, final Location loc) {
        final Squid squid = (Squid)p.getWorld().spawnEntity(loc, EntityType.SQUID);
        final ArmorStand stand = (ArmorStand)p.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
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
    
    public static void pigMissile(final Player p, final Location loc) {
        final Pig pig = (Pig)p.getWorld().spawnEntity(loc, EntityType.PIG);
        final ArmorStand stand = (ArmorStand)p.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        pig.setSaddle(true);
        stand.setGravity(false);
        stand.setPassenger(pig);
        stand.setVisible(false);
        new BukkitRunnable() {
            int i1 = 0;
            
            public void run() {
                ++this.i1;
                stand.eject();
                stand.teleport(stand.getLocation().add(0.0, 0.5, 0.0));
                stand.setPassenger(pig);
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
                    pig.remove();
                    this.i1 = 0;
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 4L, 1L);
    }
    
    public static void fireworks(final Player p, final Location loc) {
        UsefulUtilsVD.spawnFireWorks(p, 1, Color.RED, Color.GREEN, loc);
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
    
    public static void playGhost(final Player p, Location loc1) {
        new BukkitRunnable() {
            int i = 0;
            
            public void run() {
                ++this.i;
                final Location loc = UsefulUtilsVD.getRandomLocation(loc1, 2);
                final Bat bat = (Bat)p.getWorld().spawnEntity(loc, EntityType.BAT);
                bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 19999980, 1));
                bat.setMetadata("BEDBREAKEFFECT." + p.getName(), new FixedMetadataValue(util.plugin(), ""));
                final ArmorStand stand = (ArmorStand)p.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
                stand.setVisible(false);
                stand.setGravity(false);
                bat.setPassenger(stand);
                stand.setHelmet(UsefulUtilsVD.gethead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZlOTM0NjdhM2EwNzkyMjdmNGE3ZDNlYmE3NjE3NTM2ZGE0OTFiYzJmYzZkNzNlZTM5NjhkM2NmMWE2YTUifX19", ""));
                if (this.i == 5) {
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 0L, 10L);
        new BukkitRunnable() {
            public void run() {
                for (final Entity en : p.getWorld().getEntities()) {
                    if (en.getType() == EntityType.BAT && en.hasMetadata("BEDBREAKEFFECT." + p.getName())) {
                        FastParticle.spawnParticle(p, ParticleType.EXPLOSION_LARGE, en.getLocation(), 10);


                        en.getWorld().playSound(p.getLocation(), util.Explode, 0.2f, 0.2f);
                        en.remove();
                        en.getPassenger().remove();

                    }
                }
            }
        }.runTaskLater(util.plugin(), 150L);
    }
    
    public static void lightingStrike(final Player p, Location loc) {
        p.getWorld().strikeLightningEffect(loc);
    }
    
    public static void theif(final Player p, Location loc) {
        final Enderman eman = (Enderman)p.getWorld().spawnEntity(loc, EntityType.ENDERMAN);
        eman.setCarriedMaterial(new MaterialData(Material.BED));
        new BukkitRunnable() {
            public void run() {
                eman.remove();
            }
        }.runTaskLater(util.plugin(), 70L);
    }
    
    // 7 DONE!
    
    
    public static void bedbugs(Player p, Location loc1) {
    	new BukkitRunnable() {
			int i = 0;
			@Override
			public void run() {
				i++;
				Location loc = UsefulUtilsVD.getRandomLocation(loc1, 1);
				Endermite mite = (Endermite) p.getWorld().spawnEntity(loc.add(0,1,0), EntityType.ENDERMITE);
				
				mite.setMetadata("CUSTOMENDERMITE." + p.getName(), new FixedMetadataValue(util.plugin(), ""));
				if(i == 4) {
					this.cancel();
				}
				
			}
		}.runTaskTimer(util.plugin(), 0, 1);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Entity en : p.getWorld().getEntities()) {
					if(en instanceof Endermite) {
						if(en.hasMetadata("CUSTOMENDERMITE." + p.getName())) {
							en.remove();
						}
					}
				}
				
			}
		}.runTaskLater(util.plugin(), 100);
    }
    
    public static void hologram(Player p, String teamname, Location loc) {
    	ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(loc.add(0,1,0), EntityType.ARMOR_STAND);
    	stand.setVisible(false);
    	stand.setGravity(false);
    	stand.setCustomName(util.color("&c" + teamname + "'s Bed was destroyed by " + p.getName()));
    	stand.setCustomNameVisible(true);
    }
    
}
