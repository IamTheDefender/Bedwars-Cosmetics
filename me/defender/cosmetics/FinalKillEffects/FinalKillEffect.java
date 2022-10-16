package me.defender.cosmetics.FinalKillEffects;

import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import me.defender.api.BwcAPI;
import me.defender.api.enums.Cosmetics;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FinalKillEffect implements Listener {

    @EventHandler
    public void onFinalKill(PlayerKillEvent e){
        BwcAPI api = new BwcAPI();
        if(!(e.getKiller() instanceof Player))
            return;
        String selected = api.getSelectedCosmetic(e.getKiller(), Cosmetics.FinalKillEffects);
        Player victim = e.getVictim();
        Player killer = e.getKiller();
        if(!e.getCause().isFinalKill()){
            return;
        }

        Location loc = e.getVictim().getLocation();

        switch (selected){
            case "Squid-Missile":
                FinalKillEffectsUtil.squidMissile(e.getKiller(), e.getVictim().getLocation());
                break;
            case "Firework":
                FinalKillEffectsUtil.spawnFirework(killer, loc);
                break;
            case "Lighting-Strike":
                FinalKillEffectsUtil.spawnLightStrike(loc);
                break;
            case "Heart-Aura":
                FinalKillEffectsUtil.heartAura(loc);
                break;
            case "Burning-Shoes":
                FinalKillEffectsUtil.burnigShoes(killer);
                break;
            case "Rekt":
                FinalKillEffectsUtil.rekt(victim, killer.getDisplayName());
                break;
            case "Batcrux":
                FinalKillEffectsUtil.batCrux(victim);
                break;
            case "Tornado":
                FinalKillEffectsUtil.playTornado(victim, loc);
                break;
            case "None":
                 break;

        }




    }
}
