package com.waffle.dredes.gameobject.player;

import com.waffle.core.Utils;
import com.waffle.ecs.GameObject;

/**
 * A class representing the jumping state
 */
public class Jumping extends State {
    private float height;
    private float airAccel;
    private float termV;

    /**
     * Constructs a new jumping state
     * @param height the height the object will jump to
     * @param airAccel the horizontal acceleration per fram in a given direction during the jump
     * @param termV the terminal velocity in the X direction
     * @param time the number of frames it takes for the object's height to reach its peak
     */
    public Jumping(float height, float airAccel, float termV, int time) {
        super(200,200,0, time);
        this.height = height;
        this.airAccel = airAccel;
        this.termV = termV;
        sprite = Utils.loadImageFromPath("DreDes/Will-Overworld-Jump.png");
    }

    /**
     * Simulates one frame of Jumping for a GameObject
     * @param gamob the GameObject to simulate
     */
    public void apply(GameObject gamob) {
        if(gamob instanceof Player p) {
            p.kinematics.v.y = (((height * (record - counter) * 2)/(record * record)) - ((2 * height)/record))/(1f/60f);
            p.kinematics.v.x += p.inputD.x * airAccel;
            if(Math.abs(p.kinematics.v.x) > termV ) {
                p.kinematics.v.x = p.kinematics.v.x < 0? -termV : termV;
            }

        }
        update();
    }
}
