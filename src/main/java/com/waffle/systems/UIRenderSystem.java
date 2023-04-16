package com.waffle.systems;

import com.waffle.components.TransformComponent;
import com.waffle.components.UITextureComponent;
import com.waffle.core.UITexture;
import com.waffle.core.Vec2f;
import com.waffle.ecs.ECSSystem;

import java.awt.*;
import java.util.Set;

public class UIRenderSystem extends ECSSystem {
    public void update(Graphics window) {
        for(Set<Integer> layer : entities.values()) {
            for(int entity : layer) {
                TransformComponent t = world.getComponent(entity, TransformComponent.class);
                UITextureComponent a = world.getComponent(entity, UITextureComponent.class);
                for(UITexture u : a.textures) {
                    Vec2f drawPos = t.position.add(u.position);
                    window.drawImage(u.sprite, (int)drawPos.x, (int)drawPos.y, u.width, u.height, null);
                }
            }
        }

    }
}
