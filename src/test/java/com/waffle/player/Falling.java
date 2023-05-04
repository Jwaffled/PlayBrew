package com.waffle.player;

public class Falling extends PlayerState{
    float fallSpeed;
    float termV;

    public Falling(float terminalVelocity)
    {
        super(30,30,30);
    }

    void apply(Player p)
    {
        if(p.physics.v.y > termV)
        {
            p.physics.v.y = termV;
        }
    }
}
