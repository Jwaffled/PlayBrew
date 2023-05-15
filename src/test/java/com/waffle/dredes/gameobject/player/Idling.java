package com.waffle.dredes.gameobject.player;

import com.waffle.ecs.GameObject;

/**
 * A class representing the idling player state
 */
public class Idling extends State {
    public int traction;
    public int baseTraction;

    /**
     * Constructs a new idling player state
     * @param traction the amount of friction to apply to the player when idling
     */
    public Idling(int traction) {
        super(100,100,200);
        if(traction > 100) {
            throw new IllegalArgumentException("Traction " + traction + " is  greater than 100");
        }

        if(traction < 0) {
            throw new IllegalArgumentException("Traction " + traction + " is negative");
        }
        this.traction = traction;
        baseTraction = traction;
    }

    /**
     *
     * @param gamob
     */
    public void apply(GameObject gamob) {
        if(gamob instanceof Player p) {
            p.kinematics.v.x *= (1 - (float) traction / 100);
            p.kinematics.v.y = 0;
        }
        update();
    }

}