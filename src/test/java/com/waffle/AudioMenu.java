package com.waffle;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;

public class AudioMenu extends GameObject {
    private FontRenderComponent fontRenderComponent;
    private TransformComponent transformComponent;
    private double currentVolume;
    @Override
    public void update(float dt) {
        fontRenderComponent.message = String.format("Current volume: %.2f", currentVolume);
    }

    @Override
    public void start() {
        fontRenderComponent = new FontRenderComponent("Current volume: ");
        transformComponent = new TransformComponent(200, 200);
    }

    public void setCurrentVolume(double volume) {
        currentVolume = volume;
    }
}
