package com.waffle.dredes.gameobject.player;

import com.waffle.core.Utils;
import com.waffle.ecs.GameObject;

/**
 * A class representing the falling player state
 */
public class Falling extends State {
    private float grav;
    private float termVY;
    private float airAccel;
    private float termVX;

    /**
     * Constructs a falling state
     * @param terminalVelocityY the maximum velocity in the y direction that the object can fall
     * @param terminalVelocityX the maximum velocity in the x direction that the object can fall
     * @param gravity the gravity acceleration exerted upon the object
     * @param airAccel the horizontal acceleration exerted upon the object
     */
    public Falling(float terminalVelocityY, float terminalVelocityX, float gravity, float airAccel) {
        super(30,30,30);
        termVY = terminalVelocityY;
        grav = gravity;
        termVX = terminalVelocityX;
        this.airAccel = airAccel;
        sprite = Utils.loadImageFromPath("DreDes/Will-Overworld-Fall.png");
    }

    /**
     * Simulates one frame of falling for a GameObject
     * @param gamob the GameObject to simulate
     */
    public void apply(GameObject gamob) {
        if(gamob instanceof Player p) {
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
