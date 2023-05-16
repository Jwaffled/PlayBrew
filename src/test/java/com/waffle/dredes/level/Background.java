package com.waffle.dredes.level;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;

/**
 * Represents a static image background
 */
public class Background extends GameObject {
    private SpriteRenderComponent sr;
    /**
     * The positional data of the goal
     */
    public TransformComponent transform;
    /**
     * The image this background uses
     */
    public BufferedImage image;

    /**
     * Constructs a background object with the specified background image
     * @param image the background image
     */
    public Background(BufferedImage image) {
        this.image = image;
    }

    /**
     * Initializes this background object
     */
    public void start() {
        sr = new SpriteRenderComponent();
        sr.sprites.add(new SpriteRenderer(new Vec2f(), image, image.getWidth(), image.getHeight()));
        transform = new TransformComponent(new Vec2f(0,0));
    }

    /**
     * Unused
     * @param dt not used
     */
    @Override
    public void update(float dt) {}
}
