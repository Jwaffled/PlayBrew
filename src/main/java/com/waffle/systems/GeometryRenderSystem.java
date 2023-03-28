package com.waffle.systems;

import com.waffle.components.OutlineComponent;
import com.waffle.components.TransformComponent;
import com.waffle.ecs.ECSSystem;

import java.awt.*;

public class GeometryRenderSystem extends ECSSystem {
    public void update(Graphics window) {
        for(int entity : entities) {
            OutlineComponent c = world.getComponent(entity, OutlineComponent.class);
            TransformComponent t = world.getComponent(entity, TransformComponent.class);
            window.setColor(Color.black);
            window.drawRect((int)t.position.x, (int)t.position.y, c.width, c.height);
        }
    }
}
