package com.waffle;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.OutlineComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;

public class DebugMenu extends GameObject {
    private OutlineComponent outline;
    private TransformComponent transform;
    private FontRenderComponent text;
    private FrameCounter counter;
    private final int stringCount = 3;

    @Override
    public void start() {
        counter = GameTest.INSTANCE.frameCounter;
        outline = new OutlineComponent(300, 5 + 5 + (stringCount * 16));
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
        , counter.getFps(), GameTest.INSTANCE.currentVolume, world.getLivingEntityCount());
    }
}
