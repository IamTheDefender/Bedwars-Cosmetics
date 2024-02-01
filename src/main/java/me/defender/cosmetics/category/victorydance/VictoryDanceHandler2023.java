package me.defender.cosmetics.category.victorydance;

import com.tomkeuper.bedwars.api.events.gameplay.GameEndEvent;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.cosmetics.CosmeticsType;
import me.defender.cosmetics.api.cosmetics.FieldsType;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.api.cosmetics.category.VictoryDance;
import me.defender.cosmetics.api.event.VictoryDancesExecuteEvent;
import me.defender.cosmetics.util.DebugUtil;
import me.defender.cosmetics.util.StartupUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class VictoryDanceHandler2023 implements Listener {

    @EventHandler
    public void onGameEnd2023(GameEndEvent e) {

        boolean isVictoryDancesEnabled = Cosmetics.getInstance().getConfig().getBoolean("victory-dances.enabled");
        if (!isVictoryDancesEnabled) return;

        for (UUID uuid : e.getWinners()) {
            Player p = Bukkit.getPlayer(uuid);
            String selected = Cosmetics.getInstance().getApi().getSelectedCosmetic(p, CosmeticsType.VictoryDances);
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