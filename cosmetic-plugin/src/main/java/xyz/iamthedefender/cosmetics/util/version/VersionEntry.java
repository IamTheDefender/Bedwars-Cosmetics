package xyz.iamthedefender.cosmetics.util.version;

import xyz.iamthedefender.cosmetics.api.versionsupport.IVersionSupport;

import java.util.function.Supplier;

public class VersionEntry {

    private final String label;
    private final Supplier<Boolean> matcher;
    private final String className;

    VersionEntry(String label, Supplier<Boolean> matcher, String className) {
        this.label = label;
        this.matcher = matcher;
        this.className = className;
    }

    public boolean matches() {
        return matcher.get();
    }

    public IVersionSupport createInstance() {
        return VersionSupportRegistry.instantiate(className);
    }

    @Override
    public String toString() {
        return label;
    }
}
