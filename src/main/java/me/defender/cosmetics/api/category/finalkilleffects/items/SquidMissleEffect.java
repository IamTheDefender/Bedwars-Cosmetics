package me.defender.cosmetics.api.category.finalkilleffects.items;

import com.cryptomorin.xseries.XEntity;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.hakan.core.HCore;
import com.hakan.core.particle.Particle;
import com.hakan.core.particle.type.ParticleType;
import me.defender.cosmetics.api.category.finalkilleffects.FinalKillEffect;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.Utility;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFirework;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSquid;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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

    private int fireworkID;

    @Override
    public void execute(Player killer, Player victim, Location location, boolean onlyVictim) {
        ArmorStand stand = (ArmorStand) victim.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setGravity(false);
        stand.setVisible(false);

        if (onlyVictim) {
            EntitySquid squid = new EntitySquid(((CraftWorld) location.getWorld()).getHandle());
            squid.setPosition(stand.getLocation().getX(), stand.getLocation().getY(), stand.getLocation().getZ());

            PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(squid);

            HCore.sendPacket(victim, packet);

            stand.setPassenger(squid.getBukkitEntity());

            new BukkitRunnable() {
                int i1 = 0;

                public void run() {
                    ++this.i1;
                    squid.getBukkitEntity().getLocation().setYaw(180.0f);
                    stand.eject();
                    stand.teleport(stand.getLocation().add(0.0, 0.5, 0.0));
                    stand.setPassenger(squid.getBukkitEntity());

                    PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(squid);
                    HCore.sendPacket(victim, packet);

                    Particle particle = new Particle(ParticleType.FLAME, 1, 0.01f, new Vector(0.0f, 0.0f, 0.0f));
                    HCore.playParticle(victim, stand.getLocation(), particle);

                    victim.playSound(victim.getLocation(), XSound.ENTITY_CHICKEN_EGG.parseSound(), 1.0f, 1.0f);

                    if (this.i1 == 25) {
                        ItemStack stackFirework = new ItemStack(Material.FIREWORK);
                        FireworkMeta fireworkMeta = (FireworkMeta) stackFirework.getItemMeta();
                        fireworkMeta.addEffect(FireworkEffect.builder().flicker(true).trail(false).with(FireworkEffect.Type.BALL).withColor(Color.BLACK).withFade(Color.BLACK).build());
                        fireworkMeta.setPower(1);
                        stackFirework.setItemMeta(fireworkMeta);

                        EntityFireworks fw = new EntityFireworks(
                                ((CraftWorld) location.getWorld()).getHandle(),
                                squid.getBukkitEntity().getLocation().getX(),
                                squid.getBukkitEntity().getLocation().getY(),
                                squid.getBukkitEntity().getLocation().getZ(),
                                CraftItemStack.asNMSCopy(stackFirework));

                        PacketPlayOutSpawnEntity spawnFwPacket =
                                new PacketPlayOutSpawnEntity(fw, 76);
                        PacketPlayOutEntityMetadata metaDataPacket =
                                new PacketPlayOutEntityMetadata(fw.getId(), fw.getDataWatcher(), true);
                        PacketPlayOutEntityStatus statusPacket =
                                new PacketPlayOutEntityStatus(fw, (byte) 17);

                        HCore.sendPacket(victim, spawnFwPacket);
                        HCore.sendPacket(victim, metaDataPacket);
                        HCore.sendPacket(victim, statusPacket);
                        fireworkID = fw.getId();

                        stand.eject();
                        stand.remove();
                        squid.getBukkitEntity().remove();
                        PacketPlayOutEntityDestroy squidDestroyPacket = new PacketPlayOutEntityDestroy(squid.getId());
                        PacketPlayOutEntityDestroy fwDestroyPacket = new PacketPlayOutEntityDestroy(fireworkID);
                        HCore.sendPacket(victim, squidDestroyPacket);
                        HCore.sendPacket(victim, fwDestroyPacket);

                        this.i1 = 0;
                        this.cancel();
                    }
                }
            }.runTaskTimer(Utility.plugin(), 4L, 1L);
        } else {
            Squid squid = (Squid) victim.getWorld().spawnEntity(location, EntityType.SQUID);
            stand.setPassenger(squid);
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
}