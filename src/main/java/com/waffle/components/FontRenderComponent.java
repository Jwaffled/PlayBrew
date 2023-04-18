package com.waffle.components;

import com.waffle.core.Vec2f;
import com.waffle.ecs.IComponent;

import java.awt.*;

public class FontRenderComponent implements IComponent {
    public String message;
    public Vec2f position;
    public Font font;
    public Color color;

    public FontRenderComponent(String msg) {
        message = msg;
        position = new Vec2f(0, 0);
        font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        color = Color.black;
    }

    public FontRenderComponent(String msg, Font f, int fontSize, Color c) {
        message = msg;
        font = f;
        position = new Vec2f(0, 0);
        color = c;
    }
}
