package me.ollie.capturethewool.items.items;

import lombok.Builder;
import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.util.particles.ActionOnEnterVortex;
import me.ollie.capturethewool.core.util.particles.Circle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import java.util.function.Consumer;

@Builder
public class Well {

    private final Location location;

    private final Particle vortexParticle;

    private final Particle circleParticle;

    private final Consumer<LivingEntity> onEnter;

    private final int radius;

    private final long onEnterFrequency;

    private final long duration;

    @Builder.Default
    private boolean circleRainbow = false;

    @Builder.Default
    private boolean vortexRainbow = false;

    public Well(Location location, Particle vortexParticle, Particle circleParticle, Consumer<LivingEntity> onEnter, int radius, long onEnterFrequency, long duration, boolean circleRainbow, boolean vortexRainbow) {
        this.location = location;
        this.vortexParticle = vortexParticle;
        this.circleParticle = circleParticle;
        this.onEnter = onEnter;
        this.radius = radius;
        this.onEnterFrequency = onEnterFrequency;
        this.duration = duration;
        this.circleRainbow = circleRainbow;
        this.vortexRainbow = vortexRainbow;
    }

    public void run() {
        ActionOnEnterVortex vortex = new ActionOnEnterVortex(CaptureTheWool.getInstance(),
                vortexParticle,
                location.clone().subtract(0, 1, 0),
                5,
                radius,
                3,
                 onEnter,
                onEnterFrequency);

        vortex.setRainbow(vortexRainbow);

        vortex.run();

        Circle fanningOutCircle = new Circle(circleParticle, location, radius, 0.25F);
        if (circleRainbow)
            fanningOutCircle.setRainbow(true);

        fanningOutCircle.runTask(CaptureTheWool.getInstance());

        Circle persistentCircle = new Circle(circleParticle, location, radius, radius);
        if (circleRainbow)
            persistentCircle.setRainbow(true);

        persistentCircle.runTaskTimer(CaptureTheWool.getInstance(), 0L, 10L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(CaptureTheWool.getInstance(), () -> {
            vortex.destroy();
            fanningOutCircle.cancel();
            persistentCircle.cancel();
        }, duration * 20);
    }
}
