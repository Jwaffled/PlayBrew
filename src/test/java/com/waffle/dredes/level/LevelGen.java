package com.waffle.dredes.level;

import com.waffle.core.Utils;
import com.waffle.struct.Vec2f;

import java.awt.image.BufferedImage;
import java.util.HashSet;

public class LevelGen {
    public static LevelGen INSTANCE;

    public enum Biome
    {
        Grassland,
        Redland,
        Sandland,
        Saltland,
        Stoneland
    }

    public LevelGen()
    {
        INSTANCE = this;
    }

    public Tile[][] generate(Biome biome, int x, int y, boolean river)
    {
        Tile[] tiles = new Tile[7];
        RoomLoader rl = new RoomLoader();
        Tile[][] level = new Tile[6][10];
        loadBiome(rl, biome, tiles);
        if(river)
        {
            rl.addDirectory("DreDes/Rooms/River");
        }
        return null;
    }


    public void loadBiome(RoomLoader rl, Biome b, Tile[] tiles)
    {
        tiles[0] = null;
        tiles[3] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Water"), -1, -1, true, true, 1f, new Vec2f(1.5f, 2f));
        if(b.equals(Biome.Grassland))
        {
            loadGrass(rl);
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Grass"), -1, -1, false, false);
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Dirt"), -1, -1, false, false);
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Gravel"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Bricks"), -1, -1, false, false);
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Tiles"), -1, -2, false, false);
        }
        else if(b.equals(Biome.Redland))
        {
            loadRed(rl);
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Clay"), -1, -1, false, false);
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Gravel"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Sand"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.1f));
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Bricks"), -1, -1, false, false);
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Tiles"), -1, -2, false, false);
        }
        else if(b.equals(Biome.Sandland))
        {
            loadSand(rl);
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Sand"), -1, -1, false, false, 1.25f, new Vec2f(1,1.1f));
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Clay"), -1, -1, false, false);
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Gravel"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Bricks"), -1, -1, false, false);
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Tiles"), -1, -2, false, false);
        }
        else if(b.equals(Biome.Saltland))
        {
            loadSalt(rl);
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Salt"), -1, -1, false, false);
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Rock"), -1, -1, false, false);
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Salt"), -1, -1, false, false);
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Dirt"), -1, -1, false, false);
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Ice"), -1, -2, false, false, .5f, new Vec2f(1,1));
        }
        else if(b.equals(Biome.Stoneland))
        {
            loadStone(rl);
            tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Rock"), -1, -1, false, false);
            tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Rock"), -1, -1, false, false);
            tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Salt"), -1, -1, false, false);
            tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Snow"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
            tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Ice"), -1, -2, false, false, .5f, new Vec2f(1,1));
        }
    }
    public void loadGrass(RoomLoader roomLoader)
    {
        roomLoader.addDirectory("DreDes/Rooms/Hills");
        roomLoader.addDirectory("DreDes/Rooms/Caves");
        roomLoader.addDirectory("DreDes/Rooms/Pond");
    }

    public void loadRed(RoomLoader roomLoader)
    {
        roomLoader.addDirectory("DreDes/Rooms/Hills");
        roomLoader.addDirectory("DreDes/Rooms/Cliff");
        roomLoader.addDirectory("DreDes/Rooms/Caves");
        roomLoader.addDirectory("DreDes/Rooms/Pond");
    }

    public void loadSand(RoomLoader roomLoader)
    {
        roomLoader.addDirectory("DreDes/Rooms/Hills");
        roomLoader.addDirectory("DreDes/Rooms/Caves");
        roomLoader.addDirectory("DreDes/Rooms/Pit");
        roomLoader.addDirectory("DreDes/Rooms/Temple");
    }

    public void loadSalt(RoomLoader roomLoader)
    {
        roomLoader.addDirectory("DreDes/Rooms/Cliff");
        roomLoader.addDirectory("DreDes/Rooms/Caves");
        roomLoader.addDirectory("DreDes/Rooms/Pit");
        roomLoader.addDirectory("DreDes/Rooms/SaltFlats");
        roomLoader.addDirectory("DreDes/Rooms/Temple");
    }

    public void loadStone(RoomLoader roomLoader)
    {
        roomLoader.addDirectory("DreDes/Rooms/Caves");
        roomLoader.addDirectory("DreDes/Rooms/Mountain");
        roomLoader.addDirectory("DreDes/Rooms/FrozenPond");
        roomLoader.addDirectory("DreDes/Rooms/Temple");
    }
    public void tryAdd(Room center, Room.Direction direction, Room neighbor)
    {
        if(center != null && neighbor != null)
        {
            center.addNeighbor(direction, neighbor);
        }
    }

    public void addAllNeighbors(RoomLoader rl)
    {
        //tryAdd(room, direction, neighbor) for all the rooms and their possible neighbors

    }
}
