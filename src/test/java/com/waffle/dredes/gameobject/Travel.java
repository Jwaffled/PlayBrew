package com.waffle.dredes.gameobject;

import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Constants;
import com.waffle.core.RenderShape;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.awt.*;

public class Travel extends GameObject {
    public GeometryComponent circle;
    public TransformComponent transformComponent;

    public Travel(Vec2f pos)
    {

        transformComponent = new TransformComponent(pos.x, pos.y);
        circle = new GeometryComponent();
        circle.shapes.add(new RenderShape(Constants.ShapeType.RECTANGLE, Constants.DrawMode.FILLED, new Color(200, 10, 10, 120), 75, 75, new Vec2f()));
    }

    public void start() {}

    public void update(float dt)
    {

    }
}
