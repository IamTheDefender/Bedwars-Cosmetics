package xyz.iamthedefender.cosmetics.category.shopkeeperskins.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticRegistry;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.ShopKeeperSkin;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.AbstractShopKeeperSkin;
import xyz.iamthedefender.cosmetics.support.npc.PacketNpc;
import xyz.iamthedefender.cosmetics.util.CosmeticsUtil;
import xyz.iamthedefender.cosmetics.util.EntityUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;
import xyz.iamthedefender.cosmetics.support.npc.PacketNpcManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class ShopKeeperSkinsUtils {

    private static final Map<String, List<RuntimeDisplay>> ACTIVE_RUNTIME_DISPLAYS = new ConcurrentHashMap<>();

    public static void spawnShopKeeperNPC(Player player, Location location) {
        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(player, CosmeticType.SHOPKEEPER_SKINS);
        spawnRuntimeShopKeeper(player, location, selected, null);
    }

    public static void spawnShopKeeperNPC(Player player, Location location, String skinId) {
        spawnRuntimeShopKeeper(player, location, skinId, null);
    }

    public static void  spawnRuntimeShopKeeper(Player skinOwner, Location location, String skinId, Consumer<Player> interactionHandler) {
        if (skinOwner == null || location == null || location.getWorld() == null) {
            return;
        }

        ShopKeeperSkin skin = resolveDisplaySkin(skinOwner, skinId);
        if (skin == null) {
            return;
        }

        RuntimeDisplay display = createDisplay(
                skinOwner,
                location,
                skin,
                location.getWorld().getPlayers(),
                interactionHandler,
                true
        );
        if (display == null) {
            return;
        }

        ACTIVE_RUNTIME_DISPLAYS.computeIfAbsent(location.getWorld().getName(), key -> new ArrayList<>()).add(display);
    }

    public static Runnable spawnShopKeeperNPCForPreview(Player player, Location location, String skinId) {
        if (player == null || location == null || location.getWorld() == null) {
            return () -> {
            };
        }

        ShopKeeperSkin skin = resolveDisplaySkin(player, skinId);
        if (skin == null) {
            return () -> {
            };
        }

        RuntimeDisplay display = createDisplay(player, location, skin, List.of(player), null, false);
        if (display == null) {
            return () -> {
            };
        }

        return display::destroy;
    }

    public static void spawnRuntimeDisplaysForWorld(String worldName, Player player) {
        if (worldName == null || player == null || !player.isOnline()) {
            return;
        }

        ACTIVE_RUNTIME_DISPLAYS.getOrDefault(worldName, List.of()).forEach(display -> display.spawnFor(player));
    }

    public static void clearRuntimeDisplays(String worldName) {
        if (worldName == null) {
            return;
        }

        List<RuntimeDisplay> displays = ACTIVE_RUNTIME_DISPLAYS.remove(worldName);
        if (displays == null) {
            return;
        }

        displays.forEach(RuntimeDisplay::destroy);
    }

    private static RuntimeDisplay createDisplay(Player skinOwner, Location location, ShopKeeperSkin skin,
                                                Collection<? extends Player> viewers, Consumer<Player> interactionHandler,
                                                boolean runtime) {
        EntityType entityType = skin.getField(FieldsType.ENTITY_TYPE, skinOwner);
        if (entityType != null) {
            if (StartupUtils.usePacketShopkeeperEntityNpcs()) {
                PacketNpc packetNpc = CosmeticsPlugin.getInstance().getPacketNpcManager().createEntity(entityType, location);
                if (interactionHandler != null) {
                    packetNpc.interaction(interactionHandler);
                }
                packetNpc.spawn(viewers);

                BukkitTask lookTask = null;
                if (runtime && StartupUtils.shouldShopkeeperLookClose()) {
                    lookTask = Run.every(() -> {
                        location.getWorld().getPlayers().stream()
                                .filter(AbstractShopKeeperSkin::canSeeRuntimeShopkeepers)
                                .min(Comparator.comparingDouble(left -> left.getLocation().distanceSquared(location)))
                                .ifPresent(nearest -> packetNpc.lookAt(nearest.getEyeLocation()));
                    }, 10L);
                }

                return new RuntimeDisplay(location, packetNpc, null, lookTask);
            }

            Entity entity = location.getWorld().spawnEntity(location, entityType);
            configureEntity(entity);

            if (!runtime && viewers.size() == 1) {
                Player viewer = viewers.iterator().next();
                EntityUtil.entityForPlayerOnly(entity, viewer);
            }

            return new RuntimeDisplay(location, null, entity, null);
        }

        String skinValue = skin.getField(FieldsType.SKIN_VALUE, skinOwner);
        String skinSignature = skin.getField(FieldsType.SKIN_SIGN, skinOwner);
        boolean mirror = Boolean.TRUE.equals(skin.getField(FieldsType.MIRROR, skinOwner));

        PacketNpc packetNpc;
        if (mirror) {
            packetNpc = CosmeticsPlugin.getInstance().getPacketNpcManager().createClone(skinOwner, skinOwner, location);
        } else if (skinValue != null && skinSignature != null) {
            packetNpc = CosmeticsPlugin.getInstance().getPacketNpcManager().createSkinned(skinOwner, skinValue, skinSignature, location);
        } else {
            return null;
        }

        if (interactionHandler != null) {
            packetNpc.interaction(interactionHandler);
        }
        packetNpc.spawn(viewers);

        BukkitTask lookTask = null;
        if (runtime && StartupUtils.shouldShopkeeperLookClose()) {
            lookTask = Run.every(() -> {
                Player nearest = location.getWorld().getPlayers().stream()
                        .filter(AbstractShopKeeperSkin::canSeeRuntimeShopkeepers)
                        .min((left, right) -> Double.compare(
                                left.getLocation().distanceSquared(location),
                                right.getLocation().distanceSquared(location)
                        ))
                        .orElse(null);
                if (nearest != null) {
                    packetNpc.lookAt(nearest.getEyeLocation());
                }
            }, 10L);
        }

        return new RuntimeDisplay(location, packetNpc, null, lookTask);
    }

    private static ShopKeeperSkin resolveDisplaySkin(Player player, String skinId) {
        ShopKeeperSkin requested = CosmeticRegistry.getById(CosmeticType.SHOPKEEPER_SKINS, skinId, ShopKeeperSkin.class).orElse(null);
        if (requested == null) {
            return ShopKeeperSkin.getDefault(player);
        }

        if (requested.getField(FieldsType.RARITY, player) != RarityType.RANDOM) {
            return requested;
        }

        List<ShopKeeperSkin> unlocked = CosmeticsUtil.getShopKeeperSkins(player).stream()
                .filter(skin -> skin.getField(FieldsType.RARITY, player) != RarityType.RANDOM)
                .filter(skin -> skin.getField(FieldsType.RARITY, player) != RarityType.NONE)
                .collect(java.util.stream.Collectors.toList());

        if (unlocked.isEmpty()) {
            return ShopKeeperSkin.getDefault(player);
        }

        return unlocked.get(ThreadLocalRandom.current().nextInt(unlocked.size()));
    }

    private static void configureEntity(Entity entity) {
        if (!(entity instanceof LivingEntity)) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.setAI(false);
        livingEntity.setSilent(true);
        livingEntity.setRemoveWhenFarAway(false);
        livingEntity.setCanPickupItems(false);
    }

    private static class RuntimeDisplay {
        private final Location location;
        private final PacketNpc packetNpc;
        private final Entity entity;
        private final BukkitTask lookTask;

        private RuntimeDisplay(Location location, PacketNpc packetNpc, Entity entity, BukkitTask lookTask) {
            this.location = location;
            this.packetNpc = packetNpc;
            this.entity = entity;
            this.lookTask = lookTask;
        }

        private void spawnFor(Player player) {
            if (packetNpc == null || player == null || !Objects.equals(player.getWorld(), location.getWorld())) {
                return;
            }
            packetNpc.spawn(player);
        }

        private void destroy() {
            if (lookTask != null) {
                lookTask.cancel();
            }

            if (packetNpc != null) {
                CosmeticsPlugin.getInstance().getPacketNpcManager().destroy(packetNpc);
            }

            if (entity != null && !entity.isDead()) {
                CosmeticsPlugin.getInstance().getEntityPlayerHashMap().remove(entity.getEntityId());
                entity.remove();
            }
        }
    }
}
