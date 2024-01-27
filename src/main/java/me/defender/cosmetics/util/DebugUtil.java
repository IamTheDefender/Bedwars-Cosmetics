package me.defender.cosmetics.util;

public class DebugUtil {

    public static void addMessage(Object message){
        if(Utility.Cosmetics.getInstance().getConfig().getBoolean("Debug")) {
            Utility.Cosmetics.getInstance().getLogger().info("[Bedwars1058-Cosmetics] DEBUG: " + message);
        }
    }
}
