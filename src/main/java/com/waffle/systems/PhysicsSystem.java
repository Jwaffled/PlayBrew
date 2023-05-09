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
                integrate(t, k, dt);
//                k.a = k.force.div(k.mass);
//                k.force.x = 0;
//                k.force.y = 0;
//                if(k.applyGravity) {
//                    k.v.y += k.gravity * dt;
//                }
//                k.v.add(new Vec2f(k.a).mul(dt));
//                t.position.add(new Vec2f(k.v).mul(dt));
            }
        }

    }

    private void integrate(TransformComponent t, KinematicComponent k, float dt) {
        if(k.applyGravity) {
            k.force.y += k.gravity;
        }

        k.force.div(k.mass);

        k.a = new Vec2f(k.force);
        k.force = new Vec2f();

        k.v.add(new Vec2f(k.a).mul(dt));
        t.position.add(new Vec2f(k.v).mul(dt));
    }
}
