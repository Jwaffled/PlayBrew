package com.waffle.ui;

import java.awt.event.MouseEvent;

public interface ButtonEventListener {
    void buttonClicked(MouseEvent e);
    void buttonPressed(MouseEvent e);
    void buttonReleased(MouseEvent e);
    void mouseEntered();
    void mouseExited();
}
