package com.waffle.dredes.gameobject.player;

import com.waffle.core.Utils;
import com.waffle.ecs.GameObject;

/**
 * A class representing the turning state
 */
public class Turning extends State {
    int friction;
    int baseFriction;

    /**
     *
     * @param frames the amount of frames it takes to turn
     * @param friction the percent loss of horizontal speed per frame
     */
    public Turning(int frames, int friction) {
        super(150,150,200,frames);
        this.friction = friction;
        baseFriction = friction;
        sprite = Utils.loadImageFromPath("DreDes/Will-Overworld-Turn.png");
    }

    /**
     * Simulates one frame of turning for a GameObject
     * @param gamob the GameObject to simulate
     */
    public void apply(GameObject gamob) {
        if(gamob instanceof Player p) {
            p.kinematics.v.x *= (1 - (float) friction / 100);
            p.kinematics.v.y = 0;
        }
        update();
    }
}