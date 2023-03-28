package com.waffle.components;

import com.waffle.ecs.IComponent;

public class OutlineComponent implements IComponent {
    public int width, height;

    public OutlineComponent(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
