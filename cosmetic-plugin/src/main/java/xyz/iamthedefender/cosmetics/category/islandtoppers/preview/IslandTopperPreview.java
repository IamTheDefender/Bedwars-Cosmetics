package xyz.iamthedefender.cosmetics.category.islandtoppers.preview;

import com.cryptomorin.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticRegistry;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.IslandTopper;
import xyz.iamthedefender.cosmetics.api.menu.SystemGui;
import xyz.iamthedefender.cosmetics.api.util.BlockData;
import xyz.iamthedefender.cosmetics.api.util.ColorUtil;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;
import xyz.iamthedefender.cosmetics.util.StartupUtils;
import xyz.iamthedefender.cosmetics.support.protocol.PacketEventsBridge;

import java.io.File;
import java.util.*;

import static xyz.iamthedefender.cosmetics.util.StartupUtils.getCosmeticLocation;
import static xyz.iamthedefender.cosmetics.util.StartupUtils.getPlayerLocation;

public class IslandTopperPreview {

    private final Map<UUID, Map<Integer, ItemStack>> inventories = new HashMap<>();

    public void sendIslandTopperPreview(Player player, String selected, SystemGui gui) {
        for (IslandTopper islandTopper : CosmeticRegistry.getByCategory(CosmeticType.ISLAND_TOPPERS)) {
            if (islandTopper.getIdentifier().equals(selected)) {
                if (islandTopper.getField(FieldsType.RARITY, player) == RarityType.NONE) {
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

        Map<Integer, ItemStack> items = inventories.get(playerUUID);

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
        } catch (Exception exception) {
            exception.printStackTrace();
            player.sendMessage(ColorUtil.translate("&cEither Preview location or Player location is not set! Contact the admin."));
        }

        if (cosmeticLocation == null || playerLocation == null) return;

        final Location finalPlayerLocation = playerLocation;
        final Location finalCosmeticLocation = cosmeticLocation;

        ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(finalPlayerLocation, EntityType.ARMOR_STAND);
        as.setVisible(false);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
                100, 2));

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.equals(player)) continue;

            player1.hidePlayer(player);
        }
        PacketEventsBridge.sendCamera(player, as.getEntityId());


        sendIslandTopper(player, finalCosmeticLocation, selected);

        Run.delayed(() -> {
            if (!as.isDead()) as.remove();

            PacketEventsBridge.sendCamera(player, player.getEntityId());
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            player.teleport(beforeLocation);

            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (player1.equals(player)) continue;

                player1.showPlayer(player);
            }

            for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
                playerInv.setItem(entry.getKey(), entry.getValue());
            }

            gui.open(player);
        }, 5 * 20L);
    }

    private void sendIslandTopper(Player player, Location location, String selected) {
        BlockFace direction = BlockFace.SELF;

        try {
            direction = BlockFace.valueOf(rpGetPlayerDirection(player));
        } catch (IllegalArgumentException ignored) {
        }

        ConfigManager config = ConfigUtils.getIslandToppers();
        String topperFileName = config.getString(CosmeticType.ISLAND_TOPPERS.getSectionKey() + "." + selected + ".file");

        if (topperFileName == null) {
            Bukkit.getLogger().severe("Can't find file for " + selected + " island topper!");
            return;
        }

        File file = new File(CosmeticsPlugin.getInstance().getHandler().getAddonPath() + "/IslandToppers/" + topperFileName);
        if (!file.exists()) {
            Bukkit.getLogger().severe("The file " + file.getName() + " does not exist!");
            return;
        }

        // Extract block data from clipboard
        Map<Location, BlockData> blockLocations = CosmeticsPlugin.getInstance().getWorldEditHandler().extractBlockData(file, location, player.getWorld(), direction);
        if (blockLocations.isEmpty()) return;

        // Start animation
        boolean useOrder = StartupUtils.useIslandTopperOrder();
        startBlockAnimation(player, blockLocations, useOrder);
    }


    private void startBlockAnimation(Player player, Map<Location, BlockData> blockLocations, boolean useOrder) {
        List<Location> locations = new ArrayList<>(blockLocations.keySet());

        new BukkitRunnable() {
            private int index = 0;
            private boolean showingBlocks = true;

            @Override
            public void run() {
                if (blockLocations.isEmpty() || index >= locations.size()) {
                    if (showingBlocks) {
                        // Switch to hiding blocks
                        showingBlocks = false;
                        index = 0;
                    } else {
                        // Animation complete
                        cancel();
                    }
                    return;
                }

                Location loc = locations.get(index);

                if (showingBlocks) {
                    BlockData blockData = blockLocations.get(loc);
                    player.sendBlockChange(loc, blockData.getMaterial(), blockData.getData());
                } else {
                    player.sendBlockChange(loc, Material.AIR, (byte) 0);
                }

                index++;
            }
        }.runTaskTimerAsynchronously(CosmeticsPlugin.getInstance(), 0L, 0L);
    }

    private String rpGetPlayerDirection(Player playerSelf) {
        String dir;
        float y = playerSelf.getLocation().getYaw();
        if (y < 0) {
            y += 360;
        }
        y %= 360;
        int i = (int) ((y + 8) / 22.5);
        if (i == 0) {
            dir = "west";
        } else if (i == 1) {
            dir = "west northwest";
        } else if (i == 2) {
            dir = "northwest";
        } else if (i == 3) {
            dir = "north northwest";
        } else if (i == 4) {
            dir = "north";
        } else if (i == 5) {
            dir = "north northeast";
        } else if (i == 6) {
            dir = "northeast";
        } else if (i == 7) {
            dir = "east northeast";
        } else if (i == 8) {
            dir = "east";
        } else if (i == 9) {
            dir = "east southeast";
        } else if (i == 10) {
            dir = "southeast";
        } else if (i == 11) {
            dir = "south southeast";
        } else if (i == 12) {
            dir = "south";
        } else if (i == 13) {
            dir = "south southwest";
        } else if (i == 14) {
            dir = "southwest";
        } else if (i == 15) {
            dir = "west southwest";
        } else {
            dir = "west";
        }
        return dir;
    }
}
