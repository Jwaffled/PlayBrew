package com.waffle.dredes.level;


import com.waffle.ecs.GameObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Room {
    public ArrayList<Room>[] neighbors;
    boolean important;
    public int[][] configuration;
    public GameObject[] tiles;

    public enum TileType {
        AIR,
        SURFACE,
        STRUCTURE,
        WATER,
        WATER_BANK,
        SPECIAL_A,
        SPECIAL_B
    }

    public enum Direction {
        UP_LEFT,
        UP,
        UP_RIGHT,
        RIGHT,
        DOWN_RIGHT,
        DOWN,
        DOWN_LEFT,
        LEFT
    }

    public Room() {
        configuration = new int[6][8];
        neighbors = new ArrayList[8];
        tiles = new GameObject[5];
        important = false;
    }

    public void addConfig(int[][] config) {
        for(int i = 0; i < configuration.length; i++) {
            for(int j = 0; j < configuration[i].length; j++) {
                configuration[i][j] = config[i][j];
            }
        }
    }


    public void addNeighbors(Direction d , Room... rooms) {
        for(int i = 0; i < rooms.length; i++) {
            neighbors[d.ordinal()].add(rooms[i]);
        }
        important = true;
    }

    public Room getNeighbor(Direction d) {
        return neighbors[d.ordinal()].get((int)(Math.random() * neighbors[d.ordinal()].size()));
    }

    public Room[] getRoomPool(Direction d) {
        Room[] ret = new Room[neighbors[d.ordinal()].size()];
        Object[] toCast = neighbors[d.ordinal()].toArray();
        for(int i = 0; i < toCast.length; i++) {
            ret[i] = (Room)toCast[i];
        }
        return ret;
    }

    public Room flip() {
        int[][] neoConfig = new int[6][8];
        for(int i = 0; i < configuration.length; i++) {
            for(int j = 0; j < configuration[i].length; j++) {
                neoConfig[i][configuration[i].length - 1 - j] = configuration[i][j];
            }
        }
        ArrayList<Room>[] neoNeighbors = new ArrayList[8];
        neoNeighbors[Direction.UP_LEFT.ordinal()] = neighbors[Direction.UP_RIGHT.ordinal()];
        neoNeighbors[Direction.LEFT.ordinal()] = neighbors[Direction.RIGHT.ordinal()];
        neoNeighbors[Direction.DOWN_LEFT.ordinal()] = neighbors[Direction.DOWN_RIGHT.ordinal()];
        neoNeighbors[Direction.UP_RIGHT.ordinal()] = neighbors[Direction.UP_LEFT.ordinal()];
        neoNeighbors[Direction.RIGHT.ordinal()] = neighbors[Direction.LEFT.ordinal()];
        neoNeighbors[Direction.DOWN_RIGHT.ordinal()] = neighbors[Direction.DOWN_LEFT.ordinal()];
        neoNeighbors[Direction.UP.ordinal()] = neighbors[Direction.UP.ordinal()];
        neoNeighbors[Direction.DOWN.ordinal()] = neighbors[Direction.DOWN.ordinal()];
        Room ret = new Room();
        ret.configuration = neoConfig;
        ret.neighbors = neoNeighbors;
        return ret;
    }

    public String toString() {
        return String.format("""
                Neighbors: %s
                Configuration: %s
                Tiles: %s""", Arrays.toString(neighbors), Arrays.toString(configuration), Arrays.toString(tiles));
    }
}