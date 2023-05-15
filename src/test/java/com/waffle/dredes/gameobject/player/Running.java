package com.waffle.dredes.gameobject.player;

import com.waffle.ecs.GameObject;

/**
 * A class representing the running player state
 */
public class Running extends State {
    public float maxSpeed;
    public float acceleration;

    /**
     * Constructs a new running player state
     * @param maxSpeed the maximum speed the player can reach whilst running
     * @param acceleration the acceleration when initially starting to run
     */
    public Running(float maxSpeed, float acceleration) {
        super(200,100,100);
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
    }

    public void apply(GameObject gamob) {
        if(gamob instanceof Player p) {

        }
    }
}
