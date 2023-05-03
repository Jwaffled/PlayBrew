package com.waffle.render;

import com.waffle.core.Constants;
import com.waffle.systems.FontRenderSystem;
import com.waffle.systems.GeometryRenderSystem;
import com.waffle.systems.SpriteRenderSystem;
import com.waffle.systems.UIRenderSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Canvas extends JPanel {
    private SpriteRenderSystem spriteRenderSystem;
    private FontRenderSystem fontRenderSystem;
    private GeometryRenderSystem geometryRenderSystem;
    private UIRenderSystem uiRenderSystem;
    private final BufferStrategy strategy;
    private final Camera camera;
    private int width, height;
    private boolean isMinimized;

    public Canvas(int width, int height, Camera cam, BufferStrategy s) {
        this.width = width;
        this.height = height;
        camera = cam;
        strategy = s;
        isMinimized = false;
        this.setDoubleBuffered(false);
    }

    public Canvas(SpriteRenderSystem sys, FontRenderSystem sys2, GeometryRenderSystem sys3, UIRenderSystem sys4, Camera cam, int width, int height, BufferStrategy s) {
        spriteRenderSystem = sys;
        fontRenderSystem = sys2;
        geometryRenderSystem = sys3;
        uiRenderSystem = sys4;
        camera = cam;
        this.width = width;
        this.height = height;
        this.setDoubleBuffered(false);
        isMinimized = false;
        strategy = s;
    }

    @Override
    public void paintComponent(Graphics window) {

    }

    @Override
    public void paint(Graphics g) {

    }

    public void render() {
        do {
            do {
                //if(!isMinimized) {
                    Graphics g = strategy.getDrawGraphics();

                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, width, height);

                    spriteRenderSystem.update(g, camera, width, height);
                    uiRenderSystem.update(g);
                    geometryRenderSystem.update(g);
                    fontRenderSystem.update(g);

                    g.dispose();
                //}

            } while(strategy.contentsRestored());

            strategy.show();

        } while(strategy.contentsLost());
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setIsMinimized(boolean m) {
        isMinimized = m;
    }

    public void setSpriteRenderSystem(SpriteRenderSystem spriteRenderSystem) {
        this.spriteRenderSystem = spriteRenderSystem;
    }

    public void setFontRenderSystem(FontRenderSystem fontRenderSystem) {
        this.fontRenderSystem = fontRenderSystem;
    }

    public void setGeometryRenderSystem(GeometryRenderSystem geometryRenderSystem) {
        this.geometryRenderSystem = geometryRenderSystem;
    }

    public void setUiRenderSystem(UIRenderSystem uiRenderSystem) {
        this.uiRenderSystem = uiRenderSystem;
    }
}
