package com.waffle.components;

import com.waffle.struct.Vec2f;
import com.waffle.ecs.IComponent;

public class KinematicComponent implements IComponent {
    public Vec2f a;
    public Vec2f v;
    public float gravity;
    public boolean applyGravity;

    public KinematicComponent(Vec2f velocity, Vec2f accel, float grav) {
        a = accel;
        v = velocity;
        applyGravity = true;
        gravity = grav;
    }

    public KinematicComponent(Vec2f velocity, Vec2f accel) {
        a = accel;
        v = velocity;
        applyGravity = false;
        gravity = 20;
    }

    public KinematicComponent(float grav) {
        a = new Vec2f();
        v = new Vec2f();
        gravity = grav;
        applyGravity = true;
    }

    public String toString() {
        return String.format("Acceleration: %s, Velocity: %s, Gravity: %f", a, v, gravity);
    }
}
