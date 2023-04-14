package com.waffle.input;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Input implements KeyListener, MouseListener, MouseWheelListener {
    private static Input INSTANCE = new Input();
    private final Map<Integer, Boolean> currentButtons = new HashMap<>();
    private final ArrayList<MouseListener> listeners = new ArrayList<>();
    public Point mousePosition = MouseInfo.getPointerInfo().getLocation();
    // Subtract component.getLocationOnScreen() to get mouse pos relative to component


    public static Input getInstance() {
        return INSTANCE;
    }

    private Input() {
        INSTANCE = this;
    }

    public void addMouseListener(MouseListener m) {
        listeners.add(m);
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
        for(MouseListener m : listeners) {
            m.mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentButtons.put(-e.getButton(), true);
        for(MouseListener m : listeners) {
            m.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentButtons.put(-e.getButton(), false);
        for(MouseListener m : listeners) {
            m.mouseReleased(e);
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        for(MouseListener m : listeners) {
            m.mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for(MouseListener m : listeners) {
            m.mouseExited(e);
        }
    }


    public boolean read(int buttonCode) {
        return currentButtons.get(buttonCode) != null && currentButtons.get(buttonCode);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println(e.getWheelRotation());
    }
}