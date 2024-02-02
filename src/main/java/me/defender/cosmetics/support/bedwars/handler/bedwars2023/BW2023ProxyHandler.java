package me.defender.cosmetics.support.bedwars.handler.bedwars2023;

import me.defender.cosmetics.api.handler.IArenaUtil;
import me.defender.cosmetics.api.handler.IHandler;
import me.defender.cosmetics.api.handler.IScoreboardUtil;
import me.defender.cosmetics.api.handler.ISetupSession;

import java.util.UUID;

public class BW2023ProxyHandler implements IHandler {
    @Override
    public void register() {
        //TODO
    }

    @Override
    public ISetupSession getSetupSession(UUID playerUUID) {
        return null;
    }

    @Override
    public IScoreboardUtil getScoreboardUtil() {
        return null;
    }

    @Override
    public IArenaUtil getArenaUtil() {
        return null;
    }
}
