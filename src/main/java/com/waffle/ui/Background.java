package com.waffle.ui;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.struct.Vec2f;
import com.waffle.ecs.GameObject;

import java.awt.image.BufferedImage;

public class Background extends GameObject {
    private TransformComponent pos;
    private SpriteRenderComponent bg;
    private final String imagePath;

    public Background(String path) {
        imagePath = path;
    }


    @Override
    public void start() {
        pos = new TransformComponent(0, 0);
        bg = new SpriteRenderComponent();
        BufferedImage b = Utils.loadImageFromPath(imagePath);
        bg.sprites.add(new SpriteRenderer(new Vec2f(), b, 960, 540));
    }

    @Override
    public void update(float dt) {

    }
}
