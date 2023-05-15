package com.waffle.dredes.gameobject.player;

import com.waffle.components.ColliderComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Constants;
import com.waffle.core.LogLevel;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.dredes.gameobject.CollisionObject;
import com.waffle.dredes.gameobject.enemies.Enemy;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

public class Bullet extends GameObject {
    private TransformComponent transform;
    private SpriteRenderComponent sprite;
    private KinematicComponent kinematics;
    private ColliderComponent collider;
    public float damage;
    public Enemy spoil;

    public Bullet(String path, float rotation, Vec2f position, float speed, int w, int h) {
        try {
            float rads = (float)Math.toRadians(rotation);
            transform = new TransformComponent(position, rads);
            kinematics = new KinematicComponent(new Vec2f(speed * Utils.fcos(rads), -speed * Utils.fsin(rads)), new Vec2f());
            sprite = new SpriteRenderComponent();
            collider = new ColliderComponent(new Vec2f(), new Vec2f(w, h), e -> {
                if(e.getCollidedObject() instanceof CollisionObject) {
                    world.removeGameObject(this);
                }
            });
            sprite.sprites.add(new SpriteRenderer(new Vec2f(), Utils.loadImageFromPath(path), w, h));
        } catch(Exception e) {
            Constants.LOGGER.logException(e, LogLevel.SEVERE);
        }

    }

    /**
     * Called when the GameObject is added to the world
     */
    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {
        if(transform.position.x >= 5000 || transform.position.x <= -5000 || transform.position.y <= -5000 || transform.position.y >= 5000) {
            world.removeGameObject(this);
        }
        kinematics.force.y += 100;
    }
}
