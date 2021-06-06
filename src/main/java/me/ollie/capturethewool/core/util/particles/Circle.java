package me.ollie.capturethewool.core.util.particles;

import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Circle extends BukkitRunnable {

    private final List<Location> locations;

    private final World world;

    private final Particle particle;

    private final Location location;

    private final int radius;

    private final float density;

    private boolean rainbow = false;

    public static Circle rainbow(Location location, int radius, float density) {
        Circle circle = new Circle(Particle.REDSTONE, location, radius, density);
        circle.setRainbow(true);
        return circle;
    }

    public Circle(Particle particle, Location location, int radius, float density) {
        this.particle = particle;
        this.location = location;
        this.world = location.getWorld();
        this.radius = radius;
        this.density = density;
        this.locations = calculateLocations();
    }

    private List<Location> calculateLocations() {
        List<Location> locations = new ArrayList<>();
        for (float i = 0; i <= radius; i += density) {
            if (i == 0) continue;

            for (int j = 0; j < 25; j++) {
                double x = location.getX() + (i * Math.sin(j));
                double z = location.getZ() + (i * Math.cos(j));
                locations.add(new Location(world, x, location.getY() + 0.1, z));
            }
        }
        return locations;
    }

    @Override
    public void run() {
        AtomicInteger rainbowCount = new AtomicInteger(0);

        if (rainbow)
            locations.forEach(l -> world.spawnParticle(Particle.REDSTONE, l, 1, new Particle.DustOptions(getColour(rainbowCount.getAndUpdate(i -> i != 0 && i % 6 == 0 ? 0 : i + 1)), 1)));
        else
            locations.forEach(l -> world.spawnParticle(particle, l, 1, 0, 0, 0, 0));
    }

    public void setRainbow(boolean rainbow) {
        this.rainbow = rainbow;
    }

    private Color getColour(int i) {
        return switch (i) {
            case 0 -> Color.RED;
            case 1 -> Color.ORANGE;
            case 2 -> Color.YELLOW;
            case 3 -> Color.LIME;
            case 4 -> Color.BLUE;
            case 5 -> Color.PURPLE;
            case 6 -> Color.FUCHSIA;
            default -> Color.LIME;
        };
    }
}
