package com.waffle.tilemap;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;

import java.awt.image.BufferedImage;
import java.util.Map;

public class Tilemap extends GameObject {
    private TransformComponent transform;
    private SpriteRenderComponent sprites;
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
        BufferedImage grass = Utils.loadImageFromPath("Grass.png");
        BufferedImage dirt = Utils.loadImageFromPath("Dirt.png");
        transform = new TransformComponent(0, 300);
        sprites = new SpriteRenderComponent();
        for(int i = 0; i < 30; i++) {
            sprites.sprites.add(new SpriteRenderer(new Vec2f(i * 32, 0), grass, 32, 32));
            for(int j = 1; j < 5; j++) {
                sprites.sprites.add(new SpriteRenderer(new Vec2f(i * 32, j * 32), dirt, 32, 32));
            }
        }
    }

    @Override
    public void update(float dt) {

    }




}
