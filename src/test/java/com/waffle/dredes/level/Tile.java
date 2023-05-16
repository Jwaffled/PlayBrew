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

/**
 * Represents a single tile
 */
public class Tile extends GameObject {
    private boolean touchingPlayer;
    /**
     * The width of this tile (in px)
     */
    public final int width;
    /**
     * The height of this tile (in px)
     */
    public final int height;
    /**
     * Whether this tile is a fluid tile
     */
    public boolean fluid;
    private boolean water;
    private float friction;
    private Vec2f velocity;
    private ColliderComponent collider;
    private KinematicComponent k;
    private SpriteRenderComponent render;
    /**
     * The positional data of this Tile
     */
    public TransformComponent transform;

    /**
     * Constructs a new tile object
     * @param sprite the sprite of this tile
     * @param row the row to place this tile in
     * @param col the column to place this tile in
     * @param fluid whether the tile represents a fluid (no collisions)
     * @param water whether the tile represents water
     */
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
        friction = 1;
        this.fluid = fluid;
        this.water = water;
        velocity = new Vec2f(1,1);
    }

    /**
     * Constructs a new tile object
     * @param sprite the sprite of this tile
     * @param row the row to place this tile in
     * @param col the column to place this tile in
     * @param fluid whether the tile represents a fluid (no collisions)
     * @param water whether the tile represents water
     * @param frictionCoefficient the amount of friction to apply on this tile
     * @param velocityCoefficient the amount of drag to apply on this tile
     */
    public Tile(BufferedImage sprite, int row ,int col, boolean fluid, boolean water, float frictionCoefficient, Vec2f velocityCoefficient) {
        transform = new TransformComponent(new Vec2f(col * 32, row * 32));
        render = new SpriteRenderComponent();
        width = sprite.getWidth();
        height = sprite.getHeight();
        k = new KinematicComponent(new Vec2f(), new Vec2f());
        render.sprites.add(new SpriteRenderer(new Vec2f(), sprite, sprite.getWidth(), sprite.getHeight()));
        friction = frictionCoefficient;
        velocity = velocityCoefficient;
        collider = new ColliderComponent(new Vec2f(), new Vec2f(sprite.getWidth(), sprite.getHeight()), e -> {
            //System.out.println("Colliding");
            if(e.getCollidedObject() instanceof Player) {
                touchingPlayer = true;
                ((Player)e.getCollidedObject()).frictionCoEff = frictionCoefficient;
                ((Player)e.getCollidedObject()).vCoEff = velocityCoefficient;
            }
        });
        this.fluid = fluid;
        this.water = water;
        collider.isStatic = true;
    }

    /**
     * Creates a deep clone of this tile object
     * @param newRow the new row to place this tile in
     * @param newCol the new column to place this tile in
     * @return a deep clone of this tile object
     */
    public Tile copy(int newRow, int newCol) {
        return new Tile(render.sprites.get(0).getSprite(), newRow, newCol, fluid, water, friction, velocity);
    }

    /**
     * Called when the GameObject is added to the world
     */
    @Override
    public void start(){}

    /**
     * Unused
     * @param dt not used
     */
    @Override
    public void update(float dt) {

    }



}
