package com.waffle;

import com.waffle.core.Constants;
import com.waffle.dredes.MainGame;
import com.waffle.dredes.room.RoomLoader;

public class Main {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        new MainGame();
    }
}
