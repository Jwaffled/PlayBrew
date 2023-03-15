package com.waffle.main.render;

import com.waffle.main.systems.FontRenderSystem;
import com.waffle.main.systems.RenderSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Canvas extends JPanel {
    private final RenderSystem renderSystem;
    private final FontRenderSystem fontRenderSystem;
    private BufferStrategy strategy;
    private int width, height;
    public Canvas(RenderSystem system, FontRenderSystem system2, int width, int height, BufferStrategy s) {
        renderSystem = system;
        fontRenderSystem = system2;
        this.width = width;
        this.height = height;
        this.setDoubleBuffered(false);
        strategy = s;
    }

    @Override
    public void paintComponent(Graphics window) {
//        super.paintComponent(window);
//
//        Graphics2D graph = (Graphics2D) window;
//
//        BufferedImage back = (BufferedImage) (createImage(getWidth(), getHeight()));
//
//        Graphics graphToBack = back.createGraphics();
//
//        renderSystem.update(graphToBack);
//        fontRenderSystem.update(graphToBack);
//
//        graph.drawImage(back, null, 0, 0);
        //super.paintComponent(window);
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

                renderSystem.update(g);
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
