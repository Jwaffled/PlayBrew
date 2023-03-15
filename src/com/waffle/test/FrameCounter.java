package com.waffle.test;

import com.waffle.main.components.FontRenderComponent;
import com.waffle.main.components.TransformComponent;
import com.waffle.main.ecs.GameObject;

public class FrameCounter extends GameObject {
    private FontRenderComponent fontRenderComponent;
    private TransformComponent transform;
    private long lastFrameTime = System.nanoTime();
    private double currentFps;
    @Override
    public void update(float dt) {
        long dtNanos = System.nanoTime() - lastFrameTime;
        currentFps = 1e9 / dtNanos;
        lastFrameTime = System.nanoTime();
        fontRenderComponent.message = String.format("Current FPS: %.4f    Entity count: %d", currentFps, world.getLivingEntityCount());
    }

    @Override
    public void start() {
        fontRenderComponent = new FontRenderComponent(String.format("Current FPS: %.0f", currentFps));
        transform = new TransformComponent(100, 100);
    }
}
