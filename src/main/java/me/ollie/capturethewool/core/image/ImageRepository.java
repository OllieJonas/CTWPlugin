package me.ollie.capturethewool.core.image;

import org.bukkit.util.Vector;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ImageRepository {

    public static final ImageRenderer DAB = new ImageRenderer(ImageLoader.load("https://i.imgur.com/othlIoN.png", () -> {}).get(), false);

    public static final ImageRenderer HEART = new ImageRenderer(ImageLoader.load("https://i.imgur.com/lIjOkRg.jpg", () -> {}).get(), false);

    public static class Factory {

        public static ImageRenderer get(String name) {
            return switch (name.toLowerCase()) {
                case "dab" -> DAB;
                case "heart" -> HEART;
                default -> null;
            };
        }

        public static Vector getTranslation(String name) {
            switch (name.toLowerCase()) {
                case "dab":
                    return new Vector(0, 7, 0);
                case "heart":
                    return new Vector(0, 11, 0);
                default:
                    return null;
            }
        }
    }
}
