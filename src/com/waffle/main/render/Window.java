package com.waffle.main.render;

import com.waffle.main.systems.RenderSystem;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private final int width, height;

    public Window(int width, int height, String title, RenderSystem renderSystem) {
        super(title);
        this.width = width;
        this.height = height;
        Canvas canvas = new Canvas(renderSystem);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        add(canvas);
        pack();
    }
}
