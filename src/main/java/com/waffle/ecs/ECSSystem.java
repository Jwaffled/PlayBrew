package com.waffle.ecs;

import com.waffle.core.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ECSSystem {
    protected ArrayList<Set<Integer>> entities;
    protected World world;

    @SuppressWarnings("unused")
    public ECSSystem() {
    }

    public void entityAdded(int layer, int entity) {
        entities.get(layer).add(entity);
    }

    public void entityRemoved(int layer, int entity) {
        entities.get(layer).remove(entity);
    }

    public void layersCreated(int layerAmount) {
        entities = new ArrayList<>(layerAmount);
        for(int i = 0; i < layerAmount; i++) {
            entities.add(new HashSet<>());
        }
    }

    public final void setWorld(World w) {
        world = w;
        entities = new ArrayList<>();
    }

}
