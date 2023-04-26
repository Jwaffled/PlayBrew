package com.waffle.systems;

import com.waffle.components.ColliderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.*;
import com.waffle.ecs.ECSSystem;
import com.waffle.struct.DynamicQuadTreeContainer;

import java.util.*;

public class CollisionSystem extends ECSSystem {
    private DynamicQuadTreeContainer<Integer> quadTree = new DynamicQuadTreeContainer<>();
    public void update(float dt) {
        for(Set<Integer> layer : entities) {
            Iterator<Integer> it = layer.iterator();
            Set<ColliderComponent> toCall = new HashSet<>();
            while(it.hasNext()) {
                int entOne = it.next();
                Iterator<Integer> itTwo = layer.iterator();
                while(itTwo.hasNext()) {
                    int entTwo = itTwo.next();
                    if(entTwo == entOne) continue;
                    TransformComponent tOne = world.getComponent(entOne, TransformComponent.class);
                    TransformComponent tTwo = world.getComponent(entTwo, TransformComponent.class);
                    ColliderComponent cOne = world.getComponent(entOne, ColliderComponent.class);
                    ColliderComponent cTwo = world.getComponent(entTwo, ColliderComponent.class);
                    if(toCall.contains(cOne) && toCall.contains(cTwo)) continue;
                    if(intersects(cOne.hitbox, cTwo.hitbox, tOne.position, tTwo.position)) {
                        toCall.add(cOne);
                        toCall.add(cTwo);
                    }
                }
            }
            for(ColliderComponent c : toCall) {
                c.listener.collideWith(new CollisionEvent());
            }
        }
    }

    @Override
    public void entityAdded(int layer, int entity) {
        entities.get(layer).add(entity);
        // Add to quad
    }

    @Override
    public void entityRemoved(int layer, int entity) {
        entities.get(layer).remove(entity);
        // Remove from quad
    }

    private boolean intersects(BoundingBox boxOne, BoundingBox boxTwo, Vec2f posOne, Vec2f posTwo) {
        if(boxOne.shapeType() == Constants.ShapeType.RECTANGLE && boxTwo.shapeType() == Constants.ShapeType.RECTANGLE) {
            return posOne.x < posTwo.x + boxTwo.width()
                    && posOne.x + boxOne.width() > posTwo.x
                    && posOne.y < posTwo.y + boxTwo.height()
                    && posOne.y + boxOne.height() > posTwo.y;
        }

        return false;
    }
}
