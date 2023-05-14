package com.waffle.dredes.gameobject.player;
import com.waffle.ecs.GameObject;

import java.awt.Color;
public abstract class State {
    private Color color;
    public int counter;
    protected int record;
    private boolean countingUp;
    public boolean active;

    public State(int r, int g, int b) {
        color = new Color(r,b,g, 50);
        countingUp = true;
    }
    public State(int r, int g, int b, int duration) {
        color = new Color(r,b,g, 50);
        record = duration;
        countingUp = false;
    }
    public Color getColor() {
        return color;
    }

    public boolean equals(Object other) {
        return other instanceof State && ((State) other).color.equals(color);
    }

    public State activate() {
        counter = countingUp? 0 : record;
        active = true;
        return this;
    }

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
    public abstract void apply(GameObject gamob);
}
