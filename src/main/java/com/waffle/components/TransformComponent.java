package com.waffle.components;

import com.waffle.struct.Vec2f;
import com.waffle.ecs.IComponent;

public class TransformComponent implements IComponent {
    public Vec2f position;

    public TransformComponent(float x, float y) {
        position = new Vec2f(x, y);
    }

    public TransformComponent(Vec2f pos) {
        position = new Vec2f(pos);
    }
}
