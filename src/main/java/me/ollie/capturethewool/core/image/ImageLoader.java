package me.ollie.capturethewool.core.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ImageLoader {

    public static Optional<BufferedImage> load(String url, Runnable onFail) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(url));
        } catch (IOException e) {
            onFail.run();
        }


        return Optional.ofNullable(image);
    }
}
