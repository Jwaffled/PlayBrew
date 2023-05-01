package com.waffle.core;

import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;

public class UITexture {
    private Vec2f position;
    private BufferedImage sprite;
    private int width;
    private int height;

    public UITexture(Vec2f pos, BufferedImage spr, int w, int h) {
        position = pos;
        sprite = spr;
        width = w;
        height = h;
    }

    public Vec2f getPosition() {
        return position;
    }

    public void setPosition(Vec2f position) {
        this.position = position;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
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

    public String toString() {
        return String.format("Position: %s, Width: %dpx, Height: %dpx", position, width, height);
    }
}
