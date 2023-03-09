package me.defender.cosmetics.api.util;

public class DebugUtil {

    public static void addMessage(Object message){
        if(Utility.plugin().getConfig().getBoolean("Debug")) {
            Utility.plugin().getLogger().info("[Bedwars1058-Cosmetics] DEBUG: " + message);
        }
    }
}
