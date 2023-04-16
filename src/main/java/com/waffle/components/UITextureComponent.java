package com.waffle.components;

import com.waffle.core.UITexture;
import com.waffle.ecs.IComponent;

import java.util.ArrayList;
import java.util.List;

public final class UITextureComponent implements IComponent {
    private List<UITexture> textures;

    public UITextureComponent(List<UITexture> t) {
        textures = t;
    }

    public UITextureComponent() {
        textures = new ArrayList<>();
    }

    public List<UITexture> getTextures() {
        return textures;
    }

    public void setTextures(List<UITexture> textures) {
        this.textures = textures;
    }
}
