package com.waffle;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;
import com.waffle.ecs.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;

public class Player extends GameObject {
    private SpriteRenderComponent sprite;
    private final TransformComponent transform;

    public Player() {
        try {
            URL file = getClass().getClassLoader().getResource("ship.jpg");
            sprite = new SpriteRenderComponent(new Vec2f(), ImageIO.read(file), 50, 50);
        } catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        transform = new TransformComponent(new Vec2f(300, 300));
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void start() {

    }

    public void shoot() {
        for(int i = 0; i < 100; i++) {
            world.createGameObject(new Bullet(10, transform.position.x, transform.position.y));
        }
    }

    public void moveLeft() {
        transform.position.addX(-15);
    }

    public void moveRight() {
        transform.position.addX(15);
    }
}
