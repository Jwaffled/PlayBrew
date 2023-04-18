package com.waffle.tilemap;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;

import java.awt.image.BufferedImage;
import java.util.Map;

public class Tilemap extends GameObject {
    TransformComponent transform;
    SpriteRenderComponent sprites;
    public int x, y;
    public int width, height;
    public int tileWidth, tileHeight;
    public int[][] tiles;
    public Map<Integer, BufferedImage> numToTile;

    public Tilemap() {}

    public static TilemapBuilder newBuilder() {
        return new TilemapBuilder();
    }

    @Override
    public void start() {
        transform = new TransformComponent(x, y);
        sprites = new SpriteRenderComponent();
        for(int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[i].length; j++) {
                sprites.sprites.add(new SpriteRenderer(
                        new Vec2f(i * tileWidth, j * tileHeight),
                        numToTile.get(tiles[i][j]),
                        tileWidth, tileHeight
                ));
            }
        }
    }

    @Override
    public void update(float dt) {

    }

    public TransformComponent getTransform() {
        return transform;
    }

    public SpriteRenderComponent getSprites() {
        return sprites;
    }
}
