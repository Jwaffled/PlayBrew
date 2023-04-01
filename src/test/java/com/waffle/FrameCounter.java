package com.waffle;

public class FrameCounter {
    private long lastFrameTime = System.nanoTime();
    private double currentFps;

    public void update(float dt) {
        long dtNanos = System.nanoTime() - lastFrameTime;
        currentFps = 1e9 / dtNanos;
        lastFrameTime = System.nanoTime();
        //fontRenderComponent.message = String.format("Current FPS: %.4f    Entity count: %d", currentFps, world.getLivingEntityCount());
    }

    public double getFps() {
        return currentFps;
    }
}
