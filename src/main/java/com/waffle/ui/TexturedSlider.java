package com.waffle.ui;

import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.components.UITextureComponent;
import com.waffle.core.RenderShape;
import com.waffle.core.UITexture;
import com.waffle.core.Vec2f;
import com.waffle.ecs.GameObject;
import com.waffle.input.Input;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TexturedSlider extends GameObject implements MouseListener {
    public TransformComponent position;
    public UITextureComponent texture;
    UITexture sliderRect;
    UITexture sliderTrack;
    int maxValue, minValue;
    int width, height;
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
            Point mousePt = Input.getInstance().mousePosition;
            float minX = position.position.x;
            float maxX = position.position.x + width;
            if(mousePt.x <= minX) {
                sliderRect.position.x = 0;
            } else if(mousePt.x >= maxX) {
                sliderRect.position.x = width;
            } else {
                sliderRect.position.x = mousePt.x - position.position.x;
            }
        }
    }

    public float getValue() {
        return minValue + (sliderRect.position.x / width) * (maxValue - minValue);
    }

    public float getNormalizedValue() {
        return sliderRect.position.x / width;
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
        Vec2f sliderPoint = position.position.add(sliderRect.position);
        Point e = Input.getInstance().mousePosition;
        return sliderPoint.x <= e.getX()
                && sliderPoint.x + sliderRect.width >= e.getX()
                && sliderPoint.y <= e.getY()
                && sliderPoint.y + sliderRect.height >= e.getY();
    }
}
