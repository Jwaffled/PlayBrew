package com.waffle.dredes.gameobject.player;

public class Jumping extends PlayerState{
    public float height;
    public float airAccel;
    public float termV;

    public Jumping(float height, float airAccel, float termV, int time)
    {
        super(200,200,0, time);
        this.height = height;
        this.airAccel = airAccel;
        this.termV = termV;
    }

    public void apply(Player p)
    {
        p.kinematics.v.y = (((height * (record - counter) * 2)/(record * record)) - ((2 * height)/record))/(1f/60f);
        p.kinematics.v.x += p.inputD.x * airAccel;
        if(Math.abs(p.kinematics.v.x) > termV )
        {
            p.kinematics.v.x = p.kinematics.v.x < 0? -termV : termV;
        }
        update();
    }
}
