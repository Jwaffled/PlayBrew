package com.waffle.systems;

import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.RenderShape;
import com.waffle.core.Vec2f;
import com.waffle.ecs.ECSSystem;

import java.awt.*;
import java.util.Set;

import static com.waffle.core.Constants.*;

public class GeometryRenderSystem extends ECSSystem {
    public void update(Graphics window) {
        for(Set<Integer> layer : entities.values()) {
            for(int entity : layer) {
                GeometryComponent g = world.getComponent(entity, GeometryComponent.class);
                TransformComponent t = world.getComponent(entity, TransformComponent.class);
                for(RenderShape c : g.shapes) {
                    Vec2f drawPos = t.position.add(c.getPosition());
                    final int x = (int)drawPos.x;
                    final int y = (int)drawPos.y;
                    window.setColor(c.getColor());
                    switch (c.getShape()) {
                        case RECTANGLE -> {
                            if (c.getMode() == DrawMode.FILLED) {
                                window.fillRect(x, y, c.getWidth(), c.getHeight());
                            } else if (c.getMode() == DrawMode.FILLED_BORDER) {
                                window.fillRect(x, y, c.getWidth(), c.getHeight());
                                window.setColor(Color.BLACK);
                                window.drawRect(x, y, c.getWidth(), c.getHeight());
                            } else {
                                window.drawRect(x, y, c.getWidth(), c.getHeight());
                            }
                        }
                        case ELLIPSE -> {
                            if (c.getMode() == DrawMode.FILLED) {
                                window.fillOval(x, y, c.getWidth(), c.getHeight());
                            } else if (c.getMode() == DrawMode.FILLED_BORDER) {
                                window.fillOval(x, y, c.getWidth(), c.getHeight());
                                window.setColor(Color.BLACK);
                                window.drawOval(x, y, c.getWidth(), c.getHeight());
                            } else {
                                window.drawOval(x, y, c.getWidth(), c.getHeight());
                            }
                        }
                    }
                }
            }
        }

    }
}
