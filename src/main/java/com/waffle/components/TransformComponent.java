package com.waffle.components;

import com.waffle.struct.Vec2f;
import com.waffle.ecs.IComponent;

public class TransformComponent implements IComponent {
    public Vec2f position;
    public float rotation;

    public TransformComponent(float x, float y) {
        position = new Vec2f(x, y);
        rotation = 0;
    }

    public TransformComponent(Vec2f pos) {
        position = new Vec2f(pos);
        rotation = 0;
    }

    public TransformComponent(float x, float y, float rotate) {
        position = new Vec2f(x, y);
        rotation = rotate;
    }

    public TransformComponent(Vec2f pos, float rotate) {
        position = pos;
        rotation = rotate;
    }
}
