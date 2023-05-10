package com.waffle.dredes.gameobject.player;

public class Falling extends PlayerState{
    float grav;
    float termVY;
    public float airAccel;
    public float termVX;

    public Falling(float terminalVelocityY, float terminalVelocityX, float gravity, float airAccel)
    {
        super(30,30,30);
        termVY = terminalVelocityY;
        grav = gravity;
        termVX = terminalVelocityX;
        this.airAccel = airAccel;
    }

    void apply(Player p)
    {
        p.kinematics.v.y += grav;
        if(p.kinematics.v.y > termVY)
        {
            p.kinematics.v.y = termVY;
        }
        p.kinematics.v.x += p.inputD.x * airAccel;
        if(Math.abs(p.kinematics.v.x) > termVX )
        {
            p.kinematics.v.x = p.kinematics.v.x < 0? -termVX : termVX;
        }
        update();
    }
}
