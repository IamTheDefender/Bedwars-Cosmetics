package me.defender.cosmetics.listener;

import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        BwcAPI api = new BwcAPI();
        // Saving for SQLite will be on shutdown.
        if(api.isMySQL()) {
            Utility.playerDataList.get(e.getPlayer().getUniqueId()).save();
            Utility.playerOwnedDataList.get(e.getPlayer().getUniqueId()).save();
        }
    }
}
