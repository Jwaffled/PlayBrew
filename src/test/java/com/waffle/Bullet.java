package com.waffle;

import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Bullet extends GameObject {
    private static BufferedImage image;
    static {
        try {
            URL file = Bullet.class.getClassLoader().getResource("projectile.png");
            image = ImageIO.read(file);
        } catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    private final TransformComponent transform;
    private final KinematicComponent kinematics;
    private final SpriteRenderComponent sprite;
    private final int speed;

    public Bullet(int speed, float x, float y) {
        this.speed = speed;
        sprite = new SpriteRenderComponent();
        kinematics = new KinematicComponent();
        kinematics.v = new Vec2f(0, -speed);
        kinematics.a = new Vec2f(15, 0);
        sprite.sprites.add(new SpriteRenderer(new Vec2f(), image, 50, 50));
        transform = new TransformComponent(new Vec2f(x, y));
    }

    public void update(float dt) {
    }

    @Override
    public void start() {

    }
}
