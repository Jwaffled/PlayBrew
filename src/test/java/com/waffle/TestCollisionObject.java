package com.waffle;

import com.waffle.components.ColliderComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.*;
import com.waffle.ecs.GameObject;

public class TestCollisionObject extends GameObject {
    private TransformComponent t;
    private SpriteRenderComponent sprite;
    private ColliderComponent c;
    private KinematicComponent k;

    public int counter = 0;

    @Override
    public void start() {
        k = new KinematicComponent(new Vec2f(), new Vec2f());
        c = new ColliderComponent(new Vec2f(), new Vec2f(50, 50), e -> counter++);
        sprite = new SpriteRenderComponent();
        sprite.sprites.add(new SpriteRenderer(new Vec2f(), Utils.loadImageFromPath("Grass.png"), 50, 50));
        t = new TransformComponent(200, 200);
    }

    @Override
    public void update(float dt) {

    }
}
