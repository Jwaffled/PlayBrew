package com.waffle.dredes.gameobject.player;
import com.waffle.ecs.GameObject;

import java.awt.Color;

/**
 * An abstract class representing a certain player state
 */
public abstract class State {
    private Color color;
    public int counter;
    protected int record;
    private boolean countingUp;
    public boolean active;

    /**
     *
     * @param r
     * @param g
     * @param b
     */
    public State(int r, int g, int b) {
        color = new Color(r,b,g, 50);
        countingUp = true;
    }

    /**
     *
     * @param r
     * @param g
     * @param b
     * @param duration
     */
    public State(int r, int g, int b, int duration) {
        color = new Color(r,b,g, 50);
        record = duration;
        countingUp = false;
    }

    /**
     *
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        return other instanceof State s && s.color.equals(color);
    }

    /**
     *
     * @return
     */
    public State activate() {
        counter = countingUp? 0 : record;
        active = true;
        return this;
    }

    /**
     *
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
     *
     * @param gamob
     */
    public abstract void apply(GameObject gamob);
}
