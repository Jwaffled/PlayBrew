package com.waffle.dredes.gameobject.player;

import com.waffle.ecs.GameObject;

public class Turning extends State {
    int friction;
    public Turning(int frames, int friction)
    {
        super(150,150,200,frames);
    }

    public void apply(GameObject gamob)
    {
        if(gamob instanceof Player) {
            Player p = (Player) gamob;
            p.kinematics.v.x *= (1 - (float) friction / 100);
            p.kinematics.v.y = 0;
        }
        update();
    }
}