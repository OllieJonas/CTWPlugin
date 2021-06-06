package me.ollie.capturethewool.core.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MathsUtil {

    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static double roundHalf(double value) {
        return Math.round(value * 2) / 2.0;
    }

    public static double offset(Location a, Location b) {
        return offset(a.toVector(), b.toVector());
    }

    public static double offset(Vector a, Vector b) {
        return a.subtract(b).length();
    }
}
