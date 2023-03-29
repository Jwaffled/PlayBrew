package com.waffle.components;

import com.waffle.core.Constants;
import com.waffle.ecs.IComponent;

import java.awt.*;

public class GeometryComponent implements IComponent {
    public int width, height;
    public Constants.ShapeType shape;
    public Constants.DrawMode mode;
    public Color color;

    public GeometryComponent(Constants.ShapeType s, Constants.DrawMode m, Color c, int width, int height) {
        color = c;
        shape = s;
        mode = m;
        this.width = width;
        this.height = height;
    }
}
