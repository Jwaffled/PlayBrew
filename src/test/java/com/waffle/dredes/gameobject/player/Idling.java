package com.waffle.dredes.gameobject.player;

public class Idling extends PlayerState {
    public int traction;
    public Idling(int traction) {
        super(100,100,200);
        if(traction > 100) {
            throw new IllegalArgumentException("Traction " + traction + " is  greater than 100");
        }

        if(traction < 0) {
            throw new IllegalArgumentException("Traction " + traction + " is negative");
        }

        this.traction = traction;
    }

    public void apply(Player p) {
        p.kinematics.v.x *= (1 -(float)traction/100);
        update();
    }

}
