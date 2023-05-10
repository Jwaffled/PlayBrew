package com.waffle;

import com.waffle.dredes.MainGame;

public class Main {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        new MainGame();
    }
}
