package com.waffle.components;

import com.waffle.core.Vec2f;
import com.waffle.ecs.IComponent;

import java.awt.*;

public class FontRenderComponent implements IComponent {
    private String message;
    private Vec2f position;
    private Font font;
    private Color color;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Vec2f getPosition() {
        return position;
    }

    public void setPosition(Vec2f position) {
        this.position = position;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
