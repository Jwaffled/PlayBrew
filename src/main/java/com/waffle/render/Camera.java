package com.waffle.render;

import com.waffle.core.Vec2f;

public class Camera {
    private int width, height;
    private Vec2f position;

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        this.position = new Vec2f(0, 0);
    }

    public Camera(int width, int height, Vec2f position) {
        this.width = width;
        this.height = height;
        this.position = position;
    }

    public Vec2f getPosition() {
        return this.position;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
