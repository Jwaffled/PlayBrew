package com.waffle.systems;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.*;
import com.waffle.struct.Rectangle;
import com.waffle.ecs.ECSSystem;
import com.waffle.render.Camera;
import com.waffle.struct.DynamicQuadTreeContainer;
import com.waffle.struct.Vec2f;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpriteRenderSystem extends ECSSystem {
    private ArrayList<DynamicQuadTreeContainer<Integer>> ents;
    private final Map<Integer, Integer> entityToIndexMap = new HashMap<>();
    public void update(Graphics window, Camera camera, int sWidth, int sHeight) {
        Vec2f viewPort = new Vec2f(camera.getSize()).mul(camera.getZoomScale());
        Rectangle searchRect = new Rectangle(camera.getPosition(), viewPort);
        for(DynamicQuadTreeContainer<Integer> layer : ents) {
            List<Integer> list = layer.search(searchRect);
            for(int entity : list) {
                TransformComponent comp = world.getComponent(entity, TransformComponent.class);
                SpriteRenderComponent sprites = world.getComponent(entity, SpriteRenderComponent.class);
                layer.remove(entityToIndexMap.get(entity));
                entityToIndexMap.put(entity, layer.insert(entity, new Rectangle(comp.position, new Vec2f(sprites.sprites.get(0).getWidth(), sprites.sprites.get(0).getHeight()))));
                Vec2f drawPos;
                Vec2f scalar;
                for(SpriteRenderer s : sprites.sprites) {
                    drawPos = new Vec2f(comp.position)
                            .add(s.getPosition())
                            .sub(camera.getPosition());

                    scalar = new Vec2f(sWidth, sHeight)
                            .div(camera.getSize())
                            .div(camera.getZoomScale());

                    final int finalWidth = (int)(s.getWidth() * scalar.x);
                    final int finalHeight = (int)(s.getHeight() * scalar.y);
                    final int finalX = (int)(drawPos.x * scalar.x);
                    final int finalY = (int)(drawPos.y * scalar.y);

                    AffineTransform tr = new AffineTransform();
                    tr.translate(finalX, finalY);
                    tr.rotate(-comp.rotation, 0, 0);
                    tr.scale(finalWidth / (float)s.getSprite().getWidth(), finalHeight / (float)s.getSprite().getHeight());

                    ((Graphics2D)window).drawImage(s.getSprite(), tr, null);
                    if(Constants.DEBUG_MODE) {
                        window.setColor(Color.red);
                        window.drawRect(finalX, finalY, finalWidth, finalHeight);
                    }
                }
            }
        }


    }

    @Override
    public void entityAdded(int layer, int entity) {
        if(!entityToIndexMap.containsKey(entity)) {
            TransformComponent t = world.getComponent(entity, TransformComponent.class);
            SpriteRenderComponent s = world.getComponent(entity, SpriteRenderComponent.class);
            int id = ents.get(layer).insert(entity, new Rectangle(t.position, new Vec2f(s.sprites.get(0).getWidth(), s.sprites.get(0).getHeight())));
            entityToIndexMap.put(entity, id);
        }
    }

    @Override
    public void entityRemoved(int layer, int entity) {
        if(entityToIndexMap.containsKey(entity)) {
            ents.get(layer).remove(entityToIndexMap.get(entity));
            entityToIndexMap.remove(entity);
        }
    }

    @Override
    public void layersCreated(int layerAmount) {
        //super.layersCreated(layerAmount);
        ents = new ArrayList<>(layerAmount);
        for(int i = 0; i < layerAmount; i++) {
            DynamicQuadTreeContainer<Integer> container = new DynamicQuadTreeContainer<>(world.getMaxEntities());
            container.resize(new Rectangle(new Vec2f(0, 0), new Vec2f(10000, 10000)));
            ents.add(container);
        }
    }
}
