package com.waffle.main.ecs;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ECSSystem {
    protected final Set<Integer> entities;
    protected World world;

    public ECSSystem() {
        entities = new HashSet<>();
    }

    public void setWorld(World w) {
        world = w;
    }

}
