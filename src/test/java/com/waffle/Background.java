package com.waffle;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;

import java.awt.image.BufferedImage;

public class Background extends GameObject {
    private TransformComponent pos;
    private SpriteRenderComponent bg;

    public Background() {}


    @Override
    public void start() {
        pos = new TransformComponent(0, 0);
        bg = new SpriteRenderComponent();
        BufferedImage b = Utils.loadImageFromPath("DreDes-BG-Nighttime.png");
        bg.sprites.add(new SpriteRenderer(new Vec2f(), b, 960, 540));
    }

    @Override
    public void update(float dt) {

    }
}
