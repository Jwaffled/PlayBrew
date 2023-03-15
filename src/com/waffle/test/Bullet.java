package com.waffle.test;

import com.waffle.main.components.SpriteRenderComponent;
import com.waffle.main.components.TransformComponent;
import com.waffle.main.core.Vec2f;
import com.waffle.main.ecs.GameObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bullet extends GameObject {
    private static BufferedImage image;
    static {
        try {
            File file = new File("src/com/waffle/test/images/projectile.png");
            image = ImageIO.read(file);
        } catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    private TransformComponent transform;
    private SpriteRenderComponent sprite;
    private int speed;

    public Bullet(int speed, float x, float y) {
        this.speed = speed;
        sprite = new SpriteRenderComponent(new Vec2f(), image, 50, 50);
        transform = new TransformComponent(new Vec2f(x, y));
    }

    public void update(float dt) {
        transform.position.addY(speed * dt);
    }

    @Override
    public void start() {

    }
}
