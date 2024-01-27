package me.defender.cosmetics.handler.bedwars2023;

import com.hakan.core.HCore;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.handler.IHandler;
import me.defender.cosmetics.category.bedbreakeffects.BedDestroyHandler2023;
import me.defender.cosmetics.category.deathcries.DeathCryHandler2023;
import me.defender.cosmetics.category.finalkilleffects.FinalKillEffectHandler2023;
import me.defender.cosmetics.category.glyphs.GlyphHandler2023;
import me.defender.cosmetics.category.islandtoppers.IslandTopperHandler2023;
import me.defender.cosmetics.category.killmessage.KillMessageHandler2023;
import me.defender.cosmetics.category.projectiletrails.ProjectileHandler;
import me.defender.cosmetics.category.shopkeeperskins.ShopKeeperHandler2023;
import me.defender.cosmetics.category.sprays.SpraysHandler2023;
import me.defender.cosmetics.category.victorydance.VictoryDanceHandler2023;
import me.defender.cosmetics.category.woodskin.WoodSkinHandler2023;

public class BW2023Handler implements IHandler {
    @Override
    public void register() {
        HCore.registerListeners(new ProjectileHandler(Cosmetics.getInstance()));
        HCore.registerListeners(new WoodSkinHandler2023());
        HCore.registerListeners(new VictoryDanceHandler2023());
        HCore.registerListeners(new ShopKeeperHandler2023());
        HCore.registerListeners(new KillMessageHandler2023());
        HCore.registerListeners(new IslandTopperHandler2023());
        HCore.registerListeners(new GlyphHandler2023());
        HCore.registerListeners(new FinalKillEffectHandler2023());
        HCore.registerListeners(new BedDestroyHandler2023());
        HCore.registerListeners(new SpraysHandler2023());
        HCore.registerListeners(new DeathCryHandler2023());
    }
}
