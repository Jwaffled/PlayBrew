package com.waffle.ecs;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class ECSSystem {
    protected ArrayList<Set<Integer>> entities;
    protected World world;

    @SuppressWarnings("unused")
    public ECSSystem() {
    }

    public final void setWorld(World w) {
        world = w;
        entities = new ArrayList<>();
    }

}
