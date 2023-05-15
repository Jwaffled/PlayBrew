package com.waffle.components;

import com.waffle.core.CollisionEventListener;
import com.waffle.struct.Vec2f;
import com.waffle.ecs.IComponent;

public class ColliderComponent implements IComponent {
    public CollisionEventListener listener;
    public Vec2f size;
    public float mass;
    public float elasticity;
    public Vec2f position;
    public boolean isStatic;

    public ColliderComponent(Vec2f pos, Vec2f hb, CollisionEventListener cb) {
        listener = cb;
        size = hb;
        mass = 1;
        elasticity = 1;
        position = pos;
    }

    public String toString() {
        return String.format("Size: %s, Mass: %.2f, Elasticity %.2f, Position: %s", size, mass, elasticity, position);
    }
}
