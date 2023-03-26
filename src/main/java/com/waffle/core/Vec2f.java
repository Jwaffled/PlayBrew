package com.waffle.core;

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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vec2f add(Vec2f other) {
        return new Vec2f(this.x + other.x, this.y + other.y);
    }

    public Vec2f sub(Vec2f other) {
        return new Vec2f(this.x - other.x, this.y - other.y);
    }

    public Vec2f mul(Vec2f other) {
        return new Vec2f(this.x * other.x, this.y * other.y);
    }

    public Vec2f div(Vec2f other) {
        return new Vec2f(this.x / other.x, this.y / other.y);
    }

    public void setVec(Vec2f other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vec2f negate() {
        return new Vec2f(-this.x, -this.y);
    }

    public void addX(float x) {
        this.x += x;
    }

    public void addY(float y) {
        this.y += y;
    }

    @Override
    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }
}
