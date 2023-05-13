package com.waffle.dredes.level;

import com.waffle.components.ColliderComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.dredes.gameobject.player.Player;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;

public class Tile extends GameObject {
    public boolean touchingPlayer;
    public final int width, height;
    public boolean fluid;
    public boolean water;
    public ColliderComponent collider;
    public KinematicComponent k;
    public SpriteRenderComponent render;
    public TransformComponent transform;

    public Tile(BufferedImage sprite, int row ,int col, boolean fluid, boolean water) {
        transform = new TransformComponent(new Vec2f(col * 32, row * 32));
        width = sprite.getWidth();
        height = sprite.getHeight();
        render = new SpriteRenderComponent();
        k = new KinematicComponent(new Vec2f(), new Vec2f());
        render.sprites.add(new SpriteRenderer(new Vec2f(), sprite, sprite.getWidth(), sprite.getHeight()));
        collider = new ColliderComponent(new Vec2f(), new Vec2f(sprite.getWidth(), sprite.getHeight()), e -> {
            touchingPlayer = true;
        });
    }
    public Tile(BufferedImage sprite, int row ,int col, boolean fluid, boolean water, float frictionCoefficient, Vec2f velocityCoefficient) {
        transform = new TransformComponent(new Vec2f(col * 32, row * 32));
        render = new SpriteRenderComponent();
        width = sprite.getWidth();
        height = sprite.getHeight();
        k = new KinematicComponent(new Vec2f(), new Vec2f());
        render.sprites.add(new SpriteRenderer(new Vec2f(), sprite, sprite.getWidth(), sprite.getHeight()));
        collider = new ColliderComponent(new Vec2f(), new Vec2f(sprite.getWidth(), sprite.getHeight()), e -> {
            System.out.println("Colliding");
            if(e.getCollidedObject() instanceof Player) {
                touchingPlayer = true;
                ((Player)e.getCollidedObject()).frictionCoEff = frictionCoefficient;
                ((Player)e.getCollidedObject()).vCoEff = velocityCoefficient;
            }

        });
    }
    @Override
    public void start(){}

    @Override
    public void update(float dt) {

    }


}
