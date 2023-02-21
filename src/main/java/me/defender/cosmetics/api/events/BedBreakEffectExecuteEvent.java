package me.defender.cosmetics.api.events;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.enums.CosmeticsType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
/**
 * This event is called when a bed break effect is executed.
*/
public class BedBreakEffectExecuteEvent extends Event implements Cancellable {
    private Boolean cancelled;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Player WhoBrokeTheBed;
    private final String selected;
    private final ITeam effectedTeam;
    private final ITeam breakersTeam;

    public BedBreakEffectExecuteEvent(Player WhoBroke, ITeam effectedTeam){
        this.cancelled = false;
        this.WhoBrokeTheBed = WhoBroke;
        this.effectedTeam = effectedTeam;
        this.selected = new BwcAPI().getSelectedCosmetic(WhoBroke, CosmeticsType.BedBreakEffects);
        this.breakersTeam = new BwcAPI().getBwAPI().getArenaUtil().getArenaByPlayer(WhoBroke).getTeam(WhoBroke);
    }

    /**
     * Get the player that broke the bed.
     * @return Player
     */
    public Player getWhoBrokeTheBed(){
        return WhoBrokeTheBed;
    }

    /**
     * Get the selected bed break effect.
     * @return String
     */
    public String getSelected() {
        return selected;
    }

    /**
     * Get the team that lost their bed.
     * @return ITeam
     */
    public ITeam getEffectedTeam() {
        return effectedTeam;
    }

    /**
     * Get the team that broke the bed.
     * @return ITeam
     */
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
