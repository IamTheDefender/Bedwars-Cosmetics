package xyz.iamthedefender.cosmetics.api.particle;

import com.github.retrooper.packetevents.protocol.particle.Particle;
import com.github.retrooper.packetevents.protocol.particle.data.ParticleData;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleType;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.api.versionsupport.IVersionSupport;

import java.util.Objects;
import java.util.Optional;


@Getter
public class ParticleWrapper {

    private final ParticleType<?> particleType;

    public ParticleWrapper(@NotNull ParticleType<?> particleType) {
        this.particleType = Objects.requireNonNull(particleType, "ParticleType cannot be null!");
    }

    public @NotNull Particle<ParticleData> asParticle() {
        ParticleType<ParticleData> typed = (ParticleType<ParticleData>) particleType;
        return new Particle<>(typed);
    }

    public @NotNull IVersionSupport support() {
        return Utility.getApi().getVersionSupport();
    }

    public static @NotNull Optional<ParticleWrapper> getParticle(@NotNull String name) {
        Objects.requireNonNull(name, "The particle name cannot be null!");

        name = name.toLowerCase();

        try {
            ParticleType<?> type = ParticleTypes.getByName(name);

            if (!Utility.getApi().getVersionSupport().isValidParticle(name)) {
                return Optional.empty();
            }

            return Optional.of(new ParticleWrapper(type));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}