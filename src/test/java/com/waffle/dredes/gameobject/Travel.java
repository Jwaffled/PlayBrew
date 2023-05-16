package com.waffle.dredes.gameobject;

import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Constants;
import com.waffle.core.RenderShape;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.awt.*;

/**
 * A class representing a search window within the MapScene
 */
public class Travel extends GameObject {
    private GeometryComponent circle;
    /**
     * The position data associated with this GameObject
     */
    public TransformComponent transformComponent;

    /**
     * Constructs a new "travel" object
     * @param pos where the travel object should be displayed
     */
    public Travel(Vec2f pos) {
        transformComponent = new TransformComponent(pos.x, pos.y);
        circle = new GeometryComponent();
        circle.shapes.add(new RenderShape(Constants.ShapeType.RECTANGLE, Constants.DrawMode.FILLED, new Color(200, 10, 10, 120), 75, 75, new Vec2f()));
    }

    public void start() {}

    public void update(float dt)
    {

    }
}
