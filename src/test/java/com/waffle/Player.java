package com.waffle;

import com.waffle.animation.LoopingAnimation;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;

import java.awt.image.BufferedImage;

public class Player extends GameObject {
    private SpriteRenderComponent sprite;
    private LoopingAnimation animation;
    private TransformComponent transform;
    private boolean canShoot = true;

    public Player() {

    }

    @Override
    public void update(float dt) {
        sprite.sprites.get(0).setSprite(animation.getFrame());
        if(GameTest.INSTANCE.keybinds.triggered("MoveLeft")) {
            transform.position.x += -450 * dt;
        }

        if(GameTest.INSTANCE.keybinds.triggered("MoveRight")) {
            transform.position.x += 450 * dt;
        }

        if(GameTest.INSTANCE.keybinds.triggered("MoveUp")) {
            transform.position.y += -450 * dt;
        }

        if(GameTest.INSTANCE.keybinds.triggered("MoveDown")) {
            transform.position.y += 450 * dt;
        }
    }

    @Override
    public void start() {
        try {
            BufferedImage img = Utils.loadImageFromPath("ship.png");
            sprite = new SpriteRenderComponent();
            sprite.sprites.add(new SpriteRenderer(new Vec2f(), img, 32, 32));
            animation = new LoopingAnimation("PlayerAnimation", 6);
        } catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        transform = new TransformComponent(new Vec2f(300, 300));

    }

    public void shoot() {
        if(canShoot) {
            world.createGameObject(new Bullet(50, transform.position.x, transform.position.y), 1);
        }
    }

    public void setCanShoot(boolean b) {
        canShoot = b;
    }

    public boolean canShoot() {
        return canShoot;
    }

    public Vec2f getPosition() {
        return transform.position;
    }
}
