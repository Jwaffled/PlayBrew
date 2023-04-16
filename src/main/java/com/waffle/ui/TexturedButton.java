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
    public UITextureComponent texture;
    public TransformComponent position;
    public FontRenderComponent text;
    public ArrayList<ButtonEventListener> listeners = new ArrayList<>();
    public int width, height;
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
        texture.textures.get(0).sprite = b;
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
        Point e = Input.getInstance().mousePosition;
        return position.position.x <= e.getX()
                && position.position.x + width >= e.getX()
                && position.position.y <= e.getY()
                && position.position.y + height >= e.getY();
    }
}
