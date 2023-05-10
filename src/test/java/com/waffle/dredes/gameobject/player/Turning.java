package com.waffle.dredes.gameobject.player;

public class Turning extends PlayerState{
    int friction;
    public Turning(int frames, int friction)
    {
        super(150,150,200,frames);
    }

    public void apply(Player p)
    {
        p.kinematics.v.x *= (1 -(float)friction/100);
        p.kinematics.v.y = 0;
        update();
    }
}