package com.waffle.ui;

import com.waffle.components.TransformComponent;
import com.waffle.components.UITextureComponent;
import com.waffle.core.UITexture;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;
import com.waffle.input.Input;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TexturedSlider extends GameObject implements MouseListener {
    TransformComponent transform;
    UITextureComponent texture;
    UITexture sliderRect;
    UITexture sliderTrack;
    public int maxValue, minValue;
    public int width, height;
    private boolean heldDown = false;

    public static SliderBuilder newBuilder() {
        return new SliderBuilder();
    }

    @Override
    public void start() {
        texture.textures.add(sliderTrack);
        texture.textures.add(sliderRect);
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

    public UITextureComponent getTexture() {
        return texture;
    }
}
