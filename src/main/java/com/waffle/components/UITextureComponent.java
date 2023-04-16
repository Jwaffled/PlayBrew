package com.waffle.components;

import com.waffle.core.UITexture;
import com.waffle.ecs.IComponent;

import java.util.ArrayList;
import java.util.List;

public final class UITextureComponent implements IComponent {
    private List<UITexture> textures;

    public UITextureComponent(List<UITexture> t) {
        setTextures(t);
    }

    public UITextureComponent() {
        setTextures(new ArrayList<>());
    }

    public List<UITexture> getTextures() {
        return textures;
    }

    public void setTextures(List<UITexture> textures) {
        this.textures = textures;
    }
}
