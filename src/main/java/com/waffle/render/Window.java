package com.waffle.render;

import com.waffle.systems.FontRenderSystem;
import com.waffle.systems.SpriteRenderSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {
    public final Canvas canvas;
    private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public Window(String title, SpriteRenderSystem spriteRenderSystem, FontRenderSystem fontRenderSystem, Camera cam) {
        this(
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height,
                title,
                spriteRenderSystem,
                fontRenderSystem,
                cam
        );
        //device.setFullScreenWindow(this);
    }

    public Window(int width, int height, String title, SpriteRenderSystem spriteRenderSystem, FontRenderSystem fontRenderSystem, Camera cam) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
        setUndecorated(true);
        pack();
        createBufferStrategy(2);
        canvas = new Canvas(spriteRenderSystem, fontRenderSystem, cam, width, height, getBufferStrategy());
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
