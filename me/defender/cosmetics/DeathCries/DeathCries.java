// 
// @author IamTheDefender
// 

package me.defender.cosmetics.DeathCries;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.Sound;
import me.defender.api.utils.util;
import me.defender.cosmetics.ShopKeeperSkins.ShopKeeperSkin;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.Listener;

public class DeathCries implements Listener
{
    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent e) {
        if (!ShopKeeperSkin.arenas.containsKey(e.getEntity().getWorld().getName())) {
            return;
        }
        String selected = util.plugin().playerData.getConfig().getString(util.getUUID(e.getEntity()) + ".DeathCries");

        final String sound = util.plugin().dcdata.getConfig().getString("DeathCries." + selected + ".Sound");
        final Float pitch1 = (float) util.plugin().dcdata.getConfig().getInt("DeathCries." + selected + ".Pitch1");
        final Float pitch2 = (float) util.plugin().dcdata.getConfig().getInt("DeathCries." + selected + ".Pitch2");
        final Player p = e.getEntity();
        if (sound == null) {
            return;
        }
        e.getEntity().getWorld().playSound(p.getLocation(), Sound.valueOf(sound.toUpperCase()), pitch1, pitch2);
    }
}
