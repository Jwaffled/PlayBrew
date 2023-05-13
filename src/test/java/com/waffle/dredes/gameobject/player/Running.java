package com.waffle.dredes.gameobject.player;

import com.waffle.ecs.GameObject;

public class Running extends State{
    public float maxSpeed;
    public float acceleration;

    public Running(float maxSpeed, float acceleration)
    {
        super(200,100,100);
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
    }

    public void apply(GameObject gamob)
    {
        if(gamob instanceof Player)
        {

        }
    }
}
