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
        this.setMessage(msg);
        setPosition(new Vec2f(0, 0));
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        setColor(Color.black);
    }

    public FontRenderComponent(String msg, Font font, int fontSize, Color color) {
        this.setMessage(msg);
        this.setFont(font);
        this.setPosition(new Vec2f(0, 0));
        this.setColor(color);
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
