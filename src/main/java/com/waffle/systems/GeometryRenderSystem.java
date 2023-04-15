package com.waffle.systems;

import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.RenderShape;
import com.waffle.core.Vec2f;
import com.waffle.ecs.ECSSystem;

import java.awt.*;

import static com.waffle.core.Constants.*;

public class GeometryRenderSystem extends ECSSystem {
    public void update(Graphics window) {
        for(int entity : entities) {
            GeometryComponent g = world.getComponent(entity, GeometryComponent.class);
            TransformComponent t = world.getComponent(entity, TransformComponent.class);
            for(RenderShape c : g.shapes) {
                Vec2f drawPos = t.position.add(c.pos);
                final int x = (int)drawPos.x;
                final int y = (int)drawPos.y;
                window.setColor(c.color);
                switch (c.shape) {
                    case RECTANGLE -> {
                        if (c.mode == DrawMode.FILLED) {
                            window.fillRect(x, y, c.width, c.height);
                        } else if (c.mode == DrawMode.FILLED_BORDER) {
                            window.fillRect(x, y, c.width, c.height);
                            window.setColor(Color.BLACK);
                            window.drawRect(x, y, c.width, c.height);
                        } else {
                            window.drawRect(x, y, c.width, c.height);
                        }
                    }
                    case ELLIPSE -> {
                        if (c.mode == DrawMode.FILLED) {
                            window.fillOval(x, y, c.width, c.height);
                        } else if (c.mode == DrawMode.FILLED_BORDER) {
                            window.fillOval(x, y, c.width, c.height);
                            window.setColor(Color.BLACK);
                            window.drawOval(x, y, c.width, c.height);
                        } else {
                            window.drawOval(x, y, c.width, c.height);
                        }
                    }
                }
            }
        }
    }
}
