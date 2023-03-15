package com.waffle.main.render;

import com.waffle.main.systems.FontRenderSystem;
import com.waffle.main.systems.RenderSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Canvas extends JPanel {
    private final RenderSystem renderSystem;
    private final FontRenderSystem fontRenderSystem;
    private int width, height;
    public Canvas(RenderSystem system, FontRenderSystem system2, int width, int height) {
        renderSystem = system;
        fontRenderSystem = system2;
        this.width = width;
        this.height = height;
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

    public void render() {
        getGraphics().setColor(Color.WHITE);
        getGraphics().fillRect(0, 0, width, height);

        renderSystem.update(getGraphics());
        fontRenderSystem.update(getGraphics());
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
