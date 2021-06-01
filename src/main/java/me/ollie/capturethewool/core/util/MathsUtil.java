package me.ollie.capturethewool.core.util;

public class MathsUtil {

    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static double roundHalf(double value) {
        return Math.round(value * 2) / 2.0;
    }
}
