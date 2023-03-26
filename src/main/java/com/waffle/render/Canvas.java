package com.waffle.render;

import com.waffle.systems.FontRenderSystem;
import com.waffle.systems.SpriteRenderSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Canvas extends JPanel {
    private final SpriteRenderSystem spriteRenderSystem;
    private final FontRenderSystem fontRenderSystem;
    private final BufferStrategy strategy;
    private int width, height;
    public Canvas(SpriteRenderSystem system, FontRenderSystem system2, int width, int height, BufferStrategy s) {
        spriteRenderSystem = system;
        fontRenderSystem = system2;
        this.width = width;
        this.height = height;
        this.setDoubleBuffered(false);
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
                Graphics g = strategy.getDrawGraphics();

                g.setColor(Color.WHITE);
                g.fillRect(0, 0, width, height);

                spriteRenderSystem.update(g);
                fontRenderSystem.update(g);

                g.dispose();
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
}
