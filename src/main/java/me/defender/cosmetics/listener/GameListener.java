package me.defender.cosmetics.listener;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.util.Utility;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getServer;

public class GameListener implements Listener {

    @EventHandler
    public void onGameStartEvent(GameStateChangeEvent event){
        if(event.getNewState() != GameState.playing) return;
        getServer().getScheduler().runTaskLater(Cosmetics.getInstance(), () -> {
            World world = event.getArena().getWorld();
            for (Entity entity : world.getEntities()) {
                boolean isCitizensNPC = entity.hasMetadata("NPC");
                if(!entity.isDead() && isCitizensNPC){
                    NPC npc = CitizensAPI.getNPCRegistry().getNPC(entity);
                    npc.data().setPersistent(NPC.Metadata.DEATH_SOUND, "");
                    npc.data().setPersistent(NPC.Metadata.AMBIENT_SOUND, "");
                    npc.data().setPersistent(NPC.Metadata.HURT_SOUND, "");
                    npc.data().setPersistent(NPC.Metadata.SILENT, true);
                }
            }
        }, 40L);
    }
}
