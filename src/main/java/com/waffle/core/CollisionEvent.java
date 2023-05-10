package com.waffle.core;

import com.waffle.ecs.GameObject;

public class CollisionEvent {
    private GameObject objectOne;

    public CollisionEvent(GameObject one) {
        objectOne = one;
    }

    public GameObject getCollidedObject() {
        return objectOne;
    }
}
