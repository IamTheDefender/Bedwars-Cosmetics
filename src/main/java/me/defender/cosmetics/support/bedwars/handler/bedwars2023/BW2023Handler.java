package me.defender.cosmetics.support.bedwars.handler.bedwars2023;

import com.hakan.core.HCore;
import com.tomkeuper.bedwars.api.BedWars;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.handler.*;
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
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class BW2023Handler implements IHandler {

    private BedWars api = com.tomkeuper.bedwars.BedWars.getAPI();
    @Override
    public void register() {
        BedWars2023 bedWars2023 = new BedWars2023(Cosmetics.getInstance());
        bedWars2023.start();

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


    @Override
    public IArenaUtil getArenaUtil() {
        return player -> {
            if(api.getArenaUtil().getArenaByPlayer(player) == null) return null;

            return (IArenaHandler) player1 -> new ITeamHandler() {
                @Override
                public Location getBed() {
                    return api.getArenaUtil().getArenaByPlayer(player1).getTeam(player1).getBed().getBlock().getLocation();
                }

                @Override
                public List<Player> getPlayers() {
                    return api.getArenaUtil().getArenaByPlayer(player1).getTeam(player1).getMembers();
                }
            };
        };
    }

    @Override
    public IScoreboardUtil getScoreboardUtil() {
        return new IScoreboardUtil() {

            @Override
            public void giveScoreboard(Player player, boolean b) {
                api.getScoreboardUtil().givePlayerScoreboard(player, b);
            }

            @Override
            public void removePlayerScoreboard(Player player) {
                api.getScoreboardUtil().removePlayerScoreboard(player);
            }
        };
    }

    @Override
    public ISetupSession getSetupSession(UUID playerUUID) {
        com.tomkeuper.bedwars.api.server.ISetupSession session = api.getSetupSession(playerUUID);
        if(session == null) return null;
        ISetupSession cosmeticsSessionHandler = new ISetupSession() {
            @Override
            public UUID getPlayerUUID() {
                return session.getPlayer().getUniqueId();
            }

            @Override
            public FileConfiguration getConfig() {
                return session.getConfig().getYml();
            }

            @Override
            public void saveConfigLoc(String path, Location value) {
                session.getConfig().saveConfigLoc(path, value);
            }

            @Override
            public void saveConfig() {
                session.getConfig().save();
            }
        };
        return cosmeticsSessionHandler;
    }
}
