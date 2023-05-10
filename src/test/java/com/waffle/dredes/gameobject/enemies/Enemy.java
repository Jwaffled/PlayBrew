package com.waffle.dredes.gameobject.enemies;

import com.waffle.components.ColliderComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.dredes.level.SolidTile;
import com.waffle.dredes.level.WaterTile;
import com.waffle.dredes.gameobject.player.Bullet;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;

public abstract class Enemy extends GameObject {
    public ColliderComponent hb;
    public boolean gc;
    public float hp;
    public Vec2f moveSpeed;
    public BufferedImage sprite;
    public TransformComponent transform;
    public KinematicComponent kinematics;
    public float baseMass;
    public SpriteRenderComponent spriteRender;

    public Enemy(Vec2f hitBox, Vec2f offset, Vec2f moveSpeed, float health, float mass, BufferedImage sprite)
    {
        baseMass = mass;
        kinematics = new KinematicComponent(new Vec2f(), new Vec2f(), 9.8f, mass);
        hb = new ColliderComponent(offset, hitBox, e -> {
            if(e.getCollidedObject() instanceof SolidTile)
            {
                gc = true;
            }
            if(e.getCollidedObject() instanceof WaterTile)
            {
                kinematics.mass = baseMass * 2;
            }
            if(e.getCollidedObject() instanceof Bullet)
            {
                hp -= ((Bullet)e.getCollidedObject()).damage;
                ((Bullet)e.getCollidedObject()).spoil.applyEffect(this);
            }
        });
        this.moveSpeed = moveSpeed;
        hp = health;
        this.sprite = sprite;
    }

    public void start()
    {
        spriteRender = new SpriteRenderComponent();
        spriteRender.sprites.add(new SpriteRenderer(new Vec2f(), sprite, sprite.getWidth(), sprite.getHeight()));
        transform = new TransformComponent(new Vec2f());
    }
    public abstract void applyEffect(Enemy e);
}
