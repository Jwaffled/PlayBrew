package com.waffle.dredes;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.RenderShape;
import com.waffle.struct.Vec2f;
import com.waffle.ecs.GameObject;
import com.waffle.input.Input;

import java.awt.*;

import static com.waffle.core.Constants.*;

public class DebugMenu extends GameObject {
    private GeometryComponent outline;
    private TransformComponent transform;
    private FontRenderComponent text;
    private static final int STRING_COUNT = 3;

    @Override
    public void start() {
        outline = new GeometryComponent();
        outline.shapes.add(new RenderShape(ShapeType.RECTANGLE, DrawMode.OUTLINE, Color.BLACK, 300, 5 + 5 + (STRING_COUNT * 16), new Vec2f()));
        text = new FontRenderComponent("");
        text.position = new Vec2f(5, 10);
        transform = new TransformComponent(20, 20);
    }

    @Override
    public void update(float dt) {
        text.message = String.format(
                """
                        Entity count: %d
                        Mouse position: (%d, %d)
                        Render time: %.1fus""",
                world.getLivingEntityCount(),
                Input.getInstance().getMousePosition().x, Input.getInstance().getMousePosition().y,
                MainGame.INSTANCE.renderTime
        );
    }
}
