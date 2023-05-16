package com.waffle.dredes.level;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;

public class Background extends GameObject {
    public SpriteRenderComponent sr;
    public TransformComponent transform;
    public BufferedImage image;

    public Background(BufferedImage image)
    {
        this.image = image;
    }
    public void start()
    {
        sr = new SpriteRenderComponent();
        sr.sprites.add(new SpriteRenderer(new Vec2f(), image, image.getWidth(), image.getHeight()));
        transform = new TransformComponent(new Vec2f(0,0));
    }

    @Override
    public void update(float dt) {}
}
