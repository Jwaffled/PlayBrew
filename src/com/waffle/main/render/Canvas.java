package com.waffle.main.render;

import com.waffle.main.systems.RenderSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel {
    private final RenderSystem renderSystem;

    public Canvas(RenderSystem system) {
        renderSystem = system;
    }

    @Override
    public void paintComponent(Graphics window) {
        super.paintComponent(window);

        Graphics2D graph = (Graphics2D) window;

        BufferedImage back = (BufferedImage) (createImage(getWidth(), getHeight()));

        Graphics graphToBack = back.createGraphics();

        renderSystem.update(graphToBack);

        graph.drawImage(back, null, 0, 0);
    }
}
