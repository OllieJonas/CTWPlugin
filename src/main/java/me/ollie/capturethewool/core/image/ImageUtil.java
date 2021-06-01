package me.ollie.capturethewool.core.image;

import org.bukkit.Color;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageUtil {

    public static Color[][] convertSlow(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Color[][] result = new Color[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int rgb = image.getRGB(col, row);
                result[row][col] = fromRGB(rgb);
            }
        }

        return result;
    }
    // seen here: https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
    public static Color[][] convert(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        Color[][] result = new Color[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = fromRGB(argb);
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = fromRGB(argb);
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }
        return result;
    }

    public static double distance(Color rgb1, Color rgb2) {

        long r = (long) rgb1.getRed() - rgb2.getRed();
        long g = (long) rgb1.getGreen() - rgb2.getGreen();
        long b = (long) rgb1.getBlue() - rgb2.getBlue();

        long rmean = (long) (rgb1.getRed() + rgb2.getRed()) / 2;

        return Math.sqrt((((512+rmean)*r*r)>>8) + 4*g*g + (((767-rmean)*b*b)>>8));

    }

    private static Color fromRGB(int argb) {
        int r = (argb>>16) &0xFF;
        int g = (argb>>8) &0xFF;
        int b = (argb>>0) &0xFF;
        return fromRGB(r, g, b);
    }

    private static Color fromRGB(int red, int green, int blue) {
        try {
            return Color.fromRGB(red, green, blue);
        } catch (Exception e) {
            return Color.BLUE;
        }
    }
}
