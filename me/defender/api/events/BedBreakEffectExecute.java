package me.defender.api.events;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.defender.api.BwcAPI;
import me.defender.api.enums.Cosmetics;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BedBreakEffectExecute extends Event implements Cancellable {
    private Boolean cancelled;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Player WhoBrokeTheBed;
    private final String selected;
    private final ITeam effectedTeam;
    private final ITeam breakersTeam;

    public BedBreakEffectExecute(Player WhoBroke, ITeam effectedTeam){
        this.WhoBrokeTheBed = WhoBroke;
        this.effectedTeam = effectedTeam;
        this.selected = new BwcAPI().getSelectedCosmetic(WhoBroke, Cosmetics.BedBreakEffects);
        this.breakersTeam = new BwcAPI().getBwAPI().getArenaUtil().getArenaByPlayer(WhoBroke).getTeam(WhoBroke);
    }



    public Player getWhoBrokeTheBed(){
        return WhoBrokeTheBed;
    }

    public String getSelected() {
        return selected;
    }

    public ITeam getEffectedTeam() {
        return effectedTeam;
    }

    public ITeam getBreakerTeam() {
        return breakersTeam;
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
