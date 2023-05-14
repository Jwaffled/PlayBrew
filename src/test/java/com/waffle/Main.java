package com.waffle;

import com.waffle.dredes.MainGame;
import com.waffle.dredes.level.*;

public class Main {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        //new MainGame();

        LevelGen gen = new LevelGen();
        gen.generate(LevelGen.Biome.Redland, 10, 10, true);
        gen.addAllNeighbors();
    }
}
