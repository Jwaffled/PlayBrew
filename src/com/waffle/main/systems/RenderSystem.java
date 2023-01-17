package com.waffle.main.systems;

import com.waffle.main.components.TransformComponent;
import com.waffle.main.ecs.ECSSystem;

import java.awt.*;

public class RenderSystem extends ECSSystem {
    public void update(Graphics window) {

        for(int entity : entities) {
            TransformComponent comp = world.getComponent(entity, TransformComponent.class);
            window.fillRect((int)comp.x, (int)comp.y, 50, 50);
        }
    }
}
