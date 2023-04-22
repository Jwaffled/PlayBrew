package com.waffle.core;

public class BoundingBox {
    private Constants.ShapeType shape;
    private int width, height;

    public BoundingBox(Constants.ShapeType type, int w, int h) {
        shape = type;
        width = w;
        height = h;
    }

    public Constants.ShapeType getShapeType() {
        return shape;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
