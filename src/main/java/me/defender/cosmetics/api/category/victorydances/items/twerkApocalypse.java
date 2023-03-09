package me.defender.cosmetics.api.category.victorydances.items;

import com.cryptomorin.xseries.XMaterial;
import com.hakan.core.HCore;
import com.hakan.core.skin.Skin;
import me.defender.cosmetics.api.category.victorydances.VictoryDance;
import me.defender.cosmetics.api.category.victorydances.util.UsefulUtilsVD;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.MathUtil;
import me.defender.cosmetics.api.util.Utility;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.trait.LookClose;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class twerkApocalypse extends VictoryDance {
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
        List<String> values = Arrays.asList(Utility.getFromName(winner.getName()));
        Skin skin = new Skin(values.get(0), values.get(1));
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        List<NPC> npcs = new ArrayList<>();

        HCore.syncScheduler().every(1).limit(15).run(() -> {
            List<Block> freeBlocks = UsefulUtilsVD.getFreeBlocks(winner.getLocation());
            Location loc = freeBlocks.get(new Random().nextInt(freeBlocks.size())).getLocation();
            loc.setYaw((float) MathUtil.getRandom(0.0D, 360.0D));

            if(loc.getBlock().getType() == Material.AIR && loc.subtract(0,1,0).getBlock().getType() != Material.AIR) {
                NPC npc = registry.createNPC(EntityType.PLAYER, winner.getDisplayName());
                npc.getOrAddTrait(SkinTrait.class).setTexture(skin.getTexture(), skin.getSignature());
                npc.getOrAddTrait(LookClose.class).lookClose(true);
                npc.spawn(loc.add(0,1,0));
                npcs.add(npc);

                HCore.syncScheduler().every(1, TimeUnit.SECONDS).run(() -> {
                    if (npc.isSpawned()) {
                        Player npcP  = (Player) npc.getEntity();
                        npcP.setSneaking(!npcP.isSneaking());
                    }
                });
            }
        });

        HCore.syncScheduler().after(30, TimeUnit.SECONDS).run(() -> {
            npcs.forEach(NPC::destroy);
        });

    }
}
