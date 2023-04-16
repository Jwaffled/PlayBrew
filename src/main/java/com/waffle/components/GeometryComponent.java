package com.waffle.components;

import com.waffle.core.RenderShape;
import com.waffle.ecs.IComponent;

import java.util.ArrayList;
import java.util.List;

public class GeometryComponent implements IComponent {
    private List<RenderShape> shapes;

    public GeometryComponent(List<RenderShape> shapes) {
        this.setShapes(shapes);
    }

    public GeometryComponent() {
        setShapes(new ArrayList<>());
    }

    public List<RenderShape> getShapes() {
        return shapes;
    }

    public void setShapes(List<RenderShape> shapes) {
        this.shapes = shapes;
    }
}
