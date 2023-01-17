package com.waffle.main.systems;

import com.waffle.main.components.TransformComponent;
import com.waffle.main.ecs.ECSSystem;

public class PhysicsSystem extends ECSSystem {
    public void update(float dt) {
        for(int entity : entities) {
            TransformComponent component = world.getComponent(entity, TransformComponent.class);
            System.out.println("x: " + component.x + ", y: " + component.y);
            component.setX(component.x + 1);
        }
    }
}
