package com.waffle.main.input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class Input implements KeyListener, MouseListener {
    private static Input INSTANCE = new Input();
    private Map<Integer, Boolean> currentlyPressedKeys = new HashMap<>();
    public Point mousePosition = MouseInfo.getPointerInfo().getLocation();
    // Subtract component.getLocationOnScreen() to get mouse pos relative to component
    

    public static Input getInstance() {
        return INSTANCE;
    }

    private Input() {
        INSTANCE = this;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentlyPressedKeys.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentlyPressedKeys.put(e.getKeyCode(), false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
