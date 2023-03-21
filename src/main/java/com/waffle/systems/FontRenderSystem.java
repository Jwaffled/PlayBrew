package com.waffle.systems;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Vec2f;
import com.waffle.ecs.ECSSystem;

import java.awt.*;

public class FontRenderSystem extends ECSSystem {
    public void update(Graphics window) {
        for(int entity : entities) {
            TransformComponent comp = world.getComponent(entity, TransformComponent.class);
            FontRenderComponent s = world.getComponent(entity, FontRenderComponent.class);
            Vec2f drawPos = comp.position.add(s.position);
            window.setColor(Color.black);
            window.drawString(s.message, (int)drawPos.x, (int)drawPos.y);
        }

    }
}
