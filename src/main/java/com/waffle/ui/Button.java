package com.waffle.ui;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.GeometryComponent;
import com.waffle.components.TransformComponent;
import com.waffle.ecs.GameObject;
import com.waffle.input.Input;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Button extends GameObject implements MouseListener {
    TransformComponent transform;
    GeometryComponent geometry;
    FontRenderComponent text;
    ArrayList<ButtonEventListener> listeners = new ArrayList<>();
    public int width;
    public int height;
    private boolean mouseWithinLastFrame = false;
    public static ButtonBuilder newBuilder() {
        return new ButtonBuilder();
    }

    Button() {}
    @Override
    public void start() {
        Input.getInstance().addMouseListener(this);
    }

    @Override
    public void update(float dt) {
        if(mouseWithin() && !mouseWithinLastFrame) {
            mouseWithinLastFrame = true;
            for(ButtonEventListener l : listeners) {
                l.mouseEntered();
            }
        } else if(!mouseWithin() && mouseWithinLastFrame) {
            mouseWithinLastFrame = false;
            for(ButtonEventListener l : listeners) {
                l.mouseExited();
            }
        }
    }

    public void addButtonListener(ButtonEventListener l) {
        listeners.add(l);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(mouseWithin()) {
            for(ButtonEventListener l : listeners) {
                l.buttonClicked();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(mouseWithin()) {
            for(ButtonEventListener l : listeners) {
                l.buttonPressed();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(ButtonEventListener l : getListeners()) {
            l.buttonReleased();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean mouseWithin() {
        Point e = Input.getInstance().getMousePosition();
        return transform.getPosition().x <= e.getX()
                && transform.getPosition().x + width >= e.getX()
                && transform.getPosition().y <= e.getY()
                && transform.getPosition().y + height >= e.getY();
    }

    public TransformComponent getTransform() {
        return transform;
    }

    public GeometryComponent getGeometry() {
        return geometry;
    }

    public FontRenderComponent getText() {
        return text;
    }

    public ArrayList<ButtonEventListener> getListeners() {
        return listeners;
    }
}
