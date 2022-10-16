package me.defender.listeners;

import me.defender.cache.Cache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onPlayerLeave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Cache.updateMySQL(e.getPlayer());
    }
}
