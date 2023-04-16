package com.waffle.ecs;

import java.util.Set;
import java.util.TreeMap;

public class ECSSystem {
    protected TreeMap<Integer, Set<Integer>> entities;
    protected World world;

    @SuppressWarnings("unused")
    public ECSSystem() {
    }

    public final void setWorld(World w) {
        world = w;
        // Initial size of HashTable should be roughly 1.3 times the
        // maximum amount of keys that would appear in the table - UCSD
        // https://cseweb.ucsd.edu/~kube/cls/100/Lectures/lec16/lec16-8.html#:~:text=But%20a%20good%20general%20%E2%80%9Crule,should%20be%20a%20prime%20number
        entities = new TreeMap<>();
    }

}
