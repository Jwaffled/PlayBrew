package com.waffle.dredes.level;

import java.awt.image.BufferedImage;

public class SolidTile extends Tile {
    public SolidTile(BufferedImage sprite, int row, int col, boolean fluid, boolean water) {
        super(sprite, row, col, fluid, water);
    }
}
