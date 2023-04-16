package com.waffle.systems;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Vec2f;
import com.waffle.ecs.ECSSystem;

import java.awt.*;
import java.util.Set;

public class FontRenderSystem extends ECSSystem {
    public void update(Graphics window) {
        for(Set<Integer> layer : entities.values()) {
            for(int entity : layer) {
                TransformComponent comp = world.getComponent(entity, TransformComponent.class);
                FontRenderComponent s = world.getComponent(entity, FontRenderComponent.class);
                Vec2f drawPos = comp.getPosition().add(s.getPosition());
                final int fontHeight = window.getFontMetrics().getHeight();
                int lineOffset = 0;
                window.setColor(s.getColor());
                window.setFont(s.getFont());
                for(String sLine : s.getMessage().split("\n")) {
                    window.drawString(sLine, (int)drawPos.x, (int)(drawPos.y + lineOffset + fontHeight / 2));
                    lineOffset += fontHeight;
                }
            }
        }


    }
}
