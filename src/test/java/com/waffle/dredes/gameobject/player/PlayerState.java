package com.waffle.dredes.gameobject.player;
import java.awt.Color;
public abstract class PlayerState {
    private Color color;
    protected int counter;
    protected int record;
    private boolean countingUp;
    public boolean active;

    public PlayerState(int r, int g, int b) {
        color = new Color(r,b,g, 50);
        countingUp = true;
    }

    public PlayerState(int r, int g, int b, int duration) {
        color = new Color(r,b,g, 50);
        record = duration;
        countingUp = false;
    }

    public Color getColor() {
        return color;
    }

    public boolean equals(Object other) {
        return other instanceof PlayerState && ((PlayerState) other).color.equals(color);
    }

    public void activate() {
        counter = countingUp? 0 : record;
        active = true;
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

    abstract void apply(Player p);
}
