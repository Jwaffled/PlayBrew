package com.waffle.ui;

import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.RenderShape;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;
import com.waffle.input.Input;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Slider extends GameObject implements MouseListener {
    TransformComponent transform;
    GeometryComponent geometry;
    RenderShape sliderRect;
    RenderShape sliderTrack;
    public int maxValue;
    public int minValue;
    public int width;
    public int height;
    private boolean heldDown = false;

    public static SliderBuilder newBuilder() {
        return new SliderBuilder();
    }

    @Override
    public void start() {
        geometry.shapes.add(sliderTrack);
        geometry.shapes.add(sliderRect);
        Input.getInstance().addMouseListener(this);
    }

    @Override
    public void update(float dt) {
        if(heldDown) {
            Point mousePt = Input.getInstance().getMousePosition();
            float minX = transform.position.x;
            float maxX = transform.position.x + width;
            if(mousePt.x <= minX) {
              sliderRect.getPosition().x = 0;
            } else if(mousePt.x >= maxX) {
                sliderRect.getPosition().x = width;
            } else {
                sliderRect.getPosition().x = mousePt.x - transform.position.x;
            }
        }
    }

    public float getValue() {
        return minValue + (sliderRect.getPosition().x / width) * (maxValue - minValue);
    }

    public void setValue(float v) {
        sliderRect.getPosition().x = (v - minValue) / (maxValue - minValue) * width;
    }

    public float getNormalizedValue() {
        return sliderRect.getPosition().x / width;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(mouseWithin()) {
            heldDown = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        heldDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean mouseWithin() {
        Vec2f sliderPoint = transform.position.add(sliderRect.getPosition());
        Point e = Input.getInstance().getMousePosition();
        return sliderPoint.x <= e.getX()
                && sliderPoint.x + sliderRect.getWidth() >= e.getX()
                && sliderPoint.y <= e.getY()
                && sliderPoint.y + sliderRect.getHeight() >= e.getY();
    }

    public TransformComponent getTransform() {
        return transform;
    }

    public GeometryComponent getGeometry() {
        return geometry;
    }

    public RenderShape getSliderRect() {
        return sliderRect;
    }

    public RenderShape getSliderTrack() {
        return sliderTrack;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }
}
