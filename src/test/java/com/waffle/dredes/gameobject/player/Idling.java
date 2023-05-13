package com.waffle.dredes.gameobject.player;

import com.waffle.ecs.GameObject;

public class Idling extends State {
    public int traction;
    public int baseTraction;
    public Idling(int traction)
    {
        super(100,100,200);
        if(traction > 100)
        {
            throw new IllegalArgumentException("Traction " + traction + " is  greater than 100");
        }
        if(traction < 0)
        {
            throw new IllegalArgumentException("Traction " + traction + " is negative");
        }
        this.traction = traction;
        baseTraction = traction;
    }

    public void apply(GameObject gamob)
    {
        if(gamob instanceof Player) {
            Player p = (Player) gamob;
            p.kinematics.v.x *= (1 - (float) traction / 100);
            p.kinematics.v.y = 0;
        }
        update();
    }

}