package xyz.iamthedefender.cosmetics.versionsupport;

import com.hakan.core.HCore;
import xyz.iamthedefender.cosmetics.api.versionsupport.IVersionSupport;

public class VersionSupport_1_8_R3 implements IVersionSupport {
    @Override
    public org.bukkit.inventory.ItemStack getSkull(String base64) {
        return HCore.skullBuilder(base64).build();
    }
}
