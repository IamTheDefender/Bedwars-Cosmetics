package me.defender.cosmetics.category.bedbreakeffects.items;


import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.cosmetics.CosmeticsType;
import me.defender.cosmetics.api.cosmetics.RarityType;
import me.defender.cosmetics.api.cosmetics.category.BedDestroy;
import me.defender.cosmetics.api.handler.ITeamHandler;
import me.defender.cosmetics.util.StartupUtils;
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
    public void execute(Player player, Location bedLocation, ITeamHandler victimTeam) {
            List<BedDestroy> bedDestroys = new ArrayList<>();
            for (BedDestroy bedDestroy : StartupUtils.bedDestroyList) {
                if (player.hasPermission(CosmeticsType.BedBreakEffects.getPermissionFormat() + "." + bedDestroy.getIdentifier())){
                    bedDestroys.add(bedDestroy);
                }
            }
            bedDestroys.get(new Random().nextInt(bedDestroys.size())).execute(player, bedLocation, victimTeam);
    }

}