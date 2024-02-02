package me.defender.cosmetics.api.handler;

import java.util.UUID;

public interface IHandler {

    void register();

    ISetupSession getSetupSession(UUID playerUUID);
    IScoreboardUtil getScoreboardUtil();
    IArenaUtil getArenaUtil();

}
