package com.waffle.systems;

import com.waffle.core.Vec2f;
import com.waffle.render.Camera;

public final class RenderUtils {
    private RenderUtils() {}

    public static boolean shouldRender(Vec2f drawPos, int width, int height, Camera cam) {
        return !(drawPos.x + width < 0 || drawPos.x > cam.getWidth() * cam.getZoomScale() || drawPos.y + height < 0 || drawPos.y - height > cam.getHeight() * cam.getZoomScale());
    }
}
