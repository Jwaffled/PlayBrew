package com.waffle.dredes.gameobject;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.*;
import com.waffle.ecs.GameObject;
import com.waffle.render.Camera;
import com.waffle.struct.Vec2f;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a static image that follows a camera position
 */
public class Background extends GameObject {
    private TransformComponent transform;
    private SpriteRenderComponent texture;
    private final String pathToImage;
    private final int width, height;
    private final Camera cameraReference;

    /**
     * Constructs a new background
     * @param path the path to the image to be used for the background
     * @param w the width of the sprite
     * @param h the height of the sprite
     * @param camRef the camera object to position on
     */
    public Background(String path, int w, int h, Camera camRef) {
        pathToImage = path;
        width = w;
        height = h;
        cameraReference = camRef;
    }

    /**
     * Called when the GameObject is added to the world
     */
    @Override
    public void start() {
        try {
            BufferedImage b = Utils.loadImageFromPath(pathToImage);
            BufferedImage copy = Utils.applyTint(b, new Color(50, 50, 50, 55));
            transform = new TransformComponent(0, 0);
            transform.position = cameraReference.getPosition();
            texture = new SpriteRenderComponent();
            texture.sprites.add(new SpriteRenderer(new Vec2f(), copy, width, height));
        } catch(Exception e) {
            Constants.LOGGER.logException(e, LogLevel.SEVERE);
        }

    }

    /**
     * Called every frame; Unused
     * @param dt not used
     */
    @Override
    public void update(float dt) {
    }
}
