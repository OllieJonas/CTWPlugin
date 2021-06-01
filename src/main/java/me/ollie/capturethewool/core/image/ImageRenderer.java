package me.ollie.capturethewool.core.image;

import lombok.Setter;
import org.bukkit.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageRenderer {

    private final Color[][] imagePalette;

    private final int length;

    @Setter
    private boolean removeWhite = false;

    public ImageRenderer(BufferedImage image, boolean white) {
        this.length = image.getWidth();
        this.imagePalette = ImageUtil.convert(image);
    }

    private Color[][] removeWhite(boolean white, Color[][] colours) {
        if (!white) return colours;

        List<List<Color>> colors = new ArrayList<>();
        int colCount;
        for (Color[] row : colours) {
            colCount = 0;
            List<Color> rowList = new ArrayList<>();
            for (Color pixel : row) {
                if (ImageUtil.distance(pixel, Color.WHITE) >= 40) {
                    rowList.add(pixel);
                    colCount++;
                }
            }

            if (colCount != 0)
                colors.add(rowList);
        }
        return colors.stream().map(l -> l.toArray(new Color[0])).toArray(Color[][]::new);
    }

    private Color[][] markWhite(boolean white, Color[][] colours) {
        if (!white) return colours;


        int rowC = 0;
        int col;
        for (Color[] row : colours) {
            col = 0;
            for (Color pixel : row) {
                if (ImageUtil.distance(pixel, Color.WHITE) < 100) {
                    colours[rowC][col] = Color.RED;
                }
                col++;
            }
            rowC++;
        }
        return colours;
    }

    public void render(Location location) {
        render(location, 0.1F, 0.1F);
    }

    public void render(Location location, double rowDensity, double colDensity) {
        World world = location.getWorld();
        Location clone = location.add((colDensity * length) / 2, 0, 0);
        double x = location.getX();
        for (Color[] row : imagePalette) {
            for (Color pixel : row) {
                clone.subtract(colDensity, 0, 0);

                if (ImageUtil.distance(pixel, Color.WHITE) >= 40) {
                    world.spawnParticle(Particle.REDSTONE, clone, 1, new Particle.DustOptions(pixel, 1));
                }
            }
            clone.add(x - clone.getX(), -rowDensity, 0);
        }
    }

}
