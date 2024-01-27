package me.defender.cosmetics.handler.bedwars1058;

import com.hakan.core.HCore;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.handler.IHandler;
import me.defender.cosmetics.category.bedbreakeffects.BedDestroyHandler1058;
import me.defender.cosmetics.category.deathcries.DeathCryHandler1058;
import me.defender.cosmetics.category.finalkilleffects.FinalKillEffectHandler1058;
import me.defender.cosmetics.category.glyphs.GlyphHandler1058;
import me.defender.cosmetics.category.islandtoppers.IslandTopperHandler1058;
import me.defender.cosmetics.category.killmessage.KillMessageHandler1058;
import me.defender.cosmetics.category.projectiletrails.ProjectileHandler;
import me.defender.cosmetics.category.shopkeeperskins.ShopKeeperHandler1058;
import me.defender.cosmetics.category.sprays.SpraysHandler1058;
import me.defender.cosmetics.category.victorydance.VictoryDanceHandler1058;
import me.defender.cosmetics.category.woodskin.WoodSkinHandler1058;

public class BW1058Handler implements IHandler {

    @Override
    public void register() {
        HCore.registerListeners(new ShopKeeperHandler1058());
        HCore.registerListeners(new GlyphHandler1058());
        HCore.registerListeners(new KillMessageHandler1058());
        HCore.registerListeners(new VictoryDanceHandler1058());
        HCore.registerListeners(new FinalKillEffectHandler1058());
        HCore.registerListeners(new BedDestroyHandler1058());
        HCore.registerListeners(new WoodSkinHandler1058());
        HCore.registerListeners(new IslandTopperHandler1058());
        HCore.registerListeners(new ProjectileHandler(Cosmetics.getInstance()));
        HCore.registerListeners(new DeathCryHandler1058());
        HCore.registerListeners(new SpraysHandler1058());
    }
}
