package com.waffle.dredes.level;


import com.waffle.ecs.GameObject;
import com.waffle.struct.Vec2f;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class representing a "Room": an 8 by 6 tile rectangle with rules for its placement
 */
public class Room {
    public ArrayList<Room>[] strongNeighbors;
    public ArrayList<Room>[] weakNeighbors;
    boolean important;
    boolean flipped;
    public int[][] configuration;
    public String name;

    /**
     * Represents a type of tile in a biome
     */
    public enum TileType {
        AIR,
        SURFACE,
        STRUCTURE,
        WATER,
        WATER_BANK,
        SPECIAL_A,
        SPECIAL_B
    }

    /**
     * Represents a direction relative to some center location
     */
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

    /**
     * Converts a vector into a direction object
     * @param vector the vector to convert
     * @return a direction associated with the given vector
     */
    public static Direction getDirection(Vec2f vector) {
        if(vector.x > 0)
        {
            if(vector.y > 0)
            {
                return Direction.DOWN_RIGHT;
            }
            if(vector.y < 0)
            {
                return Direction.UP_RIGHT;
            }
            return Direction.RIGHT;
        }
        if(vector.x < 0)
        {
            if(vector.y > 0)
            {
                return Direction.DOWN_LEFT;
            }
            if(vector.y < 0)
            {
                return Direction.UP_LEFT;
            }
            return Direction.LEFT;
        }
        if(vector.y > 0)
        {
            return Direction.DOWN;
        }
        if(vector.y < 0)
        {
            return Direction.UP;
        }
        return null;
    }

    /**
     * Constructs an empty room with no meaningful state
     */
    public Room() {
        configuration = new int[6][8];
        weakNeighbors = new ArrayList[8];
        strongNeighbors = new ArrayList[8];
        for(int i = 0; i < strongNeighbors.length; i++) {
            strongNeighbors[i] = new ArrayList<>();
            weakNeighbors[i] = new ArrayList<>();
        }
        important = false;
        flipped = false;
    }

    /**
     * Sets the layout of the room, each int represents a TileType
     * @param config the new layout
     */
    public void addConfig(int[][] config) {
        for(int i = 0; i < configuration.length; i++) {
            for(int j = 0; j < configuration[i].length; j++) {
                configuration[i][j] = config[i][j];
            }
        }
    }

    /**
     * Adds a "strong" neighbor rule to this room
     * @param d the direction to add the neighbor in
     * @param room the room to add
     */
    public void addNeighbor(Direction d , Room room) {
        strongNeighbors[d.ordinal()].add(room);
        weakNeighbors[d.ordinal()].add(room);
        important = true;
    }

    /**
     * Adds a neighbor rule to this room
     * @param d the direction to add the neighbor in
     * @param room the room to add
     * @param strong if the room is a weak or strong relationship
     */
    public void addNeighbor(Direction d , Room room, boolean strong) {
        if(strong)
        {
            strongNeighbors[d.ordinal()].add(room);
        }
        else
        {
            weakNeighbors[d.ordinal()].add(room);
        }
    }

    /**
     * Returns a random valid neighbor based on the rules of this Room
     * @param d the direction to check
     * @return a random valid neighbor based on the rules of this Room
     */
    public Room getNeighbor(Direction d) {
        if(strongNeighbors[d.ordinal()].size() == 0)
        {
            return weakNeighbors[d.ordinal()].get((int)(Math.random() * weakNeighbors[d.ordinal()].size()));
        }
        return strongNeighbors[d.ordinal()].get((int)(Math.random() * strongNeighbors[d.ordinal()].size()));


    }

    /**
     * Returns the list of valid rooms associated with a given direction
     * @param d the direction to check
     * @param strong whether to return strong relationships
     * @return the list of valid rooms associated with a given direction
     */
    public Room[] getRoomPool(Direction d, boolean strong) {
        if(strong)
        {
            Room[] ret = new Room[strongNeighbors[d.ordinal()].size()];
            Object[] toCast = strongNeighbors[d.ordinal()].toArray();
            for(int i = 0; i < toCast.length; i++) {
                ret[i] = (Room)toCast[i];
            }
            if(flipped)
            {
                for(int i = 0; i < ret.length; i++)
                {
                    ret[i] = ret[i].flip();
                }
            }
            //System.out.println(Arrays.toString(ret));
            return ret;
        }
        Room[] ret = new Room[weakNeighbors[d.ordinal()].size()];
        Object[] toCast = weakNeighbors[d.ordinal()].toArray();
        for(int i = 0; i < toCast.length; i++) {
            ret[i] = (Room)toCast[i];
        }
        //System.out.println(Arrays.toString(ret));
        return ret;

    }

    /**
     * Returns a copy of this room, but mirrored
     * @return a copy of this room, but mirrored
     */
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
        ret.flipped = true;
        ret.name += "Flipped";
        return ret;
    }

    /**
     * Determines if two rooms are equal
     * @param obj the object to compare to
     * @return if two rooms are equal
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Room r) {
            int[][] comp = r.configuration;
            for(int i = 0; i < comp.length; i++) {
                for(int j = 0; j < comp[i].length; j++) {
                    if(comp[i][j] != configuration[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a string representation of this object
     * @return a string representation of this object
     */
    public String toString() {
        return name;
    }
}