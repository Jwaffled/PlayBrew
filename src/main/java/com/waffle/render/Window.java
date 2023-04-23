package com.waffle.render;

import com.waffle.systems.FontRenderSystem;
import com.waffle.systems.GeometryRenderSystem;
import com.waffle.systems.SpriteRenderSystem;
import com.waffle.systems.UIRenderSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {
    public final Canvas canvas;
    private final static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public Window(String title, SpriteRenderSystem sys, FontRenderSystem sys2, GeometryRenderSystem sys3, UIRenderSystem sys4, Camera cam) {
        this(
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height,
                title,
                sys,
                sys2,
                sys3,
                sys4,
                cam
        );
        device.setFullScreenWindow(this);
    }

    public Window(int width, int height, String title, SpriteRenderSystem sys, FontRenderSystem sys2, GeometryRenderSystem sys3, UIRenderSystem sys4, Camera cam) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
        setUndecorated(true);
        pack();
        createBufferStrategy(2);
        canvas = new Canvas(sys, sys2, sys3, sys4, cam, width, height, getBufferStrategy());
        setContentPane(canvas);
        addWindowStateListener(e -> {
            canvas.setIsMinimized((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED);
        });
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                canvas.setIsMinimized(false);
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                canvas.setIsMinimized(true);
            }
        });
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
