package com.waffle.main.components;

import com.waffle.main.core.Vec2f;
import com.waffle.main.ecs.IComponent;

public class TransformComponent implements IComponent {
    public Vec2f position;

    public TransformComponent(float x, float y) {
        position = new Vec2f(x, y);
    }

    public TransformComponent(Vec2f pos) {
        position = new Vec2f(pos);
    }
}
