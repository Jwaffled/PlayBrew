package com.waffle.main.systems;

import com.waffle.main.components.FontRenderComponent;
import com.waffle.main.components.TransformComponent;
import com.waffle.main.core.Vec2f;
import com.waffle.main.ecs.ECSSystem;

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
