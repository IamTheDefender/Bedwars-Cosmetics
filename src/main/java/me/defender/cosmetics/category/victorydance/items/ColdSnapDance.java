package me.defender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.api.cosmetics.category.VictoryDance;
import me.defender.cosmetics.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ColdSnapDance extends VictoryDance {
    @Override
    public ItemStack getItem() {
        return XMaterial.PACKED_ICE.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "cold-snap";
    }

    @Override
    public String getDisplayName() {
        return "Cold Snap";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Chill an area around you to", "&7absolute zero.");
    }

    @Override
    public int getPrice() {
        return 5000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.COMMON;
    }

    @Override
    public void execute(Player winner) {
        AtomicInteger radius = new AtomicInteger(2);
        int duration = 6;
        AtomicReference<Float> pitch = new AtomicReference<>(0.8f);

        int task = Bukkit.getScheduler().runTaskTimer(Cosmetics.getInstance(), () -> {
            Location loc = winner.getLocation().subtract(0, 1, 0);
            List<Block> blocks = Utility.getSphere(loc, radius.get());
            for (Block block : blocks) {
                if (block.getType() != Material.AIR && block.getType() != XMaterial.PACKED_ICE.parseMaterial() && block.getType() != XMaterial.ICE.parseMaterial()) {
                    block.setType(XMaterial.ICE.parseMaterial());
                }
            }
            XSound.ENTITY_EXPERIENCE_ORB_PICKUP.play(winner, 1, pitch.get());
            pitch.set(pitch.get() + 0.2f);
            radius.addAndGet(1);
        }, 0, 10).getTaskId();

        Bukkit.getScheduler().runTaskLater(Cosmetics.getInstance(), () -> {
            Bukkit.getScheduler().cancelTask(task);
        }, duration * 20L + 5);
    }
}
