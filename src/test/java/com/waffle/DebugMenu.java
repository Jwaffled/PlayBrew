package com.waffle;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.RenderShape;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;
import com.waffle.input.Input;

import java.awt.*;

import static com.waffle.core.Constants.*;

public class DebugMenu extends GameObject {
    private GeometryComponent outline;
    private TransformComponent transform;
    private FontRenderComponent text;
    private FrameCounter counter;
    private static final int STRING_COUNT = 9;

    @Override
    public void start() {
        counter = GameTest.INSTANCE.frameCounter;
        outline = new GeometryComponent();
        outline.getShapes().add(new RenderShape(ShapeType.RECTANGLE, DrawMode.OUTLINE, Color.BLACK, 300, 5 + 5 + (STRING_COUNT * 16), new Vec2f()));
        text = new FontRenderComponent("");
        text.setPosition(new Vec2f(5, 10));
        transform = new TransformComponent(20, 20);
    }

    @Override
    public void update(float dt) {
        text.setMessage(String.format(
                "Current FPS: %.4f\n"
                + "Volume (dB): %.2f\n"
                + "Entity count: %d\n"
                + "Camera zoom: %.1f\n"
                + "Player position: (%.2f, %.2f)\n"
                + "Camera position: (%.2f, %.2f)\n"
                + "Slider value: %.2f\n"
                + "Mouse position: (%d, %d)\n"
                + "Normalized slider val: %.2f",
                counter.getFps(),
                GameTest.INSTANCE.currentVolume,
                world.getLivingEntityCount(),
                GameTest.INSTANCE.camera.getZoomScale(),
                GameTest.INSTANCE.player.getPosition().x, GameTest.INSTANCE.player.getPosition().y,
                GameTest.INSTANCE.camera.getPosition().x, GameTest.INSTANCE.camera.getPosition().y,
                GameTest.INSTANCE.slider.getValue(),
                Input.getInstance().getMousePosition().x, Input.getInstance().getMousePosition().y,
                GameTest.INSTANCE.slider.getNormalizedValue()
        ));
    }
}
