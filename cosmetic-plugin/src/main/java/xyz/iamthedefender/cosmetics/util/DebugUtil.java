package xyz.iamthedefender.cosmetics.util;

import xyz.iamthedefender.cosmetics.CosmeticsPlugin;

public class DebugUtil {

    public static void addMessage(Object message){
        if (StartupUtils.isDebugEnabled()) {
            CosmeticsPlugin.getInstance().getLogger().info("DEBUG: " + message);
        }
    }
}
