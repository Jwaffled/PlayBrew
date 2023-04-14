package com.waffle.systems;

import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.ecs.ECSSystem;

import java.awt.*;

import static com.waffle.core.Constants.*;

public class GeometryRenderSystem extends ECSSystem {
    public void update(Graphics window) {
        for(int entity : entities) {
            GeometryComponent c = world.getComponent(entity, GeometryComponent.class);
            TransformComponent t = world.getComponent(entity, TransformComponent.class);
            window.setColor(c.color);
            switch (c.shape) {
                case RECTANGLE -> {
                    if (c.mode == DrawMode.FILLED) {
                        window.fillRect((int) t.position.x, (int) t.position.y, c.width, c.height);
                    } else if (c.mode == DrawMode.FILLED_BORDER) {
                        window.fillRect((int) t.position.x, (int) t.position.y, c.width, c.height);
                        window.setColor(Color.BLACK);
                        window.drawRect((int) t.position.x, (int) t.position.y, c.width, c.height);
                    } else {
                        window.drawRect((int) t.position.x, (int) t.position.y, c.width, c.height);
                    }
                }
                case ELLIPSE -> {
                    if (c.mode == DrawMode.FILLED) {
                        window.fillOval((int) t.position.x, (int) t.position.y, c.width, c.height);
                    } else if (c.mode == DrawMode.FILLED_BORDER) {
                        window.fillOval((int) t.position.x, (int) t.position.y, c.width, c.height);
                        window.setColor(Color.BLACK);
                        window.drawOval((int) t.position.x, (int) t.position.y, c.width, c.height);
                    } else {
                        window.drawOval((int) t.position.x, (int) t.position.y, c.width, c.height);
                    }
                }
            }
        }
    }
}
