package com.waffle.dredes.gameobject.player;

public class Walking extends PlayerState{
    public float walkSpeed;
    public Walking(float walkSpeed)
    {
        super(50,50,200);
        this.walkSpeed = walkSpeed;
    }

    public void apply(Player p)
    {
        p.kinematics.v.x = p.faceLeft? -walkSpeed : walkSpeed;
        p.kinematics.v.y = 0;
        update();
    }
}