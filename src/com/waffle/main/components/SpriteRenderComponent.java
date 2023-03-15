package com.waffle.main.components;

import com.waffle.main.core.Vec2f;
import com.waffle.main.ecs.IComponent;

import java.awt.image.BufferedImage;

public class SpriteRenderComponent implements IComponent {
    public Vec2f position;
    public int width, height;
    public BufferedImage sprite;
    public SpriteRenderComponent(Vec2f pos, BufferedImage spr, int w, int h) {
        position = pos;
        sprite = spr;
        width = w;
        height = h;
    }
}
