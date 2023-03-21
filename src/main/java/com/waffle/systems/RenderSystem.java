package com.waffle.systems;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Vec2f;
import com.waffle.ecs.ECSSystem;

import java.awt.*;

public class RenderSystem extends ECSSystem {
    public void update(Graphics window) {
        for(int entity : entities) {
            TransformComponent comp = world.getComponent(entity, TransformComponent.class);
            SpriteRenderComponent s = world.getComponent(entity, SpriteRenderComponent.class);
            Vec2f drawPos = comp.position.add(s.position);
            window.drawImage(s.sprite, (int)drawPos.x, (int)drawPos.y, s.width, s.height, null);
        }

    }
}
