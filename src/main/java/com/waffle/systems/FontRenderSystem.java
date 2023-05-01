package com.waffle.systems;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.struct.Vec2f;
import com.waffle.ecs.ECSSystem;

import java.awt.*;
import java.util.Set;

public class FontRenderSystem extends ECSSystem {
    public void update(Graphics window) {
        for(Set<Integer> layer : entities) {
            for(int entity : layer) {
                TransformComponent comp = world.getComponent(entity, TransformComponent.class);
                FontRenderComponent s = world.getComponent(entity, FontRenderComponent.class);
                Vec2f drawPos = new Vec2f(comp.position).add(s.position);
                final int fontHeight = window.getFontMetrics().getHeight();
                int lineOffset = 0;
                window.setColor(s.color);
                window.setFont(s.font);
                for(String sLine : s.message.split("\n")) {
                    window.drawString(sLine, (int)drawPos.x, (int)(drawPos.y + lineOffset + fontHeight / 2));
                    lineOffset += fontHeight;
                }
            }
        }


    }
}
