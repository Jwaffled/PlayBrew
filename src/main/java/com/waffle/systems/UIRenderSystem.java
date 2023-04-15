package com.waffle.systems;

import com.waffle.components.TransformComponent;
import com.waffle.components.UITextureComponent;
import com.waffle.core.Vec2f;
import com.waffle.ecs.ECSSystem;

import java.awt.*;

public class UIRenderSystem extends ECSSystem {
    public void update(Graphics window) {
        for(int entity : entities) {
            TransformComponent t = world.getComponent(entity, TransformComponent.class);
            UITextureComponent u = world.getComponent(entity, UITextureComponent.class);
            Vec2f drawPos = t.position.add(u.position());
            window.drawImage(u.sprite(), (int)drawPos.x, (int)drawPos.y, u.width(), u.height(), null);
        }
    }
}
