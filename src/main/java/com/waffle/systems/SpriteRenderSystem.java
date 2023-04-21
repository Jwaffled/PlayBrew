package com.waffle.systems;

import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.core.Vec2f;
import com.waffle.ecs.ECSSystem;
import com.waffle.render.Camera;

import java.awt.*;
import java.util.Set;

public class SpriteRenderSystem extends ECSSystem {
    public void update(Graphics window, Camera camera, int sWidth, int sHeight) {
        for(Set<Integer> layer : entities) {
            for(int entity : layer) {
                TransformComponent comp = world.getComponent(entity, TransformComponent.class);
                SpriteRenderComponent sprites = world.getComponent(entity, SpriteRenderComponent.class);
                Vec2f drawPos;
                Vec2f scalar;
                for(SpriteRenderer s : sprites.sprites) {
                    if(camera != null) {
                        drawPos = comp.position
                                .add(s.getPosition())
                                .sub(camera.getPosition());

                        scalar = new Vec2f(sWidth, sHeight)
                                .div(new Vec2f(camera.getWidth(), camera.getHeight()))
                                .div(camera.getZoomScale());
                    } else {
                        drawPos = comp.position.add(s.getPosition());
                        scalar = new Vec2f(1, 1);
                    }

                    final int finalWidth = (int)(s.getWidth() * scalar.x);
                    final int finalHeight = (int)(s.getHeight() * scalar.y);
                    final int finalX = (int)(drawPos.x * scalar.x);
                    final int finalY = (int)(drawPos.y * scalar.y);

                    if(Utils.shouldRender(drawPos, finalWidth, finalHeight, camera)) {
                        window.drawImage(s.getSprite(), finalX, finalY, finalWidth, finalHeight, null);
                    }
                }
            }
        }


    }
}
