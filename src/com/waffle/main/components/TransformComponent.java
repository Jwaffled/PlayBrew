package com.waffle.main.components;

import com.waffle.main.ecs.IComponent;

public class TransformComponent implements IComponent {
    public float x, y;

    public TransformComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float newX) {
        x = newX;
    }

    public void setY(float newY) {
        y = newY;
    }
}
