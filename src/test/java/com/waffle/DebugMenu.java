package com.waffle;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;

import java.awt.*;

import static com.waffle.core.Constants.*;

public class DebugMenu extends GameObject {
    private GeometryComponent outline;
    private TransformComponent transform;
    private FontRenderComponent text;
    private FrameCounter counter;
    private static final int STRING_COUNT = 6;

    @Override
    public void start() {
        counter = GameTest.INSTANCE.frameCounter;
        outline = new GeometryComponent(ShapeType.RECTANGLE, DrawMode.OUTLINE, Color.BLACK, 300, 5 + 5 + (STRING_COUNT * 16));
        text = new FontRenderComponent("");
        text.position = new Vec2f(5, 10);
        transform = new TransformComponent(20, 20);
    }

    @Override
    public void update(float dt) {
        text.message = String.format(
                "Current FPS: %.4f\n"
                + "Volume (dB): %.2f\n"
                + "Entity count: %d\n"
                + "Camera zoom: %.1f\n"
                + "Player position: (%.2f, %.2f)\n"
                + "Camera position: (%.2f, %.2f)\n",
                counter.getFps(),
                GameTest.INSTANCE.currentVolume,
                world.getLivingEntityCount(),
                GameTest.INSTANCE.camera.zoomScale,
                GameTest.INSTANCE.player.getPosition().x, GameTest.INSTANCE.player.getPosition().y,
                GameTest.INSTANCE.camera.position.x, GameTest.INSTANCE.camera.position.y
        );
    }
}
