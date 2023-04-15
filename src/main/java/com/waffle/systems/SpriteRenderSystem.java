package com.waffle.systems;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Utils;
import com.waffle.core.Vec2f;
import com.waffle.ecs.ECSSystem;
import com.waffle.render.Camera;

import java.awt.*;

public class SpriteRenderSystem extends ECSSystem {
    public void update(Graphics window, Camera camera, int sWidth, int sHeight) {
        for(int entity : entities) {
            TransformComponent comp = world.getComponent(entity, TransformComponent.class);
            SpriteRenderComponent s = world.getComponent(entity, SpriteRenderComponent.class);
            Vec2f drawPos;
            Vec2f scalar = new Vec2f(1, 1);
            if(camera != null) {
                drawPos = comp.position
                        .add(s.position)
                        .sub(camera.position);

                scalar = new Vec2f(sWidth, sHeight)
                        .div(new Vec2f(camera.getWidth(), camera.getHeight()))
                        .div(camera.zoomScale);
            } else {
                drawPos = comp.position.add(s.position);
            }

            final int finalWidth = (int)(s.width * scalar.x);
            final int finalHeight = (int)(s.height * scalar.y);
            final int finalX = (int)(drawPos.x * scalar.x);
            final int finalY = (int)(drawPos.y * scalar.y);

            if(Utils.shouldRender(drawPos, finalWidth, finalHeight, camera)) {
                window.drawImage(s.sprite, finalX, finalY, finalWidth, finalHeight, null);
            }
        }

    }
}
