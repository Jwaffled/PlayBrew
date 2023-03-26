package com.waffle.animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sprite {
    public BufferedImage image;

    /**
     * Initializes a frame given an image
     * @param sprite the image this frame represents
     */
    public Sprite(BufferedImage sprite) {
        this.image = sprite;
    }

    /**
     * Initializes a frame given an image file
     * @param filePath the file of the image the frame represents
     * @throws IOException upon a bad/nonexistent file
     */
    public Sprite(String filePath) throws IOException {
        image = ImageIO.read(getClass().getClassLoader().getResource(filePath));
    }
}
