package com.waffle.main.render;

import com.waffle.main.systems.FontRenderSystem;
import com.waffle.main.systems.RenderSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {
    public final Canvas canvas;

    public Window(int width, int height, String title, RenderSystem renderSystem, FontRenderSystem fontRenderSystem) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
        pack();
        createBufferStrategy(2);
        canvas = new Canvas(renderSystem, fontRenderSystem, width, height, getBufferStrategy());
        setContentPane(canvas);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                canvas.setWidth(getWidth());
                canvas.setHeight(getHeight());
            }
        });
    }
}
