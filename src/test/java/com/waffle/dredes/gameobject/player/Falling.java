package com.waffle.dredes.gameobject.player;

public class Falling extends PlayerState{
    public float gravAcceleration;
    public float maxFallSpeed;
    public Falling(float gravity, float maxFallSpeed) {
        super(200,200,150);
        gravAcceleration = gravity;
        this.maxFallSpeed = maxFallSpeed;
    }

    public void apply(Player p) {
        p.kinematics.v.y -= gravAcceleration;

        if(p.kinematics.v.y < -maxFallSpeed) {
            p.kinematics.v.y = -maxFallSpeed;
        }

        update();
    }

}
