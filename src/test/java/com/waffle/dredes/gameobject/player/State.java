package com.waffle.dredes.gameobject.player;
import com.waffle.ecs.GameObject;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * An abstract class representing a certain state
 */
public abstract class State {
    private Color color;
    public int counter;
    protected int record;
    private boolean countingUp;
    public boolean active;

    public BufferedImage sprite;

    /**
     *
     * @param r the red value of the state's color
     * @param g the green value of the state's color
     * @param b the blue value of the state's color
     */
    public State(int r, int g, int b) {
        color = new Color(r,b,g, 50);
        countingUp = true;
    }

    /**
     *
     * @param r the red value of the state's color
     * @param g the green value of the state's color
     * @param b the blue value of the state's color
     * @param duration the number of frames the state lasts for
     */
    public State(int r, int g, int b, int duration) {
        color = new Color(r,b,g, 50);
        record = duration;
        countingUp = false;
    }

    /**
     *  gets the state's corresponding color
     * @return the color for the state
     */
    public Color getColor() {
        return color;
    }

    /**
     *  Check whether another object is "equal" to this state
     * @param other the object to check
     * @return whether the object is "equal"
     */
    public boolean equals(Object other) {
        return other instanceof State s && s.color.equals(color);
    }

    /**
     *  resets counting variables and sets active to true
     * @return a reference to this state
     */
    public State activate() {
        counter = countingUp? 0 : record;
        active = true;
        return this;
    }

    /**
     *  Counts up/down, deactivating if a countdown reaches zero
     */
    public void update() {
        if(active) {
            if(countingUp) {
                counter++;
                if(counter > record) {
                    counter = record;
                }
            } else {
                counter--;
                if(counter < 0) {
                    counter = 0;
                    active = false;
                }
            }
        }
    }

    /**
     *  Applies this state to a GameObject
     * @param gamob the GameObject to apply the state to
     */
    public abstract void apply(GameObject gamob);
}
