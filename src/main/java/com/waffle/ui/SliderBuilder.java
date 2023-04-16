package com.waffle.ui;

import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.components.UITextureComponent;
import com.waffle.core.RenderShape;
import com.waffle.core.UITexture;
import com.waffle.core.Vec2f;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.waffle.core.Constants.*;

public class SliderBuilder {
    private int maxValue, minValue;
    private int startingValue;
    private int width, height;
    private int sliderWidth;
    private int x, y;
    private BufferedImage sliderSprite;
    private BufferedImage trackSprite;

    public SliderBuilder() {
        maxValue = 100;
        minValue = 0;
        startingValue = 50;
        width = 150;
        height = 50;
        x = 0;
        y = 0;
        sliderWidth = width / 10;
    }

    public SliderBuilder setMaxValue(int max) {
        maxValue = max;
        return this;
    }

    public SliderBuilder setMinValue(int min) {
        minValue = min;
        return this;
    }

    public SliderBuilder setStartingValue(int value) {
        startingValue = value;
        return this;
    }

    public SliderBuilder setWidth(int w) {
        width = w;
        return this;
    }

    public SliderBuilder setHeight(int h) {
        height = h;
        return this;
    }

    public SliderBuilder setSliderWidth(int w) {
        sliderWidth = w;
        return this;
    }

    public SliderBuilder setX(int newX) {
        x = newX;
        return this;
    }

    public SliderBuilder setY(int newY) {
        y = newY;
        return this;
    }

    public SliderBuilder setTrackTexture(BufferedImage s) {
        trackSprite = s;
        return this;
    }

    public SliderBuilder setSliderTexture(BufferedImage s){
        sliderSprite = s;
        return this;
    }

    public Slider buildSlider() {
        Slider s = new Slider();
        s.geometryComponent = new GeometryComponent();
        s.position = new TransformComponent(x, y);
        s.sliderRect = new RenderShape(ShapeType.RECTANGLE, DrawMode.FILLED, Color.BLACK,
                sliderWidth, height,
                new Vec2f(calculateSliderRectX(), 0)
        );
        s.sliderTrack = new RenderShape(ShapeType.RECTANGLE, DrawMode.FILLED, Color.GRAY,
                width, height / 6,
                new Vec2f(0, (height / 2f - height / 12f)));
        s.width = width;
        s.height = height;
        s.maxValue = maxValue;
        s.minValue = minValue;
        return s;
    }

    public TexturedSlider buildTexturedSlider() {
        TexturedSlider s = new TexturedSlider();
        s.texture = new UITextureComponent();
        s.position = new TransformComponent(x, y);
        if(sliderSprite == null || trackSprite == null) {
            throw new IllegalStateException("Tried to build textured slider without both a track and slider texture. (Call setTrackTexture() and setSliderTexture()?)");
        }
        s.sliderRect = new UITexture(new Vec2f(calculateSliderRectX(), 0), sliderSprite, sliderWidth, height);
        s.sliderTrack = new UITexture(new Vec2f(0, height / 2f - height / 12f), trackSprite, width, height / 6);
        s.width = width;
        s.height = height;
        s.maxValue = maxValue;
        s.minValue = minValue;
        return s;
    }

    private float calculateSliderRectX() {
        return (float)(startingValue - minValue) / (maxValue - minValue) * width;
    }
}
