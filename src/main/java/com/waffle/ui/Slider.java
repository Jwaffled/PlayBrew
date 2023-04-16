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
    public TransformComponent position;
    public GeometryComponent geometryComponent;
    RenderShape sliderRect;
    RenderShape sliderTrack;
    int maxValue, minValue;
    int width, height;
    private boolean heldDown = false;

    public static SliderBuilder newBuilder() {
        return new SliderBuilder();
    }

    @Override
    public void start() {
        geometryComponent.shapes.add(sliderTrack);
        geometryComponent.shapes.add(sliderRect);
        Input.getInstance().addMouseListener(this);
    }

    @Override
    public void update(float dt) {
        if(heldDown) {
            Point mousePt = Input.getInstance().mousePosition;
            float minX = position.position.x;
            float maxX = position.position.x + width;
            if(mousePt.x <= minX) {
              sliderRect.pos.x = 0;
            } else if(mousePt.x >= maxX) {
                sliderRect.pos.x = width;
            } else {
                sliderRect.pos.x = mousePt.x - position.position.x;
            }
        }
    }

    public float getValue() {
        return minValue + (sliderRect.pos.x / width) * (maxValue - minValue);
    }

    public void setValue(float v) {
        sliderRect.pos.x = (v - minValue) / (maxValue - minValue) * width;
    }

    public float getNormalizedValue() {
        return sliderRect.pos.x / width;
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
        Vec2f sliderPoint = position.position.add(sliderRect.pos);
        Point e = Input.getInstance().mousePosition;
        return sliderPoint.x <= e.getX()
                && sliderPoint.x + sliderRect.width >= e.getX()
                && sliderPoint.y <= e.getY()
                && sliderPoint.y + sliderRect.height >= e.getY();
    }
}
