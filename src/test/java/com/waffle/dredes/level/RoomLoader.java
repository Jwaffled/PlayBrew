package com.waffle.dredes.level;

import com.waffle.core.Constants;
import com.waffle.core.Utils;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class RoomLoader {
    private final Map<String, Room> rooms;

    public RoomLoader(Iterable<String> filePaths) {
        rooms = new HashMap<>();
        for(String path : filePaths) {
            addRoomPath(path);
        }
    }

    public RoomLoader(String... filePaths) {
        rooms = new HashMap<>();
        for(String path : filePaths) {
            addRoomPath(path);
        }
    }

    public RoomLoader() {
        rooms = new HashMap<>();
    }

    public void addDirectory(String dir) {
        for(String path : Utils.getFilesInDirectory(dir)) {
            addRoomPath(dir + "/" + path);
        }
    }

    public void addRoomPath(String path) {
        addRoomData(Utils.loadFileAsString(path));
    }

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
        rooms.put(name, room);
    }

    public Room getRoom(String name) {
        return rooms.get(name);
    }

    public String toString() {
        return rooms.toString();
    }
}
