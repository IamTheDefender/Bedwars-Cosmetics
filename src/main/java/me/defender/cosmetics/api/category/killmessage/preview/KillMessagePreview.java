package me.defender.cosmetics.api.category.killmessage.preview;

import com.cryptomorin.xseries.XSound;
import com.hakan.core.HCore;
import com.hakan.core.ui.inventory.InventoryGui;
import com.hakan.core.utils.ColorUtil;
import me.defender.cosmetics.api.category.killmessage.KillMessage;
import me.defender.cosmetics.api.category.killmessage.util.KillMessageUtils;
import me.defender.cosmetics.api.enums.FieldsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.StartupUtils;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class KillMessagePreview {


    /**
     * Sends preview message to given player from given previewID or selected.
     * @param player Player object that needs to receive the messages.
     * @param selected previewID for the selected preview, example is "bbq".
     */
    public void sendPreviewMessage(Player player, String selected){
        for(KillMessage killMessage : StartupUtils.killMessageList){
            if(killMessage.getIdentifier().equals(selected)){
                if(killMessage.getField(FieldsType.RARITY, player) == RarityType.NONE) return;
            }
        }
        InventoryGui gui = HCore.getInventoryByPlayer(player);
        player.closeInventory();
        XSound.ENTITY_ARROW_HIT_PLAYER.play(player, 1.0f, 1.0f);
        List<String> message = Utility.getListLang(player, "cosmetics." + "kill-message" + ".preview.message");
        for (String s : message) {
            if(s.contains("%message%")){
                KillMessageUtils.sendKillMessage(player, "Player", null, false, ChatColor.GREEN, ChatColor.GRAY, "PvP", "Old Message" ,true, selected, player.getDisplayName());
                KillMessageUtils.sendKillMessage(player, "Player", null, false, ChatColor.GREEN, ChatColor.GRAY, "Void", "Old Message" ,true, selected, player.getDisplayName());
                KillMessageUtils.sendKillMessage(player, "Player", null, false, ChatColor.GREEN, ChatColor.GRAY, "Shoot", "Old Message" ,true, selected, player.getDisplayName());
                KillMessageUtils.sendKillMessage(player, "Player", null, false, ChatColor.GREEN, ChatColor.GRAY, "Explosion", "Old Message" ,true, selected, player.getDisplayName());
            }else{
                player.sendMessage(ColorUtil.colored(s));
            }
        }
        HCore.syncScheduler().after(5, TimeUnit.SECONDS).run(() -> {
           gui.open(player);
        });

    }
}
