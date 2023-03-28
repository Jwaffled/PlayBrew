package com.waffle;

import com.waffle.animation.LoopingAnimation;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;

import javax.imageio.ImageIO;
import java.net.URL;

public class Player extends GameObject {
    private SpriteRenderComponent sprite;
    private LoopingAnimation animation;
    private TransformComponent transform;

    public Player() {

    }

    @Override
    public void update(float dt) {
        sprite.sprite = animation.getFrame();
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
            sprite = new SpriteRenderComponent(new Vec2f(), ImageIO.read(file), 32, 32);
            animation = new LoopingAnimation("PlayerAnimation", 6);
        } catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        transform = new TransformComponent(new Vec2f(300, 300));

    }

    public void shoot() {
        for(int i = 0; i < 100; i++) {
            world.createGameObject(new Bullet((int)(Math.random() * 150 + 10), transform.position.x, transform.position.y));
        }
    }

    public void moveLeft() {
        transform.position.addX(-15);
    }

    public void moveRight() {
        transform.position.addX(15);
    }

    public void moveUp() {
        transform.position.addY(-15);
    }

    public void moveDown() {
        transform.position.addY(15);
    }
}
