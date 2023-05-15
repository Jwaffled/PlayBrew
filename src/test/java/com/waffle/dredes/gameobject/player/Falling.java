package com.waffle.dredes.gameobject.player;

import com.waffle.ecs.GameObject;

/**
 * A class representing the falling player state
 */
public class Falling extends State {
    float grav;
    float termVY;
    public float airAccel;
    public float termVX;

    /**
     * Constructs a falling state
     * @param terminalVelocityY the maximum velocity in the y direction that the player can fall
     * @param terminalVelocityX the maximum velocity in the x direction that the player can fall
     * @param gravity the gravity acceleration exerted upon the player
     * @param airAccel
     */
    public Falling(float terminalVelocityY, float terminalVelocityX, float gravity, float airAccel) {
        super(30,30,30);
        termVY = terminalVelocityY;
        grav = gravity;
        termVX = terminalVelocityX;
        this.airAccel = airAccel;
    }

    /**
     *
     * @param gamob
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
