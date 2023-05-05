package com.waffle.components;

import com.waffle.struct.Vec2f;
import com.waffle.ecs.IComponent;

public class KinematicComponent implements IComponent {
    public Vec2f a;
    public Vec2f v;
    public Vec2f force;
    public float mass;
    public float gravity;
    public boolean applyGravity;

    public KinematicComponent(Vec2f velocity, Vec2f accel, float grav, float m) {
        a = accel;
        v = velocity;
        applyGravity = true;
        gravity = grav;
        mass = m;
        force = new Vec2f();
    }

    public KinematicComponent(Vec2f velocity, Vec2f accel) {
        a = accel;
        v = velocity;
        applyGravity = false;
        force = new Vec2f();
        mass = 1;
        gravity = 20;
    }

    public KinematicComponent(float grav) {
        a = new Vec2f();
        v = new Vec2f();
        force = new Vec2f();
        mass = 1;
        gravity = grav;
        applyGravity = true;
    }

    public String toString() {
        return String.format("Acceleration: %s, Velocity: %s, Gravity: %f", a, v, gravity);
    }
}
