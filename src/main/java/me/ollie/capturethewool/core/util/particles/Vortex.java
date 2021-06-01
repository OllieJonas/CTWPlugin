package me.ollie.capturethewool.core.util.particles;

import me.ollie.capturethewool.core.util.collections.CircularArrayList;
import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Vortex extends BukkitRunnable {

    private List<Spiral> spiralLocations;

    private World world;

    private Particle particle;

    private Location location;

    private final boolean downwards;

    private boolean rainbow = false;

    private final AtomicInteger rainbowCount = new AtomicInteger(0);


    public Vortex(Location location, Particle particle, int height, int radius, int noSpirals) {
        this(location, particle, height, radius, noSpirals, false);
    }

    public Vortex(Location location, Particle particle, int height, int radius, int noSpirals, boolean downwards) {
        this.location = location;
        this.spiralLocations = calculateVortexLocations(height, noSpirals, radius, location);
        this.particle = particle;
        this.downwards = downwards;
        this.world = location.getWorld();
    }

    public void rainbow(boolean rainbow) {
        this.rainbow = rainbow;
    }


    @Override
    public void run() {

        spiralLocations.forEach(s -> {
            if (rainbow)
                world.spawnParticle(Particle.REDSTONE, s.next(), 1, new Particle.DustOptions(getColour(rainbowCount.getAndUpdate(i -> i != 0 && i % 6 == 0 ? 0 : i + 1)), 1));
            else
                world.spawnParticle(particle, s.next(), 1, 0, 0, 0, 0);
        });
    }

    private Color getColour(int i) {
        switch (i) {
            case 0: return Color.RED;
            case 1: return Color.ORANGE;
            case 2: return Color.YELLOW;
            case 3: return Color.LIME;
            case 4: return Color.BLUE;
            case 5: return Color.PURPLE;
            case 6: return Color.FUCHSIA;
            default: return Color.LIME;
        }
    }

    public void destroy() {
        cancel();
        this.spiralLocations = null;
        this.world = null;
        this.particle = null;
        this.location = null;
    }

    private List<Spiral> calculateVortexLocations(int height, int noSpirals, int radius, Location startLocation) {
        List<Spiral> vortexLocations = new ArrayList<>();

        float increment = Math.max(0.01F, (float) height / (noSpirals * 22));

        for (int i = 0; i < noSpirals; i++) {
            Spiral spiral = new Spiral(height, radius, increment, startLocation.clone().add(0, i * (float) (height / noSpirals), 0), i % 2 == 0, downwards);
            spiral.setCount(Math.round(i * (float) (height / noSpirals)));
            vortexLocations.add(spiral);
        }

        return vortexLocations;
    }

    private class Spiral {

        private final List<Location> locations;

        private int count;


        public Spiral(int height, int radius, float increment, Location startLocation) {
            this(height, radius, increment, startLocation, true, false);
        }

        public Spiral(int height, int radius, float increment, Location startLocation, boolean clockwise) {
            this(height, radius, increment, startLocation, clockwise, false);
        }

        public Spiral(int height, int radius, float increment, Location startLocation, boolean clockwise, boolean downwards) {
            this.locations = calculateSpiralLocations(height, radius, increment, startLocation, clockwise, downwards);
            this.count = 0;
        }

        public Location next() {
            return locations.get(count++);
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    private List<Location> calculateSpiralLocations(int height, int radius, float increment, Location startLocation, boolean clockwise, boolean downwards) {
        return downwards ? calculateSpiralLocationsDownwards(height, radius, increment, startLocation, clockwise)
                : calculateSpiralLocationsUpwards(height, radius, increment, startLocation, clockwise);
    }

    private List<Location> calculateSpiralLocationsDownwards(int height, int radius, float increment, Location startLocation, boolean clockwise) {
        List<Location> locations = new CircularArrayList<>();
        // parameters

        // useful stuff
        World world = startLocation.getWorld();
        double y = startLocation.getY() + height;

        double xz = 0;

        for (double i = 0; i < height; i += increment) {
            float translatedX = (clockwise ? 1 : -1) * (float) (radius * Math.sin(xz));
            float translatedZ = (clockwise ? 1 : -1) * (float) -(radius * Math.cos(xz));

            xz += 10;
            y += increment;

//            if (y <= location.getY())
//                y = location.getY() + height;

            locations.add(new Location(world, startLocation.getX() + translatedX, y -= increment, startLocation.getZ() + translatedZ));
        }
        return locations;
    }

    private List<Location> calculateSpiralLocationsUpwards(int height, int radius, float increment, Location startLocation, boolean clockwise) {
        List<Location> locations = new CircularArrayList<>();
        // parameters

        // useful stuff
        World world = startLocation.getWorld();
        double y = startLocation.getY();


        for (double i = 0; i < height; i += increment) {
            float translatedX = (clockwise ? 1 : -1) * (float) (radius * Math.sin(i));
            float translatedZ = (clockwise ? 1 : -1) * (float) -(radius * Math.cos(i));

            y += increment;

            if (y >= location.getY() + height)
                y = location.getY();

            locations.add(new Location(world, startLocation.getX() + translatedX, y += increment, startLocation.getZ() + translatedZ));
        }
        return locations;
    }
}
