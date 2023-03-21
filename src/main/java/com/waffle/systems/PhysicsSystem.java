package com.waffle.systems;

import com.waffle.components.TransformComponent;
import com.waffle.ecs.ECSSystem;

public class PhysicsSystem extends ECSSystem {
    public void update(float dt) {
        for(int entity : entities) {
            TransformComponent component = world.getComponent(entity, TransformComponent.class);
            //component.position.addX(1);
        }

    }
}
