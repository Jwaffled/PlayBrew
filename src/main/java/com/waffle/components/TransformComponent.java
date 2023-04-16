package com.waffle.components;

import com.waffle.core.Vec2f;
import com.waffle.ecs.IComponent;

public class TransformComponent implements IComponent {
    private Vec2f position;

    public TransformComponent(float x, float y) {
        position = new Vec2f(x, y);
    }

    public TransformComponent(Vec2f pos) {
        position = new Vec2f(pos);
    }

    public Vec2f getPosition() {
        return position;
    }

    public void setPosition(Vec2f position) {
        this.position = position;
    }
}
