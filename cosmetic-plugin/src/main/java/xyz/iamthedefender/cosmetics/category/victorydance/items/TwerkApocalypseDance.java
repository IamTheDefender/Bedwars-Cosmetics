package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.victorydance.util.UsefulUtilsVD;
import xyz.iamthedefender.cosmetics.support.npc.PacketNpc;
import xyz.iamthedefender.cosmetics.util.MathUtil;
import xyz.iamthedefender.cosmetics.support.npc.PacketNpcManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TwerkApocalypseDance extends VictoryDance {

    private final Map<Player, List<PacketNpc>> npcsStorage = new HashMap<>();

    @Override
    public ItemStack getItem() {
        return XMaterial.LEATHER_BOOTS.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "twerk-apocalypse";
    }

    @Override
    public String getDisplayName() {
        return "Twerk Apocalypse";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Spawns tons of more yourself and", "&7have them twerk.");
    }

    @Override
    public int getPrice() {
        return 100000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.LEGENDARY;
    }

    @Override
    public void execute(Player winner) {
        PacketNpcManager manager = CosmeticsPlugin.getInstance().getPacketNpcManager();

        addTask(winner, Run.every(() -> {
            List<Block> freeBlocks = UsefulUtilsVD.getFreeBlocks(winner.getLocation());
            if (freeBlocks.isEmpty()) {
                return;
            }

            Location location = freeBlocks.get(MathUtil.getRandom(0, freeBlocks.size() - 1)).getLocation();
            location.setYaw((float) MathUtil.getRandom(0.0D, 360.0D));

            if (location.getBlock().getType() != Material.AIR || location.clone().subtract(0, 1, 0).getBlock().getType() == Material.AIR) {
                return;
            }

            PacketNpc npc = manager.createClone(winner, winner, location.add(0, 1, 0));
            npc.spawn(winner.getWorld().getPlayers());
            npcsStorage.computeIfAbsent(winner, key -> new ArrayList<>()).add(npc);

            addTask(winner, Run.every(() -> npc.sneaking(!npc.isSneaking()), 20L, 9));
        }, 1L, 15));

        addTask(winner, Run.delayed(() -> Optional.ofNullable(npcsStorage.remove(winner))
                .ifPresent(list -> list.forEach(manager::destroy)), 20L * 9 + 10L));
    }

    @Override
    public void stopExecution(Player winner) {
        super.stopExecution(winner);

        PacketNpcManager manager = CosmeticsPlugin.getInstance().getPacketNpcManager();
        Optional.ofNullable(npcsStorage.remove(winner)).ifPresent(list -> list.forEach(manager::destroy));
    }
}
