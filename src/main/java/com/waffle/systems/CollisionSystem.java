package com.waffle.systems;

import com.waffle.components.ColliderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.BoundingBox;
import com.waffle.core.CollisionEvent;
import com.waffle.core.Constants;
import com.waffle.core.Vec2f;
import com.waffle.ecs.ECSSystem;

import java.util.*;

public class CollisionSystem extends ECSSystem {
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

    private boolean intersects(BoundingBox boxOne, BoundingBox boxTwo, Vec2f posOne, Vec2f posTwo) {
        if(boxOne.getShapeType() == Constants.ShapeType.RECTANGLE && boxTwo.getShapeType() == Constants.ShapeType.RECTANGLE) {
            return posOne.x < posTwo.x + boxTwo.getWidth()
                    && posOne.x + boxOne.getWidth() > posTwo.x
                    && posOne.y < posTwo.y + boxTwo.getHeight()
                    && posOne.y + boxOne.getHeight() > posTwo.y;
        }

        return false;
    }
}
