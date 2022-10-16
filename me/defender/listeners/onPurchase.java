package me.defender.listeners;

import me.defender.api.events.CosmeticPurchaseEvent;
import me.defender.api.utils.util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class onPurchase implements Listener {

    @EventHandler
    public void onPurchase(CosmeticPurchaseEvent e){
        if(e.isCancelled())
            return;


        util.updateOwned(e.getPlayer());
    }
}
