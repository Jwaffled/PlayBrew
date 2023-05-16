package com.waffle.struct;

public class Vec2f {
    public float x, y;

    public Vec2f() {
        this(0.0f, 0.0f);
    }

    public Vec2f(Vec2f other) {
        this(other.x, other.y);
    }

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Vec2f add(Vec2f other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vec2f sub(Vec2f other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vec2f mul(Vec2f other) {
        this.x *= other.x;
        this.y *= other.y;
        return this;
    }

    public Vec2f mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vec2f div(Vec2f other) {
        this.x /= other.x;
        this.y /= other.y;
        return this;
    }

    public Vec2f div(float scalar) {
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

    public Vec2f negate() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    public Vec2f normalize() {
        double magnitude = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
        this.x /= magnitude;
        this.y /= magnitude;
        return this;
    }

    public Vec2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    @Override
    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }
}
