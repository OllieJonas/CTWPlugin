package me.ollie.capturethewool.core.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
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
}
