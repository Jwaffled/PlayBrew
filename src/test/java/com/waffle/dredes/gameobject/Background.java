package com.waffle.dredes.gameobject;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.components.UITextureComponent;
import com.waffle.core.*;
import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;

public class Background extends GameObject {
    private TransformComponent transform;
    private UITextureComponent texture;
    private final String pathToImage;
    private final int width, height;

    /**
     * Constructs a new background
     * @param path the path to the image to be used for the background
     * @param w the width of the sprite
     * @param h the height of the sprite
     */
    public Background(String path, int w, int h) {
        pathToImage = path;
        width = w;
        height = h;
    }

    /**
     * Called when the GameObject is added to the world
     */
    @Override
    public void start() {
        try {
            BufferedImage b = Utils.loadImageFromPath(pathToImage);
            transform = new TransformComponent(0, 0);
            texture = new UITextureComponent();
            texture.textures.add(new UITexture(new Vec2f(), b, width, height));
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
