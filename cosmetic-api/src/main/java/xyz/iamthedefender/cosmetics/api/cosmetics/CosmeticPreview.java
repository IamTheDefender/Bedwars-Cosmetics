package xyz.iamthedefender.cosmetics.api.cosmetics;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.menu.SystemGui;
import xyz.iamthedefender.cosmetics.api.util.Utility;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class CosmeticPreview {

    private final CosmeticType<?> type;
    private final Map<Player, List<ItemStack>> inventoryStorage;
    private final Map<Player, Location> originalLocations;
    private final Map<Player, Runnable> onEnd;
    private final Map<Player, org.bukkit.scheduler.BukkitTask> activeTasks;
    private final Set<Player> programmaticClose;

    public CosmeticPreview(CosmeticType<?> type) {
        this.type = type;
        inventoryStorage = new ConcurrentHashMap<>();
        originalLocations = new ConcurrentHashMap<>();
        onEnd = new ConcurrentHashMap<>();
        activeTasks = new ConcurrentHashMap<>();
        programmaticClose = Collections.newSetFromMap(new ConcurrentHashMap<>());

        Utility.getApi().getPreviewList().add(this);
    }

    public abstract void showPreview(Player player, Cosmetics selected, Location previewLocation, Location playerLocation) throws IllegalArgumentException;

    public void stopPreview(Player player) {
        if (activeTasks.containsKey(player)) {
            activeTasks.remove(player).cancel();
        }
        cleanup(player);
    }

    private void cleanup(Player player) {
        programmaticClose.remove(player);
        if (onEnd.containsKey(player)) {
            Runnable runnable = onEnd.remove(player);
            if (runnable != null) runnable.run();
        }
        
        
        if (player.isOnline()) {
            showPlayer(player);
            
            
            Location oldLocation = originalLocations.remove(player);
            if (oldLocation != null) {
                player.teleport(oldLocation);
            }

            List<ItemStack> items = inventoryStorage.remove(player);
            if (items != null) {
                player.getInventory().clear();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i) != null) {
                        player.getInventory().setItem(i, items.get(i));
                    }
                }
            }
        } else {
            inventoryStorage.remove(player);
            originalLocations.remove(player);
            onEnd.remove(player);
        }
    }

    public void handleLocation(Player player, Location newLocation) throws IllegalArgumentException {
        if (newLocation == null) {
            throw new IllegalArgumentException("Player location is not set!");
        }

        if (activeTasks.containsKey(player)) {
            activeTasks.remove(player).cancel();
            Runnable runnable = onEnd.remove(player);
            if (runnable != null) runnable.run();
        }

        if (!originalLocations.containsKey(player)) {
            originalLocations.put(player, player.getLocation().clone());
        }

        SystemGui systemGui = Utility.getApi().getSystemGuiManager().getByPlayer(player);

        hidePlayer(player);
        player.teleport(newLocation);

        if (!inventoryStorage.containsKey(player)) {
            List<ItemStack> contents = new ArrayList<>(Arrays.asList(player.getInventory().getContents()));
            inventoryStorage.put(player, contents);
            player.getInventory().clear();
        }

        programmaticClose.add(player);
        player.closeInventory();

        org.bukkit.scheduler.BukkitTask task = org.bukkit.Bukkit.getScheduler().runTaskLater(Utility.getPlugin(), () -> {
            activeTasks.remove(player);
            cleanup(player);
            
            if (player.isOnline() && systemGui != null) {
                systemGui.open(player);
            }
        }, getEndDelay());

        activeTasks.put(player, task);
    }

    private void hidePlayer(Player player) {
        try {
            Bukkit.getOnlinePlayers().stream().filter(p -> !p.equals(player)).forEach(p -> p.hidePlayer(Utility.getPlugin(), player));
        }catch (NoSuchMethodError e) {
            Bukkit.getOnlinePlayers().stream().filter(p -> !p.equals(player)).forEach(p -> p.hidePlayer(player));
        }
    }

    private void showPlayer(Player player) {
        try {
            Bukkit.getOnlinePlayers().stream().filter(p -> !p.equals(player)).forEach(p -> p.showPlayer(Utility.getPlugin(), player));
        }catch (NoSuchMethodError e) {
            Bukkit.getOnlinePlayers().stream().filter(p -> !p.equals(player)).forEach(p -> p.showPlayer(player));
        }
    }

    public void setOnEnd(Player player, Runnable runnable) {
        onEnd.put(player, runnable);
    }

    public long getEndDelay() {
        return 3 * 20L;
    }

    public boolean isProgrammaticClose(Player player) {
        return programmaticClose.contains(player);
    }

}
