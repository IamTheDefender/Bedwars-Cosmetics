package me.defender.cosmetics.api.event;

import lombok.Getter;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.cosmetics.CosmeticsType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VictoryDancesExecuteEvent extends Event implements Cancellable {

    private Boolean cancelled;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    /**
     * -- GETTER --
     *  Get the player that won the game.
     *
     * @return Player
     */
    @Getter
    private final Player player;
    /**
     * -- GETTER --
     *  Get the selected victory dance.
     *
     * @return String
     */
    @Getter
    private final String selected;

    public VictoryDancesExecuteEvent(Player winner){
        this.cancelled = false;
        this.player = winner;
        this.selected = Cosmetics.getInstance().getApi().getSelectedCosmetic(winner, CosmeticsType.VictoryDances);
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
