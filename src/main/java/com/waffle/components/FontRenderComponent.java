package com.waffle.components;

import com.waffle.core.Vec2f;
import com.waffle.ecs.IComponent;

public class FontRenderComponent implements IComponent {
    public String message;
    public Vec2f position;

    public FontRenderComponent(String msg) {
        this.message = msg;
        position = new Vec2f(0, 0);
    }
}
