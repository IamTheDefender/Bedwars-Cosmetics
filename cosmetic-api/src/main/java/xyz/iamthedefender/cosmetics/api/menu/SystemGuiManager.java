package xyz.iamthedefender.cosmetics.api.menu;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.iamthedefender.cosmetics.api.util.Utility;

import java.util.HashMap;

@Getter
public class SystemGuiManager implements Listener {
    private final HashMap<Player, SystemGui> guiStorage;
    private final HashMap<Player, SystemGui> lastOpenGuiStorage;
    private final JavaPlugin plugin;

    public SystemGuiManager(JavaPlugin plugin){
        guiStorage = new HashMap<>();
        lastOpenGuiStorage = new HashMap<>();
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        if(clickedInventory == null) return;
        
        SystemGui gui = getByPlayer(player);
        if(gui == null) return;

        if (!clickedInventory.equals(event.getView().getTopInventory())) {
            if(!gui.getOptions().contains(SystemGui.Option.DO_NOT_CANCEL_CLICK)){
                event.setCancelled(true);
            }
            return;
        }

        if(!gui.getOptions().contains(SystemGui.Option.DO_NOT_CANCEL_CLICK)){
            event.setCancelled(true);
        }

        ClickableItem item = gui.getItemsStorage().get(event.getSlot());

        if(item == null) {
            gui.onClick(event);
            return;
        }

        item.getAction().accept(event);
    }

    @EventHandler
    public void onPlayerDrag(InventoryDragEvent event){
        Player player = (Player) event.getWhoClicked();
        SystemGui gui = getByPlayer(player);
        if(gui == null) return;

        // Ensure drag is only in the top inventory
        for (int slot : event.getRawSlots()) {
            if (slot >= event.getView().getTopInventory().getSize()) {
                if(!gui.getOptions().contains(SystemGui.Option.DO_NOT_CANCEL_CLICK)){
                    event.setCancelled(true);
                    return;
                }
            }
        }

        if(!gui.getOptions().contains(SystemGui.Option.DO_NOT_CANCEL_CLICK)){
            event.setCancelled(true);
        }

        for (Integer inventorySlot : event.getInventorySlots()) {
            InventoryClickEvent inventoryClickEvent = new InventoryClickEvent(event.getView(), InventoryType.SlotType.CONTAINER, inventorySlot, ClickType.RIGHT, InventoryAction.PLACE_ONE);
            gui.onClick(inventoryClickEvent);
        }

    }

    @EventHandler
    public void onPlayerClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        SystemGui systemGui = getByPlayer(player);
        if(systemGui != null){
            Bukkit.getScheduler().runTaskLater(plugin, () -> systemGui.onClose(player), 1L);
            guiStorage.remove(player);

            getLastOpenGuiStorage().put(player, systemGui);
        }
        
        // Stop any active previews if the player manually closed the inventory
        Utility.getApi().getPreviewList().forEach(preview -> {
            if (preview.getActiveTasks().containsKey(player)) {
                // Only stop if it's NOT a programmatic close (like handleLocation closing it to start preview)
                if (!preview.getProgrammaticClose().contains(player)) {
                    preview.stopPreview(player);
                } else {
                    // It was a programmatic close, so we consume the marker
                    preview.getProgrammaticClose().remove(player);
                }
            }
        });
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        // Check if player is in any active preview
        boolean inPreview = Utility.getApi().getPreviewList().stream()
                .anyMatch(preview -> preview.getActiveTasks().containsKey(player));

        if (inPreview) {
            Location from = event.getFrom();
            Location to = event.getTo();
            if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
                event.setTo(from.setDirection(to.getDirection()));
            }
        }
    }

    public SystemGui getByPlayer(Player player){
        return guiStorage.get(player);
    }

    public void setByPlayer(Player player, SystemGui systemGui){
        guiStorage.put(player, systemGui);
    }
}
