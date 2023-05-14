package com.waffle.dredes.level;

import com.waffle.core.Constants;
import com.waffle.core.Utils;
import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import static com.waffle.dredes.level.Room.Direction.*;
import static com.waffle.dredes.level.Room.TileType.*;
public class LevelGen {
    public static LevelGen INSTANCE = new LevelGen();
    private RoomLoader roomLoader;

    public enum Biome
    {
        Grassland,
        Redland,
        Sandland,
        Saltland,
        Stoneland
    }

    private LevelGen() {}

    public Tile[][] generate(Biome biome, int x, int y, boolean river)
    {
        Tile[] tiles = new Tile[7];
        roomLoader = new RoomLoader();
        Room[][] level = new Room[6][10];
        loadBiome(roomLoader, biome, tiles);
        if(river) {
            roomLoader.addDirectory("DreDes/Rooms/River");
        }
        int height = 3;
        int direction = -1;
        level[2][0] = roomLoader.getRoom("Camp");
        level[2][9] = roomLoader.getRoom("Source");
        for(int i = 0; i < level[0].length/2; i++)
        {
            for(int j = height; j < level.length && j > 0; j+= direction)
            {
                level[j][i] = pickRoom(level, j, i);
                level[j][level[j].length - 1 - i] = pickRoom(level, j, level[j].length - 1 - i);
                height = j;
            }
            direction *= -1;
        }
        for(int i = 0; i < level[0].length; i++)
        {
            for(int j = level.length - 1; j > 0; j--)
            {
                if(level[j][i] == null)
                {
                    level[j][i] = pickRoom(level, j, i);
                }
            }
        }
        Tile[][] ret = new Tile[36][80];
        for(int i = 0; i < level.length; i++)
        {
            for(int j = 0; j < level[0].length; j++)
            {
                if(level[i][j] != null)
                {
                    int[][] blueprint = level[i][j].configuration;
                    for(int k = 0; k < blueprint.length; k++)
                    {
                        for(int l = 0; l < blueprint[k].length; l++)
                        {
                            if(tiles[blueprint[k][l]] != null)
                            {
                                System.out.printf("%d %d %d %d%n", i, j, k, l);
                                ret[(i * 6)+ k][(j * 8) + l] = tiles[blueprint[k][l]].copy((i * 6) + k,(j * 8) + l);
                            }
                        }
                    }
                }

            }
        }
        return ret;

    }


    public void loadBiome(RoomLoader rl, Biome b, Tile[] tiles)
    {
        tiles[0] = null;
        tiles[3] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Water.png"), -1, -1, true, true, 1f, new Vec2f(1.5f, 2f));
        if(b.equals(Biome.Grassland))
        {
            loadGrass(rl);
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Grass.png"), -1, -1, false, false);
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Dirt.png"), -1, -1, false, false);
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Gravel.png"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Bricks.png"), -1, -1, false, false);
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Tiles.png"), -1, -2, false, false);
        }
        else if(b.equals(Biome.Redland))
        {
            loadRed(rl);
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Clay.png"), -1, -1, false, false);
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Gravel.png"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Sand.png"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.1f));
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Bricks.png"), -1, -1, false, false);
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Tiles.png"), -1, -2, false, false);
        }
        else if(b.equals(Biome.Sandland))
        {
            loadSand(rl);
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Sand.png"), -1, -1, false, false, 1.25f, new Vec2f(1,1.1f));
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Clay.png"), -1, -1, false, false);
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Gravel.png"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Bricks.png"), -1, -1, false, false);
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Tiles.png"), -1, -2, false, false);
        }
        else if(b.equals(Biome.Saltland))
        {
            loadSalt(rl);
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Salt.png"), -1, -1, false, false);
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Rock.png"), -1, -1, false, false);
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Salt.png"), -1, -1, false, false);
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Dirt.png"), -1, -1, false, false);
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Ice.png"), -1, -2, false, false, .5f, new Vec2f(1,1));
        }
        else if(b.equals(Biome.Stoneland))
        {
            loadStone(rl);
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Rock.png"), -1, -1, false, false);
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Rock.png"), -1, -1, false, false);
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Salt.png"), -1, -1, false, false);
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Snow.png"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Ice.png"), -1, -2, false, false, .5f, new Vec2f(1,1));
        }
    }
    public void loadGrass(RoomLoader roomLoader)
    {
        roomLoader.addDirectory("DreDes/Rooms/Hills");
        roomLoader.addDirectory("DreDes/Rooms/Cave");
        roomLoader.addDirectory("DreDes/Rooms/Pond");
    }

    public void loadRed(RoomLoader roomLoader)
    {
        roomLoader.addDirectory("DreDes/Rooms/Hills");
        roomLoader.addDirectory("DreDes/Rooms/Cliff");
        roomLoader.addDirectory("DreDes/Rooms/Cave");
        roomLoader.addDirectory("DreDes/Rooms/Pond");
    }

    public void loadSand(RoomLoader roomLoader)
    {
        roomLoader.addDirectory("DreDes/Rooms/Hills");
        roomLoader.addDirectory("DreDes/Rooms/Cave");
        roomLoader.addDirectory("DreDes/Rooms/Pit");
        roomLoader.addDirectory("DreDes/Rooms/Temple");
    }

    public void loadSalt(RoomLoader roomLoader)
    {
        roomLoader.addDirectory("DreDes/Rooms/Cliff");
        roomLoader.addDirectory("DreDes/Rooms/Cave");
        roomLoader.addDirectory("DreDes/Rooms/Pit");
        roomLoader.addDirectory("DreDes/Rooms/SaltFlats");
        roomLoader.addDirectory("DreDes/Rooms/Temple");
    }

    public void loadStone(RoomLoader roomLoader)
    {
        roomLoader.addDirectory("DreDes/Rooms/Cave");
        roomLoader.addDirectory("DreDes/Rooms/Mountain");
        roomLoader.addDirectory("DreDes/Rooms/FrozenPond");
        roomLoader.addDirectory("DreDes/Rooms/Temple");
    }

    public Room pickRoom(Room[][] rooms, int row, int col)
    {
        Collection<Room> pool = roomLoader.getRooms().values();
        for(int i = -1; i < 2; i++)
        {
            for(int j = -1; j < 2; j++)
            {
                if(row + i > 0 && row + 1 < rooms.length && col + j > 0 && col + j < rooms[0].length && i != 0 && j != 0)
                {
                    if(rooms[row + i][col + j] != null)
                    {
                        if(!Arrays.asList(rooms[row + i][col + j].getRoomPool(Room.getDirection(new Vec2f(-j, -i)), true)).isEmpty())
                        {
                            pool.retainAll(Arrays.asList(rooms[row + i][col + j].getRoomPool(Room.getDirection(new Vec2f(-j, -i)), true)));
                        }

                    }
                }
            }
        }
        if(!pool.isEmpty())
        {
            return (Room)pool.toArray()[(int)(Math.random() * pool.size())];
        }
        pool = roomLoader.getRooms().values();
        for(int i = -1; i < 2; i++)
        {
            for(int j = -1; j < 2; j++)
            {
                if(row + i > 0 && row + 1 < rooms.length && col + j > 0 && col + j < rooms[0].length && i != 0 && j != 0)
                {
                    if(rooms[row + i][col + j] != null)
                    {
                        if(!Arrays.asList(rooms[row + i][col + j].getRoomPool(Room.getDirection(new Vec2f(-j, -i)), false)).isEmpty())
                        {
                            pool.retainAll(Arrays.asList(rooms[row + i][col + j].getRoomPool(Room.getDirection(new Vec2f(-j, -i)), false)));
                        }
                    }
                }
            }
        }
        if(!pool.isEmpty())
        {
            return (Room)pool.toArray()[(int)(Math.random() * pool.size())];
        }
        return roomLoader.getRoom("OpenAir");
//        HashSet<Room> pool = new HashSet<Room>();
//        try {
//            pool.addAll(Arrays.asList(rooms[row - 1][col - 1].getRoomPool(DOWN_RIGHT, true)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row][col - 1].getRoomPool(RIGHT, true)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row + 1][col - 1].getRoomPool(UP_RIGHT, true)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row - 1][col].getRoomPool(UP, true)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row + 1][col].getRoomPool(DOWN, true)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row - 1][col + 1].getRoomPool(DOWN_LEFT, true)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row][col + 1].getRoomPool(LEFT, true)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row][col + 1].getRoomPool(UP_LEFT, true)));
//        }catch (Exception e){};
//        if (!pool.isEmpty())
//        {
//            return (Room)pool.toArray()[(int)(Math.random() * pool.size())];
//        }
//        pool.clear();
//        try {
//            pool.addAll(Arrays.asList(rooms[row - 1][col - 1].getRoomPool(DOWN_RIGHT, false)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row][col - 1].getRoomPool(RIGHT, false)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row + 1][col - 1].getRoomPool(UP_RIGHT, false)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row - 1][col].getRoomPool(UP, false)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row + 1][col].getRoomPool(DOWN, false)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row - 1][col + 1].getRoomPool(DOWN_LEFT, false)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row][col + 1].getRoomPool(LEFT, false)));
//        }catch (Exception e){};
//        try {
//            pool.addAll(Arrays.asList(rooms[row][col + 1].getRoomPool(UP_LEFT, false)));
//        }catch (Exception e){};
//
//        return (Room)pool.toArray()[(int)(Math.random() * pool.size())];
    }
    public void tryAdd(String center, Room.Direction direction, String neighbor) {
        Room c = roomLoader.getRoom(center);
        Room n = roomLoader.getRoom(neighbor);

        if(c == null) {
            Constants.LOGGER.logSevere("Room '" + center + "' was not found!");
        }

        if(n == null) {
            Constants.LOGGER.logSevere("Room '" + neighbor + "' was not found!");
        }

        if(c != null && n != null) {
            c.addNeighbor(direction, n);
        }
    }

    public void addAllNeighbors() {
        //tryAdd(room, direction, neighbor) for all the rooms and their possible neighbors
        //region Universal Tiles
        tryAdd("OpenAir", UP_LEFT, "OpenAir");
        tryAdd("OpenAir", UP, "OpenAir");
        tryAdd("OpenAir", UP_RIGHT, "OpenAir");
        tryAdd("OpenAir", RIGHT, "OpenAir");
        tryAdd("OpenAir", DOWN_RIGHT, "OpenAir");
        tryAdd("OpenAir", DOWN, "OpenAir");
        tryAdd("OpenAir", DOWN_LEFT, "OpenAir");
        tryAdd("OpenAir", LEFT, "OpenAir");


        tryAdd("SolidGround", UP_LEFT, "SolidGround");
        tryAdd("SolidGround", UP_LEFT, "OpenAir");
        tryAdd("SolidGround", UP, "SolidGround");
        tryAdd("SolidGround", UP, "OpenAir");
        tryAdd("SolidGround", UP_RIGHT, "SolidGround");
        tryAdd("SolidGround", UP_RIGHT, "OpenAir");
        tryAdd("SolidGround", RIGHT, "SolidGround");
        tryAdd("SolidGround", DOWN_RIGHT, "SolidGround");
        tryAdd("SolidGround", DOWN, "SolidGround");
        tryAdd("SolidGround", DOWN_RIGHT, "SolidGround");
        tryAdd("SolidGround", LEFT, "SolidGround");


        tryAdd("OpenWater", UP_LEFT, "OpenWater");
        tryAdd("OpenWater", UP, "OpenWater");
        tryAdd("OpenWater", UP_RIGHT, "OpenWater");
        tryAdd("OpenWater", RIGHT, "OpenWater");
        tryAdd("OpenWater", DOWN_RIGHT, "OpenWater");
        tryAdd("OpenWater", DOWN, "OpenWater");
        tryAdd("OpenWater", DOWN_LEFT, "OpenWater");
        tryAdd("OpenWater", LEFT, "OpenWater");
        //endregion

        //region Hills
        tryAdd("SmallHill", UP_LEFT, "OpenAir");
        tryAdd("SmallHill", UP, "OpenAir");
        tryAdd("SmallHill", UP_RIGHT, "OpenAir");
        tryAdd("SmallHill", UP_RIGHT, "Hilltop");

        tryAdd("SmallHill", RIGHT, "SmallHill");
        tryAdd("SmallHill", RIGHT, "BigHill");
        tryAdd("SmallHill", RIGHT, "Drop");
        tryAdd("SmallHill", RIGHT, "CliffRoof");
        tryAdd("SmallHill", RIGHT, "SmallPit");
        tryAdd("SmallHill", RIGHT, "PitStart");
        tryAdd("SmallHill", RIGHT, "SmallPond");
        tryAdd("SmallHill", RIGHT, "PondStart");
        tryAdd("SmallHill", RIGHT, "SolidGround");

        tryAdd("SmallHill", DOWN_RIGHT, "SolidGround");
        tryAdd("SmallHill", DOWN, "SolidGround");
        tryAdd("SmallHill", DOWN_LEFT, "SolidGround");

        tryAdd("SmallHill", LEFT, "SmallHill");
        tryAdd("SmallHill", LEFT, "BigHill");
        tryAdd("SmallHill", LEFT, "Hilltop");
        tryAdd("SmallHill", LEFT, "UndergroundEntrance");
        tryAdd("SmallHill", LEFT, "SmallPond");
        tryAdd("SmallHill", LEFT, "PondStart");
        tryAdd("SmallHill", LEFT, "TempleEntrance");


        tryAdd("BigHill", UP_LEFT, "OpenAir");
        tryAdd("BigHill", UP, "OpenAir");

        tryAdd("BigHill", UP_RIGHT, "OpenAir");
        tryAdd("BigHill", UP_RIGHT, "SmallHill");
        tryAdd("BigHill", UP_RIGHT, "BigHill");
        tryAdd("BigHill", UP_RIGHT, "Hilltop");
        tryAdd("BigHill", UP_RIGHT, "UndergroundEntrance");

        tryAdd("BigHill", RIGHT, "SmallHill");
        tryAdd("BigHill", RIGHT, "BigHill");
        tryAdd("BigHill", RIGHT, "Drop");
        tryAdd("BigHill", RIGHT, "CliffRoof");
        tryAdd("BigHill", RIGHT, "SmallPit");
        tryAdd("BigHill", RIGHT, "PitStart");
        tryAdd("BigHill", RIGHT, "SolidGround");

        tryAdd("BigHill", DOWN_RIGHT, "SolidGround");
        tryAdd("BigHill", DOWN, "SolidGround");
        tryAdd("BigHill", DOWN_LEFT, "SolidGround");

        tryAdd("BigHill", LEFT, "BigHill");
        tryAdd("BigHill", LEFT, "SmallHill");
        tryAdd("BigHill", LEFT, "Hilltop");
        tryAdd("BigHill", LEFT, "UndergroundEntrance");
        tryAdd("BigHill", LEFT, "SmallPond");
        tryAdd("BigHill", LEFT, "PondStart");
        tryAdd("BigHill", LEFT, "TempleEntrance");


        tryAdd("Hilltop", UP_LEFT, "OpenAir");
        tryAdd("Hilltop", UP, "OpenAir");
        tryAdd("Hilltop", UP_RIGHT, "OpenAir");

        tryAdd("Hilltop", RIGHT, "SmallHill");
        tryAdd("Hilltop", RIGHT, "BigHill");
        tryAdd("Hilltop", RIGHT, "Drop");
        tryAdd("Hilltop", RIGHT, "CliffRoof");
        tryAdd("Hilltop", RIGHT, "UndergroundEntrance");
        tryAdd("Hilltop", RIGHT, "SmallPit");
        tryAdd("Hilltop", RIGHT, "PitStart");
        tryAdd("Hilltop", RIGHT, "SmallPond");
        tryAdd("Hilltop", RIGHT, "PondStart");
        tryAdd("Hilltop", RIGHT, "TempleEntrance");

        tryAdd("Hilltop", DOWN_RIGHT, "BigHill");
        tryAdd("Hilltop", DOWN_RIGHT, "SolidGround");
        tryAdd("Hilltop", DOWN, "CliffWall");
        tryAdd("Hilltop", DOWN, "SolidGround");
        tryAdd("Hilltop", DOWN_LEFT, "SolidGround");

        tryAdd("Hilltop", LEFT, "OpenAir");
        tryAdd("Hilltop", LEFT, "SmallHill");
        tryAdd("Hilltop", LEFT, "BigHill");
        tryAdd("Hilltop", LEFT, "UndergroundEntrance");
        tryAdd("Hilltop", LEFT, "TempleEntrance");
        //endregion

        //region Cliffs
        tryAdd("Drop", UP_LEFT, "OpenAir");
        tryAdd("Drop", UP, "OpenAir");
        tryAdd("Drop", UP_RIGHT, "OpenAir");

        tryAdd("Drop", RIGHT, "SmallHill");
        tryAdd("Drop", RIGHT, "UndergroundEntrance");
        tryAdd("Drop", RIGHT, "PitMiddle");
        tryAdd("Drop", RIGHT, "RockColumns");
        tryAdd("Drop", RIGHT, "TempleEntrance");

        tryAdd("Drop", DOWN_RIGHT, "SolidGround");
        tryAdd("Drop", DOWN, "CliffWall");
        tryAdd("Drop", DOWN, "SolidGround");
        tryAdd("Drop", DOWN_LEFT, "SolidGround");

        tryAdd("Drop", LEFT, "SmallHill");
        tryAdd("Drop", LEFT, "CliffRoof");
        tryAdd("Drop", LEFT, "UndergroundEntrance");
        tryAdd("Drop", LEFT, "SmallPit");
        tryAdd("Drop", LEFT, "PitStart");
        tryAdd("Drop", LEFT, "SmallPond");
        tryAdd("Drop", LEFT, "PondStart");
        tryAdd("Drop", LEFT, "Pool");
        tryAdd("Drop", LEFT, "Pool");
        tryAdd("Drop", LEFT, "RockColumns");
        tryAdd("Drop", LEFT, "TempleEntrance");


        tryAdd("CliffWall", UP_LEFT, "OpenAir");
        tryAdd("CliffWall", UP_LEFT, "PitStart");
        tryAdd("CliffWall", UP, "Hilltop");
        tryAdd("CliffWall", UP, "Drop");
        tryAdd("CliffWall", UP, "PitStart");
        tryAdd("CliffWall", UP, "TempleEntrance");
        tryAdd("CliffWall", UP_RIGHT, "OpenAir");
        tryAdd("CliffWall", RIGHT, "SolidGround");
        tryAdd("CliffWall", DOWN_RIGHT, "SolidGround");
        tryAdd("CliffWall", DOWN_RIGHT, "CliffWall");
        tryAdd("CliffWall", DOWN, "OpenAir");
        tryAdd("CliffWall", DOWN_LEFT, "SolidGround");
        tryAdd("CliffWall", LEFT, "CliffWall");
        tryAdd("CliffWall", LEFT, "PitMiddle");


        tryAdd("CliffRoof", UP_LEFT, "OpenAir");
        tryAdd("CliffRoof", UP, "OpenAir");
        tryAdd("CliffRoof", UP_RIGHT, "OpenAir");

        tryAdd("CliffRoof", RIGHT, "SmallHill");
        tryAdd("CliffRoof", RIGHT, "BigHill");
        tryAdd("CliffRoof", RIGHT, "CliffRoof");
        tryAdd("CliffRoof", RIGHT, "Drop");
        tryAdd("CliffRoof", RIGHT, "SmallPit");
        tryAdd("CliffRoof", RIGHT, "PitStart");
        tryAdd("CliffRoof", RIGHT, "SmallPond");
        tryAdd("CliffRoof", RIGHT, "PondStart");
        tryAdd("CliffRoof", RIGHT, "Pool");
        tryAdd("CliffRoof", RIGHT, "Pool");
        tryAdd("CliffRoof", RIGHT, "RockColumns");

        tryAdd("CliffRoof", DOWN_RIGHT, "SolidGround");
        tryAdd("CliffRoof", DOWN, "SolidGround");
        tryAdd("CliffRoof", DOWN_LEFT, "SolidGround");

        tryAdd("CliffRoof", LEFT, "SmallHill");
        tryAdd("CliffRoof", LEFT, "BigHill");
        tryAdd("CliffRoof", LEFT, "Drop");
        tryAdd("CliffRoof", LEFT, "CliffRoof");
        tryAdd("CliffRoof", LEFT, "UndergroundEntrance");
        tryAdd("CliffRoof", LEFT, "SmallPit");
        tryAdd("CliffRoof", LEFT, "PitStart");
        tryAdd("CliffRoof", LEFT, "SmallPond");
        tryAdd("CliffRoof", LEFT, "PondStart");
        tryAdd("CliffRoof", LEFT, "Flat");
        tryAdd("CliffRoof", LEFT, "Pool");
        tryAdd("CliffRoof", LEFT, "RockColumns");
        tryAdd("CliffRoof", LEFT, "TempleEntrance");

        //endregion

        //region Caves
        tryAdd("UndergroundEntrance", UP_LEFT, "OpenAir");
        tryAdd("UndergroundEntrance", UP_LEFT, "CaveWall");
        tryAdd("UndergroundEntrance", UP, "CaveWall");
        tryAdd("UndergroundEntrance", UP, "MountainSideB");
        tryAdd("UndergroundEntrance", UP_RIGHT, "OpenAir");
        tryAdd("UndergroundEntrance", UP_RIGHT, "CaveWall");
        tryAdd("UndergroundEntrance", UP_RIGHT, "SolidGround");

        tryAdd("UndergroundEntrance", RIGHT, "Hollow");
        tryAdd("UndergroundEntrance", RIGHT, "CaveHallway");
        tryAdd("UndergroundEntrance", RIGHT, "Platform");
        tryAdd("UndergroundEntrance", RIGHT, "UndergroundPondStart");

        tryAdd("UndergroundEntrance", DOWN_RIGHT, "SolidGround");
        tryAdd("UndergroundEntrance", DOWN_RIGHT, "Platform");
        tryAdd("UndergroundEntrance", DOWN, "SolidGround");
        tryAdd("UndergroundEntrance", DOWN, "CliffWall");
        tryAdd("UndergroundEntrance", DOWN_LEFT, "MountainSideC");
        tryAdd("UndergroundEntrance", DOWN_LEFT, "Staircase");

        tryAdd("UndergroundEntrance", LEFT, "SmallHill");
        tryAdd("UndergroundEntrance", LEFT, "BigHill");
        tryAdd("UndergroundEntrance", LEFT, "Hilltop");
        tryAdd("UndergroundEntrance", LEFT, "Drop");
        tryAdd("UndergroundEntrance", LEFT, "UndergroundEntrance");
        tryAdd("UndergroundEntrance", LEFT, "PitMiddle");
        tryAdd("UndergroundEntrance", LEFT, "MountainSideA");
        tryAdd("UndergroundEntrance", LEFT, "MountainTop");
        tryAdd("UndergroundEntrance", LEFT, "SmallPond");
        tryAdd("UndergroundEntrance", LEFT, "PondStart");
        tryAdd("UndergroundEntrance", LEFT, "FrozenPondStart");
        tryAdd("UndergroundEntrance", LEFT, "Pool");
        tryAdd("UndergroundEntrance", LEFT, "Pool");
        tryAdd("UndergroundEntrance", LEFT, "RockColumns");
        tryAdd("UndergroundEntrance", LEFT, "TempleEntrance");


        tryAdd("Hollow", UP_LEFT, "CaveWall");
        tryAdd("Hollow", UP_LEFT, "OpenAir");
        tryAdd("Hollow", UP_LEFT, "SolidGround");
        tryAdd("Hollow", UP, "CliffRoof");
        tryAdd("Hollow", UP, "OpenAir");
        tryAdd("Hollow", UP, "CaveWall");
        tryAdd("Hollow", UP, "MountainTop");
        tryAdd("Hollow", UP, "SolidGround");
        tryAdd("Hollow", UP_RIGHT, "CaveWall");
        tryAdd("Hollow", UP_RIGHT, "OpenAir");
        tryAdd("Hollow", UP_RIGHT, "SolidGround");

        tryAdd("Hollow", RIGHT, "UndergroundEntrance");
        tryAdd("Hollow", RIGHT, "Hollow");
        tryAdd("Hollow", RIGHT, "CaveHallway");
        tryAdd("Hollow", RIGHT, "Platform");
        tryAdd("Hollow", RIGHT, "SmallUndergroundPond");
        tryAdd("Hollow", RIGHT, "UndergroundPondStart");

        tryAdd("Hollow", DOWN_RIGHT, "SolidGround");
        tryAdd("Hollow", DOWN_RIGHT, "Platform");
        tryAdd("Hollow", DOWN, "SolidGround");
        tryAdd("Hollow", DOWN_LEFT, "SolidGround");
        tryAdd("Hollow", DOWN_LEFT, "Platform");

        tryAdd("Hollow", LEFT, "UndergroundEntrance");
        tryAdd("Hollow", LEFT, "Hollow");
        tryAdd("Hollow", LEFT, "CaveHallway");
        tryAdd("Hollow", LEFT, "Platform");
        tryAdd("Hollow", LEFT, "SmallUndergroundPond");
        tryAdd("Hollow", LEFT, "UndergroundPondStart");


        tryAdd("CaveWall", UP_LEFT, "OpenAir");
        tryAdd("CaveWall", UP, "CaveWall");
        tryAdd("CaveWall", UP, "OpenAir");
        tryAdd("CaveWall", UP, "SolidGround");
        tryAdd("CaveWall", UP_RIGHT, "OpenAir");
        tryAdd("CaveWall", RIGHT, "OpenAir");
        tryAdd("CaveWall", DOWN_RIGHT, "OpenAir");
        tryAdd("CaveWall", DOWN_RIGHT, "SolidGround");
        tryAdd("CaveWall", DOWN, "UndergroundEntrance");
        tryAdd("CaveWall", DOWN, "OpenAir");
        tryAdd("CaveWall", DOWN_LEFT, "OpenAir");
        tryAdd("CaveWall", DOWN_LEFT, "SolidGround");
        tryAdd("CaveWall", LEFT, "OpenAir");


        tryAdd("CaveHallway", UP_LEFT, "OpenAir");
        tryAdd("CaveHallway", UP_LEFT, "CaveWall");
        tryAdd("CaveHallway", UP_LEFT, "SolidGround");
        tryAdd("CaveHallway", UP, "CliffRoof");
        tryAdd("CaveHallway", UP, "CaveWall");
        tryAdd("CaveHallway", UP, "MountainTop");
        tryAdd("CaveHallway", UP, "SolidGround");
        tryAdd("CaveHallway", UP_RIGHT, "OpenAir");
        tryAdd("CaveHallway", UP_RIGHT, "CaveWall");
        tryAdd("CaveHallway", UP_RIGHT, "SolidGround");

        tryAdd("CaveHallway", RIGHT, "UndergroundEntrance");
        tryAdd("CaveHallway", RIGHT, "Hollow");
        tryAdd("CaveHallway", RIGHT, "CaveHallway");
        tryAdd("CaveHallway", RIGHT, "Platform");
        tryAdd("CaveHallway", RIGHT, "UndergroundPondStart");

        tryAdd("CaveHallway", DOWN_RIGHT, "SolidGround");
        tryAdd("CaveHallway", DOWN_RIGHT, "Platform");
        tryAdd("CaveHallway", DOWN, "SolidGround");
        tryAdd("CaveHallway", DOWN_LEFT, "SolidGround");

        tryAdd("CaveHallway", LEFT, "UndergroundEntrance");
        tryAdd("CaveHallway", LEFT, "Hollow");
        tryAdd("CaveHallway", LEFT, "CaveHallway");
        tryAdd("CaveHallway", LEFT, "Platform");
        tryAdd("CaveHallway", LEFT, "UndergroundPondStart");


        tryAdd("Platform", UP_LEFT, "OpenAir");
        tryAdd("Platform", UP_LEFT, "CaveWall");
        tryAdd("Platform", UP_LEFT, "SolidGround");
        tryAdd("Platform", UP, "OpenAir");

        tryAdd("Platform", UP_RIGHT, "UndergroundEntrance");
        tryAdd("Platform", UP_RIGHT, "Hollow");
        tryAdd("Platform", UP_RIGHT, "CaveHallway");
        tryAdd("Platform", UP_RIGHT, "Platform");
        tryAdd("Platform", UP_RIGHT, "OpenAir");
        tryAdd("Platform", UP_RIGHT, "CaveWall");
        tryAdd("Platform", UP_RIGHT, "SolidGround");

        tryAdd("Platform", RIGHT, "UndergroundEntrance");
        tryAdd("Platform", RIGHT, "Hollow");
        tryAdd("Platform", RIGHT, "CaveHallway");
        tryAdd("Platform", RIGHT, "Platform");
        tryAdd("Platform", RIGHT, "SmallUndergroundPond");
        tryAdd("Platform", RIGHT, "UndergroundPondStart");

        tryAdd("Platform", DOWN_RIGHT, "SolidGround");
        tryAdd("Platform", DOWN_RIGHT, "Platform");
        tryAdd("Platform", DOWN, "SolidGround");
        tryAdd("Platform", DOWN_LEFT, "SolidGround");

        tryAdd("Platform", LEFT, "UndergroundEntrance");
        tryAdd("Platform", LEFT, "Hollow");
        tryAdd("Platform", LEFT, "CaveHallway");
        tryAdd("Platform", LEFT, "Platform");
        tryAdd("Platform", LEFT, "SmallUndergroundPond");
        tryAdd("Platform", LEFT, "UndergroundPondStart");


        tryAdd("SmallUndergroundPond", UP_LEFT, "OpenAir");
        tryAdd("SmallUndergroundPond", UP_LEFT, "CaveWall");
        tryAdd("SmallUndergroundPond", UP_LEFT, "SolidGround");
        tryAdd("SmallUndergroundPond", UP, "OpenAir");

        tryAdd("SmallUndergroundPond", UP_RIGHT, "UndergroundEntrance");
        tryAdd("SmallUndergroundPond", UP_RIGHT, "Hollow");
        tryAdd("SmallUndergroundPond", UP_RIGHT, "CaveHallway");
        tryAdd("SmallUndergroundPond", UP_RIGHT, "Platform");
        tryAdd("SmallUndergroundPond", UP_RIGHT, "OpenAir");

        tryAdd("SmallUndergroundPond", RIGHT, "Platform");
        tryAdd("SmallUndergroundPond", RIGHT, "SmallUndergroundPond");
        tryAdd("SmallUndergroundPond", RIGHT, "UndergroundPondStart");
        tryAdd("SmallUndergroundPond", RIGHT, "UndergroundPondMiddle");

        tryAdd("SmallUndergroundPond", DOWN_RIGHT, "SolidGround");
        tryAdd("SmallUndergroundPond", DOWN, "SolidGround");
        tryAdd("SmallUndergroundPond", DOWN_LEFT, "SolidGround");

        tryAdd("SmallUndergroundPond", LEFT, "UndergroundEntrance");
        tryAdd("SmallUndergroundPond", LEFT, "Platform");
        tryAdd("SmallUndergroundPond", LEFT, "SmallUndergroundPond");
        tryAdd("SmallUndergroundPond", LEFT, "UndergroundPondStart");
        tryAdd("SmallUndergroundPond", LEFT, "UndergroundPondMiddle");


        tryAdd("UndergroundPondStart", UP_LEFT, "OpenAir");
        tryAdd("UndergroundPondStart", UP_LEFT, "CaveWall");
        tryAdd("UndergroundPondStart", UP_LEFT, "SolidGround");
        tryAdd("UndergroundPondStart", UP, "OpenAir");
        tryAdd("UndergroundPondStart", UP, "CaveWall");
        tryAdd("UndergroundPondStart", UP, "SolidGround");
        tryAdd("UndergroundPondStart", UP_RIGHT, "OpenAir");
        tryAdd("UndergroundPondStart", UP_RIGHT, "CaveWall");
        tryAdd("UndergroundPondStart", UP_RIGHT, "SolidGround");
        tryAdd("UndergroundPondStart", RIGHT, "SmallUndergroundPond");
        tryAdd("UndergroundPondStart", RIGHT, "UndergroundPondStart");
        tryAdd("UndergroundPondStart", RIGHT, "UndergroundPondMiddle");
        tryAdd("UndergroundPondStart", DOWN_RIGHT, "SolidGround");
        tryAdd("UndergroundPondStart", DOWN, "SolidGround");
        tryAdd("UndergroundPondStart", DOWN_LEFT, "SolidGround");

        tryAdd("UndergroundPondStart", LEFT, "UndergroundEntrance");
        tryAdd("UndergroundPondStart", LEFT, "Hollow");
        tryAdd("UndergroundPondStart", LEFT, "CaveHallway");
        tryAdd("UndergroundPondStart", LEFT, "Platform");
        tryAdd("UndergroundPondStart", LEFT, "SmallUndergroundPond");
        tryAdd("UndergroundPondStart", LEFT, "UndergroundPondStart");
        tryAdd("UndergroundPondStart", LEFT, "UndergroundPondMiddle");


        tryAdd("UndergroundPondMiddle", UP_LEFT, "OpenAir");
        tryAdd("UndergroundPondMiddle", UP_LEFT, "CaveWall");
        tryAdd("UndergroundPondMiddle", UP_LEFT, "SolidGround");
        tryAdd("UndergroundPondMiddle", UP, "OpenAir");
        tryAdd("UndergroundPondMiddle", UP, "CaveWall");
        tryAdd("UndergroundPondMiddle", UP, "SolidGround");
        tryAdd("UndergroundPondMiddle", UP_RIGHT, "OpenAir");
        tryAdd("UndergroundPondMiddle", UP_RIGHT, "CaveWall");
        tryAdd("UndergroundPondMiddle", UP_RIGHT, "SolidGround");
        tryAdd("UndergroundPondMiddle", RIGHT, "SmallUndergroundPond");
        tryAdd("UndergroundPondMiddle", RIGHT, "UndergroundPondStart");
        tryAdd("UndergroundPondMiddle", RIGHT, "UndergroundPondMiddle");
        tryAdd("UndergroundPondMiddle", DOWN_RIGHT, "SolidGround");
        tryAdd("UndergroundPondMiddle", DOWN, "SolidGround");
        tryAdd("UndergroundPondMiddle", DOWN_LEFT, "SolidGround");
        tryAdd("UndergroundPondMiddle", LEFT, "SmallUndergroundPond");
        tryAdd("UndergroundPondMiddle", LEFT, "UndergroundPondStart");
        tryAdd("UndergroundPondMiddle", LEFT, "UndergroundPondMiddle");
        //endregion

        //region Pits
        tryAdd("SmallPit", UP_LEFT, "OpenAir");
        tryAdd("SmallPit", UP, "OpenAir");
        tryAdd("SmallPit", UP_RIGHT, "OpenAir");

        tryAdd("SmallPit", RIGHT, "SmallHill");
        tryAdd("SmallPit", RIGHT, "BigHill");
        tryAdd("SmallPit", RIGHT, "Hilltop");
        tryAdd("SmallPit", RIGHT, "Drop");
        tryAdd("SmallPit", RIGHT, "CliffRoof");
        tryAdd("SmallPit", RIGHT, "SmallPit");
        tryAdd("SmallPit", RIGHT, "PitMiddle");
        tryAdd("SmallPit", RIGHT, "Pool");
        tryAdd("SmallPit", RIGHT, "Pool");
        tryAdd("SmallPit", RIGHT, "RockColumns");

        tryAdd("SmallPit", DOWN_RIGHT, "SolidGround");
        tryAdd("SmallPit", DOWN_RIGHT, "CliffWall");
        tryAdd("SmallPit", DOWN, "OpenAir");
        tryAdd("SmallPit", DOWN_LEFT, "SolidGround");
        tryAdd("SmallPit", DOWN_LEFT, "CliffWall");

        tryAdd("SmallPit", LEFT, "SmallHill");
        tryAdd("SmallPit", LEFT, "BigHill");
        tryAdd("SmallPit", LEFT, "Drop");
        tryAdd("SmallPit", LEFT, "CliffRoof");
        tryAdd("SmallPit", LEFT, "SmallPit");
        tryAdd("SmallPit", LEFT, "PitStart");
        tryAdd("SmallPit", LEFT, "PitMiddle");
        tryAdd("SmallPit", LEFT, "RockColumns");


        tryAdd("PitStart", UP_LEFT, "OpenAir");
        tryAdd("PitStart", UP, "OpenAir");
        tryAdd("PitStart", UP_RIGHT, "OpenAir");
        tryAdd("PitStart", RIGHT, "SmallHill");
        tryAdd("PitStart", RIGHT, "BigHill");
        tryAdd("PitStart", RIGHT, "PitStart");
        tryAdd("PitStart", RIGHT, "PitMiddle");
        tryAdd("PitStart", DOWN_RIGHT, "OpenAir");
        tryAdd("PitStart", DOWN, "CliffWall");
        tryAdd("PitStart", DOWN, "OpenAir");

        tryAdd("PitStart", DOWN_RIGHT, "SolidGround");
        tryAdd("PitStart", DOWN_RIGHT, "CliffWall");

        tryAdd("PitStart", LEFT, "SmallHill");
        tryAdd("PitStart", LEFT, "BigHill");
        tryAdd("PitStart", LEFT, "Hilltop");
        tryAdd("PitStart", LEFT, "Drop");
        tryAdd("PitStart", LEFT, "CliffRoof");
        tryAdd("PitStart", LEFT, "SmallPit");
        tryAdd("PitStart", LEFT, "PitStart");
        tryAdd("PitStart", LEFT, "PitMiddle");
        tryAdd("PitStart", LEFT, "RockColumns");


        tryAdd("PitMiddle", UP_LEFT, "OpenAir");
        tryAdd("PitMiddle", UP, "OpenAir");
        tryAdd("PitMiddle", UP_RIGHT, "OpenAir");
        tryAdd("PitMiddle", RIGHT, "PitStart");
        tryAdd("PitMiddle", RIGHT, "PitMiddle");
        tryAdd("PitMiddle", RIGHT, "Drop");
        tryAdd("PitMiddle", DOWN_RIGHT, "OpenAir");
        tryAdd("PitMiddle", DOWN, "OpenAir");
        tryAdd("PitMiddle", DOWN_LEFT, "OpenAir");

        tryAdd("PitMiddle", LEFT, "Drop");
        tryAdd("PitMiddle", LEFT, "PitStart");
        tryAdd("PitMiddle", LEFT, "PitMiddle");
        //endregion

        //region Mountains
        tryAdd("MountainSideA", UP_LEFT, "OpenAir");
        tryAdd("MountainSideA", UP, "MountainSideB");
        tryAdd("MountainSideA", UP_RIGHT, "SolidGround");
        tryAdd("MountainSideA", RIGHT, "SolidGround");
        tryAdd("MountainSideA", DOWN_RIGHT, "SolidGround");
        tryAdd("MountainSideA", DOWN, "SolidGround");
        tryAdd("MountainSideA", DOWN_LEFT, "SolidGround");

        tryAdd("MountainSideA", LEFT, "UndergroundEntrance");
        tryAdd("MountainSideA", LEFT, "FrozenPondStart");
        tryAdd("MountainSideA", LEFT, "MountainSideC");
        tryAdd("MountainSideA", LEFT, "MountainTop");
        tryAdd("MountainSideA", LEFT, "TempleEntrance");


        tryAdd("MountainSideB", UP_LEFT, "OpenAir");
        tryAdd("MountainSideB", UP, "MountainSideC");
        tryAdd("MountainSideB", UP_RIGHT, "SolidGround");
        tryAdd("MountainSideB", RIGHT, "SolidGround");
        tryAdd("MountainSideB", DOWN_RIGHT, "SolidGround");
        tryAdd("MountainSideB", DOWN, "MountainSideA");
        tryAdd("MountainSideB", DOWN_LEFT, "SolidGround");
        tryAdd("MountainSideB", LEFT, "OpenAir");


        tryAdd("MountainSideC", UP_LEFT, "OpenAir");
        tryAdd("MountainSideC", UP, "OpenAir");
        tryAdd("MountainSideC", UP_RIGHT, "OpenAir");
        tryAdd("MountainSideC", RIGHT, "FrozenPondStart");
        tryAdd("MountainSideC", RIGHT, "MountainTop");
        tryAdd("MountainSideC", RIGHT, "MountainSideA");
        tryAdd("MountainSideC", DOWN_RIGHT, "SolidGround");
        tryAdd("MountainSideC", DOWN, "MountainSideB");
        tryAdd("MountainSideC", DOWN_LEFT, "OpenAir");
        tryAdd("MountainSideC", LEFT, "OpenAir");


        tryAdd("MountainTop", UP_LEFT, "OpenAir");
        tryAdd("MountainTop", UP, "OpenAir");
        tryAdd("MountainTop", UP_RIGHT, "OpenAir");
        tryAdd("MountainTop", RIGHT, "FrozenPondStart");
        tryAdd("MountainTop", RIGHT, "MountainTop");
        tryAdd("MountainTop", RIGHT, "MountainSideA");
        tryAdd("MountainTop", RIGHT, "TempleEntrance");
        tryAdd("MountainTop", DOWN_RIGHT, "SolidGround");
        tryAdd("MountainTop", DOWN, "SolidGround");
        tryAdd("MountainTop", DOWN_LEFT, "OpenAir");
        tryAdd("MountainTop", DOWN_LEFT, "SolidGround");
        tryAdd("MountainTop", LEFT, "MountainTop");
        tryAdd("MountainTop", LEFT, "MountainSideA");
        tryAdd("MountainTop", LEFT, "TempleEntrance");
        //endregion

        //region Ponds
        tryAdd("SmallPond", UP_LEFT, "OpenAir");
        tryAdd("SmallPond", UP, "OpenAir");
        tryAdd("SmallPond", UP_RIGHT, "OpenAir");

        tryAdd("SmallPond", RIGHT, "SmallHill");
        tryAdd("SmallPond", RIGHT, "BigHill");
        tryAdd("SmallPond", RIGHT, "Hilltop");
        tryAdd("SmallPond", RIGHT, "Drop");
        tryAdd("SmallPond", RIGHT, "CliffRoof");
        tryAdd("SmallPond", RIGHT, "UndergroundEntrance");
        tryAdd("SmallPond", RIGHT, "SmallPond");

        tryAdd("SmallPond", DOWN_RIGHT, "SolidGround");
        tryAdd("SmallPond", DOWN, "SolidGround");
        tryAdd("SmallPond", DOWN_LEFT, "SolidGround");

        tryAdd("SmallPond", LEFT, "SmallHill");
        tryAdd("SmallPond", LEFT, "BigHill");
        tryAdd("SmallPond", LEFT, "Hilltop");
        tryAdd("SmallPond", LEFT, "Drop");
        tryAdd("SmallPond", LEFT, "CliffRoof");
        tryAdd("SmallPond", LEFT, "UndergroundEntrance");
        tryAdd("SmallPond", LEFT, "SmallPond");
        tryAdd("SmallPond", LEFT, "PondStart");


        tryAdd("PondStart", UP_LEFT, "OpenAir");
        tryAdd("PondStart", UP, "OpenAir");
        tryAdd("PondStart", UP_RIGHT, "OpenAir");
        tryAdd("PondStart", RIGHT, "SmallPond");
        tryAdd("PondStart", RIGHT, "PondStart");
        tryAdd("PondStart", RIGHT, "PondMiddle");
        tryAdd("PondStart", DOWN_RIGHT, "SolidGround");
        tryAdd("PondStart", DOWN, "SolidGround");
        tryAdd("PondStart", DOWN_LEFT, "SolidGround");

        tryAdd("PondStart", LEFT, "SmallHill");
        tryAdd("PondStart", LEFT, "BigHill");
        tryAdd("PondStart", LEFT, "Hilltop");
        tryAdd("PondStart", LEFT, "Drop");
        tryAdd("PondStart", LEFT, "CliffRoof");
        tryAdd("PondStart", LEFT, "UndergroundEntrance");
        tryAdd("PondStart", LEFT, "SmallPond");
        tryAdd("PondStart", LEFT, "PondStart");


        tryAdd("PondMiddle", UP_LEFT, "OpenAir");
        tryAdd("PondMiddle", UP, "OpenAir");
        tryAdd("PondMiddle", UP_RIGHT, "OpenAir");
        tryAdd("PondMiddle", RIGHT, "PondStart");
        tryAdd("PondMiddle", RIGHT, "PondMiddle");
        tryAdd("PondMiddle", DOWN_RIGHT, "SolidGround");
        tryAdd("PondMiddle", DOWN, "DeepPond");
        tryAdd("PondMiddle", DOWN, "SolidGround");
        tryAdd("PondMiddle", DOWN_LEFT, "SolidGround");
        tryAdd("PondMiddle", LEFT, "PondStart");
        tryAdd("PondMiddle", LEFT, "PondMiddle");


        tryAdd("DeepPond", UP_LEFT, "PondMiddle");
        tryAdd("DeepPond", UP, "PondMiddle");
        tryAdd("DeepPond", UP_RIGHT, "PondMiddle");
        tryAdd("DeepPond", RIGHT, "SolidGround");
        tryAdd("DeepPond", DOWN_RIGHT, "SolidGround");
        tryAdd("DeepPond", DOWN, "SolidGround");
        tryAdd("DeepPond", DOWN_LEFT, "SolidGround");
        tryAdd("DeepPond", LEFT, "SolidGround");

        //endregion

        //region Frozen Ponds
        tryAdd("FrozenPondStart", UP_LEFT, "OpenAir");
        tryAdd("FrozenPondStart", UP, "OpenAir");
        tryAdd("FrozenPondStart", UP_RIGHT, "OpenAir");
        tryAdd("FrozenPondStart", RIGHT, "FrozenPondStart");
        tryAdd("FrozenPondStart", RIGHT, "FrozenPondMiddle");
        tryAdd("FrozenPondStart", DOWN_RIGHT, "SolidGround");
        tryAdd("FrozenPondStart", DOWN, "SolidGround");
        tryAdd("FrozenPondStart", DOWN_LEFT, "SolidGround");
        tryAdd("FrozenPondStart", LEFT, "MountainSideA");
        tryAdd("FrozenPondStart", LEFT, "MountainSideC");
        tryAdd("FrozenPondStart", LEFT, "MountainTop");


        tryAdd("FrozenPondMiddle", UP_LEFT, "OpenAir");
        tryAdd("FrozenPondMiddle", UP, "OpenAir");
        tryAdd("FrozenPondMiddle", UP_RIGHT, "OpenAir");
        tryAdd("FrozenPondMiddle", RIGHT, "FrozenPondStart");
        tryAdd("FrozenPondMiddle", RIGHT, "FrozenPondMiddle");
        tryAdd("FrozenPondMiddle", DOWN_RIGHT, "SolidGround");
        tryAdd("FrozenPondMiddle", DOWN, "SolidGround");
        tryAdd("FrozenPondMiddle", DOWN_LEFT, "SolidGround");
        tryAdd("FrozenPondMiddle", LEFT, "FrozenPondStart");
        tryAdd("FrozenPondMiddle", LEFT, "FrozenPondMiddle");

        //endregion

        //region Rivers
        tryAdd("OpenRiver", UP_LEFT, "OpenAir");
        tryAdd("OpenRiver", UP, "OpenAir");
        tryAdd("OpenRiver", UP_RIGHT, "OpenAir");
        tryAdd("OpenRiver", RIGHT, "OpenRiver");
        tryAdd("OpenRiver", RIGHT, "RiverStart");
        tryAdd("OpenRiver", DOWN_RIGHT, "RiverBase");
        tryAdd("OpenRiver", DOWN_RIGHT, "RiverWall");
        tryAdd("OpenRiver", DOWN, "RiverBase");
        tryAdd("OpenRiver", DOWN_LEFT, "RiverBase");
        tryAdd("OpenRiver", DOWN_LEFT, "RiverWall");
        tryAdd("OpenRiver", LEFT, "OpenRiver");
        tryAdd("OpenRiver", LEFT, "RiverStart");


        tryAdd("RiverStart", UP_LEFT, "OpenAir");
        tryAdd("OpenRiver", UP, "OpenAir");
        tryAdd("OpenRiver", UP_RIGHT, "OpenAir");
        tryAdd("OpenRiver", RIGHT, "OpenRiver");
        tryAdd("OpenRiver", RIGHT, "RiverStart");
        tryAdd("OpenRiver", DOWN_RIGHT, "RiverBase");
        tryAdd("OpenRiver", DOWN_RIGHT, "RiverWall");
        tryAdd("OpenRiver", DOWN, "RiverBase");
        tryAdd("OpenRiver", DOWN, "RiverWall");
        tryAdd("OpenRiver", DOWN_LEFT, "RiverBase");
        tryAdd("OpenRiver", DOWN_LEFT, "RiverWall");
        tryAdd("OpenRiver", LEFT, "OpenRiver");
        tryAdd("OpenRiver", LEFT, "RiverStart");


        tryAdd("RiverBase", UP_LEFT, "OpenRiver");
        tryAdd("RiverBase", UP_LEFT, "RiverStart");
        tryAdd("RiverBase", UP, "OpenRiver");
        tryAdd("RiverBase", UP_RIGHT, "OpenRiver");
        tryAdd("RiverBase", UP_RIGHT, "RiverStart");
        tryAdd("RiverBase", RIGHT, "RiverBase");
        tryAdd("RiverBase", RIGHT, "RiverWall");
        tryAdd("RiverBase", DOWN_RIGHT, "SolidGround");
        tryAdd("RiverBase", DOWN, "SolidGround");
        tryAdd("RiverBase", DOWN_LEFT, "SolidGround");
        tryAdd("RiverBase", LEFT, "RiverBase");
        tryAdd("RiverBase", LEFT, "RiverWall");


        tryAdd("RiverWall", UP_LEFT, "SolidGround");
        tryAdd("RiverWall", UP, "RiverStart");
        tryAdd("RiverWall", UP_RIGHT, "OpenRiver");
        tryAdd("RiverWall", RIGHT, "RiverBase");
        tryAdd("RiverWall", DOWN_RIGHT, "SolidGround");
        tryAdd("RiverWall", DOWN, "SolidGround");
        tryAdd("RiverWall", DOWN_LEFT, "SolidGround");
        tryAdd("RiverWall", LEFT, "SolidGround");

        //endregion

        //region Salt Flats
        tryAdd("Flat", UP_LEFT, "OpenAir");
        tryAdd("Flat", UP, "OpenAir");
        tryAdd("Flat", UP_RIGHT, "OpenAir");

        tryAdd("Flat", RIGHT, "Drop");
        tryAdd("Flat", RIGHT, "CliffRoof");
        tryAdd("Flat", RIGHT, "UndergroundEntrance");
        tryAdd("Flat", RIGHT, "SmallPit");
        tryAdd("Flat", RIGHT, "PitStart");
        tryAdd("Flat", RIGHT, "Flat");
        tryAdd("Flat", RIGHT, "Pool");
        tryAdd("Flat", RIGHT, "RockColumns");
        tryAdd("Flat", RIGHT, "TempleEntrance");

        tryAdd("Flat", DOWN_RIGHT, "SolidGround");
        tryAdd("Flat", DOWN, "SolidGround");
        tryAdd("Flat", DOWN_LEFT, "SolidGround");

        tryAdd("Flat", LEFT, "Drop");
        tryAdd("Flat", LEFT, "CliffRoof");
        tryAdd("Flat", LEFT, "UndergroundEntrance");
        tryAdd("Flat", LEFT, "SmallPit");
        tryAdd("Flat", LEFT, "PitStart");
        tryAdd("Flat", LEFT, "Flat");
        tryAdd("Flat", LEFT, "Pool");
        tryAdd("Flat", LEFT, "RockColumns");
        tryAdd("Flat", LEFT, "TempleEntrance");


        tryAdd("Pool", UP_LEFT, "OpenAir");
        tryAdd("Pool", UP, "OpenAir");
        tryAdd("Pool", UP_RIGHT, "OpenAir");

        tryAdd("Pool", RIGHT, "Drop");
        tryAdd("Pool", RIGHT, "CliffRoof");
        tryAdd("Pool", RIGHT, "UndergroundEntrance");
        tryAdd("Pool", RIGHT, "SmallPit");
        tryAdd("Pool", RIGHT, "PitStart");
        tryAdd("Pool", RIGHT, "Pool");
        tryAdd("Pool", RIGHT, "RockColumns");
        tryAdd("Pool", RIGHT, "TempleEntrance");

        tryAdd("Pool", DOWN_RIGHT, "SolidGround");
        tryAdd("Pool", DOWN, "SolidGround");
        tryAdd("Pool", DOWN_LEFT, "SolidGround");

        tryAdd("Pool", LEFT, "Drop");
        tryAdd("Pool", LEFT, "CliffRoof");
        tryAdd("Pool", LEFT, "UndergroundEntrance");
        tryAdd("Pool", LEFT, "SmallPit");
        tryAdd("Pool", LEFT, "PitStart");
        tryAdd("Pool", LEFT, "Pool");
        tryAdd("Pool", LEFT, "RockColumns");
        tryAdd("Pool", LEFT, "TempleEntrance");


        tryAdd("RockColumns", UP_LEFT, "OpenAir");
        tryAdd("RockColumns", UP, "OpenAir");
        tryAdd("RockColumns", UP_RIGHT, "OpenAir");

        tryAdd("RockColumns", RIGHT, "Drop");
        tryAdd("RockColumns", RIGHT, "CliffRoof");
        tryAdd("RockColumns", RIGHT, "UndergroundEntrance");
        tryAdd("RockColumns", RIGHT, "SmallPit");
        tryAdd("RockColumns", RIGHT, "PitStart");
        tryAdd("RockColumns", RIGHT, "Flat");
        tryAdd("RockColumns", RIGHT, "Pool");
        tryAdd("RockColumns", RIGHT, "RockColumns");
        tryAdd("RockColumns", RIGHT, "TempleEntrance");

        tryAdd("RockColumns", DOWN_RIGHT, "SolidGround");
        tryAdd("RockColumns", DOWN, "SolidGround");
        tryAdd("RockColumns", DOWN_LEFT, "SolidGround");

        tryAdd("RockColumns", LEFT, "Drop");
        tryAdd("RockColumns", LEFT, "CliffRoof");
        tryAdd("RockColumns", LEFT, "UndergroundEntrance");
        tryAdd("RockColumns", LEFT, "SmallPit");
        tryAdd("RockColumns", LEFT, "PitStart");
        tryAdd("RockColumns", LEFT, "Flat");
        tryAdd("RockColumns", LEFT, "Pool");
        tryAdd("RockColumns", LEFT, "RockColumns");
        tryAdd("RockColumns", LEFT, "TempleEntrance");

        //endregion

        //region Temples
        tryAdd("TempleEntrance", UP_LEFT, "OpenAir");
        tryAdd("TempleEntrance", UP, "OpenAir");
        tryAdd("TempleEntrance", UP, "TempleWall");
        tryAdd("TempleEntrance", UP_RIGHT, "OpenAir");
        tryAdd("TempleEntrance", RIGHT, "TempleHallway");
        tryAdd("TempleEntrance", RIGHT, "Staircase");
        tryAdd("TempleEntrance", RIGHT, "Altar");
        tryAdd("TempleEntrance", RIGHT, "OpenAir");
        tryAdd("TempleEntrance", DOWN_RIGHT, "Staircase");
        tryAdd("TempleEntrance", DOWN_RIGHT, "SolidGround");
        tryAdd("TempleEntrance", DOWN, "SolidGround");
        tryAdd("TempleEntrance", DOWN, "CliffWall");
        tryAdd("TempleEntrance", DOWN_LEFT, "SolidGround");
        tryAdd("TempleEntrance", DOWN_LEFT, "CliffRoof");

        tryAdd("TempleEntrance", LEFT, "SmallHill");
        tryAdd("TempleEntrance", LEFT, "BigHill");
        tryAdd("TempleEntrance", LEFT, "Hilltop");
        tryAdd("TempleEntrance", LEFT, "Drop");
        tryAdd("TempleEntrance", LEFT, "UndergroundEntrance");
        tryAdd("TempleEntrance", LEFT, "MountainTop");
        tryAdd("TempleEntrance", LEFT, "Flat");
        tryAdd("TempleEntrance", LEFT, "Pool");
        tryAdd("TempleEntrance", LEFT, "RockColumns");
        tryAdd("TempleEntrance", LEFT, "TempleEntrance");

        tryAdd("TempleHallway", UP_LEFT, "OpenAir");
        tryAdd("TempleHallway", UP_LEFT, "TempleWall");
        tryAdd("TempleHallway", UP, "OpenAir");
        tryAdd("TempleHallway", UP, "TempleWall");
        tryAdd("TempleHallway", UP, "TempleRoof");
        tryAdd("TempleHallway", UP, "Trial");
        tryAdd("TempleHallway", UP_RIGHT, "OpenAir");

        tryAdd("TempleHallway", RIGHT, "TempleEntrance");
        tryAdd("TempleHallway", RIGHT, "TempleHallway");
        tryAdd("TempleHallway", RIGHT, "Staircase");
        tryAdd("TempleHallway", RIGHT, "Altar");
        tryAdd("TempleHallway", RIGHT, "OpenAir");
        tryAdd("TempleHallway", RIGHT, "TempleWall");

        tryAdd("TempleHallway", DOWN_RIGHT, "Staircase");
        tryAdd("TempleHallway", DOWN_RIGHT, "SolidGround");
        tryAdd("TempleHallway", DOWN, "SolidGround");
        tryAdd("TempleHallway", DOWN_LEFT, "SolidGround");
        tryAdd("TempleHallway", DOWN_LEFT, "CliffRoof");

        tryAdd("TempleHallway", LEFT, "TempleEntrance");
        tryAdd("TempleHallway", LEFT, "TempleHallway");
        tryAdd("TempleHallway", LEFT, "Staircase");
        tryAdd("TempleHallway", LEFT, "Altar");
        tryAdd("TempleHallway", LEFT, "Trial");
        tryAdd("TempleHallway", LEFT, "OpenAir");
        tryAdd("TempleHallway", LEFT, "TempleWall");


        tryAdd("Staircase", UP_LEFT, "OpenAir");
        tryAdd("Staircase", UP_LEFT, "TempleWall");
        tryAdd("Staircase", UP, "OpenAir");
        tryAdd("Staircase", UP, "TempleWall");
        tryAdd("Staircase", UP, "TempleRoof");
        tryAdd("Staircase", UP, "Trial");

        tryAdd("Staircase", UP_RIGHT, "OpenAir");
        tryAdd("Staircase", UP_RIGHT, "TempleEntrance");
        tryAdd("Staircase", UP_RIGHT, "TempleHallway");
        tryAdd("Staircase", UP_RIGHT, "Staircase");
        tryAdd("Staircase", UP_RIGHT, "Altar");

        tryAdd("Staircase", RIGHT, "TempleHallway");
        tryAdd("Staircase", RIGHT, "Staircase");
        tryAdd("Staircase", RIGHT, "Trial");
        tryAdd("Staircase", RIGHT, "OpenAir");
        tryAdd("Staircase", RIGHT, "TempleWall");

        tryAdd("Staircase", DOWN_RIGHT, "Staircase");
        tryAdd("Staircase", DOWN_RIGHT, "SolidGround");
        tryAdd("Staircase", DOWN, "SolidGround");
        tryAdd("Staircase", DOWN_LEFT, "SolidGround");
        tryAdd("Staircase", DOWN_LEFT, "CliffRoof");
        tryAdd("Staircase", DOWN_LEFT, "Staircase");

        tryAdd("Staircase", LEFT, "TempleEntrance");
        tryAdd("Staircase", LEFT, "TempleHallway");
        tryAdd("Staircase", LEFT, "Staircase");
        tryAdd("Staircase", LEFT, "Altar");
        tryAdd("Staircase", LEFT, "Trial");
        tryAdd("Staircase", LEFT, "OpenAir");
        tryAdd("Staircase", LEFT, "TempleWall");


        tryAdd("Altar", UP_LEFT, "OpenAir");
        tryAdd("Altar", UP_LEFT, "TempleWall");
        tryAdd("Altar", UP, "OpenAir");
        tryAdd("Altar", UP, "TempleWall");
        tryAdd("Altar", UP, "TempleRoof");
        tryAdd("Altar", UP, "Trial");
        tryAdd("Altar", UP_RIGHT, "OpenAir");

        tryAdd("Altar", RIGHT, "TempleHallway");
        tryAdd("Altar", RIGHT, "Staircase");
        tryAdd("Altar", RIGHT, "Trial");
        tryAdd("Altar", RIGHT, "OpenAir");
        tryAdd("Altar", RIGHT, "TempleWall");
        tryAdd("Altar", RIGHT, "TempleEntrance");
        tryAdd("Altar", RIGHT, "TempleHallway");
        tryAdd("Altar", RIGHT, "Staircase");
        tryAdd("Altar", RIGHT, "Altar");

        tryAdd("Altar", DOWN_RIGHT, "Staircase");
        tryAdd("Altar", DOWN_RIGHT, "SolidGround");
        tryAdd("Altar", DOWN, "SolidGround");
        tryAdd("Altar", DOWN_LEFT, "SolidGround");
        tryAdd("Altar", DOWN_LEFT, "CliffRoof");
        tryAdd("Altar", DOWN_LEFT, "Staircase");

        tryAdd("Altar", LEFT, "TempleEntrance");
        tryAdd("Altar", LEFT, "TempleHallway");
        tryAdd("Altar", LEFT, "Staircase");
        tryAdd("Altar", LEFT, "Altar");
        tryAdd("Altar", LEFT, "Trial");
        tryAdd("Altar", LEFT, "OpenAir");
        tryAdd("Altar", LEFT, "TempleWall");

        tryAdd("TempleWall", UP_LEFT, "TempleRoof");
        tryAdd("TempleWall", UP_LEFT, "OpenAir");
        tryAdd("TempleWall", UP, "TempleWall");
        tryAdd("TempleWall", UP, "CaveWall");
        tryAdd("TempleWall", UP, "Altar");
        tryAdd("TempleWall", UP_RIGHT, "TempleRoof");
        tryAdd("TempleWall", UP_RIGHT, "OpenAir");
        tryAdd("TempleWall", RIGHT, "OpenAir");
        tryAdd("TempleWall", DOWN_RIGHT, "OpenAir");
        tryAdd("TempleWall", DOWN, "TempleWall");
        tryAdd("TempleWall", DOWN_LEFT, "OpenAir");
        tryAdd("TempleWall", LEFT, "OpenAir");


        tryAdd("TempleRoof", UP_LEFT, "OpenAir");
        tryAdd("TempleRoof", UP, "OpenAir");
        tryAdd("TempleRoof", UP_RIGHT, "OpenAir");
        tryAdd("TempleRoof", RIGHT, "OpenAir");
        tryAdd("TempleRoof", RIGHT, "Altar");
        tryAdd("TempleRoof", RIGHT, "TempleRoof");
        tryAdd("TempleRoof", DOWN_RIGHT, "OpenAir");
        tryAdd("TempleRoof", DOWN_RIGHT, "TempleWall");
        tryAdd("TempleRoof", DOWN, "TempleWall");
        tryAdd("TempleRoof", DOWN_LEFT, "TempleWall");
        tryAdd("TempleRoof", DOWN_LEFT, "OpenAir");
        tryAdd("TempleRoof", LEFT, "OpenAir");


        tryAdd("Trial", UP_LEFT, "OpenAir");
        tryAdd("Trial", UP, "OpenAir");
        tryAdd("Trial", UP_RIGHT, "OpenAir");
        tryAdd("Trial", RIGHT, "Staircase");
        tryAdd("Trial", RIGHT, "Trial");
        tryAdd("Trial", DOWN_RIGHT, "OpenAir");
        tryAdd("Trial", DOWN_RIGHT, "TempleWall");
        tryAdd("Trial", DOWN, "OpenAir");
        tryAdd("Trial", DOWN, "SolidGround");
        tryAdd("Trial", DOWN_LEFT, "OpenAir");
        tryAdd("Trial", DOWN_LEFT, "TempleWall");
        tryAdd("Trial", LEFT, "Staircase");
        tryAdd("Trial", LEFT, "Trial");

        //endregion

        //region Special Rooms
        tryAdd("Camp", UP_LEFT, "SolidGround");

        tryAdd("Camp", RIGHT, "SmallHill");
        tryAdd("Camp", RIGHT, "BigHill");
        tryAdd("Camp", RIGHT, "Hilltop");
        tryAdd("Camp", RIGHT, "UndergroundEntrance");
        tryAdd("Camp", RIGHT, "Hollow");
        tryAdd("Camp", RIGHT, "CaveHallway");
        tryAdd("Camp", RIGHT, "Platform");
        tryAdd("Camp", RIGHT, "UndergroundPondStart");
        tryAdd("Camp", RIGHT, "MountainSideA");
        tryAdd("Camp", RIGHT, "MountainTop");
        tryAdd("Camp", RIGHT, "SmallPond");
        tryAdd("Camp", RIGHT, "PondStart");
        tryAdd("Camp", RIGHT, "Flat");
        tryAdd("Camp", RIGHT, "Pool");
        tryAdd("Camp", RIGHT, "RockColumns");
        tryAdd("Camp", RIGHT, "TempleEntrance");
        tryAdd("Camp", RIGHT, "TempleHallway");
        tryAdd("Camp", RIGHT, "Staircase");

        tryAdd("Camp", DOWN_RIGHT, "BigHill");
        tryAdd("Camp", DOWN_RIGHT, "CliffRoof");
        tryAdd("Camp", DOWN_RIGHT, "Platform");
        tryAdd("Camp", DOWN_RIGHT, "SmallUndergroundPond");
        tryAdd("Camp", DOWN_RIGHT, "SmallPit");
        tryAdd("Camp", DOWN_RIGHT, "RockColumns");
        tryAdd("Camp", DOWN_RIGHT, "Staircase");

        tryAdd("Camp", DOWN_LEFT, "SolidGround");
        tryAdd("Camp", LEFT, "SolidGround");


        tryAdd("Source", UP_RIGHT, "SolidGround");
        tryAdd("Source", RIGHT, "SolidGround");
        tryAdd("Source", DOWN_RIGHT, "SolidGround");

        tryAdd("Source", DOWN_LEFT, "BigHill");
        tryAdd("Source", DOWN_LEFT, "CliffRoof");
        tryAdd("Source", DOWN_LEFT, "Platform");
        tryAdd("Source", DOWN_LEFT, "SmallUndergroundPond");
        tryAdd("Source", DOWN_LEFT, "SmallPit");
        tryAdd("Source", DOWN_LEFT, "RockColumns");
        tryAdd("Source", DOWN_LEFT, "Staircase");

        tryAdd("Source", LEFT, "SmallHill");
        tryAdd("Source", LEFT, "BigHill");
        tryAdd("Source", LEFT, "Hilltop");
        tryAdd("Source", LEFT, "UndergroundEntrance");
        tryAdd("Source", LEFT, "Hollow");
        tryAdd("Source", LEFT, "CaveHallway");
        tryAdd("Source", LEFT, "Platform");
        tryAdd("Source", LEFT, "UndergroundPondStart");
        tryAdd("Source", LEFT, "MountainSideA");
        tryAdd("Source", LEFT, "MountainTop");
        tryAdd("Source", LEFT, "SmallPond");
        tryAdd("Source", LEFT, "PondStart");
        tryAdd("Source", LEFT, "Flat");
        tryAdd("Source", LEFT, "Pool");
        tryAdd("Source", LEFT, "RockColumns");
        tryAdd("Source", LEFT, "TempleEntrance");
        tryAdd("Source", LEFT, "TempleHallway");
        tryAdd("Source", LEFT, "Staircase");
        //endregion
    }
}
