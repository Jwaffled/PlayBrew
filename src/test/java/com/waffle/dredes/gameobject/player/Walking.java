package com.waffle.dredes.gameobject.player;

import com.waffle.core.Utils;
import com.waffle.ecs.GameObject;

/**
 * A class representing the walking state
 */
public class Walking extends State {
    /**
     * The horizontal speed the object can travel at withing this state
     */
    public float walkSpeed;

    /**
     * Constructs a new Walking state given a walking speed
     * @param walkSpeed
     */
    public Walking(float walkSpeed) {
        super(50,50,200);
        this.walkSpeed = walkSpeed;
        sprite = Utils.loadImageFromPath("DreDes/Will-Overworld-Walk.png");
    }

    /**
     * Simulates one frame of walking for a GameObject
     * @param gamob the GameObject to simulate
     */
    public void apply(GameObject gamob) {
        if(gamob instanceof Player p) {
            p.kinematics.v.x = p.faceLeft ? -walkSpeed : walkSpeed;
            p.kinematics.v.y = 0;
        }
        update();
    }
}