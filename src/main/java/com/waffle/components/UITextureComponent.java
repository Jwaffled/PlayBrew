package com.waffle.components;

import com.waffle.core.Vec2f;
import com.waffle.ecs.IComponent;

import java.awt.image.BufferedImage;

public final class UITextureComponent implements IComponent {
    public Vec2f position;
    public BufferedImage sprite;
    public int width;
    public int height;

    public UITextureComponent(Vec2f position, BufferedImage sprite, int width, int height) {
        this.position = position;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
    }
}
