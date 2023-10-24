package me.defender.cosmetics.api.event;

import com.andrei1058.bedwars.api.arena.IArena;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FinalKillEffectsExecuteEvent extends Event implements Cancellable {
    private Boolean cancelled;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    /**
     * -- GETTER --
     *  Get the victim.
     *
     * @return Player
     */
    @Getter
    private Player victim;
    /**
     * -- GETTER --
     *  Get the killer.
     *
     * @return Player
     */
    @Getter
    private Player killer;

    /**
     * -- GETTER --
     *  Get the Arena.
     *
     * @return IArena
     */
    @Getter
    private IArena arena;
    /**
     * -- GETTER --
     *  Get the selected final kill effect.
     *
     * @return String
     */
    @Getter
    private String selected;
    public FinalKillEffectsExecuteEvent(Player victim, Player killer, IArena arena, String selected){
        this.cancelled = false;
        this.victim = victim;
        this.killer = killer;
        this.arena = arena;
        this.selected = selected;
       }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public static HandlerList getHandlersList() {
        return HANDLERS_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
