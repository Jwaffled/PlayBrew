package com.waffle.core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * A class containing static helper methods for miscellaneous operations
 */
public final class Utils {
    private Utils() {}

    /**
     * Applies a "tint" on an image of any transparent (or opaque) color
     * @param src the original image to be "tinted"
     * @param color the color to "tint" the image with (must be transparent to show original image)
     * @return the tinted image
     */
    public static BufferedImage applyTint(BufferedImage src, Color color) {
        BufferedImage img = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = img.createGraphics();
        graphics.drawImage(src, null, 0, 0);
        graphics.setComposite(AlphaComposite.SrcAtop);
        graphics.setColor(color);
        graphics.fillRect(0, 0, src.getWidth(), src.getHeight());
        graphics.dispose();

        return img;
    }

    /**
     * Loads a BufferedImage from the resources folder
     * @param path path relative to the resources folder
     * @return the image corresponding to the path given
     */
    public static BufferedImage loadImageFromPath(String path) {
        BufferedImage img;
        URL f = Utils.class.getClassLoader().getResource(path);
        if(f == null) {
            throw new IllegalArgumentException("Image file '" + path + "' could not be found!");
        }
        try {
            img = ImageIO.read(f);
        } catch(IOException e) {
            throw new IllegalStateException("Something went wrong while reading image file '" + path + "': " + e.getMessage());
        }

        return img;
    }

    /**
     * Returns a random integer from [min, max] (Inclusive)
     * @param min the minimum range to pick from
     * @param max the maximum range to pick from
     * @return a random integer from [min, max]
     */
    public static int unseededRandInclusive(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
