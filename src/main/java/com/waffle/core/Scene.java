package com.waffle.core;

import com.waffle.ecs.World;
import com.waffle.systems.FontRenderSystem;
import com.waffle.systems.GeometryRenderSystem;
import com.waffle.systems.SpriteRenderSystem;
import com.waffle.systems.UIRenderSystem;

public interface Scene {
    void focus();
    void lostFocus();
    void update(float dt);
    void start();
    World getWorld();
    SpriteRenderSystem getSpriteRenderSystem();
    UIRenderSystem getUIRenderSystem();
    GeometryRenderSystem getGeometryRenderSystem();
    FontRenderSystem getFontRenderSystem();
}
