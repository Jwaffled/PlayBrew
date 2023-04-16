package com.waffle.tilemap;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TilemapBuilder {
    private int width, height;
    private int tileWidth, tileHeight;
    private int[][] tiles;
    private Map<Integer, BufferedImage> numToTile;

    public static final int TO_END = -1;

    public TilemapBuilder() {
        width = 640;
        height = 640;
        tileWidth = 32;
        tileHeight = 32;
        tiles = new int[width / tileWidth][height / tileHeight];
        numToTile = new HashMap<>();
    }

    public TilemapBuilder setWidth(int w) {
        width = w;
        return this;
    }

    public TilemapBuilder setHeight(int h) {
        height = h;
        return this;
    }

    public TilemapBuilder setTileWidth(int w) {
        tileWidth = w;
        int newWidth = Math.round((float)width / tileWidth);
        int newHeight = Math.round((float)height / tileHeight);
        tiles = new int[newWidth][newHeight];
        return this;
    }

    public TilemapBuilder setTileHeight(int h) {
        tileHeight = h;
        int newWidth = Math.round((float)width / tileWidth);
        int newHeight = Math.round((float)height / tileHeight);
        tiles = new int[newWidth][newHeight];
        return this;
    }

    public TilemapBuilder addTilemapping(int i, BufferedImage spr) {
        numToTile.put(i, spr);
        return this;
    }

    public TilemapBuilder setTilemapping(Map<Integer, BufferedImage> map) {
        numToTile = map;
        return this;
    }

    public TilemapBuilder setTile(int row, int col, int tile) {
        tiles[row][col] = tile;
        return this;
    }

    public TilemapBuilder setRow(int row, int tile) {
        for(int i = 0; i < tiles[row].length; i++) {
            tiles[row][i] = tile;
        }
        return this;
    }

    public TilemapBuilder setCol(int col, int tile) {
        for(int i = 0; i < tiles.length; i++) {
            tiles[i][col] = tile;
        }
        return this;
    }

    public TilemapBuilder setAll(int tile) {
        for(int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = tile;
            }
        }
        return this;
    }

    public TilemapBuilder setRows(int from, int to, int tile) {
        if(to == TO_END) {
            to = tiles.length;
        }
        for(int i = from; i < to; i++) {
            for(int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = tile;
            }
        }
        return this;
    }

    public TilemapBuilder setCols(int from, int to, int tile) {
        if(to == TO_END) {
            to = tiles[from].length;
        }
        for(int i = from; i < to; i++) {
            for(int j = 0; j < tiles.length; j++) {
                tiles[j][i] = tile;
            }
        }
        return this;
    }

    public Tilemap buildTilemap() {
        Tilemap t = new Tilemap();
        t.height = height;
        t.width = width;
        t.tiles = tiles;
        t.tileHeight = tileHeight;
        t.tileWidth = tileWidth;
        t.numToTile = numToTile;
        return t;
    }

}
