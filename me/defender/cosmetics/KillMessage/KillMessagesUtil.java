// 
// @author IamTheDefender
// 

package me.defender.cosmetics.KillMessage;

import me.defender.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.ThreadLocalRandom;

public class KillMessagesUtil {
    
    public static FileConfiguration killmessagecfg() {
         Main plugin = Main.getPlugin(Main.class);
        return plugin.killmessagecfg.getConfig();
    }

}
