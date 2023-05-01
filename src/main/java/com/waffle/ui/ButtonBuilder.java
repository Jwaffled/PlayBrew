package com.waffle.ui;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.components.UITextureComponent;
import com.waffle.core.RenderShape;
import com.waffle.core.UITexture;
import com.waffle.struct.Vec2f;

import javax.imageio.ImageIO;

import static com.waffle.core.Constants.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ButtonBuilder {
    private Color backgroundColor = Color.LIGHT_GRAY;
    private Color fontColor = Color.BLACK;
    private String buttonMessage = "";
    private Vec2f messageOffset = new Vec2f();
    private DrawMode drawMode = DrawMode.FILLED_BORDER;
    private ShapeType shapeType = ShapeType.RECTANGLE;
    private int x = 0;
    private int y = 0;
    private int width = 100;
    private int height = 50;
    private final ArrayList<ButtonEventListener> listeners = new ArrayList<>();
    private BufferedImage buttonTexture;

    public ButtonBuilder setBackgroundColor(Color c) {
        backgroundColor = c;
        return this;
    }

    public ButtonBuilder setFontColor(Color c) {
        fontColor = c;
        return this;
    }

    public ButtonBuilder setButtonMessage(String s) {
        buttonMessage = s;
        return this;
    }

    public ButtonBuilder setMessageOffset(Vec2f offset) {
        messageOffset = offset;
        return this;
    }

    public ButtonBuilder setDrawMode(DrawMode d) {
        drawMode = d;
        return this;
    }

    public ButtonBuilder setShapeType(ShapeType s) {
        shapeType = s;
        return this;
    }

    public ButtonBuilder setX(int newX) {
        x = newX;
        return this;
    }

    public ButtonBuilder setY(int newY) {
        y = newY;
        return this;
    }

    public ButtonBuilder setWidth(int w) {
        width = w;
        return this;
    }

    public ButtonBuilder setHeight(int h) {
        height = h;
        return this;
    }

    public ButtonBuilder addButtonListener(ButtonEventListener l) {
        listeners.add(l);
        return this;
    }

    public ButtonBuilder setButtonTexture(String resourcePath) {
        URL f = getClass().getClassLoader().getResource(resourcePath);
        if(f == null) {
            throw new IllegalArgumentException("Button texture file '" + resourcePath + "' could not be found!");
        }
        try {
            buttonTexture = ImageIO.read(f);
        } catch(IOException e) {
            throw new IllegalStateException("Something went wrong while reading button texture file '" + resourcePath + "': " + e.getMessage());
        }

        return this;
    }

    public ButtonBuilder setButtonTexture(BufferedImage b) {
        buttonTexture = b;
        return this;
    }

    public TexturedButton buildTexturedButton() {
        TexturedButton b = new TexturedButton();
        if(buttonTexture == null) {
            throw new IllegalStateException("Tried to build textured button without a valid texture. (Call addButtonTexture()?)");
        }
        b.listeners = listeners;
        b.texture = new UITextureComponent();
        b.texture.textures.add(new UITexture(new Vec2f(0, 0), buttonTexture, width, height));
        b.transform = new TransformComponent(x, y);
        b.width = width;
        b.height = height;
        if(!buttonMessage.equals("")) {
            b.text = new FontRenderComponent(buttonMessage);
            b.text.color = fontColor;
            b.text.position = messageOffset;
        }
        return b;
    }

    public Button buildButton() {
        Button b = new Button();
        b.listeners = listeners;
        b.geometry = new GeometryComponent();
        b.geometry.shapes.add(new RenderShape(shapeType, drawMode, backgroundColor, width, height, new Vec2f()));
        b.width = width;
        b.height = height;
        b.transform = new TransformComponent(x, y);
        b.text = new FontRenderComponent(buttonMessage);
        b.text.position = messageOffset;
        b.text.color = fontColor;
        return b;
    }

}
