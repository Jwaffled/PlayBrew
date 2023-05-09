package com.waffle.dredes.gameobject;

import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Constants;
import com.waffle.core.LogLevel;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

public class Bullet extends GameObject {
    private TransformComponent transform;
    private SpriteRenderComponent sprite;
    private KinematicComponent kinematics;

    public Bullet(String path, float rotation, Vec2f position, float speed, int w, int h) {
        try {
            float rads = (float)Math.toRadians(rotation);
            transform = new TransformComponent(position, rads);
            kinematics = new KinematicComponent(new Vec2f(speed * Utils.fcos(rads), -speed * Utils.fsin(rads)), new Vec2f());
            sprite = new SpriteRenderComponent();
            sprite.sprites.add(new SpriteRenderer(new Vec2f(), Utils.loadImageFromPath(path), w, h));
        } catch(Exception e) {
            Constants.LOGGER.logException(e, LogLevel.SEVERE);
        }

    }

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {
        if(transform.position.x >= 5000 || transform.position.x <= -5000 || transform.position.y <= -5000 || transform.position.y >= 5000) {
            world.removeGameObject(this);
        }
    }
}
