package com.waffle.systems;

import com.waffle.components.KinematicComponent;
import com.waffle.components.TransformComponent;
import com.waffle.ecs.ECSSystem;

import java.util.Set;

public class PhysicsSystem extends ECSSystem {
    public void update(float dt) {
        for(Set<Integer> layer : entities.values()) {
            for(int entity : layer) {
                TransformComponent t = world.getComponent(entity, TransformComponent.class);
                KinematicComponent k = world.getComponent(entity, KinematicComponent.class);

                k.v = k.v.add(k.a.mul(dt));
                t.position = t.position.add(k.v.mul(dt));
            }
        }

    }
}
