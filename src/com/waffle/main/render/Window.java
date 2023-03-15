package com.waffle.main.render;

import com.waffle.main.systems.FontRenderSystem;
import com.waffle.main.systems.RenderSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Window extends JFrame {
    private final int width, height;
    public final Canvas canvas;

    public Window(int width, int height, String title, RenderSystem renderSystem, FontRenderSystem fontRenderSystem) {
        super(title);
        this.width = width;
        this.height = height;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
        canvas = new Canvas(renderSystem, fontRenderSystem, width, height);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                canvas.setWidth(getWidth());
                canvas.setHeight(getHeight());
            }
        });

        add(canvas);
        pack();
    }
}
