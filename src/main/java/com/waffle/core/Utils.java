package com.waffle.core;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
     * Loads the specified file path as a String
     * @param path path relative to the resources folder
     * @return the file data as a String
     */
    public static String loadFileAsString(String path) {
        InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream(path);
        if(inputStream != null) {
            try {
                byte[] data = inputStream.readAllBytes();
                return new String(data, StandardCharsets.UTF_8);
            } catch(IOException e) {
                throw new IllegalStateException("Something went wrong while reading file '" + path + "': " + e.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        } else {
            throw new IllegalArgumentException("File '" + path + "' could not be found!");
        }
    }

    public static String[] getFilesInDirectory(String path) {
        try(InputStream in = Utils.class.getClassLoader().getResourceAsStream(path)) {
            if(in != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                    List<String> fileList = new ArrayList<>();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        fileList.add(line);
                    }
                    return fileList.toArray(new String[0]);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            } else {
                throw new IllegalStateException(path + " cannot be found");
            }
        } catch(IOException e) {
            throw new IllegalStateException("Error while reading directory " + path + " in JAR file", e);
        }

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

    public static float fcos(double rads) {
        return (float)Math.cos(rads);
    }

    public static float fsin(double rads) {
        return (float)Math.sin(rads);
    }
}
