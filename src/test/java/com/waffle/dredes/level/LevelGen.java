package com.waffle.dredes.level;

import com.waffle.core.Constants;
import com.waffle.core.Utils;
import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

import static com.waffle.dredes.level.Room.Direction.*;
import static com.waffle.dredes.level.Room.TileType.*;

/**
 * A class used for procedural generation of Rooms
 */
public class LevelGen {
    /**
     * The Singleton access to this level generator
     */
    public static LevelGen INSTANCE = new LevelGen();
    private RoomLoader roomLoader;
    private Random rng;

    /**
     * Represents a type of biome
     */
    public enum Biome {
        Grassland,
        Redland,
        Sandland,
        Saltland,
        Stoneland
    }

    private String[] caveFolder = {
            "CaveEntrance.txt",
            "CaveHallway.txt",
            "CaveHollow.txt",
            "CavePlatform.txt",
            "CaveWall.txt",
            "SmallUndergroundPond.txt",
            "UndergroundPondMiddle.txt",
            "UndergroundPondStart.txt"
    };

    private String[] cliffFolder = {
            "CliffDrop.txt",
            "CliffRoof.txt",
            "CliffWall.txt"
    };

    private String[] frozenPondFolder = {
            "FrozenPondMiddle.txt",
            "FrozenPondStart.txt"
    };

    private String[] hillsFolder = {
            "BigHill.txt",
            "SmallHill.txt"
    };

    private String[] mountainFolder = {
            "MountainSideA.txt",
            "MountainSideB.txt",
            "MountainSideC.txt",
            "MountainTop.txt"
    };

    private String[] pitFolder = {
            "PitMiddle.txt",
            "PitStart.txt",
            "SmallPit.txt"
    };

    private String[] pondFolder = {
            "DeepPond.txt",
            "PondMiddle.txt",
            "PondStart.txt",
            "SmallPond.txt"
    };

    private String[] riverFolder = {
            "OpenRiver.txt",
            "RiverBase.txt",
            "RiverStart.txt",
            "RiverWall.txt"
    };

    private String[] saltFlatsFolder = {
            "Flat.txt",
            "Pool.txt",
            "RockColumns.txt"
    };

    private String[] specialFolder = {
            "Camp.txt",
            "Source.txt"
    };

    private String[] templeFolder = {
            "Altar.txt",
            "Hallway.txt",
            "Staircase.txt",
            "TempleEntrance.txt",
            "TempleRoof.txt",
            "TempleWall.txt",
            "Trial.txt"
    };

    private String[] universalFolder = {
            "OpenAir.txt",
            "OpenWater.txt",
            "SolidGround.txt"
    };

    private LevelGen() {}

    /**
     * Generates a level
     * @param biome the biome type to generate
     * @param x the x coordinate of where to start generation
     * @param y the y coordinate of where to start generation
     * @param river true if rivers should be generated
     * @return a two-dimensional tile array with the generated rooms
     */
    public Tile[][] generate(Biome biome, int x, int y, boolean river) {
        Tile[] tiles = new Tile[7];
        roomLoader = new RoomLoader();
        rng = new Random(x ^ y);
        Room[][] level = new Room[6][10];
        loadBiome(biome, tiles);

        if(river) {
            loadRiverFolder();
        }
        loadUniversalFolder();
        loadSpecialFolder();
        addAllNeighbors();



        level[2][0] = roomLoader.getRoom("Camp"); //loading Camp
        level[2][9] = roomLoader.getRoom("Source"); //loading Source

//        pickNeighbors(level, pickRoom(level, 2, 1), 2, 1);
//        debugLevelGen(level, tiles);



        int height = 2; //decides starting height for initial snaking
        int direction = 1; //decides initail vertical traversal direction


        level[2][0] = roomLoader.getRoom("Camp");
        level[2][9] = roomLoader.getRoom("Source");

        for(int i = 2; i < level.length; i++) {
            for(int j = 1; j < level[i].length - 1; j++) {
                Room r = pickRoom(level, i, j);
                level[i][j] = r;
            }
        }
        for(int i = 1; i > 0; i--) {
            for(int j = 1; j < level[i].length - 1; j++) {
                Room r = pickRoom(level, i, j);
                level[i][j] = r;
            }
        }
        for(int i = 3; i < level.length; i++)
        {
            level[i][9] = roomLoader.getRoom("SolidGround");
        }

        Tile[][] ret = new Tile[36][80]; //creates the return object
        for(int i = 0; i < level.length; i++) //traverses the newly-filled Room[][]
        {
            for(int j = 0; j < level[0].length; j++)
            {
                if(level[i][j] != null) //makes sure the room isnt null
                {
                    int[][] blueprint = level[i][j].configuration; //gets the tile configuration from the room
                    for(int k = 0; k < blueprint.length; k++) //traverses the tile configuration
                    {
                        for(int l = 0; l < blueprint[k].length; l++)
                        {
                            if(tiles[blueprint[k][l]] != null) //basically an aircheck
                            {
                                ret[(i * 6)+ k][(j * 8) + l] = tiles[blueprint[k][l]].copy((i * 6) + k,(j * 8) + l); //multiplies room cell by the dimensions of the room to calculate the tile's cell. The 2 inner forloop's control variables then act as an offset from that
                            }
                        }
                    }
                }

            }
        }

        return ret;
    }

    private void debugLevelGen(Room[][] level, Tile[] tiles) {
        int[][] debug = new int[36][80];
        for(int i = 0; i < level.length; i++) {
            for(int j = 0; j < level[0].length; j++) {
                if(level[i][j] != null) {
                    int[][] blueprint = level[i][j].configuration;
                    for(int k = 0; k < blueprint.length; k++) {
                        for(int l = 0; l < blueprint[k].length; l++) {
                            if(tiles[blueprint[k][l]] != null) {
                                debug[(i * 6)+ k][(j * 8) + l] = blueprint[k][l];
                            }
                        }
                    }
                }
            }
        }

        int count = 0;
        int countRow = 0;
        for(int[] iArr : debug) {
            for(int i : iArr) {
                switch (i) {
                    case 1 -> System.out.print("Su ");
                    case 2 -> System.out.print("St ");
                    case 3 -> System.out.print("Wa ");
                    case 4 -> System.out.print("WB ");
                    case 5 -> System.out.print("SA ");
                    case 6 -> System.out.print("SB ");
                    default ->System.out.print(".  ");
                }
                count++;
                if(count % 8 == 0) {
                    System.out.print("|");
                }
            }
            countRow++;
            System.out.println();
            if(countRow % 6 == 0) {
                for(int j = 0; j < 80; j++) {
                    System.out.print("---");
                }
                System.out.println();
            }
        }
        System.out.print("\n\n\n");
    }



    private void loadBiome(Biome b, Tile[] tiles) {
        tiles[0] = null;
        tiles[3] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Water.png"), -1, -1, true, true, 1f, new Vec2f(1.5f, 2f));
        if(b.equals(Biome.Grassland)) {
            loadGrass();
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Grass.png"), -1, -1, false, false);
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Dirt.png"), -1, -1, false, false);
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Gravel.png"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Bricks.png"), -1, -1, false, false);
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Tiles.png"), -1, -2, false, false);
        } else if(b.equals(Biome.Redland)) {
            loadRed();
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Clay.png"), -1, -1, false, false);
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Gravel.png"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Sand.png"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.1f));
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Bricks.png"), -1, -1, false, false);
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Tiles.png"), -1, -2, false, false);
        } else if(b.equals(Biome.Sandland)) {
            loadSand();
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Sand.png"), -1, -1, false, false, 1.25f, new Vec2f(1,1.1f));
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Clay.png"), -1, -1, false, false);
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Gravel.png"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Bricks.png"), -1, -1, false, false);
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Tiles.png"), -1, -2, false, false);
        } else if(b.equals(Biome.Saltland)) {
            loadSalt();
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Salt.png"), -1, -1, false, false);
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Rock.png"), -1, -1, false, false);
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Salt.png"), -1, -1, false, false);
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Dirt.png"), -1, -1, false, false);
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Ice.png"), -1, -2, false, false, .5f, new Vec2f(1,1));
        } else if(b.equals(Biome.Stoneland)) {
            loadStone();
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Rock.png"), -1, -1, false, false);
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Rock.png"), -1, -1, false, false);
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Salt.png"), -1, -1, false, false);
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Snow.png"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Ice.png"), -1, -2, false, false, .5f, new Vec2f(1,1));
        }
    }
    private void loadGrass() {
        loadHillsFolder();
        loadCaveFolder();
        loadPondFolder();
    }

    private void loadRed() {
        loadHillsFolder();
        loadCliffFolder();
        loadCaveFolder();
        loadPondFolder();
    }

    private void loadSand() {
        loadHillsFolder();
        loadCaveFolder();
        loadPitFolder();
        loadTempleFolder();
    }

    private void loadSalt() {
        loadCliffFolder();
        loadCaveFolder();
        loadPitFolder();
        loadSaltFlatsFolder();
        loadTempleFolder();
    }

    private void loadStone() {
        loadCaveFolder();
        loadMountainFolder();
        loadFrozenPondFolder();
        loadTempleFolder();
    }

    private void loadCaveFolder() {
        for(String s : caveFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/Cave/" + s);
        }
    }

    private void loadCliffFolder() {
        for(String s : cliffFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/Cliff/" + s);
        }
    }

    private void loadFrozenPondFolder() {
        for(String s : frozenPondFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/FrozenPond/" + s);
        }
    }

    private void loadHillsFolder() {
        for(String s : hillsFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/Hills/" + s);
        }
    }

    private void loadMountainFolder() {
        for(String s : mountainFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/Mountain/" + s);
        }
    }

    private void loadPitFolder() {
        for(String s : pitFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/Pit/" + s);
        }
    }

    private void loadPondFolder() {
        for(String s : pondFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/Pond/" + s);
        }
    }

    private void loadRiverFolder() {
        for(String s : riverFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/River/" + s);
        }
    }

    private void loadSaltFlatsFolder() {
        for(String s : saltFlatsFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/SaltFlats/" + s);
        }
    }

    private void loadSpecialFolder() {
        for(String s : specialFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/Special/" + s);
        }
    }

    private void loadTempleFolder() {
        for(String s : templeFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/Temple/" + s);
        }
    }

    private void loadUniversalFolder() {
        for(String s : universalFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/Universal/" + s);
        }
    }

    private Room pickRoom(Room[][] rooms, int row, int col) {
        ArrayList<Room> test= new ArrayList<Room>();
        try {
            Collection<Room> temp = Arrays.asList(rooms[row - 1][col].getRoomPool(DOWN, true));
            if(!temp.isEmpty())
                test.addAll(temp);
        } catch (Exception ignored){}
        try {
            Collection<Room> temp = Arrays.asList(rooms[row + 1][col].getRoomPool(UP, true));
            if(!temp.isEmpty()) {
                if(test.isEmpty()) {
                    test.addAll(temp);
                } else {
                    test.retainAll(temp);
                }
            }

        } catch (Exception ignored){}
        try {
            Collection<Room> temp = Arrays.asList(rooms[row][col + 1].getRoomPool(LEFT, true));
            if(!temp.isEmpty())
            {
                if(test.isEmpty())
                {
                    test.addAll(temp);
                }
                else
                    test.retainAll(temp);
            }

        }
        catch (Exception ignored){}
        try {
            Collection<Room> temp = Arrays.asList(rooms[row][col - 1].getRoomPool(RIGHT, true));
            if(!temp.isEmpty())
            {
                if(test.isEmpty())
                {
                    test.addAll(temp);
                }
                else
                    test.retainAll(temp);
            }

        }
        catch (Exception ignored){}
        //System.out.println("Strong " + test);
        if(test.size() > 0)
        {
            test.remove(roomLoader.getRoom("HilltopFlipped"));
            return test.get(rng.nextInt(test.size()));
        }
        try {
            Collection<Room> temp = Arrays.asList(rooms[row - 1][col].getRoomPool(DOWN, false));
            if(!temp.isEmpty())
                test.addAll(temp);
        }
        catch (Exception ignored){}
        try {
            Collection<Room> temp = Arrays.asList(rooms[row + 1][col].getRoomPool(UP, false));
            if(!temp.isEmpty())
            {
                if(test.isEmpty())
                {
                    test.addAll(temp);
                }
                else
                    test.retainAll(temp);
            }

        }
        catch (Exception ignored){}
        try {
            Collection<Room> temp = Arrays.asList(rooms[row][col + 1].getRoomPool(LEFT, false));
            if(!temp.isEmpty())
            {
                if(test.isEmpty())
                {
                    test.addAll(temp);
                }
                else
                    test.retainAll(temp);
            }

        }
        catch (Exception ignored){}
        try {
            Collection<Room> temp = Arrays.asList(rooms[row][col - 1].getRoomPool(RIGHT, false));
            if(!temp.isEmpty())
            {
                if(test.isEmpty())
                {
                    test.addAll(temp);
                }
                else
                    test.retainAll(temp);
            }

        }
        catch (Exception ignored){}
        //System.out.println("Weak " + test);
        if(test.size() > 0)
        {
            return test.get(rng.nextInt(test.size()));
        }
        //System.out.printf("[%02d][%02d] cannot be decided%n", row, col);
        return roomLoader.getRoom("OpenAir");
    }
    private void tryAdd(String center, Room.Direction direction, String neighbor) {
        Room c = roomLoader.getRoom(center);
        Room n = roomLoader.getRoom(neighbor);

        if(c != null && n != null) {
            c.addNeighbor(direction, n, true);
        }
    }

    private void tryAdd(String center, Room.Direction direction, String neighbor, boolean flipped) {
        Room c = roomLoader.getRoom(center);
        Room n = roomLoader.getRoom(neighbor);

        if(c != null && n != null) {
            if(flipped) {
                c.addNeighbor(direction, roomLoader.getRoom(neighbor+"Flipped"), true);
            } else {
                c.addNeighbor(direction, n, false);
            }
        }
    }

    /**
     * Checks for every single room combination
     */
    private void addAllNeighbors() {
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
        tryAdd("OpenAir", RIGHT, "OpenAir");


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

        tryAdd("SmallHill", RIGHT, "Drop");
        tryAdd("SmallHill", RIGHT, "CliffRoof");

        tryAdd("SmallHill", RIGHT, "PitStart");
        tryAdd("SmallHill", RIGHT, "SmallPond");
        tryAdd("SmallHill", RIGHT, "PondStart");
        tryAdd("SmallHill", RIGHT, "SolidGround", false);

        tryAdd("SmallHill", DOWN_RIGHT, "SolidGround", false);
        tryAdd("SmallHill", DOWN, "SolidGround", false);
        tryAdd("SmallHill", DOWN_LEFT, "SolidGround", false);


        tryAdd("SmallHill", LEFT, "Hilltop");

        tryAdd("SmallHill", LEFT, "SmallPond");



        tryAdd("BigHill", UP_LEFT, "OpenAir", false);
        tryAdd("BigHill", UP, "OpenAir", false);

        tryAdd("BigHill", UP_RIGHT, "OpenAir");
        tryAdd("BigHill", UP_RIGHT, "SmallHill");
        tryAdd("BigHill", UP_RIGHT, "BigHill");
        tryAdd("BigHill", UP_RIGHT, "Hilltop");
        tryAdd("BigHill", UP_RIGHT, "UndergroundEntrance");


        tryAdd("BigHill", RIGHT, "BigHill");
        tryAdd("BigHill", RIGHT, "Drop");
        tryAdd("BigHill", RIGHT, "CliffRoof");
        tryAdd("BigHill", RIGHT, "SmallPit");
        tryAdd("BigHill", RIGHT, "PitStart");
        tryAdd("BigHill", RIGHT, "SolidGround", false);

        tryAdd("BigHill", DOWN_RIGHT, "SolidGround", false);
        tryAdd("BigHill", DOWN, "SolidGround", false);
        tryAdd("BigHill", DOWN_LEFT, "SolidGround", false);


        tryAdd("BigHill", LEFT, "Hilltop");

        tryAdd("BigHill", LEFT, "SmallPond");



        tryAdd("Hilltop", UP_LEFT, "OpenAir", false);
        tryAdd("Hilltop", UP, "OpenAir", false);
        tryAdd("Hilltop", UP_RIGHT, "OpenAir", false);

        tryAdd("Hilltop", RIGHT, "SmallHill");
        tryAdd("Hilltop", RIGHT, "BigHill");
        tryAdd("Hilltop", RIGHT, "Drop");

        tryAdd("Hilltop", RIGHT, "UndergroundEntrance");
        tryAdd("Hilltop", RIGHT, "SmallPit");
        tryAdd("Hilltop", RIGHT, "PitStart");
        tryAdd("Hilltop", RIGHT, "SmallPond");
        tryAdd("Hilltop", RIGHT, "PondStart");
        tryAdd("Hilltop", RIGHT, "TempleEntrance");


        tryAdd("Hilltop", DOWN_RIGHT, "SolidGround", false);
        tryAdd("Hilltop", DOWN, "CliffWall");
        tryAdd("Hilltop", DOWN, "SolidGround");
        tryAdd("Hilltop", DOWN_LEFT, "SolidGround", false);

        tryAdd("Hilltop", LEFT, "OpenAir");

        //endregion

        //region Cliffs
        tryAdd("Drop", UP_LEFT, "OpenAir", false);
        tryAdd("Drop", UP, "OpenAir");
        tryAdd("Drop", UP_RIGHT, "OpenAir", false);

        tryAdd("Drop", RIGHT, "SmallHill");
        tryAdd("Drop", RIGHT, "UndergroundEntrance");
        tryAdd("Drop", RIGHT, "PitMiddle");
        tryAdd("Drop", RIGHT, "RockColumns");
        tryAdd("Drop", RIGHT, "TempleEntrance");

        tryAdd("Drop", DOWN_RIGHT, "SolidGround", false);

        tryAdd("Drop", DOWN, "SolidGround");
        tryAdd("Drop", DOWN_LEFT, "SolidGround", false);

        tryAdd("Drop", LEFT, "SmallHill");
        tryAdd("Drop", LEFT, "CliffRoof");

        tryAdd("Drop", LEFT, "SmallPit");
        tryAdd("Drop", LEFT, "PitStart");
        tryAdd("Drop", LEFT, "SmallPond");

        tryAdd("Drop", LEFT, "Pool");
        tryAdd("Drop", LEFT, "Pool");
        tryAdd("Drop", LEFT, "RockColumns");



        tryAdd("CliffWall", UP_LEFT, "OpenAir", false);

        tryAdd("CliffWall", UP, "Hilltop");

        tryAdd("CliffWall", UP, "TempleEntrance");
        tryAdd("CliffWall", UP_RIGHT, "OpenAir");
        tryAdd("CliffWall", RIGHT, "SolidGround", false);
        tryAdd("CliffWall", DOWN_RIGHT, "SolidGround", false);
        tryAdd("CliffWall", DOWN_RIGHT, "CliffWall");
        tryAdd("CliffWall", DOWN, "OpenAir");
        tryAdd("CliffWall", DOWN_LEFT, "SolidGround", false);

        tryAdd("CliffWall", LEFT, "PitMiddle");


        tryAdd("CliffRoof", UP_LEFT, "OpenAir", false);
        tryAdd("CliffRoof", UP, "OpenAir", false);
        tryAdd("CliffRoof", UP_RIGHT, "OpenAir", false);


        tryAdd("CliffRoof", RIGHT, "CliffRoof");
        tryAdd("CliffRoof", RIGHT, "Drop");

        tryAdd("CliffRoof", RIGHT, "PitStart");
        tryAdd("CliffRoof", RIGHT, "SmallPond");
        tryAdd("CliffRoof", RIGHT, "PondStart");
        tryAdd("CliffRoof", RIGHT, "Pool");
        tryAdd("CliffRoof", RIGHT, "RockColumns");

        tryAdd("CliffRoof", DOWN_RIGHT, "SolidGround", false);
        tryAdd("CliffRoof", DOWN, "SolidGround");
        tryAdd("CliffRoof", DOWN_LEFT, "SolidGround", false);

        tryAdd("CliffRoof", LEFT, "SmallHill");
        tryAdd("CliffRoof", LEFT, "BigHill");

        tryAdd("CliffRoof", LEFT, "CliffRoof");

        tryAdd("CliffRoof", LEFT, "SmallPit");

        tryAdd("CliffRoof", LEFT, "SmallPond");

        tryAdd("CliffRoof", LEFT, "Flat");
        tryAdd("CliffRoof", LEFT, "Pool");
        tryAdd("CliffRoof", LEFT, "RockColumns");


        //endregion

        //region Caves
        tryAdd("UndergroundEntrance", UP_LEFT, "OpenAir", false);
        tryAdd("UndergroundEntrance", UP_LEFT, "CaveWall", false);
        tryAdd("UndergroundEntrance", UP, "CaveWall");
        tryAdd("UndergroundEntrance", UP, "MountainSideB");
        tryAdd("UndergroundEntrance", UP_RIGHT, "OpenAir", false);
        tryAdd("UndergroundEntrance", UP_RIGHT, "CaveWall", false);
        //tryAdd("UndergroundEntrance", UP_RIGHT, "SolidGround", false);

        tryAdd("UndergroundEntrance", RIGHT, "Hollow");
        tryAdd("UndergroundEntrance", RIGHT, "CaveHallway");
        tryAdd("UndergroundEntrance", RIGHT, "Platform");
        tryAdd("UndergroundEntrance", RIGHT, "UndergroundPondStart");

        tryAdd("UndergroundEntrance", DOWN_RIGHT, "SolidGround", false);

        tryAdd("UndergroundEntrance", DOWN, "SolidGround", false);
        tryAdd("UndergroundEntrance", DOWN, "CliffWall");
        tryAdd("UndergroundEntrance", DOWN_LEFT, "MountainSideC");
        tryAdd("UndergroundEntrance", DOWN_LEFT, "Staircase");


        tryAdd("UndergroundEntrance", LEFT, "Hilltop");

        tryAdd("UndergroundEntrance", LEFT, "PitMiddle");

        tryAdd("UndergroundEntrance", LEFT, "MountainTop");
        tryAdd("UndergroundEntrance", LEFT, "SmallPond");

        tryAdd("UndergroundEntrance", LEFT, "Pool");
        tryAdd("UndergroundEntrance", LEFT, "RockColumns");



        tryAdd("Hollow", UP_LEFT, "CaveWall", false);
        tryAdd("Hollow", UP_LEFT, "OpenAir", false);
        //tryAdd("Hollow", UP_LEFT, "SolidGround", false);
        tryAdd("Hollow", UP, "CliffRoof");
        tryAdd("Hollow", UP, "OpenAir");
        tryAdd("Hollow", UP, "CaveWall");
        tryAdd("Hollow", UP, "MountainTop");
        //tryAdd("Hollow", UP, "SolidGround");
        tryAdd("Hollow", UP_RIGHT, "CaveWall", false);
        tryAdd("Hollow", UP_RIGHT, "OpenAir", false);
        //tryAdd("Hollow", UP_RIGHT, "SolidGround", false);


        tryAdd("Hollow", RIGHT, "CaveHallway");
        tryAdd("Hollow", RIGHT, "Platform");
        tryAdd("Hollow", RIGHT, "SmallUndergroundPond");
        tryAdd("Hollow", RIGHT, "UndergroundPondStart");

        tryAdd("Hollow", DOWN_RIGHT, "SolidGround", false);

        tryAdd("Hollow", DOWN, "SolidGround", false);
        tryAdd("Hollow", DOWN_LEFT, "SolidGround", false);
        tryAdd("Hollow", DOWN_LEFT, "Platform");

        tryAdd("Hollow", LEFT, "UndergroundEntrance");
        tryAdd("Hollow", LEFT, "Hollow");
        tryAdd("Hollow", LEFT, "CaveHallway");
        tryAdd("Hollow", LEFT, "Platform");



        tryAdd("CaveWall", UP_LEFT, "OpenAir", false);
        tryAdd("CaveWall", UP, "CaveWall", false);
        tryAdd("CaveWall", UP, "OpenAir", false);
        tryAdd("CaveWall", UP, "SolidGround", false);
        tryAdd("CaveWall", UP_RIGHT, "OpenAir", false);
        tryAdd("CaveWall", RIGHT, "OpenAir", false);
        tryAdd("CaveWall", DOWN_RIGHT, "OpenAir", false);
        tryAdd("CaveWall", DOWN_RIGHT, "SolidGround", false);
        tryAdd("CaveWall", DOWN, "UndergroundEntrance", false);
        tryAdd("CaveWall", DOWN, "OpenAir", false);
        tryAdd("CaveWall", DOWN_LEFT, "OpenAir", false);
        tryAdd("CaveWall", DOWN_LEFT, "SolidGround", false);
        tryAdd("CaveWall", LEFT, "OpenAir", false);


        tryAdd("CaveHallway", UP_LEFT, "OpenAir", false);
        tryAdd("CaveHallway", UP_LEFT, "CaveWall", false);
        tryAdd("CaveHallway", UP_LEFT, "SolidGround", false);
        tryAdd("CaveHallway", UP, "CliffRoof");
        tryAdd("CaveHallway", UP, "CaveWall");
        tryAdd("CaveHallway", UP, "MountainTop");
        tryAdd("CaveHallway", UP, "SolidGround");
        tryAdd("CaveHallway", UP_RIGHT, "OpenAir", false);
        tryAdd("CaveHallway", UP_RIGHT, "CaveWall", false);
        tryAdd("CaveHallway", UP_RIGHT, "SolidGround", false);


        tryAdd("CaveHallway", RIGHT, "Hollow");
        tryAdd("CaveHallway", RIGHT, "CaveHallway");
        tryAdd("CaveHallway", RIGHT, "Platform");
        tryAdd("CaveHallway", RIGHT, "UndergroundPondStart");

        tryAdd("CaveHallway", DOWN_RIGHT, "SolidGround", false);

        tryAdd("CaveHallway", DOWN, "SolidGround", false);
        tryAdd("CaveHallway", DOWN_LEFT, "SolidGround", false);

        tryAdd("CaveHallway", LEFT, "UndergroundEntrance");
        tryAdd("CaveHallway", LEFT, "Hollow");
        tryAdd("CaveHallway", LEFT, "CaveHallway");
        tryAdd("CaveHallway", LEFT, "Platform");



        tryAdd("Platform", UP_LEFT, "OpenAir", false);
        tryAdd("Platform", UP_LEFT, "CaveWall", false);
        tryAdd("Platform", UP_LEFT, "SolidGround", false);
        tryAdd("Platform", UP, "OpenAir");


        tryAdd("Platform", UP_RIGHT, "Hollow");
        tryAdd("Platform", UP_RIGHT, "CaveHallway");
        tryAdd("Platform", UP_RIGHT, "Platform");
        tryAdd("Platform", UP_RIGHT, "OpenAir", false);
        tryAdd("Platform", UP_RIGHT, "CaveWall", false);
        tryAdd("Platform", UP_RIGHT, "SolidGround", false);


        tryAdd("Platform", RIGHT, "Hollow");
        tryAdd("Platform", RIGHT, "CaveHallway");
        tryAdd("Platform", RIGHT, "Platform");
        tryAdd("Platform", RIGHT, "SmallUndergroundPond");
        tryAdd("Platform", RIGHT, "UndergroundPondStart");

        tryAdd("Platform", DOWN_RIGHT, "SolidGround", false);

        tryAdd("Platform", DOWN, "SolidGround", false);
        tryAdd("Platform", DOWN_LEFT, "SolidGround", false);

        tryAdd("Platform", LEFT, "UndergroundEntrance");
        tryAdd("Platform", LEFT, "Hollow");
        tryAdd("Platform", LEFT, "CaveHallway");
        tryAdd("Platform", LEFT, "Platform");
        tryAdd("Platform", LEFT, "SmallUndergroundPond");



        tryAdd("SmallUndergroundPond", UP_LEFT, "OpenAir", false);
        tryAdd("SmallUndergroundPond", UP_LEFT, "CaveWall", false);
        tryAdd("SmallUndergroundPond", UP_LEFT, "SolidGround", false);
        tryAdd("SmallUndergroundPond", UP, "OpenAir");


        tryAdd("SmallUndergroundPond", UP_RIGHT, "Hollow");
        tryAdd("SmallUndergroundPond", UP_RIGHT, "CaveHallway");

        tryAdd("SmallUndergroundPond", UP_RIGHT, "OpenAir", false);

        tryAdd("SmallUndergroundPond", RIGHT, "Platform");
        tryAdd("SmallUndergroundPond", RIGHT, "SmallUndergroundPond");
        tryAdd("SmallUndergroundPond", RIGHT, "UndergroundPondStart");
        tryAdd("SmallUndergroundPond", RIGHT, "UndergroundPondMiddle");

        tryAdd("SmallUndergroundPond", DOWN_RIGHT, "SolidGround", false);
        tryAdd("SmallUndergroundPond", DOWN, "SolidGround", false);
        tryAdd("SmallUndergroundPond", DOWN_LEFT, "SolidGround", false);

        tryAdd("SmallUndergroundPond", LEFT, "UndergroundEntrance");
        tryAdd("SmallUndergroundPond", LEFT, "Platform");
        tryAdd("SmallUndergroundPond", LEFT, "SmallUndergroundPond");
        tryAdd("SmallUndergroundPond", LEFT, "UndergroundPondStart");
        tryAdd("SmallUndergroundPond", LEFT, "UndergroundPondMiddle");


        tryAdd("UndergroundPondStart", UP_LEFT, "OpenAir", false);

        tryAdd("UndergroundPondStart", UP_LEFT, "SolidGround", false);
        tryAdd("UndergroundPondStart", UP, "OpenAir", false);

        tryAdd("UndergroundPondStart", UP, "SolidGround", false);
        tryAdd("UndergroundPondStart", UP_RIGHT, "OpenAir", false);

        tryAdd("UndergroundPondStart", UP_RIGHT, "SolidGround", false);
        tryAdd("UndergroundPondStart", RIGHT, "SmallUndergroundPond");

        tryAdd("UndergroundPondStart", RIGHT, "UndergroundPondMiddle");
        tryAdd("UndergroundPondStart", DOWN_RIGHT, "SolidGround", false);
        tryAdd("UndergroundPondStart", DOWN, "SolidGround", false);
        tryAdd("UndergroundPondStart", DOWN_LEFT, "SolidGround", false);

        tryAdd("UndergroundPondStart", LEFT, "UndergroundEntrance");
        tryAdd("UndergroundPondStart", LEFT, "Hollow");
        tryAdd("UndergroundPondStart", LEFT, "CaveHallway");
        tryAdd("UndergroundPondStart", LEFT, "Platform");
        tryAdd("UndergroundPondStart", LEFT, "SmallUndergroundPond");

        tryAdd("UndergroundPondStart", LEFT, "UndergroundPondMiddle");


        tryAdd("UndergroundPondMiddle", UP_LEFT, "OpenAir", false);

        tryAdd("UndergroundPondMiddle", UP_LEFT, "SolidGround", false);
        tryAdd("UndergroundPondMiddle", UP, "OpenAir", false);

        tryAdd("UndergroundPondMiddle", UP, "SolidGround", false);
        tryAdd("UndergroundPondMiddle", UP_RIGHT, "OpenAir", false);

        tryAdd("UndergroundPondMiddle", UP_RIGHT, "SolidGround", false);
        tryAdd("UndergroundPondMiddle", RIGHT, "SmallUndergroundPond");

        tryAdd("UndergroundPondMiddle", RIGHT, "UndergroundPondMiddle");
        tryAdd("UndergroundPondMiddle", DOWN_RIGHT, "SolidGround", false);
        tryAdd("UndergroundPondMiddle", DOWN, "SolidGround", false);
        tryAdd("UndergroundPondMiddle", DOWN_LEFT, "SolidGround", false);
        tryAdd("UndergroundPondMiddle", LEFT, "SmallUndergroundPond");
        tryAdd("UndergroundPondMiddle", LEFT, "UndergroundPondStart");
        tryAdd("UndergroundPondMiddle", LEFT, "UndergroundPondMiddle");
        //endregion

        //region Pits
        tryAdd("SmallPit", UP_LEFT, "OpenAir", false);
        tryAdd("SmallPit", UP, "OpenAir", false);
        tryAdd("SmallPit", UP_RIGHT, "OpenAir", false);


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


        tryAdd("SmallPit", LEFT, "SmallHill");
        tryAdd("SmallPit", LEFT, "BigHill");

        tryAdd("SmallPit", LEFT, "CliffRoof");
        tryAdd("SmallPit", LEFT, "SmallPit");
        tryAdd("SmallPit", LEFT, "PitStart");
        tryAdd("SmallPit", LEFT, "PitMiddle");
        tryAdd("SmallPit", LEFT, "RockColumns");


        tryAdd("PitStart", UP_LEFT, "OpenAir", false);
        tryAdd("PitStart", UP, "OpenAir", false);
        tryAdd("PitStart", UP_RIGHT, "OpenAir", false);

        tryAdd("PitStart", RIGHT, "PitMiddle");
        tryAdd("PitStart", DOWN_RIGHT, "OpenAir", false);
        tryAdd("PitStart", DOWN, "CliffWall");
        tryAdd("PitStart", DOWN, "OpenAir");

        tryAdd("PitStart", DOWN_LEFT, "SolidGround");


        tryAdd("PitStart", LEFT, "SmallHill");
        tryAdd("PitStart", LEFT, "BigHill");
        tryAdd("PitStart", LEFT, "Hilltop");

        tryAdd("PitStart", LEFT, "CliffRoof");
        tryAdd("PitStart", LEFT, "SmallPit");
        tryAdd("PitStart", LEFT, "PitStart");
        tryAdd("PitStart", LEFT, "PitMiddle");
        tryAdd("PitStart", LEFT, "RockColumns");

        tryAdd("PitMiddle", UP_LEFT, "OpenAir", false);
        tryAdd("PitMiddle", UP, "OpenAir", false);
        tryAdd("PitMiddle", UP_RIGHT, "OpenAir", false);

        tryAdd("PitMiddle", RIGHT, "PitMiddle");

        tryAdd("PitMiddle", DOWN_RIGHT, "OpenAir", false);
        tryAdd("PitMiddle", DOWN, "OpenAir");
        tryAdd("PitMiddle", DOWN_LEFT, "OpenAir", false);

        tryAdd("PitMiddle", LEFT, "Drop");
        tryAdd("PitMiddle", LEFT, "PitStart");
        tryAdd("PitMiddle", LEFT, "PitMiddle");
        //endregion

        //region Mountains
        tryAdd("MountainSideA", UP_LEFT, "OpenAir", false);
        tryAdd("MountainSideA", UP, "MountainSideB");
        tryAdd("MountainSideA", UP_RIGHT, "SolidGround", false);
        tryAdd("MountainSideA", RIGHT, "SolidGround", false);
        tryAdd("MountainSideA", DOWN_RIGHT, "SolidGround", false);
        tryAdd("MountainSideA", DOWN, "SolidGround", false);
        tryAdd("MountainSideA", DOWN_LEFT, "SolidGround", false);


        tryAdd("MountainSideA", LEFT, "MountainSideC");
        tryAdd("MountainSideA", LEFT, "MountainTop");



        tryAdd("MountainSideB", UP_LEFT, "OpenAir", false);
        tryAdd("MountainSideB", UP, "MountainSideC");
        tryAdd("MountainSideB", UP_RIGHT, "SolidGround", false);
        tryAdd("MountainSideB", RIGHT, "SolidGround", false);
        tryAdd("MountainSideB", DOWN_RIGHT, "SolidGround", false);
        tryAdd("MountainSideB", DOWN, "MountainSideA");
        tryAdd("MountainSideB", DOWN_LEFT, "SolidGround", false);
        tryAdd("MountainSideB", LEFT, "OpenAir", false);


        tryAdd("MountainSideC", UP_LEFT, "OpenAir", false);
        tryAdd("MountainSideC", UP, "OpenAir");
        tryAdd("MountainSideC", UP_RIGHT, "OpenAir", false);
        tryAdd("MountainSideC", RIGHT, "FrozenPondStart");
        tryAdd("MountainSideC", RIGHT, "MountainTop");
        tryAdd("MountainSideC", RIGHT, "MountainSideA");
        tryAdd("MountainSideC", DOWN_RIGHT, "SolidGround", false);
        tryAdd("MountainSideC", DOWN, "MountainSideB");
        tryAdd("MountainSideC", DOWN_LEFT, "OpenAir", false);
        tryAdd("MountainSideC", LEFT, "OpenAir", false);


        tryAdd("MountainTop", UP_LEFT, "OpenAir", false);
        tryAdd("MountainTop", UP, "OpenAir");
        tryAdd("MountainTop", UP_RIGHT, "OpenAir", false);
        tryAdd("MountainTop", RIGHT, "FrozenPondStart");
        tryAdd("MountainTop", RIGHT, "MountainTop");
        tryAdd("MountainTop", RIGHT, "MountainSideA");
        tryAdd("MountainTop", RIGHT, "TempleEntrance");
        tryAdd("MountainTop", DOWN_RIGHT, "SolidGround", false);
        tryAdd("MountainTop", DOWN, "SolidGround", false);
        tryAdd("MountainTop", DOWN_LEFT, "OpenAir", false);
        tryAdd("MountainTop", DOWN_LEFT, "SolidGround", false);
        tryAdd("MountainTop", LEFT, "MountainTop");

        //endregion

        //region Ponds
        tryAdd("SmallPond", UP_LEFT, "OpenAir", false);
        tryAdd("SmallPond", UP, "OpenAir", false);
        tryAdd("SmallPond", UP_RIGHT, "OpenAir", false);

        tryAdd("SmallPond", RIGHT, "SmallHill");
        tryAdd("SmallPond", RIGHT, "BigHill");

        tryAdd("SmallPond", RIGHT, "Drop");
        tryAdd("SmallPond", RIGHT, "CliffRoof");
        tryAdd("SmallPond", RIGHT, "UndergroundEntrance");
        tryAdd("SmallPond", RIGHT, "SmallPond");

        tryAdd("SmallPond", DOWN_RIGHT, "SolidGround", false);
        tryAdd("SmallPond", DOWN, "SolidGround", false);
        tryAdd("SmallPond", DOWN_LEFT, "SolidGround", false);

        tryAdd("SmallPond", LEFT, "SmallHill");
        tryAdd("SmallPond", LEFT, "BigHill");
        tryAdd("SmallPond", LEFT, "Hilltop");

        tryAdd("SmallPond", LEFT, "CliffRoof");



        tryAdd("PondStart", UP_LEFT, "OpenAir", false);
        tryAdd("PondStart", UP, "OpenAir", false);
        tryAdd("PondStart", UP_RIGHT, "OpenAir", false);

        tryAdd("PondStart", RIGHT, "PondMiddle");
        tryAdd("PondStart", DOWN_RIGHT, "SolidGround", false);
        tryAdd("PondStart", DOWN, "SolidGround", false);
        tryAdd("PondStart", DOWN_LEFT, "SolidGround", false);

        tryAdd("PondStart", LEFT, "SmallHill");

        tryAdd("PondStart", LEFT, "Hilltop");

        tryAdd("PondStart", LEFT, "CliffRoof");

        tryAdd("PondStart", LEFT, "SmallPond");



        tryAdd("PondMiddle", UP_LEFT, "OpenAir", false);
        tryAdd("PondMiddle", UP, "OpenAir", false);
        tryAdd("PondMiddle", UP_RIGHT, "OpenAir", false);

        tryAdd("PondMiddle", RIGHT, "PondMiddle");
        tryAdd("PondMiddle", DOWN_RIGHT, "SolidGround", false);
        tryAdd("PondMiddle", DOWN, "DeepPond");
        tryAdd("PondMiddle", DOWN, "SolidGround");
        tryAdd("PondMiddle", DOWN_LEFT, "SolidGround", false);
        tryAdd("PondMiddle", LEFT, "PondStart");



        tryAdd("DeepPond", UP_LEFT, "PondMiddle", false);
        tryAdd("DeepPond", UP, "PondMiddle");
        tryAdd("DeepPond", UP_RIGHT, "PondMiddle", false);
        tryAdd("DeepPond", RIGHT, "SolidGround", false);
        tryAdd("DeepPond", DOWN_RIGHT, "SolidGround", false);
        tryAdd("DeepPond", DOWN, "SolidGround");
        tryAdd("DeepPond", DOWN_LEFT, "SolidGround", false);
        tryAdd("DeepPond", LEFT, "SolidGround", false);

        //endregion

        //region Frozen Ponds
        tryAdd("FrozenPondStart", UP_LEFT, "OpenAir", false);
        tryAdd("FrozenPondStart", UP, "OpenAir", false);
        tryAdd("FrozenPondStart", UP_RIGHT, "OpenAir", false);
        tryAdd("FrozenPondStart", RIGHT, "FrozenPondStart", true);
        tryAdd("FrozenPondStart", RIGHT, "FrozenPondMiddle");
        tryAdd("FrozenPondStart", DOWN_RIGHT, "SolidGround", false);
        tryAdd("FrozenPondStart", DOWN, "SolidGround", false);
        tryAdd("FrozenPondStart", DOWN_LEFT, "SolidGround", false);

        tryAdd("FrozenPondStart", LEFT, "MountainSideC");
        tryAdd("FrozenPondStart", LEFT, "MountainTop");


        tryAdd("FrozenPondMiddle", UP_LEFT, "OpenAir", false);
        tryAdd("FrozenPondMiddle", UP, "OpenAir", false);
        tryAdd("FrozenPondMiddle", UP_RIGHT, "OpenAir", false);

        tryAdd("FrozenPondMiddle", DOWN_RIGHT, "SolidGround", false);
        tryAdd("FrozenPondMiddle", DOWN, "SolidGround", false);
        tryAdd("FrozenPondMiddle", DOWN_LEFT, "SolidGround", false);
        tryAdd("FrozenPondMiddle", LEFT, "FrozenPondStart");
        tryAdd("FrozenPondMiddle", LEFT, "FrozenPondMiddle", true);

        //endregion

        //region Rivers
        tryAdd("OpenRiver", UP_LEFT, "OpenAir", false);
        tryAdd("OpenRiver", UP, "OpenAir", false);
        tryAdd("OpenRiver", UP_RIGHT, "OpenAir", false);

        tryAdd("OpenRiver", DOWN_RIGHT, "RiverBase", false);
        tryAdd("OpenRiver", DOWN_RIGHT, "RiverWall", false);
        tryAdd("OpenRiver", DOWN, "RiverBase");
        tryAdd("OpenRiver", DOWN_LEFT, "RiverBase", false);
        tryAdd("OpenRiver", DOWN_LEFT, "RiverWall", false);
        tryAdd("OpenRiver", LEFT, "OpenRiver");



        tryAdd("RiverStart", UP_LEFT, "OpenAir", false);
        tryAdd("RiverStart", UP, "OpenAir", false);
        tryAdd("RiverStart", UP_RIGHT, "OpenAir", false);
        tryAdd("RiverStart", RIGHT, "OpenRiver");

        tryAdd("RiverStart", DOWN_RIGHT, "RiverBase", false);
        tryAdd("RiverStart", DOWN_RIGHT, "RiverWall", false);
        tryAdd("RiverStart", DOWN, "RiverBase");
        tryAdd("RiverStart", DOWN_LEFT, "RiverBase", false);
        tryAdd("RiverStart", DOWN_LEFT, "RiverWall", false);
        tryAdd("RiverStart", LEFT, "OpenRiver");


        tryAdd("RiverBase", UP_LEFT, "OpenRiver", false);
        tryAdd("RiverBase", UP_LEFT, "RiverStart", false);
        tryAdd("RiverBase", UP, "OpenRiver", false);
        tryAdd("RiverBase", UP_RIGHT, "OpenRiver", false);
        tryAdd("RiverBase", UP_RIGHT, "RiverStart", false);
        tryAdd("RiverBase", RIGHT, "RiverBase", false);
        tryAdd("RiverBase", RIGHT, "RiverWall", false);
        tryAdd("RiverBase", DOWN_RIGHT, "SolidGround", false);
        tryAdd("RiverBase", DOWN, "SolidGround", false);
        tryAdd("RiverBase", DOWN_LEFT, "SolidGround", false);
        tryAdd("RiverBase", LEFT, "RiverBase", false);
        tryAdd("RiverBase", LEFT, "RiverWall", false);


        tryAdd("RiverWall", UP_LEFT, "SolidGround", false);
        tryAdd("RiverWall", UP, "RiverStart", false);
        tryAdd("RiverWall", UP_RIGHT, "OpenRiver", false);
        tryAdd("RiverWall", RIGHT, "RiverBase", false);
        tryAdd("RiverWall", DOWN_RIGHT, "SolidGround", false);
        tryAdd("RiverWall", DOWN, "SolidGround", false);
        tryAdd("RiverWall", DOWN_LEFT, "SolidGround", false);
        tryAdd("RiverWall", LEFT, "SolidGround", false);

        //endregion

        //region Salt Flats
        tryAdd("Flat", UP_LEFT, "OpenAir", false);
        tryAdd("Flat", UP, "OpenAir", false);
        tryAdd("Flat", UP_RIGHT, "OpenAir", false);

        tryAdd("Flat", RIGHT, "Drop");

        tryAdd("Flat", RIGHT, "UndergroundEntrance");
        tryAdd("Flat", RIGHT, "SmallPit");
        tryAdd("Flat", RIGHT, "PitStart");
        tryAdd("Flat", RIGHT, "Flat");
        tryAdd("Flat", RIGHT, "Pool");
        tryAdd("Flat", RIGHT, "RockColumns");
        tryAdd("Flat", RIGHT, "TempleEntrance");

        tryAdd("Flat", DOWN_RIGHT, "SolidGround", false);
        tryAdd("Flat", DOWN, "SolidGround", false);
        tryAdd("Flat", DOWN_LEFT, "SolidGround", false);


        tryAdd("Flat", LEFT, "CliffRoof");

        tryAdd("Flat", LEFT, "SmallPit");

        tryAdd("Flat", LEFT, "Flat");
        tryAdd("Flat", LEFT, "Pool");
        tryAdd("Flat", LEFT, "RockColumns");



        tryAdd("Pool", UP_LEFT, "OpenAir");
        tryAdd("Pool", UP, "OpenAir");
        tryAdd("Pool", UP_RIGHT, "OpenAir");

        tryAdd("Pool", RIGHT, "Drop");

        tryAdd("Pool", RIGHT, "UndergroundEntrance");

        tryAdd("Pool", RIGHT, "PitStart");
        tryAdd("Pool", RIGHT, "Pool");
        tryAdd("Pool", RIGHT, "RockColumns");
        tryAdd("Pool", RIGHT, "TempleEntrance");

        tryAdd("Pool", DOWN_RIGHT, "SolidGround", false);
        tryAdd("Pool", DOWN, "SolidGround", false);
        tryAdd("Pool", DOWN_LEFT, "SolidGround", false);


        tryAdd("Pool", LEFT, "CliffRoof");

        tryAdd("Pool", LEFT, "SmallPit");

        tryAdd("Pool", LEFT, "Pool");
        tryAdd("Pool", LEFT, "RockColumns");



        tryAdd("RockColumns", UP_LEFT, "OpenAir", false);
        tryAdd("RockColumns", UP, "OpenAir", false);
        tryAdd("RockColumns", UP_RIGHT, "OpenAir", false);

        tryAdd("RockColumns", RIGHT, "Drop");
        tryAdd("RockColumns", RIGHT, "CliffRoof");
        tryAdd("RockColumns", RIGHT, "UndergroundEntrance");
        tryAdd("RockColumns", RIGHT, "SmallPit");
        tryAdd("RockColumns", RIGHT, "PitStart");
        tryAdd("RockColumns", RIGHT, "Flat");
        tryAdd("RockColumns", RIGHT, "Pool");
        tryAdd("RockColumns", RIGHT, "RockColumns");
        tryAdd("RockColumns", RIGHT, "TempleEntrance");

        tryAdd("RockColumns", DOWN_RIGHT, "SolidGround", false);
        tryAdd("RockColumns", DOWN, "SolidGround", false);
        tryAdd("RockColumns", DOWN_LEFT, "SolidGround", false);


        tryAdd("RockColumns", LEFT, "CliffRoof");

        tryAdd("RockColumns", LEFT, "SmallPit");

        tryAdd("RockColumns", LEFT, "Flat");
        tryAdd("RockColumns", LEFT, "Pool");
        tryAdd("RockColumns", LEFT, "RockColumns");


        //endregion

        //region Temples
        tryAdd("TempleEntrance", UP_LEFT, "OpenAir", false);
        tryAdd("TempleEntrance", UP, "OpenAir", false);
        tryAdd("TempleEntrance", UP, "TempleWall");
        tryAdd("TempleEntrance", UP_RIGHT, "OpenAir", false);
        tryAdd("TempleEntrance", RIGHT, "TempleHallway");
        tryAdd("TempleEntrance", RIGHT, "Staircase");
        tryAdd("TempleEntrance", RIGHT, "Altar");
        tryAdd("TempleEntrance", RIGHT, "OpenAir", false);
        tryAdd("TempleEntrance", DOWN_RIGHT, "Staircase", true);
        tryAdd("TempleEntrance", DOWN_RIGHT, "SolidGround", false);
        tryAdd("TempleEntrance", DOWN, "SolidGround", false);
        tryAdd("TempleEntrance", DOWN_LEFT, "SolidGround", false);

        tryAdd("TempleEntrance", LEFT, "Hilltop");
        tryAdd("TempleEntrance", LEFT, "Drop");

        tryAdd("TempleEntrance", LEFT, "MountainTop");
        tryAdd("TempleEntrance", LEFT, "Flat");
        tryAdd("TempleEntrance", LEFT, "Pool");
        tryAdd("TempleEntrance", LEFT, "RockColumns");


        tryAdd("TempleHallway", UP_LEFT, "OpenAir", false);
        tryAdd("TempleHallway", UP_LEFT, "TempleWall", false);
        tryAdd("TempleHallway", UP, "OpenAir", false);
        tryAdd("TempleHallway", UP, "TempleWall");
        tryAdd("TempleHallway", UP, "TempleRoof");
        tryAdd("TempleHallway", UP, "Trial");
        tryAdd("TempleHallway", UP_RIGHT, "OpenAir", false);


        tryAdd("TempleHallway", RIGHT, "TempleHallway");
        tryAdd("TempleHallway", RIGHT, "Staircase");
        tryAdd("TempleHallway", RIGHT, "Altar");
        tryAdd("TempleHallway", RIGHT, "OpenAir", false);
        tryAdd("TempleHallway", RIGHT, "TempleWall", false);


        tryAdd("TempleHallway", DOWN_RIGHT, "SolidGround", false);
        tryAdd("TempleHallway", DOWN, "SolidGround", false);
        tryAdd("TempleHallway", DOWN_LEFT, "SolidGround", false);


        tryAdd("TempleHallway", LEFT, "TempleEntrance");
        tryAdd("TempleHallway", LEFT, "TempleHallway");

        tryAdd("TempleHallway", LEFT, "Altar");
        tryAdd("TempleHallway", LEFT, "Trial");
        tryAdd("TempleHallway", LEFT, "OpenAir", false);
        tryAdd("TempleHallway", LEFT, "TempleWall", false);


        tryAdd("Staircase", UP_LEFT, "OpenAir", false);
        tryAdd("Staircase", UP_LEFT, "TempleWall", false);
        tryAdd("Staircase", UP, "OpenAir", false);
        tryAdd("Staircase", UP, "TempleWall");
        tryAdd("Staircase", UP, "TempleRoof");
        tryAdd("Staircase", UP, "Trial");

        tryAdd("Staircase", UP_RIGHT, "OpenAir", false);
        tryAdd("Staircase", UP_RIGHT, "TempleEntrance", true);
        tryAdd("Staircase", UP_RIGHT, "TempleHallway");
        tryAdd("Staircase", UP_RIGHT, "Staircase");
        tryAdd("Staircase", UP_RIGHT, "Altar");

        tryAdd("Staircase", RIGHT, "TempleHallway");

        tryAdd("Staircase", RIGHT, "Trial");
        tryAdd("Staircase", RIGHT, "OpenAir", false);
        tryAdd("Staircase", RIGHT, "TempleWall", false);

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

        tryAdd("Camp", RIGHT, "SmallHill", false);
        tryAdd("Camp", RIGHT, "BigHill", false);
        tryAdd("Camp", RIGHT, "Hilltop", true);
        tryAdd("Camp", RIGHT, "UndergroundEntrance", false);
        tryAdd("Camp", RIGHT, "Hollow", true);
        tryAdd("Camp", RIGHT, "CaveHallway", false);
        tryAdd("Camp", RIGHT, "Platform", false);
        tryAdd("Camp", RIGHT, "UndergroundPondStart", false);
        tryAdd("Camp", RIGHT, "MountainSideA", false);
        tryAdd("Camp", RIGHT, "MountainTop", false);
        tryAdd("Camp", RIGHT, "SmallPond", false);
        tryAdd("Camp", RIGHT, "PondStart", false);
        tryAdd("Camp", RIGHT, "Flat", false);
        tryAdd("Camp", RIGHT, "Pool", false);
        tryAdd("Camp", RIGHT, "RockColumns", false);
        tryAdd("Camp", RIGHT, "TempleEntrance", false);
        tryAdd("Camp", RIGHT, "TempleHallway", false);
        tryAdd("Camp", RIGHT, "Staircase", false);

        tryAdd("Camp", DOWN_RIGHT, "BigHill", true);
        tryAdd("Camp", DOWN_RIGHT, "CliffRoof" ,false);
        tryAdd("Camp", DOWN_RIGHT, "Platform", true);
        tryAdd("Camp", DOWN_RIGHT, "SmallUndergroundPond", true);
        tryAdd("Camp", DOWN_RIGHT, "SmallPit", false);
        tryAdd("Camp", DOWN_RIGHT, "RockColumns", false);
        tryAdd("Camp", DOWN_RIGHT, "Staircase", true);
        tryAdd("Camp", DOWN_RIGHT, "SolidGround", false);

        tryAdd("Camp", DOWN_LEFT, "SolidGround");
        tryAdd("Camp", LEFT, "SolidGround");


        tryAdd("Source", UP_RIGHT, "SolidGround");
        tryAdd("Source", RIGHT, "SolidGround");
        tryAdd("Source", DOWN_RIGHT, "SolidGround");

        tryAdd("Source", DOWN_LEFT, "BigHill");
        tryAdd("Source", DOWN_LEFT, "CliffRoof", true);
        tryAdd("Source", DOWN_LEFT, "Platform");
        tryAdd("Source", DOWN_LEFT, "SmallUndergroundPond");
        tryAdd("Source", DOWN_LEFT, "SmallPit", true);
        tryAdd("Source", DOWN_LEFT, "RockColumns", true);
        tryAdd("Source", DOWN_LEFT, "Staircase");
        tryAdd("Source", DOWN_LEFT, "SolidGround", false);

        tryAdd("Source", LEFT, "SmallHill", true);
        tryAdd("Source", LEFT, "BigHill", true);
        tryAdd("Source", LEFT, "Hilltop");
        tryAdd("Source", LEFT, "UndergroundEntrance", true);
        tryAdd("Source", LEFT, "Hollow");
        tryAdd("Source", LEFT, "CaveHallway");
        tryAdd("Source", LEFT, "Platform");
        tryAdd("Source", LEFT, "UndergroundPondStart", true);
        tryAdd("Source", LEFT, "MountainSideA", true);
        tryAdd("Source", LEFT, "MountainTop");
        tryAdd("Source", LEFT, "SmallPond");
        tryAdd("Source", LEFT, "PondStart", true);
        tryAdd("Source", LEFT, "Flat", true);
        tryAdd("Source", LEFT, "Pool");
        tryAdd("Source", LEFT, "RockColumns");
        tryAdd("Source", LEFT, "TempleEntrance");
        tryAdd("Source", LEFT, "TempleHallway");
        tryAdd("Source", LEFT, "Staircase", true);
        //endregion
        tryAdd("Hollow", LEFT, "SmallUndergroundPond", true);
        tryAdd("Hollow", LEFT, "UndergroundPondStart", true);
        tryAdd("CaveHallway", RIGHT, "UndergroundEntrance", true);
        tryAdd("CaveHallway", DOWN_RIGHT, "Platform", true);
        tryAdd("CaveHallway", LEFT, "UndergroundPondStart", true);
        tryAdd("Platform", UP_RIGHT, "UndergroundEntrance", true);
        tryAdd("Platform", RIGHT, "UndergroundEntrance", true);
        tryAdd("Platform", DOWN_RIGHT, "Platform", true);
        tryAdd("Platform", LEFT, "UndergroundPondStart", true);
        tryAdd("SmallUndergroundPond", UP_RIGHT, "UndergroundEntrance", true);
        tryAdd("SmallUndergroundPond", UP_RIGHT, "Platform", true);
        tryAdd("UndergroundPondStart", UP_LEFT, "CaveWall", true);
        tryAdd("UndergroundPondStart", UP, "CaveWall", true);
        tryAdd("UndergroundPondStart", UP_RIGHT, "CaveWall", true);
        tryAdd("UndergroundPondStart", RIGHT, "UndergroundPondStart", true);
        tryAdd("UndergroundPondStart", LEFT, "UndergroundPondStart", true);
        tryAdd("UndergroundPondMiddle", UP_LEFT, "CaveWall", true);
        tryAdd("UndergroundPondMiddle", UP, "CaveWall", true);
        tryAdd("UndergroundPondMiddle", UP_RIGHT, "CaveWall", true);
        tryAdd("UndergroundPondMiddle", RIGHT, "UndergroundPondStart", true);
        tryAdd("SmallPit", RIGHT, "SmallHill", true);
        tryAdd("SmallPit", RIGHT, "BigHill", true);
        tryAdd("SmallPit", RIGHT, "Hilltop",true);
        tryAdd("SmallPit", DOWN_LEFT, "CliffWall", true);
        tryAdd("SmallPit", LEFT, "Drop", true);
        tryAdd("PitStart", RIGHT, "SmallHill", true);
        tryAdd("PitStart", RIGHT, "BigHill", true);
        tryAdd("PitStart", RIGHT, "PitStart", true);
        tryAdd("PitStart", LEFT, "Drop", true);
        tryAdd("PitStart", DOWN_LEFT, "CliffWall", true);
        tryAdd("PitMiddle", RIGHT, "Drop", true);
        tryAdd("PitMiddle", RIGHT, "PitStart", true);
        tryAdd("MountainSideA", LEFT, "UndergroundEntrance", true);
        tryAdd("MountainSideA", LEFT, "FrozenPondStart", true);
        tryAdd("MountainSideA", LEFT, "TempleEntrance", true);
        tryAdd("MountainTop", LEFT, "MountainSideA", true);
        tryAdd("MountainTop", LEFT, "TempleEntrance", true);
        tryAdd("SmallPond", RIGHT, "Hilltop", true);
        tryAdd("SmallPond", LEFT, "UndergroundEntrance", true);
        tryAdd("SmallPond", LEFT, "SmallPond", true);
        tryAdd("SmallPond", LEFT, "PondStart", true);
        tryAdd("SmallPond", LEFT, "Drop", true);
        tryAdd("PondStart", RIGHT, "SmallPond" ,true);
        tryAdd("PondStart", RIGHT, "PondStart", true);
        tryAdd("PondStart", LEFT, "BigHill", true);
        tryAdd("PondStart", LEFT, "Drop", true);
        tryAdd("PondStart", LEFT, "UndergroundEntrance", true);
        tryAdd("PondStart", LEFT, "PondStart", true);
        tryAdd("PondMiddle", RIGHT, "PondStart", true);
        tryAdd("PondMiddle", LEFT, "PondMiddle", true);
        tryAdd("FrozenPondStart", LEFT, "MountainSideA", true);
        tryAdd("FrozenPondMiddle", RIGHT, "FrozenPondStart", true);
        tryAdd("FrozenPondMiddle", RIGHT, "FrozenPondMiddle", true);
        tryAdd("OpenRiver", RIGHT, "OpenRiver", true);
        tryAdd("OpenRiver", RIGHT, "RiverStart", true);
        tryAdd("OpenRiver", LEFT, "RiverStart", true);
        tryAdd("RiverStart", RIGHT, "RiverStart", true);
        tryAdd("Flat", RIGHT, "CliffRoof", true);
        tryAdd("Flat", LEFT, "Drop", true);
        tryAdd("Flat", LEFT, "UndergroundEntrance", true);
        tryAdd("Flat", LEFT, "PitStart", true);
        tryAdd("Flat", LEFT, "TempleEntrance", true);
        tryAdd("Pool", RIGHT, "CliffRoof", true);
        tryAdd("Pool", RIGHT, "SmallPit", true);
        tryAdd("Pool", LEFT, "Drop", true);
        tryAdd("Pool", LEFT, "UndergroundEntrance", true);
        tryAdd("Pool", LEFT, "PitStart", true);
        tryAdd("Pool", LEFT, "TempleEntrance", true);
        tryAdd("RockColumns", LEFT, "Drop", true);
        tryAdd("RockColumns", LEFT, "UndergroundEntrance", true);
        tryAdd("RockColumns", LEFT, "PitStart", true);
        tryAdd("RockColumns", LEFT, "TempleEntrance", true);
        tryAdd("TempleEntrance", DOWN_LEFT, "CliffRoof", true);

        tryAdd("TempleEntrance", LEFT, "SmallHill", true);
        tryAdd("TempleEntrance", LEFT, "BigHill", true);
        tryAdd("TempleEntrance", LEFT, "UndergroundEntrance", true);
        tryAdd("TempleEntrance", LEFT, "TempleEntrance", true);
        tryAdd("TempleHallway", RIGHT, "TempleEntrance", true);
        tryAdd("TempleHallway", DOWN_RIGHT, "Staircase", true);
        tryAdd("TempleHallway", DOWN_LEFT, "CliffRoof", true);
        tryAdd("TempleHallway", LEFT, "Staircase", true);
        tryAdd("Staircase", RIGHT, "Staircase", true);
        for(String key: roomLoader.getRooms().keySet()) {
            if(key.contains("Flipped")) {
                Room temp = roomLoader.getRoom(key.replace("Flipped", "")).flip();
                Room fix = roomLoader.getRoom(key);
                fix.weakNeighbors = temp.weakNeighbors;
                fix.strongNeighbors = temp.strongNeighbors;
                fix.flipped = true;
            }
        }
    }
}
