package xyz.iamthedefender.cosmetics.util.version;

import xyz.iamthedefender.cosmetics.api.handler.IWorldEditHandler;
import xyz.iamthedefender.cosmetics.api.versionsupport.IVersionSupport;

import java.util.List;

public final class VersionSupportRegistry {

    private static final List<VersionEntry> VERSION_ENTRIES = List.of(
            new VersionEntry("1.8-1.17",
                    () -> VersionSupportUtil.isLowerThan("1.18"),
                    "xyz.iamthedefender.cosmetics.versionsupport.VersionSupport_1_8_R3"),
            new VersionEntry("1.18+",
                    () -> VersionSupportUtil.isHigherThanOrEqual("1.18"),
                    "xyz.iamthedefender.cosmetics.versionsupport.VersionSupport_1_20")
    );

    private static final List<WorldEditEntry> WORLD_EDIT_ENTRIES = List.of(
            new WorldEditEntry("1.8-1.12",
                    () -> VersionSupportUtil.isLowerThan("1.13"),
                    "xyz.iamthedefender.cosmetics.versionsupport.LegacyWorldEditHandler"),
            new WorldEditEntry("1.13+",
                    () -> VersionSupportUtil.isHigherThanOrEqual("1.13"),
                    "xyz.iamthedefender.cosmetics.versionsupport.ModernWorldEditHandler")
    );

    public static IVersionSupport resolveVersionSupport() {
        return VERSION_ENTRIES.stream()
                .filter(VersionEntry::matches)
                .findFirst()
                .map(VersionEntry::createInstance)
                .orElse(null);
    }

    public static IWorldEditHandler resolveWorldEditHandler() {
        return WORLD_EDIT_ENTRIES.stream()
                .filter(WorldEditEntry::matches)
                .findFirst()
                .map(WorldEditEntry::createInstance)
                .orElse(null);
    }

    public static <T> T instantiate(String className) {
        try {
            return (T) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate class: " + className, e);
        }
    }
}