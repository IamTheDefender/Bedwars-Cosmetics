package me.defender.cosmetics.api.events;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FinalKillEffectsExecuteEvent extends Event implements Cancellable {
    private Boolean cancelled;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Player victim;
    private Player killer;

    private IArena arena;
    private String selected;
    public FinalKillEffectsExecuteEvent(Player victim, Player killer, IArena arena, String selected){
        this.cancelled = false;
        this.victim = victim;
        this.killer = killer;
        this.arena = arena;
        this.selected = selected;
       }


    public IArena getArena() {
        return arena;
    }

    public String getSelected(){
        return selected;
    }

    public Player getVictim() {
        return victim;
    }

    public Player getKiller() {
        return killer;
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
