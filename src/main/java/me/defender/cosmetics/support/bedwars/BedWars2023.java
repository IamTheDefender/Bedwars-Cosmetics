package me.defender.cosmetics.support.bedwars;

import me.defender.cosmetics.Cosmetics;
import org.bukkit.Bukkit;

public class BedWars2023 {
    private final Cosmetics plugin;

    public BedWars2023(Cosmetics plugin) {
        this.plugin = plugin;
    }

    public void start() {
        if (Bukkit.getPluginManager().getPlugin("BedWars2023") != null) {
            plugin.setBedWars2023API(plugin.getServer().getServicesManager().getRegistration(com.tomkeuper.bedwars.api.BedWars.class).getProvider());
            plugin.getBedWars2023API().getAddonsUtil().registerAddon(new BedWars2023Addon(plugin));
        }
    }
}