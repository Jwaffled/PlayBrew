package com.waffle.dredes.gameobject;

import com.waffle.components.ColliderComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.dredes.scenes.GameplayScene;
import com.waffle.dredes.scenes.PhysicsScene;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;

public class CollisionObject extends GameObject {
    private TransformComponent transform;
    private SpriteRenderComponent spriteRenderComponent;
    private ColliderComponent collider;
    private KinematicComponent kinematics;
    public long totalCollisions = 0;

    @Override
    public void start() {
        transform = new TransformComponent(400, 300);
        collider = new ColliderComponent(
                new Vec2f(0, 0), new Vec2f(100, 100),
                e -> {
                    //System.out.println(e.getCollidedObject());
                    GameplayScene.INSTANCE.player.onGround = true;
                    totalCollisions++;
                });
        kinematics = new KinematicComponent(new Vec2f(), new Vec2f());
        spriteRenderComponent = new SpriteRenderComponent();
        spriteRenderComponent.sprites.add(new SpriteRenderer(new Vec2f(), new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB), 100, 100));
    }

    @Override
    public void update(float dt) {

    }
}
