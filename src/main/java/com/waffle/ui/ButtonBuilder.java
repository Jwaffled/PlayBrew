package com.waffle.ui;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Vec2f;

import static com.waffle.core.Constants.*;

import java.awt.*;
import java.util.ArrayList;

public class ButtonBuilder {
    private Color backgroundColor = Color.LIGHT_GRAY;
    private Color fontColor = Color.BLACK;
    private String buttonMessage = "Button Filler Message";
    private Vec2f messageOffset = new Vec2f();
    private DrawMode drawMode = DrawMode.FILLED_BORDER;
    private ShapeType shapeType = ShapeType.RECTANGLE;
    private int x = 0;
    private int y = 0;
    private int width = 100;
    private int height = 50;
    private ArrayList<ButtonEventListener> listeners = new ArrayList<>();

    public ButtonBuilder setBackgroundColor(Color c) {
        backgroundColor = c;
        return this;
    }

    public ButtonBuilder setFontColor(Color c) {
        fontColor = c;
        return this;
    }

    public ButtonBuilder setButtonMessage(String s) {
        buttonMessage = s;
        return this;
    }

    public ButtonBuilder setMessageOffset(Vec2f offset) {
        messageOffset = offset;
        return this;
    }

    public ButtonBuilder setDrawMode(DrawMode d) {
        drawMode = d;
        return this;
    }

    public ButtonBuilder setShapeType(ShapeType s) {
        shapeType = s;
        return this;
    }

    public ButtonBuilder setX(int newX) {
        x = newX;
        return this;
    }

    public ButtonBuilder setY(int newY) {
        y = newY;
        return this;
    }

    public ButtonBuilder setWidth(int w) {
        width = w;
        return this;
    }

    public ButtonBuilder setHeight(int h) {
        height = h;
        return this;
    }

    public ButtonBuilder addButtonListener(ButtonEventListener l) {
        listeners.add(l);
        return this;
    }

    public Button build() {
        Button b = new Button();
        b.listeners = listeners;
        b.geometryComponent = new GeometryComponent(shapeType, drawMode, backgroundColor, width, height);
        b.position = new TransformComponent(x, y);
        b.text = new FontRenderComponent(buttonMessage);
        b.text.position = messageOffset;
        b.text.color = fontColor;
        return b;
    }

}
