package com.waffle;

import com.waffle.dredes.MainGame;

/**
 * Main class containing main method
 */
public class Main {

    /**
     * Main method/entry point of program
     * @param args not used
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        new MainGame();
    }
}
