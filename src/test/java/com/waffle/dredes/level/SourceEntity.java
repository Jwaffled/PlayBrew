package com.waffle.dredes.level;

import com.waffle.components.ColliderComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.dredes.gameobject.player.Player;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;

public class SourceEntity extends GameObject {
    private ColliderComponent hb;
    private TransformComponent transform;
    private KinematicComponent kinematics;
    private SpriteRenderComponent sr;
    /**
     * Represents the health of this entity
     */
    public float health;

    /**
     * Constructs a new SourceEntity object
     */
    public SourceEntity() {
        health = 3;
        kinematics = new KinematicComponent(new Vec2f(), new Vec2f());
        BufferedImage image = Utils.loadImageFromPath("DreDes/DreDes-Source.png");
        hb = new ColliderComponent(new Vec2f(), new Vec2f(128, 96),  e ->{
            if(e.getCollidedObject() instanceof Player) {
                health--;
            }
        });
        sr = new SpriteRenderComponent();
        sr.sprites.add(new SpriteRenderer(new Vec2f(), image, 128, 96));
    }

    /**
     * Initializes this SourceEntity's state
     */
    public void start() {
        transform = new TransformComponent(2368, 416 );
    }

    /**
     * Unused
     * @param dt not used
     */
    public void update(float dt){}
}