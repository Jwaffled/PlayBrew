package com.waffle.dredes.level;

import com.waffle.components.ColliderComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.CollisionEvent;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.dredes.gameobject.player.Bullet;
import com.waffle.dredes.gameobject.player.Player;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;

public class SourceEntity extends GameObject {
    public ColliderComponent hb;
    public TransformComponent transform;
    public KinematicComponent kinematics;
    public SpriteRenderComponent sr;
    public float health;

    public SourceEntity()
    {
        health = 3;
        kinematics = new KinematicComponent(new Vec2f(), new Vec2f());
        BufferedImage image = Utils.loadImageFromPath("DreDes/DreDes-Source.png");
        hb = new ColliderComponent(new Vec2f(), new Vec2f(128, 96),  e ->{
            if(e.getCollidedObject() instanceof Player || e.getCollidedObject() instanceof Bullet)
            {

                health--;
            }
        });
        sr = new SpriteRenderComponent();
        sr.sprites.add(new SpriteRenderer(new Vec2f(), image, 128, 96));
    }

    public void start()
    {
        transform = new TransformComponent(2368, 416 );
    }

    public void update(float dt){}
}