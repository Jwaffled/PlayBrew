package com.waffle.systems;

import com.waffle.components.TransformComponent;
import com.waffle.components.UITextureComponent;
import com.waffle.core.UITexture;
import com.waffle.struct.Vec2f;
import com.waffle.ecs.ECSSystem;

import java.awt.*;
import java.util.Set;

public class UIRenderSystem extends ECSSystem {
    public void update(Graphics window) {
        for(Set<Integer> layer : entities) {
            for(int entity : layer) {
                TransformComponent t = world.getComponent(entity, TransformComponent.class);
                UITextureComponent a = world.getComponent(entity, UITextureComponent.class);
                for(UITexture u : a.textures) {
                    Vec2f drawPos = new Vec2f(t.position).add(u.getPosition());
                    window.drawImage(u.getSprite(), (int)drawPos.x, (int)drawPos.y, u.getWidth(), u.getHeight(), null);
                }
            }
        }

    }
}
