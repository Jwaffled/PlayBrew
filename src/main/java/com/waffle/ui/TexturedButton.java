package com.waffle.ui;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.components.UITextureComponent;
import com.waffle.ecs.GameObject;
import com.waffle.input.Input;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TexturedButton extends GameObject implements MouseListener {
    UITextureComponent texture;
    TransformComponent transform;
    FontRenderComponent text;
    ArrayList<ButtonEventListener> listeners = new ArrayList<>();
    public int width;
    public int height;
    private boolean mouseWithinLastFrame = false;
    public static ButtonBuilder newBuilder() {
        return new ButtonBuilder();
    }

    TexturedButton() {}
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

    public void setCurrentTexture(BufferedImage b) {
        texture.textures.get(0).setSprite(b);
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
        for(ButtonEventListener l : listeners) {
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
        return transform.position.x <= e.getX()
                && transform.position.x + width >= e.getX()
                && transform.position.y <= e.getY()
                && transform.position.y + height >= e.getY();
    }

    public UITextureComponent getTexture() {
        return texture;
    }

    public TransformComponent getTransform() {
        return transform;
    }

    public FontRenderComponent getText() {
        return text;
    }

    public ArrayList<ButtonEventListener> getListeners() {
        return listeners;
    }
}
