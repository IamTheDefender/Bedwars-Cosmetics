package me.defender.cosmetics.api.categories.woodskins.items.log;

import com.cryptomorin.xseries.XMaterial;
import me.defender.cosmetics.api.categories.woodskins.WoodSkin;
import me.defender.cosmetics.api.enums.RarityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class jungleLog extends WoodSkin {
    @Override
    public ItemStack getItem() {
        return XMaterial.JUNGLE_LOG.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "jungle-log";
    }

    @Override
    public String getDisplayName() {
        return "Jungle Log";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Select the Jungle Log Wood Skin", "&7to be used when placing wood", "&7blocks.");
    }

    @Override
    public int getPrice() {
        return 55000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.LEGENDARY;
    }

    @Override
    public ItemStack woodSkin() {
        return XMaterial.JUNGLE_LOG.parseItem();
    }
}
