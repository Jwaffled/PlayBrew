package com.waffle.dredes.gameobject.player;

public class Jumping extends PlayerState {
    public float height;

    public Jumping(float height, int time) {
        super(200,200,0, time);
        this.height = height;
    }

    public void apply(Player p) {
        p.kinematics.v.y = ((2 * height / record) - (2 * height * (record - counter) / (record * record)))/60;
        update();
    }
}
