package com.waffle.components;

import com.waffle.core.SpriteRenderer;
import com.waffle.ecs.IComponent;

import java.util.ArrayList;
import java.util.List;

public class SpriteRenderComponent implements IComponent {
    private List<SpriteRenderer> sprites;
    public SpriteRenderComponent(List<SpriteRenderer> l) {
        sprites = l;
    }

    public SpriteRenderComponent() {
        sprites = new ArrayList<>();
    }

    public List<SpriteRenderer> getSprites() {
        return sprites;
    }

    public void setSprites(List<SpriteRenderer> sprites) {
        this.sprites = sprites;
    }
}
