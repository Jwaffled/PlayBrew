package com.waffle.dredes.level;

import com.waffle.core.Utils;

import java.util.*;

/**
 * A manager class for loading "rooms" (8 by 6 tilemaps used for WFC)<br>
 * <b>ALL</b> data files are searched for inside of the <code>resources</code> folder
 * <pre>{@code
 * // Room.txt
 * RoomName
 * data
 * }</pre>
 */
public class RoomLoader {
    private final Map<String, Room> rooms;

    /**
     * Constructs a RoomLoader object and automatically loads the files
     * @param filePaths the paths to each data file
     */
    public RoomLoader(Iterable<String> filePaths) {
        rooms = new HashMap<>();
        for(String path : filePaths) {
            addRoomPath(path);
        }
    }

    /**
     * Constructs a RoomLoader object and automatically loads the files
     * @param filePaths the paths to each data file
     */
    public RoomLoader(String... filePaths) {
        rooms = new HashMap<>();
        for(String path : filePaths) {
            addRoomPath(path);
        }
    }

    /**
     * Constructs an empty RoomLoader with no rooms loaded
     */
    public RoomLoader() {
        rooms = new HashMap<>();
    }

    /**
     * Adds a directory to the loader and loads all non-directory paths within it
     * @param dir the path to the folder to add
     */
    public void addDirectory(String dir) {
        for(String path : Utils.getFilesInDirectory(dir)) {
            addRoomPath(dir + "/" + path);
        }
    }

    /**
     * Adds a path to the loader and loads the file associated with it
     * @param path the path to the data file
     */
    public void addRoomPath(String path) {
        addRoomData(Utils.loadFileAsString(path));
    }

    /**
     * Parses a room data file and adds it to the loader
     * @param data the raw String representation of the data
     */
    public void addRoomData(String data) {
        String[] lines = data.split("\n");
        String name = lines[0].trim();
        List<List<Integer>> config = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            ArrayList<Integer> toAdd = new ArrayList<>();
            Arrays.stream(lines[i].split(" ")).map(String::trim).mapToInt(Integer::parseInt).forEach(toAdd::add);
            config.add(toAdd);
        }

        Room room = new Room();
        int[][] arr = new int[config.size()][config.get(0).size()];
        for(int i = 0; i < config.size(); i++) {
            for(int j = 0; j < config.get(i).size(); j++) {
                arr[i][j] = config.get(i).get(j);
            }
        }

        room.addConfig(arr);
        room.name = name;
        Room flipped = room.flip();
        flipped.name = name + "Flipped";
        rooms.put(name, room);
        rooms.put(name + "Flipped", flipped);
    }

    /**
     * Returns a room loaded by this loader with a specified name
     * @param name the name of the room
     * @return a room loaded by this loader with a specified name
     */
    public Room getRoom(String name) {
        return rooms.get(name);
    }

    /**
     * Returns a Hashmap of all added rooms
     * @return a map of all the rooms
     */
    public Map<String, Room> getRooms() {
        return rooms;
    }


    /**
     * Returns a string representation of this RoomLoader
     * @return a string representation of this RoomLoader
     */
    public String toString() {
        return rooms.toString();
    }
}
