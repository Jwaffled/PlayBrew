package com.waffle.render;

import com.waffle.core.Vec2f;

public class Camera {
    private final int width;
    private final int height;
    public Vec2f position;
    public float zoomScale;

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        this.position = new Vec2f(0, 0);
        zoomScale = 1;
    }

    public Camera(int width, int height, Vec2f position) {
        this.width = width;
        this.height = height;
        this.position = position;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
