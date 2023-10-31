package me.defender.cosmetics.api.category.sprays.preview;

import com.cryptomorin.xseries.XSound;
import com.hakan.core.HCore;
import com.hakan.core.ui.inventory.InventoryGui;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.api.category.sprays.Spray;
import me.defender.cosmetics.api.category.sprays.util.CustomRenderer;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.configuration.ConfigUtils;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.StartupUtils;
import me.defender.cosmetics.api.util.Utility;
import me.defender.cosmetics.support.sounds.GSound;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.map.MapView;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static me.defender.cosmetics.api.util.StartupUtils.getCosmeticLocation;
import static me.defender.cosmetics.api.util.StartupUtils.getPlayerLocation;

public class SprayPreview {

    private final Map<UUID, Map<Integer, org.bukkit.inventory.ItemStack>> inventories = new HashMap<>();

    private EntityItemFrame frame;
    private MapView view;

    private int currentID;

    public void sendSprayPreview(Player player, String selected, InventoryGui gui){
        for (Spray spray : StartupUtils.sprayList) {
            if (spray.getIdentifier().equals(selected)){
                if (spray.getField(FieldsType.RARITY, player) == RarityType.NONE) {
                    gui.open(player);
                    XSound.ENTITY_VILLAGER_NO.play(player, 1.0f, 1.0f);
                    return;
                }
            }
        }
        UUID playerUUID = player.getUniqueId();

        Location beforeLocation = player.getLocation().clone();

        Inventory playerInv = player.getInventory();
        if (!inventories.containsKey(playerUUID)) inventories.put(playerUUID, new HashMap<>());

        Map<Integer, org.bukkit.inventory.ItemStack> items = inventories.get(playerUUID);

        for (int i = 0; i < playerInv.getSize(); i++) {
            if (playerInv.getItem(i) == null) continue;
            if (playerInv.getItem(i).getType() == null) continue;
            if (playerInv.getItem(i).getType() == Material.AIR) continue;

            items.put(i, playerInv.getItem(i));
        }

        playerInv.clear();
        player.closeInventory();
        Location cosmeticLocation = null, playerLocation = null;

        try {
            cosmeticLocation = getCosmeticLocation();
            playerLocation = getPlayerLocation();
        }catch (Exception exception){
            exception.printStackTrace();
            player.sendMessage(ColorUtil.colored("&cEither Preview location or Player location is not set! Contact the admin."));
        }

        if(cosmeticLocation == null || playerLocation == null) return;

        final Location finalPlayerLocation = playerLocation;
        final Location finalCosmeticLocation = cosmeticLocation;
        final Location itemFrameLocation = finalCosmeticLocation.clone().add(0.0, 1.0, 0.0);

        ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(finalPlayerLocation, EntityType.ARMOR_STAND);
        as.setVisible(false);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
                100, 2));

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.equals(player)) continue;

            player1.hidePlayer(player);
        }

        sendFrame(player, itemFrameLocation, selected);

        CraftPlayer craftPlayer = (CraftPlayer)player;

        PacketPlayOutCamera cameraPacket = new PacketPlayOutCamera(((CraftArmorStand) as).getHandle());
        PacketPlayOutCamera resetPacket = new PacketPlayOutCamera(craftPlayer.getHandle());

        sendPacket(player, cameraPacket);

        HCore.syncScheduler().after(5, TimeUnit.SECONDS).run(() -> {
            if (!as.isDead()) as.remove();
            PacketPlayOutEntityDestroy itemFrameDestroyPacket = new PacketPlayOutEntityDestroy(currentID);
            sendPacket(player, itemFrameDestroyPacket);

            sendPacket(player, resetPacket);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            player.teleport(beforeLocation);

            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (player1.equals(player)) continue;

                player1.showPlayer(player);
            }
            for (Map.Entry<Integer, org.bukkit.inventory.ItemStack> entry: items.entrySet()) {
                playerInv.setItem(entry.getKey(), entry.getValue());
            }

            gui.open(player);
        });
    }

    public org.bukkit.inventory.ItemStack createMapItem() {
        org.bukkit.inventory.ItemStack bukkitItem = new org.bukkit.inventory.ItemStack(Material.MAP);
        bukkitItem.setDurability(view.getId());
        return bukkitItem;
    }

    public void sendFrame(Player player, Location location, String selected) {
        view = Bukkit.createMap(player.getWorld());

        CustomRenderer renderer = new CustomRenderer();
        ConfigManager config = ConfigUtils.getSprays();

        String sprayFile = config.getString(CosmeticsType.Sprays.getSectionKey() + "." + selected + ".file");
        String sprayURL = config.getString(CosmeticsType.Sprays.getSectionKey() + "." + selected + ".url");
        if (sprayFile == null) {
            if (!renderer.load(sprayURL)) {
                player.sendMessage(ColorUtil.colored("&cLooks like there's an error rendering the Spray, contact the admin!"));
                Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not load the URL for the " + selected + " Check if the URL in Sprays.yml is valid!");
                return;
            }
        } else {
            File file = new File(System.getProperty("user.dir") + "/plugins/BW1058-Cosmetics/" + Utility.plugin().getConfig().getString("Spray-Dir") + "/" + sprayFile);
            if (!renderer.load(file)) {
                player.sendMessage(ColorUtil.colored("&cLooks like there's an error rendering the Spray, contact the admin!"));
                Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not load the File for the " + selected + " Check if the File in Sprays.yml is valid!");
                Logger.getLogger("Minecraft").log(Level.SEVERE, "FilePath: " + file.getAbsolutePath());
                return;
            }
        }

        view.addRenderer(renderer);

        World nmsWorld = ((CraftWorld)location.getWorld()).getHandle();

        ItemStack nmsItem = CraftItemStack.asNMSCopy(createMapItem());
        org.bukkit.inventory.ItemStack bukkitItem = CraftItemStack.asBukkitCopy(nmsItem);

        frame = new EntityItemFrame(nmsWorld);
        frame.setPosition(location.getX(), location.getY(), location.getZ());
        frame.setItem(nmsItem);

        currentID = frame.getId();

        PacketPlayOutSpawnEntity spawnEntityPacket = new PacketPlayOutSpawnEntity(frame, 71, 0);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(frame.getId(), frame.getDataWatcher(), true);

        sendPacket(player, spawnEntityPacket, metadataPacket);
        player.sendMap(view);

        player.getInventory().setItem(0, bukkitItem);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.getInventory().remove(bukkitItem);
            }
        }.runTaskAsynchronously(HCore.getInstance());

        Sound sound = GSound.ENTITY_SILVERFISH_HURT.parseSound();
        player.playSound(frame.getBukkitEntity().getLocation(), sound, 10f, 10f);
    }

    private void sendPacket(Player player, Packet<?>... packets) {
        for (Packet<?> packet : packets) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }
}