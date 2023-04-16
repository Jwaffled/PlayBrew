package com.waffle.core;

import java.awt.image.BufferedImage;

public class SpriteRenderer {
    private Vec2f position;
    private int width;
    private int height;
    private BufferedImage sprite;

    public SpriteRenderer(Vec2f pos, BufferedImage spr, int w, int h) {
        setPosition(pos);
        setSprite(spr);
        setWidth(w);
        setHeight(h);
    }

    public Vec2f getPosition() {
        return position;
    }

    public void setPosition(Vec2f position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }
}
