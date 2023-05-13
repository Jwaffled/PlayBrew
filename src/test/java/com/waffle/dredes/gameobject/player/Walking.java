package com.waffle.dredes.gameobject.player;

import com.waffle.ecs.GameObject;

public class Walking extends State {
    public float walkSpeed;
    public Walking(float walkSpeed)
    {
        super(50,50,200);
        this.walkSpeed = walkSpeed;
    }

    public void apply(GameObject gamob)
    {
        if(gamob instanceof Player) {
            Player p = (Player) gamob;
            p.kinematics.v.x = p.faceLeft ? -walkSpeed : walkSpeed;
            p.kinematics.v.y = 0;
        }
        update();
    }
}