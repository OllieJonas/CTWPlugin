package me.ollie.capturethewool.core.util;

import lombok.experimental.UtilityClass;
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
}
