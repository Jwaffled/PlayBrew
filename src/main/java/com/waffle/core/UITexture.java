package com.waffle.core;

import java.awt.image.BufferedImage;

public class UITexture {
    public Vec2f position;
    public BufferedImage sprite;
    public int width;
    public int height;

    public UITexture(Vec2f position, BufferedImage sprite, int width, int height) {
        this.position = position;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
    }
}
