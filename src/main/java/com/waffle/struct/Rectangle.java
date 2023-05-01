package com.waffle.struct;

public class Rectangle {
    public Vec2f pos;
    public Vec2f size;

    public Rectangle() {
        pos = new Vec2f(0, 0);
        size = new Vec2f(1, 1);
    }

    public Rectangle(Vec2f pos, Vec2f size) {
        this.pos = pos;
        this.size = size;
    }

    public final boolean contains(Vec2f p) {
        return !(p.x < pos.x || p.y < pos.y || p.x >= (pos.x + size.x) || p.y >= (pos.y + size.y));
    }

    public final boolean contains(Rectangle r) {
        return r.pos.x >= pos.x
                && r.pos.x + r.size.x < pos.x + size.x
                && r.pos.y >= pos.y
                && r.pos.y + r.size.y < pos.y + size.y;
    }

    public final boolean overlaps(Rectangle r) {
        return pos.x < r.pos.x + r.size.x
                && pos.x + size.x > r.pos.x
                && pos.y < r.pos.y + r.size.y
                && pos.y + size.y > r.pos.y;
    }
}
