package com.waffle.ui;

import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.RenderShape;
import com.waffle.core.Vec2f;

import java.awt.*;

import static com.waffle.core.Constants.*;

public class SliderBuilder {
    private int maxValue, minValue;
    private int startingValue;
    private int width, height;
    private int x, y;

    public SliderBuilder() {
        maxValue = 100;
        minValue = 0;
        startingValue = 50;
        width = 150;
        height = 50;
        x = 0;
        y = 0;
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

    public SliderBuilder setX(int newX) {
        x = newX;
        return this;
    }

    public SliderBuilder setY(int newY) {
        y = newY;
        return this;
    }

    public Slider buildSlider() {
        Slider s = new Slider();
        s.geometryComponent = new GeometryComponent();
        s.position = new TransformComponent(x, y);
        s.sliderRect = new RenderShape(ShapeType.RECTANGLE, DrawMode.FILLED, Color.BLACK,
                width / 10, height,
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

    private int calculateSliderRectX() {
        return (int)((float)startingValue / (maxValue + minValue) * width);
    }
}
