package me.ollie.capturethewool.core.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;

import java.util.function.Consumer;

public class VectorCircle extends BukkitRunnable {

    private final LivingEntity entity;

    private final Location location;

    private final double speed;

    private final double radius;

    private final Vector axis;

    private final Consumer<Location> atLocation;

    private double theta;

    public VectorCircle(LivingEntity entity, Vector axis, double speed, double radius, Consumer<Location> atLocation) {
        this.entity = entity;
        this.axis = axis;
        this.speed = speed;
        this.radius = radius;
        this.atLocation = atLocation;

        this.location = getLocation();
    }

    @Override
    public void run() {

        if (theta >= 2 * Math.PI) theta = 0;
        theta += speed;

        // drawVector(Particle.VILLAGER_HAPPY, getLocation().clone(), axis.clone());

        Vector perpendicular = rotateVectorAboutAxis(getLocation().clone().toVector(), axis.clone(), theta);

        drawVector(Particle.VILLAGER_HAPPY, entity.getLocation(), perpendicular);

        // figures out location
        Location loc = getLocation().clone().add(perpendicular.clone().normalize().multiply(radius));

        loc.setYaw(VectorUtil.getYaw(perpendicular.clone()) - 90);
        loc.setPitch(VectorUtil.getPitch(perpendicular.clone()));


        atLocation.accept(loc);
    }

    private static void drawVector(Particle particle, Location location, Vector vector) {
        Location clone = location.clone();
        Vector cloneV = vector.clone();
        for (int i = 0; i < 50; i++) {
            clone.getWorld().spawnParticle(particle, clone.add(cloneV.normalize().multiply(0.5)), 1, 0, 0, 0, 0);
        }
    }

    public static Vector rotateVectorAboutAxis(Vector vec, Vector axis, double theta) {

        Vector vecClone = vec.clone();
        Vector axisClone = axis.clone();

        double x, y, z;
        double u, v, w;
        x = vecClone.getX();
        y = vecClone.getY();
        z = vecClone.getZ();

        u = axisClone.getX();
        v = axisClone.getY();
        w = axisClone.getZ();

        double v1 = u * x + v * y + w * z;

        double xPrime = u * v1 * (1.0D - Math.cos(theta))
                + x * Math.cos(theta)
                + (-w * y + v * z) * Math.sin(theta);

        double yPrime = v * v1 * (1d - Math.cos(theta))
                + y * Math.cos(theta)
                + (w * x - u * z) * Math.sin(theta);
        double zPrime = w * v1 * (1d - Math.cos(theta))
                + z * Math.cos(theta)
                + (-v * x + u * y) * Math.sin(theta);
        return new Vector(xPrime, yPrime, zPrime);
    }

    private Location getLocation() {
        return entity.getLocation().clone().add(0, 1, 0);
    }
}
