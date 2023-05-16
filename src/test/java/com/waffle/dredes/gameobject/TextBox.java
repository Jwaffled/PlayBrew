package com.waffle.dredes.gameobject;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Constants;
import com.waffle.core.RenderShape;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.awt.*;

public class TextBox extends GameObject {
    private String message;
    private TransformComponent transform;
    private FontRenderComponent fontRender;
    private GeometryComponent geoRender;
    private int scroll;
    public int scrollSpeed;
    private int counter;

    public TextBox(String message)
    {
        this.message = message;
        transform = new TransformComponent(new Vec2f());
        fontRender = new FontRenderComponent(message, new Font("MONOSPACED", 1, 20), 20, new Color(200,200,200,200));
        fontRender.position = new Vec2f(20, 30);
        geoRender = new GeometryComponent();
        geoRender.shapes.add(new RenderShape(Constants.ShapeType.RECTANGLE, Constants.DrawMode.FILLED, new Color(55,55,55,200),940, 80, new Vec2f(10, 10) ));
        scroll = 0;
        scrollSpeed = 6;
        counter = 0;
    }

    public void setNewMessage(String message) {
        if(!this.message.equals(message)) {
            this.message = message;
            scroll = 0;
        }
    }

    public boolean isFinished() {
        return scroll == message.length();
    }

    public void start() {}

    public void update(float dt) {
        if(scroll + 1 <= message.length()) {
            fontRender.message = message.substring(0,scroll + 1);
            counter++;
            if(counter == scrollSpeed) {
                scroll++;
                counter = 0;
            }
        }
    }
}