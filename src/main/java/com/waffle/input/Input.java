package com.waffle.input;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Input implements KeyListener, MouseListener, MouseWheelListener {
    private static Input INSTANCE = new Input();
    private final Map<Integer, Boolean> currentButtons = new HashMap<>();
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
        currentButtons.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentButtons.put(e.getKeyCode(), false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentButtons.put(-e.getButton(), true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentButtons.put(-e.getButton(), false);
    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    public boolean read(int buttonCode) {
        return currentButtons.get(buttonCode) != null && currentButtons.get(buttonCode);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println(e.getWheelRotation());
    }
}