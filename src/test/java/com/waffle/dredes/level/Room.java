package com.waffle.dredes.level;


import com.waffle.ecs.GameObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Room {
    public ArrayList<Room>[] strongNeighbors;
    public ArrayList<Room>[] weakNeighbors;
    boolean important;
    boolean flipped;
    public int[][] configuration;


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
        weakNeighbors = new ArrayList[8];
        strongNeighbors = new ArrayList[8];
        important = false;
        flipped = false;
    }

    public void addConfig(int[][] config) {
        for(int i = 0; i < configuration.length; i++) {
            for(int j = 0; j < configuration[i].length; j++) {
                configuration[i][j] = config[i][j];
            }
        }
    }

    public void addNeighbor(Direction d , Room room) {
        strongNeighbors[d.ordinal()].add(room);
        weakNeighbors[d.ordinal()].add(room);
        important = true;
    }

    public Room getNeighbor(Direction d) {
        if(strongNeighbors[d.ordinal()].size() == 0)
        {
            return weakNeighbors[d.ordinal()].get((int)(Math.random() * weakNeighbors[d.ordinal()].size()));
        }
        return strongNeighbors[d.ordinal()].get((int)(Math.random() * strongNeighbors[d.ordinal()].size()));


    }

    public Room[] getRoomPool(Direction d, boolean strong) {
        if(strong)
        {
            Room[] ret = new Room[strongNeighbors[d.ordinal()].size()];
            Object[] toCast = strongNeighbors[d.ordinal()].toArray();
            for(int i = 0; i < toCast.length; i++) {
                ret[i] = (Room)toCast[i];
            }
            return ret;
        }
        Room[] ret = new Room[weakNeighbors[d.ordinal()].size()];
        Object[] toCast = weakNeighbors[d.ordinal()].toArray();
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
        ArrayList<Room>[] neoStrongNeighbors = new ArrayList[8];
        neoStrongNeighbors[Direction.UP_LEFT.ordinal()] = strongNeighbors[Direction.UP_RIGHT.ordinal()];
        neoStrongNeighbors[Direction.LEFT.ordinal()] = strongNeighbors[Direction.RIGHT.ordinal()];
        neoStrongNeighbors[Direction.DOWN_LEFT.ordinal()] = strongNeighbors[Direction.DOWN_RIGHT.ordinal()];
        neoStrongNeighbors[Direction.UP_RIGHT.ordinal()] = strongNeighbors[Direction.UP_LEFT.ordinal()];
        neoStrongNeighbors[Direction.RIGHT.ordinal()] = strongNeighbors[Direction.LEFT.ordinal()];
        neoStrongNeighbors[Direction.DOWN_RIGHT.ordinal()] = strongNeighbors[Direction.DOWN_LEFT.ordinal()];
        neoStrongNeighbors[Direction.UP.ordinal()] = strongNeighbors[Direction.UP.ordinal()];
        neoStrongNeighbors[Direction.DOWN.ordinal()] = strongNeighbors[Direction.DOWN.ordinal()];

        ArrayList<Room>[] neoWeakNeighbors = new ArrayList[8];
        neoWeakNeighbors[Direction.UP_LEFT.ordinal()] = weakNeighbors[Direction.UP_RIGHT.ordinal()];
        neoWeakNeighbors[Direction.LEFT.ordinal()] = weakNeighbors[Direction.RIGHT.ordinal()];
        neoWeakNeighbors[Direction.DOWN_LEFT.ordinal()] = weakNeighbors[Direction.DOWN_RIGHT.ordinal()];
        neoWeakNeighbors[Direction.UP_RIGHT.ordinal()] = weakNeighbors[Direction.UP_LEFT.ordinal()];
        neoWeakNeighbors[Direction.RIGHT.ordinal()] = weakNeighbors[Direction.LEFT.ordinal()];
        neoWeakNeighbors[Direction.DOWN_RIGHT.ordinal()] = weakNeighbors[Direction.DOWN_LEFT.ordinal()];
        neoWeakNeighbors[Direction.UP.ordinal()] = weakNeighbors[Direction.UP.ordinal()];
        neoWeakNeighbors[Direction.DOWN.ordinal()] = weakNeighbors[Direction.DOWN.ordinal()];
        Room ret = new Room();
        ret.configuration = neoConfig;
        ret.strongNeighbors = neoStrongNeighbors;
        ret.weakNeighbors = neoWeakNeighbors;
        flipped = !flipped;
        return ret;
    }

//    public String toString() {
//        return String.format("""
//                Neighbors: %s
//                Configuration: %s
//                Tiles: %s""", Arrays.toString(neighbors), Arrays.toString(configuration), Arrays.toString(tiles));
//    }
}