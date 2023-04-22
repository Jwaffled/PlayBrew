package com.waffle.core;

import com.waffle.ecs.GameObject;

public class CollisionEvent {
    private GameObject objectOne;
    private GameObject objectTwo;

    public GameObject getObjectOne() {
        return objectOne;
    }

    public GameObject getObjectTwo() {
        return objectTwo;
    }
}
