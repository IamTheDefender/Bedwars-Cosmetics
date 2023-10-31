

package me.defender.cosmetics.api.category.victorydances;

import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.event.VictoryDancesExecuteEvent;
import me.defender.cosmetics.api.util.StartupUtils;
import me.defender.cosmetics.api.util.DebugUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

import static me.defender.cosmetics.api.util.Utility.plugin;

public class VictoryDanceHandler1058 implements Listener {
    @EventHandler
    public void onGameEnd1058(GameEndEvent e) {

        boolean isVictoryDancesEnabled = plugin().getConfig().getBoolean("victory-dances.enabled");
        if (!isVictoryDancesEnabled) return;

        for (UUID uuid : e.getWinners()) {
             Player p = Bukkit.getPlayer(uuid);
            String selected = new BwcAPI().getSelectedCosmetic(p, CosmeticsType.VictoryDances);
            VictoryDancesExecuteEvent event = new VictoryDancesExecuteEvent(p);
            Bukkit.getPluginManager().callEvent(event);

            if(event.isCancelled())
                return;

            DebugUtil.addMessage("Executing " + selected + " Victory Dance for " + p.getDisplayName());
            for(VictoryDance victoryDance : StartupUtils.victoryDancesList){
                if(selected.equals(victoryDance.getIdentifier())){
                    if(victoryDance.getField(FieldsType.RARITY, p) == RarityType.NONE) return;
                    victoryDance.execute(p);
                }
            }
        }
    }
}