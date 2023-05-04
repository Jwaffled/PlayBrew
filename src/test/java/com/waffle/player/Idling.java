package com.waffle.player;

public class Idling extends PlayerState{

    public Idling()
    {
        super(50,50,50);
    }

    @Override
    void apply(Player p) {
        p.physics.v.x /= 1.05;
        super.apply();
    }
}
