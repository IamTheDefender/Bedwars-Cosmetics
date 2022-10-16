package me.defender.api.events;

import me.defender.api.enums.Cosmetics;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CosmeticPurchaseEvent extends Event implements Cancellable {
    private boolean isCancelled;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Player player;

    private final Cosmetics category;

    public CosmeticPurchaseEvent(Player p, Cosmetics category){
        this.isCancelled = false;
        this.player = p;
        this.category = category;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    public Player getPlayer(){
        return player;
    }

    public Cosmetics getCategory(){
        return category;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = true;
    }

}
