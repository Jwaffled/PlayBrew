package com.waffle.systems;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.*;
import com.waffle.core.Rectangle;
import com.waffle.ecs.ECSSystem;
import com.waffle.render.Camera;
import com.waffle.struct.StaticQuadTree;
import com.waffle.struct.StaticQuadTreeContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class SpriteRenderSystem extends ECSSystem {
    private ArrayList<StaticQuadTreeContainer<Integer>> ents;
    public void update(Graphics window, Camera camera, int sWidth, int sHeight) {
        ents.clear();
        for(int i = 0; i < entities.size(); i++) {
            for(int ent : entities.get(i)) {
                TransformComponent t = world.getComponent(ent, TransformComponent.class);
                SpriteRenderComponent s = world.getComponent(ent, SpriteRenderComponent.class);
                ents.get(i).insert(ent, new Rectangle(t.position, new Vec2f(s.sprites.get(0).getWidth(), s.sprites.get(0).getHeight())));
            }
        }
        for(StaticQuadTreeContainer<Integer> layer : ents) {
            for(int entity : layer.search(new Rectangle(camera.getPosition(), new Vec2f(camera.getWidth(), camera.getHeight())))) {
                TransformComponent comp = world.getComponent(layer.get(entity), TransformComponent.class);
                SpriteRenderComponent sprites = world.getComponent(layer.get(entity), SpriteRenderComponent.class);
                Vec2f drawPos;
                Vec2f scalar;
                for(SpriteRenderer s : sprites.sprites) {
                    if(camera != null) {
                        drawPos = comp.position
                                .add(s.getPosition())
                                .sub(camera.getPosition());

                        scalar = new Vec2f(sWidth, sHeight)
                                .div(new Vec2f(camera.getWidth(), camera.getHeight()))
                                .div(camera.getZoomScale());
                    } else {
                        drawPos = comp.position.add(s.getPosition());
                        scalar = new Vec2f(1, 1);
                    }

                    final int finalWidth = (int)(s.getWidth() * scalar.x);
                    final int finalHeight = (int)(s.getHeight() * scalar.y);
                    final int finalX = (int)(drawPos.x * scalar.x);
                    final int finalY = (int)(drawPos.y * scalar.y);

                    window.drawImage(s.getSprite(), finalX, finalY, finalWidth, finalHeight, null);

//                    if(Utils.shouldRender(drawPos, finalWidth, finalHeight, camera)) {
//                        window.drawImage(s.getSprite(), finalX, finalY, finalWidth, finalHeight, null);
//                    }
                }
            }
        }


    }

    @Override
    public void entityAdded(int layer, int entity) {
        entities.get(layer).add(entity);
        TransformComponent t = world.getComponent(entity, TransformComponent.class);
        SpriteRenderComponent s = world.getComponent(entity, SpriteRenderComponent.class);
        ents.get(layer).insert(entity, new Rectangle(t.position, new Vec2f(s.sprites.get(0).getWidth(), s.sprites.get(0).getHeight())));
    }

    @Override
    public void entityRemoved(int layer, int entity) {
        entities.get(layer).remove(entity);
        StaticQuadTreeContainer<Integer> tree = ents.get(layer);
        tree.clear();
        for(int e : entities.get(layer)) {
            TransformComponent t = world.getComponent(entity, TransformComponent.class);
            SpriteRenderComponent s = world.getComponent(entity, SpriteRenderComponent.class);
            tree.insert(e, new Rectangle(t.position, new Vec2f(s.sprites.get(0).getWidth(), s.sprites.get(0).getHeight())));
        }
    }

    @Override
    public void layersCreated(int layerAmount) {
        super.layersCreated(layerAmount);
        ents = new ArrayList<>(layerAmount);
        for(int i = 0; i < layerAmount; i++) {
            StaticQuadTreeContainer<Integer> container = new StaticQuadTreeContainer<>();
            container.resize(new Rectangle(new Vec2f(0, 0), new Vec2f(100000, 100000)));
            ents.add(container);
        }
    }
}
