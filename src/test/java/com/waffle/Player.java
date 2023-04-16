package com.waffle;

import com.waffle.animation.LoopingAnimation;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;

import javax.imageio.ImageIO;
import java.net.URL;

public class Player extends GameObject {
    private SpriteRenderComponent sprite;
    private LoopingAnimation animation;
    private TransformComponent transform;
    private boolean canShoot = true;

    public Player() {

    }

    @Override
    public void update(float dt) {
        sprite.sprites.get(0).sprite = animation.getFrame();
        if(GameTest.INSTANCE.keybinds.triggered("MoveLeft")) {
            transform.position.addX(-450 * dt);
        }

        if(GameTest.INSTANCE.keybinds.triggered("MoveRight")) {
            transform.position.addX(450 * dt);
        }

        if(GameTest.INSTANCE.keybinds.triggered("MoveUp")) {
            transform.position.addY(-450 * dt);
        }

        if(GameTest.INSTANCE.keybinds.triggered("MoveDown")) {
            transform.position.addY(450 * dt);
        }
    }

    @Override
    public void start() {
        try {
            URL file = getClass().getClassLoader().getResource("ship.png");
            sprite = new SpriteRenderComponent();
            sprite.sprites.add(new SpriteRenderer(new Vec2f(), ImageIO.read(file), 32, 32));
            animation = new LoopingAnimation("PlayerAnimation", 6);
        } catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        transform = new TransformComponent(new Vec2f(300, 300));

    }

    public void shoot() {
        if(canShoot) {
            world.createGameObject(new Bullet((int)(Math.random() * 150 + 10), transform.position.x, transform.position.y), 0);
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
