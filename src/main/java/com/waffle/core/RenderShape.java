package com.waffle.core;

import java.awt.*;

public class RenderShape {
    private int width;
    private int height;
    private Constants.ShapeType shape;
    private Constants.DrawMode mode;
    private Color color;
    private Vec2f position;
    public RenderShape(Constants.ShapeType s, Constants.DrawMode m, Color c, int w, int h, Vec2f p) {
        setShape(s);
        setMode(m);
        setColor(c);
        setWidth(w);
        setHeight(h);
        setPosition(p);
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

    public Constants.ShapeType getShape() {
        return shape;
    }

    public void setShape(Constants.ShapeType shape) {
        this.shape = shape;
    }

    public Constants.DrawMode getMode() {
        return mode;
    }

    public void setMode(Constants.DrawMode mode) {
        this.mode = mode;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vec2f getPosition() {
        return position;
    }

    public void setPosition(Vec2f position) {
        this.position = position;
    }
}
