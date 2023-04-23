package com.waffle;

import com.waffle.components.ColliderComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.BoundingBox;
import com.waffle.core.Constants;
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
    private final ColliderComponent collider;
    private final float speed;

    public Bullet(float speed, float x, float y) {
        this.speed = speed;
        sprite = new SpriteRenderComponent();
        kinematics = new KinematicComponent(new Vec2f(speed, -speed), new Vec2f(0, 0), 100);
        kinematics.applyGravity = true;
        sprite.sprites.add(new SpriteRenderer(new Vec2f(), image, 50, 50));
        transform = new TransformComponent(new Vec2f(x, y));
        collider = new ColliderComponent(
                new Vec2f(),
                new BoundingBox(Constants.ShapeType.RECTANGLE, 50, 50),
                e -> { world.removeGameObject(this); }
        );
    }

    public void update(float dt) {
    }

    @Override
    public void start() {

    }
}
