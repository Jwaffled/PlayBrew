package com.waffle.dredes.gameobject.player;

import java.util.LinkedList;
import java.util.Queue;

public class Chamber {

    public Queue<Bullet> bullets;

    public Chamber()
    {
        bullets = new LinkedList<Bullet>();
    }
}
