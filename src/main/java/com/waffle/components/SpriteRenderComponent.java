package com.waffle.components;

import com.waffle.core.SpriteRenderer;
import com.waffle.core.Vec2f;
import com.waffle.ecs.IComponent;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SpriteRenderComponent implements IComponent {
    public List<SpriteRenderer> sprites;
    public SpriteRenderComponent(List<SpriteRenderer> l) {
        sprites = l;
    }

    public SpriteRenderComponent() {
        sprites = new ArrayList<>();
    }
}
