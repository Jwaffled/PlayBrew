package com.waffle.systems;

import com.waffle.components.ColliderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.BoundingBox;
import com.waffle.core.CollisionEvent;
import com.waffle.core.Constants;
import com.waffle.core.Vec2f;
import com.waffle.ecs.ECSSystem;

import java.util.Set;

public class CollisionSystem extends ECSSystem {
    public void update(float dt) {
        for(Set<Integer> layer : entities) {
            for(int entOne = 0; entOne < layer.size(); entOne++) {
                for(int entTwo = entOne + 1; entTwo < layer.size(); entTwo++) {
                    TransformComponent tOne = world.getComponent(entOne, TransformComponent.class);
                    TransformComponent tTwo = world.getComponent(entTwo, TransformComponent.class);
                    ColliderComponent cOne = world.getComponent(entOne, ColliderComponent.class);
                    ColliderComponent cTwo = world.getComponent(entTwo, ColliderComponent.class);
                    if(intersects(cOne.hitbox, cTwo.hitbox, tOne.position, tTwo.position)) {
                        cOne.listener.collideWith(new CollisionEvent());
                        cTwo.listener.collideWith(new CollisionEvent());
                    }
                }
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
