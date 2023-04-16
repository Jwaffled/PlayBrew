package com.waffle.core;

import java.awt.image.BufferedImage;

public class SpriteRenderer {
    public Vec2f position;
    public int width, height;
    public BufferedImage sprite;

    public SpriteRenderer(Vec2f pos, BufferedImage spr, int w, int h) {
        position = pos;
        sprite = spr;
        width = w;
        height = h;
    }
}
