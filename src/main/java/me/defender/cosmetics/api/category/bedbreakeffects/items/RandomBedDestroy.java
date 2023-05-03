package me.defender.cosmetics.api.category.bedbreakeffects.items;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.category.bedbreakeffects.BedDestroy;
import me.defender.cosmetics.api.enums.CosmeticsType;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.StartupUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBedDestroy extends BedDestroy {
    @Override
    public ItemStack getItem() {
        return XMaterial.ENDER_CHEST.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "random";
    }

    @Override
    public String getDisplayName() {
        return "Random";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Select RANDOM as your" ,"bed destroy.");
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.RANDOM;
    }

    @Override
    public void execute(Player player, Location bedLocation, ITeam victimTeam) {
            List<BedDestroy> bedDestroys = new ArrayList<>();
            for (BedDestroy bedDestroy : StartupUtils.bedDestroyList) {
                if(player.hasPermission(CosmeticsType.BedBreakEffects.getPermissionFormat() + "." + bedDestroy.getIdentifier())){
                    bedDestroys.add(bedDestroy);
                }
            }
            bedDestroys.get(new Random().nextInt(bedDestroys.size())).execute(player, bedLocation, victimTeam);
    }
}
