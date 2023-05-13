package com.waffle.dredes.gameobject.player;

import com.waffle.ecs.GameObject;

public class Falling extends State {
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

    public void apply(GameObject gamob)
    {
        if(gamob instanceof Player) {
            Player p = (Player) gamob;
            p.kinematics.v.y += grav;
            if (p.kinematics.v.y > termVY) {
                p.kinematics.v.y = termVY;
            }
            p.kinematics.v.x += p.inputD.x * airAccel;
            if (Math.abs(p.kinematics.v.x) > termVX) {
                p.kinematics.v.x = p.kinematics.v.x < 0 ? -termVX : termVX;
            }
        }
        update();
    }
}
