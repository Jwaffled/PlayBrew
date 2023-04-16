package com.waffle.render;

import com.waffle.core.Vec2f;

public class Camera {
    private final int width;
    private final int height;
    private Vec2f position;
    private float zoomScale;

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        this.setPosition(new Vec2f(0, 0));
        setZoomScale(1);
    }

    public Camera(int width, int height, Vec2f position) {
        this.width = width;
        this.height = height;
        this.setPosition(position);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Vec2f getPosition() {
        return position;
    }

    public void setPosition(Vec2f position) {
        this.position = position;
    }

    public float getZoomScale() {
        return zoomScale;
    }

    public void setZoomScale(float zoomScale) {
        this.zoomScale = zoomScale;
    }
}
