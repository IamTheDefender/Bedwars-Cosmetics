package xyz.iamthedefender.cosmetics.category.finalkilleffects.preview;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticPreview;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.FinalKillEffect;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.support.protocol.PacketEventsBridge;
import xyz.iamthedefender.cosmetics.support.npc.PacketNpcManager;
import xyz.iamthedefender.cosmetics.support.npc.impl.PacketPlayerNpc;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FinalKillEffectPreview extends CosmeticPreview {

    private static final String DERPERINO_TEXTURE = "ewogICJ0aW1lc3RhbXAiIDogMTY4NDU1ODYwMzIyNywKICAicHJvZmlsZUlkIiA6ICI1NjY3NWIyMjMyZjA0ZWUwODkxNzllOWM5MjA2Y2ZlOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVJbmRyYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81MzhiZjY4MTQ5MWE2ZmM1NmZlZDdhNjlmNDQ5MmYyN2ExZDE4YTdhMDM5ZTNjOWEzZWMyYjkzZmFkOWZlZDY2IgogICAgfQogIH0KfQ";
    private static final String DERPERINO_SIGNATURE = "BHb9Cye246WSOcArShXCY8Qv+yXWgANNwnKgCcIh6EEZMuWF6pQFgvYuqPn8l+SikiO4qimBcsjKLAigRCO7nnWCjE/GqtTEAmk5ermP5p+56tbS+AEvnSSOG5+0MtI8hcOEDnZTEI3GMcx/cQSmnRylMNlgMYAt7GL7uAkAd8bjnxG01lrrX5KqIzFnvc9quvruKeDV9fvAwgpc8zzZJwcVzOTZhrLxm1rj+iaVmLrP7PpRMOLF9bx3Q4URLedALLbX5PzkRQvZQBgGUsdCx+UDGjicp8gq7RIYnx+RnCEYHrkf9rrs6b8SW0qyAkhxLqlNZeCIPU8GECD4OpOMxathuQ4anI0j9bntXV/Yegdd42vQVjJVTAnQEGahqI5yTyaxL9r5GbegUHi2YQRTnZMuWNAETUxgaaC0v4kvV/DIDowmBgIAP6Anp2JSDAYXkW/mr/WBjyhk0oG31IHwySU1AuV5mch0v1AV5jrBi3Hjrxp6S+j7vMSpXYpzHmx0O92OfaiSg4J8tyJ/3cRxbGUas2Uc2ZuYa3Ke1FGyKmWVWqcX6APmLXagN5Zuug/aCHSPaoogNY29+YK7JQtRlJisUrG30sh7JUmKeIYMtOuQIgzVdHGhvC6xDqcK9hYnLV8XHNVqXZvq8ArrYD/Nw03MMWomFLM0NUaHaNYmLHo=";

    public FinalKillEffectPreview() {
        super(CosmeticType.FINAL_KILL_EFFECTS);
    }

    @Override
    public void showPreview(Player player, Cosmetics selected, Location previewLocation, Location playerLocation) throws IllegalArgumentException {
        handleLocation(player, playerLocation);

        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(playerLocation, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setBasePlate(false);
        armorStand.setSmall(false);
        armorStand.teleport(playerLocation);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 80, 2));

        AtomicReference<Runnable> cleanupRef = new AtomicReference<>(null);

        Run.delayed(() -> {
            if (!player.isOnline() || !getActiveTasks().containsKey(player)) {
                return;
            }
            cleanupRef.set(sendKillEffect(player, previewLocation, (FinalKillEffect) selected));
        }, 5L);

        Run.delayed(() -> {
            if (!player.isOnline() || armorStand.isDead()) {
                return;
            }
            PacketEventsBridge.sendCamera(player, armorStand.getEntityId());
        }, 2L);

        setOnEnd(player, () -> {
            if (!armorStand.isDead()) {
                armorStand.remove();
            }
            PacketEventsBridge.sendCamera(player, player.getEntityId());
            player.removePotionEffect(PotionEffectType.INVISIBILITY);

            Runnable cleanup = cleanupRef.get();
            if (cleanup != null) {
                cleanup.run();
            }
        });
    }

    public Runnable sendKillEffect(Player player, Location location, FinalKillEffect killEffect) {
        PacketNpcManager manager = CosmeticsPlugin.getInstance().getPacketNpcManager();
        Vector direction = location.getDirection().normalize();
        Vector side = new Vector(-direction.getZ(), 0, direction.getX()).normalize();

        Location victimSpawn = location.clone().subtract(direction.clone().multiply(2)).add(side.clone().multiply(1.5));
        Location killerSpawn = location.clone();

        PacketPlayerNpc killerNpc = manager.createNamedClone(player, player, "Player", victimSpawn);
        killerNpc.mainHand(new ItemStack(Material.IRON_SWORD));
        PacketPlayerNpc victimNpc = manager.createNamedSkinned(player, "Enemy", DERPERINO_TEXTURE, DERPERINO_SIGNATURE, killerSpawn);

        AtomicBoolean cleaned = new AtomicBoolean(false);

        killerNpc.spawnWithVisibleTabName(player);
        victimNpc.spawnWithVisibleTabName(player);

        Run.delayed(() -> {
            BukkitTask movementTask = Run.every(task -> {
                if (manager.destroyed(victimNpc)) {
                    task.cancel();
                    return;
                }

                Location current = killerNpc.getLocation().clone();
                Vector offset = killerSpawn.toVector().subtract(current.toVector());
                double distance = offset.length();
                if (distance <= 1.35D) {
                    return;
                }

                Vector step = offset.normalize().multiply(Math.min(0.28D, distance));
                current.add(step);
                current.setDirection(offset);
                killerNpc.teleport(current);
            }, 1L);

            Run.delayed(() -> {
                cleaned.set(true);

                movementTask.cancel();
                manager.destroy(victimNpc);
                killerNpc.swingArm(player);

                killEffect.execute(player, player, victimNpc.getLocation(), true);
            }, 25L);
        }, 20L);

        return () -> {
            manager.destroy(killerNpc);
            manager.destroy(victimNpc);
        };
    }

    @Override
    public long getEndDelay() {
        return super.getEndDelay() + 20L;
    }
}
