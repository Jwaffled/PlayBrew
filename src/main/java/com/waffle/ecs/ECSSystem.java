package com.waffle.ecs;

import java.util.HashSet;
import java.util.Set;

public class ECSSystem {
    protected Set<Integer> entities;
    protected World world;

    public ECSSystem() {
    }

    public final void setWorld(World w) {
        world = w;
        // Initial size of HashTable should be roughly 1.3 times the
        // maximum amount of keys that would appear in the table - UCSD
        // https://cseweb.ucsd.edu/~kube/cls/100/Lectures/lec16/lec16-8.html#:~:text=But%20a%20good%20general%20%E2%80%9Crule,should%20be%20a%20prime%20number
        entities = new HashSet<>((int)(w.getMaxEntities() * 1.3));
    }

}
