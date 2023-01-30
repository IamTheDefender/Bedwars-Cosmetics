package me.defender.cosmetics.api.events;

import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.enums.CosmeticsType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VictoryDancesExecuteEvent extends Event implements Cancellable {

    private Boolean cancelled;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Player player;
    private final String selected;

    public VictoryDancesExecuteEvent(Player winner){
        this.cancelled = false;
        this.player = winner;
        this.selected = new BwcAPI().getSelectedCosmetic(winner, CosmeticsType.VictoryDances);
    }


    public Player getPlayer() {
        return player;
    }

    public String getSelected() {
        return selected;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public static HandlerList getHandlersList() {
        return HANDLERS_LIST;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
