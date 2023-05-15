package com.waffle.dredes.gameobject.player;

import com.waffle.ecs.GameObject;

/**
 * A class representing the turning player state
 */
public class Turning extends State {
    int friction;
    int baseFriction;

    /**
     *
     * @param frames
     * @param friction
     */
    public Turning(int frames, int friction) {
        super(150,150,200,frames);
        this.friction = friction;
        baseFriction = friction;
    }

    /**
     *
     * @param gamob
     */
    public void apply(GameObject gamob) {
        if(gamob instanceof Player p) {
            p.kinematics.v.x *= (1 - (float) friction / 100);
            p.kinematics.v.y = 0;
        }
        update();
    }
}