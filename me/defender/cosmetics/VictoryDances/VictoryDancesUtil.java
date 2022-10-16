// 
// @author IamTheDefender
// 

package me.defender.cosmetics.VictoryDances;

import com.hakan.core.HCore;
import com.hakan.core.npc.HNPC;
import com.hakan.core.skin.Skin;
import org.bukkit.entity.Fireball;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.entity.EnderDragon;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Horse;
import org.bukkit.entity.ArmorStand;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Bat;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.bukkit.entity.Wither;
import org.bukkit.entity.FallingBlock;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import me.defender.api.utils.util;
import org.bukkit.Location;
import org.bukkit.Color;
import me.defender.cosmetics.ShopKeeperSkins.ShopKeeperSkin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.event.Listener;

public class VictoryDancesUtil implements Listener
{
    public static HashMap<String, Boolean> toystickplayer;
    
    static {
        VictoryDancesUtil.toystickplayer = new HashMap<String, Boolean>();
    }
    
    public static void spawnFireworksVD(final Player p) {
        new BukkitRunnable() {
            public void run() {
                if (ShopKeeperSkin.arenas.containsKey(p.getWorld().getName())) {
                    final Location loc = p.getEyeLocation();
                    UsefulUtilsVD.spawnFireWorks(p, 1, Color.RED, Color.BLUE, loc);
                    new BukkitRunnable() {
                        public void run() {
                            UsefulUtilsVD.spawnFireWorks(p, 1, Color.ORANGE, Color.YELLOW, loc);
                        }
                    }.runTaskLater(util.plugin(), 300L);
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 0L, 60L);
    }
    
    public static void RainingPigVD(final Player p) {
        new BukkitRunnable() {
            public void run() {
                if (ShopKeeperSkin.arenas.containsKey(p.getWorld().getName())) {
                    final Location loc = UsefulUtilsVD.getRandomLocation(p.getLocation(), 20);
                    final Pig pig = (Pig)p.getWorld().spawnEntity(loc, EntityType.PIG);
                    pig.setSaddle(true);
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 1L, 1L);
    }
    
    @EventHandler
    public void onPigFallDamage(final EntityDamageEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getEntity().getType() == null) {
            return;
        }
        if (e.getEntity().getType() == EntityType.PIG) {
            if (ShopKeeperSkin.arenas.containsKey(e.getEntity().getWorld().getName())) {
                e.setCancelled(true);
            }
        }
        else if (e.getEntity().getType() == EntityType.HORSE) {
            if (ShopKeeperSkin.arenas.containsKey(e.getEntity().getWorld().getName())) {
                e.setCancelled(true);
            }
        }
        else if (e.getEntity().getType() == EntityType.SHEEP) {
            if (ShopKeeperSkin.arenas.containsKey(e.getEntity().getWorld().getName())) {
                e.setCancelled(true);
            }
        }
        else if (e.getEntity().getType() == EntityType.ENDER_DRAGON) {
            if (ShopKeeperSkin.arenas.containsKey(e.getEntity().getWorld().getName())) {
                e.setCancelled(true);
            }
        }
        else if (e.getEntity().getType() == EntityType.ENDERMAN && ShopKeeperSkin.arenas.containsKey(e.getEntity().getWorld().getName())) {
            e.setCancelled(true);
        }
        else if(e.getEntity().getType() == EntityType.PLAYER){
            if(e.getEntity().getVehicle() == null)
                return;

            if(e.getEntity().getVehicle().hasMetadata("VD")){
                e.setCancelled(true);
            }
            if(e.getEntity().hasMetadata("VD")){
                e.setCancelled(true);
            }
        }
    }
    
    public static void AnvilRainVD(final Player p) {
        new BukkitRunnable() {
            public void run() {
                if (ShopKeeperSkin.arenas.containsKey(p.getWorld().getName())) {
                    final Location loc = UsefulUtilsVD.getRandomLocation(p.getLocation(), 20);
                    final FallingBlock anvil = p.getWorld().spawnFallingBlock(loc, Material.ANVIL, (byte)0);
                    anvil.setHurtEntities(false);
                    anvil.setDropItem(false);
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 1L, 1L);
    }
    
    public static void NightShiftVD(final Player p) {
        new BukkitRunnable() {
            public void run() {
                if (ShopKeeperSkin.arenas.containsKey(p.getWorld().getName())) {
                    Thread thread = null;
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            p.getWorld().setTime(0L);
                            try {
                                Thread.sleep(1000L);
                            }
                            catch (final InterruptedException e) {
                                e.printStackTrace();
                            }
                            p.getWorld().setTime(2000L);
                            try {
                                Thread.sleep(1000L);
                            }
                            catch (final InterruptedException e) {
                                e.printStackTrace();
                            }
                            p.getWorld().setTime(4000L);
                            try {
                                Thread.sleep(1000L);
                            }
                            catch (final InterruptedException e) {
                                e.printStackTrace();
                            }
                            p.getWorld().setTime(6000L);
                            try {
                                Thread.sleep(1000L);
                            }
                            catch (final InterruptedException e) {
                                e.printStackTrace();
                            }
                            p.getWorld().setTime(8000L);
                            try {
                                Thread.sleep(1000L);
                            }
                            catch (final InterruptedException e) {
                                e.printStackTrace();
                            }
                            p.getWorld().setTime(10000L);
                            try {
                                Thread.sleep(1000L);
                            }
                            catch (final InterruptedException e) {
                                e.printStackTrace();
                            }
                            p.getWorld().setTime(12000L);
                            try {
                                Thread.sleep(1000L);
                            }
                            catch (final InterruptedException e) {
                                e.printStackTrace();
                            }
                            p.getWorld().setTime(14000L);
                            try {
                                Thread.sleep(1000L);
                            }
                            catch (final InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }
                else {
                    p.getWorld().setTime(0L);
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 0L, 80L);
    }
    
    public static void WitherDanceVD(final Player p) {
        final Wither wither = (Wither)p.getWorld().spawnEntity(p.getLocation(), EntityType.WITHER);
        wither.setPassenger(p);
        wither.setMetadata("VD", new FixedMetadataValue(util.plugin(), ""));
        wither.setCustomName(util.color("&a" + p.getName() + "'s Wither!"));
        new BukkitRunnable() {
            public void run() {
                if (ShopKeeperSkin.arenas.containsKey(p.getWorld().getName())) {
                    if(wither.getPassenger() != p){
                        wither.setPassenger(p);
                    }
                    final Vector direction = p.getEyeLocation().clone().getDirection().normalize().multiply(0.5);
                    wither.setVelocity(direction);
                }
                else {
                    wither.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 0L, 1L);
    }
    
    @EventHandler
    public void ClickEvent(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (player.getVehicle() instanceof Wither) {
            final WitherSkull witherSkull = player.getWorld().spawn(player.getEyeLocation().clone().add(player.getEyeLocation().getDirection().normalize().multiply(2)), WitherSkull.class);
            witherSkull.setShooter(player);
            witherSkull.setVelocity(player.getEyeLocation().clone().getDirection().normalize().multiply(3));
        }
    }
    
    public static void ToyStickVD(final Player p) {
        final ItemStack i = new ItemStack(Material.STICK);
        final ItemMeta im = i.getItemMeta();
        im.setDisplayName(util.color("&aToy Stick"));
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(util.color("&7Right Click on a block"));
        lore.add(util.color("&7to fly!"));
        i.setItemMeta(im);
        p.getInventory().addItem(i);
        VictoryDancesUtil.toystickplayer.put(p.getUniqueId().toString(), true);
    }
    
    @EventHandler
    public void CancelAutoTarget(final EntityTargetLivingEntityEvent event) {
        if (event.getEntity() instanceof Wither) {
            event.setCancelled(true);
        }
        else if (event.getEntity() instanceof Enderman) {
            event.setCancelled(true);
        }
        else if(event.getEntity() instanceof Endermite) {
        	event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onWitherAttack(final EntityTargetEvent e) {
        if (e.getEntityType() == EntityType.WITHER) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onRightClick(final PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getItemMeta() != null && e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Toy Stick")) {
            e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(-6).setY(6));
            for (final Location loc : UsefulUtilsVD.generateSphere(e.getPlayer().getLocation(), 6, false)) {
                final Block block = loc.getBlock();
                UsefulUtilsVD.bounceBlock(block);
                block.breakNaturally();
            }
        }
    }
    
    public static void floatingLanternsVD(final Player p) {
        new BukkitRunnable() {
            public void run() {
                if (ShopKeeperSkin.arenas.containsKey(p.getWorld().getName())) {
                    final Location loc = UsefulUtilsVD.getRandomLocation(p.getLocation(), 10);
                    final Bat bat = (Bat)p.getWorld().spawnEntity(loc, EntityType.BAT);
                    bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 19999980, 1));
                    final ArmorStand stand = (ArmorStand)p.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
                    stand.setVisible(false);
                    bat.setPassenger(stand);
                    stand.setHelmet(UsefulUtilsVD.gethead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTRjNzM2ODY0YTY0OWY2NjVmMDRmMjhiYjE0YTNjNGVhMGYyNDVlODQ2MDE3YWNmZTM2NmU3ZDEzNWI0ZmNhOCJ9fX0=", ""));
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 1L, 8L);
    }
    
    public static void YeeHawVD(final Player p) {
        final Horse horse = (Horse)p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.setJumpStrength(10.0);
        horse.setPassenger(p);
        horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 190000000, 3));
        new BukkitRunnable() {
            public void run() {
                if (ShopKeeperSkin.arenas.containsKey(p.getWorld().getName())) {
                    if (horse.getPassenger() != p) {
                        horse.remove();
                    }
                }
                else {
                    horse.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 0L, 1L);
    }
    
    public static void sheepVD(final Player p) {
        final Sheep sheep = (Sheep)p.getWorld().spawnEntity(p.getLocation(), EntityType.SHEEP);
        sheep.setColor(DyeColor.RED);
        sheep.setCustomName("Dolly");
    }
    
    @EventHandler
    public void onSheepClick(final PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked().getType() == EntityType.SHEEP) {
            final Sheep sheep = (Sheep)e.getRightClicked();
            if (sheep.getCustomName() == null) {
                return;
            }
            if (sheep.getCustomName() == "Dolly") {
                final ArrayList<String> color = new ArrayList<String>();
                color.add("RED");
                color.add("BLUE");
                color.add("BROWN");
                color.add("GREEN");
                color.add("PINK");
                color.add("YELLOW");
                color.add("GRAY");
                color.add("LIME");
                color.add("CYAN");
                color.add("MAGENTA");
                final int size = ThreadLocalRandom.current().nextInt(color.size());
                final String colors = color.get(size);
                final Sheep sheep2 = (Sheep)sheep.getWorld().spawnEntity(sheep.getLocation(), EntityType.SHEEP);
                sheep2.getWorld().createExplosion(sheep2.getLocation(), 1.0f);
                sheep2.setColor(DyeColor.valueOf(colors));
                sheep2.setVelocity(sheep.getLocation().getDirection().multiply(-0.5).setY(1));
                sheep2.setCustomName("Dolly");
            }
        }
    }
    
    @EventHandler
    public void onSheepHit(final EntityDamageEvent e) {
        if (e.getEntityType() == EntityType.SHEEP) {
            e.setCancelled(true);
        }
    }
    
    public static void hauntedVD(final Player p) {
        new BukkitRunnable() {
            public void run() {
                if (ShopKeeperSkin.arenas.containsKey(p.getWorld().getName())) {
                    final Location loc = UsefulUtilsVD.getRandomLocation(p.getLocation(), 10);
                    final Bat bat = (Bat)p.getWorld().spawnEntity(loc, EntityType.BAT);
                    bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 19999980, 1));
                    final ArmorStand stand = (ArmorStand)p.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
                    stand.setVisible(false);
                    bat.setPassenger(stand);
                    final ItemStack leather = new ItemStack(Material.LEATHER_CHESTPLATE);
                    final LeatherArmorMeta lm = (LeatherArmorMeta)leather.getItemMeta();
                    lm.setColor(Color.GRAY);
                    leather.setItemMeta(lm);
                    stand.setChestplate(leather);
                    stand.setHelmet(UsefulUtilsVD.gethead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjhkMjE4MzY0MDIxOGFiMzMwYWM1NmQyYWFiN2UyOWE5NzkwYTU0NWY2OTE2MTllMzg1NzhlYTRhNjlhZTBiNiJ9fX0=", ""));
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 1L, 8L);
    }
    
    public static void snowedIn(final Player p) {
        new BukkitRunnable() {
            public void run() {
                if (ShopKeeperSkin.arenas.containsKey(p.getWorld().getName())) {
                    final Location loc = UsefulUtilsVD.getRandomLocation(p.getLocation(), 10);
                    if (loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != Material.SNOW && loc.getBlock().getType() != Material.BED) {
                        final Location loc2 = loc.add(0.0, 1.0, 0.0);
                        if (loc2.getBlock().getType() == Material.AIR && loc2.getBlock().getType() != Material.SNOW && loc.getBlock().getType() != Material.ACACIA_STAIRS && loc.getBlock().getType() != Material.COBBLESTONE_STAIRS && loc.getBlock().getType() != Material.BIRCH_WOOD_STAIRS && loc.getBlock().getType() != Material.BRICK_STAIRS) {
                            loc2.getBlock().setType(Material.SNOW);
                        }
                        else {
                            this.run();
                        }
                    }
                    else {
                        this.run();
                    }
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 0L, 1L);
    }
    
    public static void ColdSnapVD(final Player p) {
        new BukkitRunnable() {
            public void run() {
                final Location loc = UsefulUtilsVD.getRandomLocation(p.getLocation(), 3);
                if(ShopKeeperSkin.arenas.containsKey(p.getWorld().getName())) {
                    if (loc.getBlock().getType() != Material.AIR) {
                        loc.getBlock().setType(Material.ICE);
                        p.playSound(p.getLocation(), util.Note_Pling, 1.0f, 1.0f);
                    } else {
                        this.run();
                    }
                }else{
                    this.cancel();
                }
            }
        }.runTaskTimer(util.plugin(), 0L, 0L);
    }
    
    public static void dragonVD(final Player p) {
        final EnderDragon dragon = (EnderDragon)p.getWorld().spawnEntity(p.getLocation(), EntityType.ENDER_DRAGON);
        
        dragon.setPassenger(p);
        final ArmorStand stand = (ArmorStand)p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setMetadata("FAKE_TARGET", new FixedMetadataValue(util.plugin(), ""));

        dragon.setMetadata("VD", new FixedMetadataValue(util.plugin(), ""));
        util.plugin().stands.put(p, stand);
        Thread thread = null;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new BukkitRunnable() {
                    public void run() {
                        final Location original = p.getEyeLocation();
                        final Vector direction = p.getEyeLocation().clone().getDirection().normalize().multiply(20);
                        final Location newLocation = original.add(direction);
                        stand.teleport(newLocation);
                        for (Block blocks : UsefulUtilsVD.getBlocksInRadius(dragon.getLocation(), 10, false)) {
                            blocks.setType(Material.AIR);
                        }
                        if(!ShopKeeperSkin.arenas.containsKey(p.getWorld().getName())) {
                            this.cancel();
                            stand.remove();
                            dragon.remove();

                        }
                    }
                }.runTaskTimer(util.plugin(), 0L, 1L);
            }
        });
        thread.start();
    }
    
    @EventHandler
    public void onTarget(final EntityTargetEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            final EnderDragon dragon = (EnderDragon)event.getEntity();
            if (dragon.getPassenger() instanceof Player) {
                final Player player = (Player)event.getEntity().getPassenger();
                if (!event.getTarget().hasMetadata("FAKE_TARGET")) {
                    event.setTarget(util.plugin().stands.get(player));
                }
            }
        }
    }
    
    @EventHandler
    public void onClickOnDragon(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (player.getVehicle() instanceof EnderDragon) {
            final Fireball fireball = player.getWorld().spawn(player.getEyeLocation().clone().add(player.getEyeLocation().getDirection().normalize().multiply(2)), Fireball.class);
            fireball.setShooter(player);
            fireball.setVelocity(player.getEyeLocation().clone().getDirection().normalize().multiply(3));
        }
    }

    public static void twerk(Player p){
        List<String> values = Arrays.asList(util.getFromName(p.getName()));
        Skin skin = new Skin(values.get(0), values.get(1));
        AtomicInteger amount = new AtomicInteger();
        List<HNPC> npcs = new ArrayList<>();
        HashMap<Player, HNPC> players = new HashMap<>();
        HNPC.Animation animation = HNPC.Animation.
        HCore.syncScheduler().every(1).run(() -> {
           if(amount.get() > 10){
               return;
           }
           amount.getAndIncrement();
           HNPC npc = HCore.npcBuilder(amount + p.getName())
                   .showEveryone(true)
                   .skin(skin)
                   .build();
           npcs.add(npc);
           HCore.syncScheduler().every(1).run(if(!npc.isDead()) npc.lookAt(p); npc.playAnimation(););

        });
    }

}
