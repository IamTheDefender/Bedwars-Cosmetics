package me.defender.cosmetics.listener;

import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.event.CosmeticPurchaseEvent;
import me.defender.cosmetics.util.Utility;
import me.defender.cosmetics.database.PlayerOwnedData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CosmeticPurchaseListener implements Listener {

    @EventHandler
    public void onPurchase(CosmeticPurchaseEvent e){
        if(e.isCancelled())
            return;
        Cosmetics.getInstance()
                .getPlayerManager()
                .getPlayerOwnedData(e.getPlayer().getUniqueId())
                .updateOwned();

    }
}
