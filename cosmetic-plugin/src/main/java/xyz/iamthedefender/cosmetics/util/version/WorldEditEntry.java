package xyz.iamthedefender.cosmetics.util.version;

import xyz.iamthedefender.cosmetics.api.handler.IWorldEditHandler;

import java.util.function.Supplier;

public final class WorldEditEntry {
    private final String label;
    private final Supplier<Boolean> matcher;
    private final String className;

    WorldEditEntry(String label, Supplier<Boolean> matcher, String className) {
        this.label = label;
        this.matcher = matcher;
        this.className = className;
    }

    public boolean matches() {
        return matcher.get();
    }

    public IWorldEditHandler createInstance() {
        return VersionSupportRegistry.instantiate(className);
    }

    @Override
    public String toString() {
        return label;
    }
}
