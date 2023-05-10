package com.waffle.dredes.gameobject.player;

import java.util.LinkedList;
import java.util.Queue;

public class Holster {

    public Queue<Bullet> bullets;

    public Holster()
    {
        bullets = new LinkedList<Bullet>();
    }
}
