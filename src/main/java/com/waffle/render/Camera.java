package com.waffle.render;

import com.waffle.struct.Vec2f;

public class Camera {
    private final Vec2f size;
    private Vec2f position;
    private float zoomScale;

    public Camera(float width, float height) {
        size = new Vec2f(width, height);
        this.position = new Vec2f(0, 0);
        setZoomScale(1);
    }

    public Camera(float width, float height, Vec2f position) {
        size = new Vec2f(width, height);
        this.position = new Vec2f(position);
    }

    public Vec2f getSize() {
        return size;
    }

    public Vec2f getPosition() {
        return position;
    }

    public float getZoomScale() {
        return zoomScale;
    }

    public void setZoomScale(float zoomScale) {
        this.zoomScale = zoomScale;
    }
}
