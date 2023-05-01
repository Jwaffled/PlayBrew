package com.waffle.systems;

import com.waffle.components.KinematicComponent;
import com.waffle.components.TransformComponent;
import com.waffle.struct.Vec2f;
import com.waffle.ecs.ECSSystem;

import java.util.Set;

public class PhysicsSystem extends ECSSystem {
    public void update(float dt) {
        for(Set<Integer> layer : entities) {
            for(int entity : layer) {
                TransformComponent t = world.getComponent(entity, TransformComponent.class);
                KinematicComponent k = world.getComponent(entity, KinematicComponent.class);
                if(k.applyGravity) {
                    k.v.y += k.gravity * dt;
                }
                k.v.add(new Vec2f(k.a).mul(dt));
                t.position.add(new Vec2f(k.v).mul(dt));
            }
        }

    }
}
