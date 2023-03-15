package com.waffle.main.components;

import com.waffle.main.core.Vec2f;
import com.waffle.main.ecs.IComponent;

public class FontRenderComponent implements IComponent {
    public String message;
    public Vec2f position;

    public FontRenderComponent(String msg) {
        this.message = msg;
        position = new Vec2f(0, 0);
    }
}
