package me.ollie.capturethewool.core.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

@UtilityClass
public class VectorUtil {

    public Vector rotateAroundYAxis(Vector v, double angle) {
        angle = -angle;
        angle = Math.toRadians(angle);
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    public Vector knockback(Location loc1, Location loc2, double amount) {
        return loc1.clone().toVector().subtract(loc2.clone().toVector()).normalize().setY(2 * amount);
    }

    public static float getPitch(Vector vec) {
        double x = vec.getX();
        double y = vec.getY();
        double z = vec.getZ();
        double xz = Math.sqrt((x*x) + (z*z));

        double pitch = Math.toDegrees(Math.atan(xz/y));
        if (y <= 0)			pitch += 90;
        else				pitch -= 90;

        if (pitch == 180)
            pitch = 0;

        return (float) pitch;
    }

    public static float getYaw(Vector vec) {
        double x = vec.getX();
        double z = vec.getZ();

        double yaw = Math.toDegrees(Math.atan((-x)/z));
        if (z < 0) yaw += 180;

        return (float) yaw;
    }

    public static Vector trajectory(Vector from, Vector to) {
        return to.clone().subtract(from.clone()).normalize();
    }

    public static void velocity(Entity ent, double str, double yAdd, double yMax, boolean groundBoost) {
        velocity(ent, ent.getLocation().getDirection(), str, false, 0, yAdd, yMax, groundBoost);
    }

    public static void velocity(Entity entity, Vector vector, double strength, boolean ySet, double yBase, double yAdd, double yMax, boolean groundBoost) {
        if (Double.isNaN(vector.getX()) || Double.isNaN(vector.getY()) || Double.isNaN(vector.getZ()) || vector.length() == 0)
            return;

        //YSet
        if (ySet)
            vector.setY(yBase);

        //Modify
        vector.normalize();
        vector.multiply(strength);

        //YAdd
        vector.setY(vector.getY() + yAdd);

        //Limit
        if (vector.getY() > yMax)
            vector.setY(yMax);

        if (groundBoost)
            if (entity.getLocation().getBlock().getRelative(BlockFace.DOWN).isSolid())
                vector.setY(vector.getY() + 0.2);

        //Velocity
        entity.setFallDistance(0);
        entity.setVelocity(vector);
    }
}
