package com.waffle.dredes.gameobject.enemies;

import com.waffle.components.ColliderComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.dredes.gameobject.player.Bullet;
import com.waffle.dredes.level.Tile;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;

public class Enemy extends GameObject {

    public enum EnemyType
    {
        SCORPION
    }
    public EnemyType type;
    public boolean faceLeft;
    public ColliderComponent hb;
    public boolean gc;
    public float hp;
    public Vec2f moveSpeed;
    public BufferedImage sprite;
    public TransformComponent transform;
    public KinematicComponent kinematics;
    public float baseMass;
    public SpriteRenderComponent spriteRender;

    public Enemy(Vec2f hitBox, Vec2f offset, Vec2f moveSpeed, float health, float mass, BufferedImage sprite) {
        baseMass = mass;
        kinematics = new KinematicComponent(new Vec2f(), new Vec2f(), 9.8f, mass);
        hb = new ColliderComponent(offset, hitBox, e -> {
            if(e.getCollidedObject() instanceof Tile) {
                if(((Tile) e.getCollidedObject()).water) {
                    kinematics.mass = mass * 2;
                } else {
                    if(!((Tile) e.getCollidedObject()).fluid) {
                        gc = true;
                        kinematics.mass = mass;
                    }
                }
            }

            if(e.getCollidedObject() instanceof Bullet) {
                hp -= ((Bullet)e.getCollidedObject()).damage;
                ((Bullet)e.getCollidedObject()).spoil.applyEffect(this);
            }
        });
        this.moveSpeed = moveSpeed;
        hp = health;
        this.sprite = sprite;
    }

    public void start() {
        spriteRender = new SpriteRenderComponent();
        spriteRender.sprites.add(new SpriteRenderer(new Vec2f(), sprite, sprite.getWidth(), sprite.getHeight()));
        transform = new TransformComponent(new Vec2f());
    }
    public  void applyEffect(GameObject gameObject)
    {

    }

    @Override
    public void update(float dt) {

    }

    public void mutateToScorpion()
    {
        sprite = Utils.loadImageFromPath("DreDes/Scorpion.png");

    }
}
