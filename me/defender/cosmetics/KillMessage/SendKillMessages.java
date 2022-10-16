// 
// @author IamTheDefender
// 

package me.defender.cosmetics.KillMessage;

import java.util.List;

import me.defender.api.BwcAPI;
import me.defender.api.enums.Cosmetics;
import me.defender.api.utils.util;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SendKillMessages {
    
    public static void sendpvpkm(Player p, String victim, String killer, Boolean FinalKill, ChatColor color1, ChatColor color2) {

        String selected = new BwcAPI().getSelectedCosmetic(p, Cosmetics.KillMessage);
        final List<String> pvp = KillMessagesUtil.killmessagecfg().getStringList("KillMessages." + selected + ".PvP-Kill");
        if(pvp.isEmpty())
            return;
        if (selected != "Default") {
                final String pvp2 = pvp.get(ThreadLocalRandom.current().nextInt(pvp.size())).replace("{victim}", color1 + victim).replace("{killer}", color2 + killer);
                if (FinalKill) {
                    p.sendMessage(util.color(pvp2 + " &b&lFINAL KILL!"));
                }
                else {
                    p.sendMessage(util.color(pvp2));
                }
        }
    }
    
    public static void sendvoidkm(final Player p, final String victim, final String killer, final Boolean FinalKill, final ChatColor color1, final ChatColor color2) {
        String selected = new BwcAPI().getSelectedCosmetic(p, Cosmetics.KillMessage);
        final List<String> voidkill = KillMessagesUtil.killmessagecfg().getStringList("KillMessages." + selected + ".Void-Kill");
        if (selected != "Default") {
            if (voidkill.isEmpty()) {
                sendpvpkm(p, victim, killer, FinalKill, color1, color2);
            }
            else if (voidkill.size() == 0) {
                final String voidkill2 = voidkill.get(0).replace("{victim}", color1 + victim).replace("{killer}", color2 + killer);
                if (FinalKill) {
                    p.sendMessage(util.color(voidkill2 + " &b&lFINAL KILL!"));
                }
                else {
                    p.sendMessage(util.color(voidkill2));
                }
            }
            else if (voidkill.size() > 0) {
                final int voidkill3 = ThreadLocalRandom.current().nextInt(voidkill.size());
                final String voidkill4 = voidkill.get(voidkill3).replace("{victim}", color1 + victim).replace("{killer}", color2 + killer);
                if (FinalKill) {
                    p.sendMessage(util.color(voidkill4 + " &b&lFINAL KILL!"));
                }
                else {
                    p.sendMessage(util.color(voidkill4));
                }
            }
        }
    }
    
    public static void sendshootkm(final Player p, final String victim, final String killer, final Boolean FinalKill, final ChatColor color1, final ChatColor color2) {
        String selected = new BwcAPI().getSelectedCosmetic(p, Cosmetics.KillMessage);
        final List<String> voidkill = KillMessagesUtil.killmessagecfg().getStringList("KillMessages." + selected + ".Shoot-Kill");
        if (selected != "Default") {
            if (voidkill.isEmpty()) {
                sendpvpkm(p, victim, killer, FinalKill, color1, color2);
            }
            else if (voidkill.size() == 0) {
                final String voidkill2 = voidkill.get(0).replace("{victim}", color1 + victim).replace("{killer}", color2 + killer);
                if (FinalKill) {
                    p.sendMessage(util.color(voidkill2 + " &b&lFINAL KILL!"));
                }
                else {
                    p.sendMessage(util.color(voidkill2));
                }
            }
            else if (voidkill.size() > 0) {
                final int voidkill3 = ThreadLocalRandom.current().nextInt(voidkill.size());
                final String voidkill4 = voidkill.get(voidkill3).replace("{victim}", color1 + victim).replace("{killer}", color2 + killer);
                if (FinalKill) {
                    p.sendMessage(util.color(voidkill4 + " &b&lFINAL KILL!"));
                }
                else {
                    p.sendMessage(util.color(voidkill4));
                }
            }
        }
    }
    
    public static void sendexplosionkm(final Player p, final String victim, final String killer, final Boolean FinalKill, final ChatColor color1, final ChatColor color2) {
        String selected = new BwcAPI().getSelectedCosmetic(p, Cosmetics.KillMessage);
        final List<String> voidkill = KillMessagesUtil.killmessagecfg().getStringList("KillMessages." + selected + ".Explosion-Kill");
        if (selected != "Default") {
            if (voidkill.isEmpty()) {
                sendpvpkm(p, victim, killer, FinalKill, color1, color2);
            }
            else if (voidkill.size() == 0) {
                final String voidkill2 = voidkill.get(0).replace("{victim}", color1 + victim).replace("{killer}", color2 + killer);
                if (FinalKill) {
                    p.sendMessage(util.color(voidkill2 + " &b&lFINAL KILL!"));
                }
                else {
                    p.sendMessage(util.color(voidkill2));
                }
            }

            else if (voidkill.size() > 0) {
                final int voidkill3 = ThreadLocalRandom.current().nextInt(voidkill.size());
                final String voidkill4 = voidkill.get(voidkill3).replace("{victim}", color1 + victim).replace("{killer}", color2 + killer);
                if (FinalKill) {
                    p.sendMessage(util.color(voidkill4 + " &b&lFINAL KILL!"));
                }
                else {
                    p.sendMessage(util.color(voidkill4));
                }
            }
        }
    }
}
