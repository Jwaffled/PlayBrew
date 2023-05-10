package com.waffle.dredes.room;


import com.waffle.ecs.GameObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Room {
    public ArrayList<Room>[] neighbors;
    boolean important;
    public int[][] configuration;
    public GameObject[] tiles;

    public enum TileType
    {
        AIR,
        SURFACE,
        STRUCTURE,
        WATER,
        WATER_BANK,
        SPECIAL_A,
        SPECIAL_B
    }

    public enum Direction
    {
        UP_LEFT,
        UP,
        UP_RIGHT,
        RIGHT,
        DOWN_RIGHT,
        DOWN,
        DOWN_LEFT,
        LEFT
    }

    public Room()
    {
        configuration = new int[6][8];
        neighbors = new ArrayList[8];
        tiles = new GameObject[5];
        important = false;
    }

    public void addConfig(int[][] config)
    {
        for(int i = 0; i < configuration.length; i++)
        {
            for(int j = 0; j < configuration[i].length; j++)
            {
                configuration[i][j] = config[i][j];
            }
        }
    }

    public void flip()
    {
        int[][] neoConfig = new int[6][8];
        for(int i = 0; i < configuration.length; i++)
        {
            for(int j = 0; j < configuration[i].length; j++)
            {
                neoConfig[i][configuration[i].length - 1 - j] = configuration[i][j];
            }
        }
    }

    public void addNeighbors(Direction d , Room... rooms)
    {
        for(int i = 0; i < rooms.length; i++)
        {
            neighbors[d.ordinal()].add(rooms[i]);
        }
        important = true;
    }

    public Room getNeighbor(Direction d)
    {
        return neighbors[d.ordinal()].get((int)(Math.random() * neighbors[d.ordinal()].size()));
    }

    public Room[] getRoomPool(Direction d)
    {
        Room[] ret = new Room[neighbors[d.ordinal()].size()];
        Object[] toCast = neighbors[d.ordinal()].toArray();
        for(int i = 0; i < toCast.length; i++)
            ret[i] = (Room)toCast[i];
        return ret;
    }

    public String toString() {
        return String.format("""
                Neighbors: %s
                Configuration: %s
                Tiles: %s""", Arrays.toString(neighbors), Arrays.toString(configuration), Arrays.toString(tiles));
    }
}