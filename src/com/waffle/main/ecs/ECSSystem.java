package com.waffle.main.ecs;

import java.util.HashSet;
import java.util.Set;

public class ECSSystem {
    protected Set<Integer> entities;
    protected World world;

    public ECSSystem() {
        entities = new HashSet<>();
    }

    public void setWorld(World w) {
        world = w;
    }
}
