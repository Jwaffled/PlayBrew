package com.waffle.components;

import com.waffle.core.RenderShape;
import com.waffle.ecs.IComponent;

import java.util.ArrayList;
import java.util.List;

public class GeometryComponent implements IComponent {
    public List<RenderShape> shapes;

    public GeometryComponent(List<RenderShape> shapes) {
        this.shapes = shapes;
    }

    public GeometryComponent() {
        shapes = new ArrayList<>();
    }
}
