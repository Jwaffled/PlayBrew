package com.waffle.components;

import com.waffle.core.BoundingBox;
import com.waffle.core.CollisionEventListener;
import com.waffle.core.Vec2f;
import com.waffle.ecs.IComponent;

public class ColliderComponent implements IComponent {
    public CollisionEventListener listener;
    public BoundingBox hitbox;
    public int mass;
    public float elasticity;
    public Vec2f position;

    public ColliderComponent(Vec2f pos, BoundingBox hb, CollisionEventListener cb) {
        listener = cb;
        hitbox = hb;
        mass = 1;
        elasticity = 1;
        position = pos;
    }
}
