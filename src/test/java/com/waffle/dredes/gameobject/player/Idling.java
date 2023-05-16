package com.waffle.dredes.gameobject.player;

import com.waffle.core.Utils;
import com.waffle.ecs.GameObject;

/**
 * A class representing the idling player state
 */
public class Idling extends State {
    /**
     * The percent of horizontal speed lost every frame
     */
    public int traction;
    /**
     *  A reference for traction in case of modification
     */
    public int baseTraction;

    /**
     * Constructs a new idling player state
     * @param traction the amount of friction to apply to the object when idling
     */
    public Idling(int traction) {
        super(100,100,200);
        if(traction > 100) {
            throw new IllegalArgumentException("Traction " + traction + " is  greater than 100");
        }

        if(traction < 0) {
            throw new IllegalArgumentException("Traction " + traction + " is negative");
        }
        sprite = Utils.loadImageFromPath("DreDes/Will-Overworld-Idle.png");
        this.traction = traction;
        baseTraction = traction;
    }

    /**
     * Simulates one frame of Idling for a GameObject
     * @param gamob the GameObject to simulate
     */
    public void apply(GameObject gamob) {
        if(gamob instanceof Player p) {
            p.kinematics.v.x *= (1 - (float) traction / 100);
            p.kinematics.v.y = 0;
        }
        update();
    }

}