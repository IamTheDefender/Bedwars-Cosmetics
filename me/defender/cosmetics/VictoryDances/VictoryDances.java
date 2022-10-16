// 
// @author IamTheDefender
// 

package me.defender.cosmetics.VictoryDances;

import me.defender.api.BwcAPI;
import me.defender.api.enums.Cosmetics;
import me.defender.api.events.VictoryDancesExecute;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import java.util.UUID;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import org.bukkit.event.Listener;

public class VictoryDances implements Listener {
    @EventHandler
    public void onGameEnd(GameEndEvent e) {
        for (UUID uuid : e.getWinners()) {
             Player p = Bukkit.getPlayer(uuid);
            String selected = new BwcAPI().getSelectedCosmetic(p, Cosmetics.VictoryDances);
            VictoryDancesExecute event = new VictoryDancesExecute(p);
            Bukkit.getPluginManager().callEvent(event);

            if(event.isCancelled())
                return;

            switch (selected) {
                case "None":
                    break;
                case "Anvil-Rain":
                    VictoryDancesUtil.AnvilRainVD(p);
                    break;
                case "Yee-Haw":
                    VictoryDancesUtil.YeeHawVD(p);
                    break;
                case "Fireworks":
                    VictoryDancesUtil.spawnFireworksVD(p);
                    break;
                case "Cold-Snap":
                    VictoryDancesUtil.ColdSnapVD(p);
                    break;
                case "Rainbow-Dolly":
                    VictoryDancesUtil.sheepVD(p);
                    break;
                case "Night-Shift":
                    VictoryDancesUtil.NightShiftVD(p);
                    break;
                case "Wither-Rider":
                    VictoryDancesUtil.WitherDanceVD(p);
                    break;
                case "Raining-Pigs":
                    VictoryDancesUtil.RainingPigVD(p);
                    break;
                case "Haunted":
                    VictoryDancesUtil.hauntedVD(p);
                    break;
                case "Floating-Lantern":
                    VictoryDancesUtil.floatingLanternsVD(p);
                    break;
                case "Toy-Stick":
                    VictoryDancesUtil.ToyStickVD(p);
                    break;
                case "Dragon-Rider":
                    VictoryDancesUtil.dragonVD(p);
                    break;
            }
        }
    }
}
