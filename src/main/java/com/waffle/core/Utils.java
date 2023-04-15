package com.waffle.core;

import com.waffle.render.Camera;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public final class Utils {
    private Utils() {}

    public static boolean shouldRender(Vec2f drawPos, int width, int height, Camera cam) {
        return !(drawPos.x + width < 0 || drawPos.x > cam.getWidth() * cam.zoomScale || drawPos.y + height < 0 || drawPos.y - height > cam.getHeight() * cam.zoomScale);
    }

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
}
