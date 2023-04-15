package com.waffle.core;

import java.awt.*;

public class RenderShape {
    public int width, height;
    public Constants.ShapeType shape;
    public Constants.DrawMode mode;
    public Color color;
    public Vec2f pos;
    public RenderShape(Constants.ShapeType s, Constants.DrawMode m, Color c, int w, int h, Vec2f p) {
        shape = s;
        mode = m;
        color = c;
        width = w;
        height = h;
        pos = p;
    }
}
