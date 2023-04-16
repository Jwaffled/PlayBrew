package com.waffle.ecs;

import java.util.*;

public class EntityManager {
    private final Deque<Integer> availableEntities = new LinkedList<>();
    private final Map<Integer, Integer> entityToLayers = new HashMap<>();
    private final BitSet[] signatures;
    private final int maxEntityCount;
    private int livingEntityCount;

    public EntityManager(int max) {
        signatures = new BitSet[max];
        maxEntityCount = max;
        livingEntityCount = 0;
        for(int i = 0; i < max; i++) {
            availableEntities.push(i);
        }
    }

    public int createEntity(int layer) {
        if(livingEntityCount >= maxEntityCount) {
            throw new IllegalStateException("Too many entities in existence.");
        }

        int id = availableEntities.pop();
        entityToLayers.put(id, layer);
        livingEntityCount++;
        return id;
    }

    public int getEntityLayer(int entity) {
        if(entity > maxEntityCount) {
            throw new IllegalStateException("Entity out of range.");
        }

        return entityToLayers.get(entity);
    }

    public void removeEntity(int entity) {
        if(entity > maxEntityCount) {
            throw new IllegalStateException("Entity out of range.");
        }

        signatures[entity].clear();
        availableEntities.push(entity);
        livingEntityCount--;
    }

    public void setSignature(int entity, BitSet signature) {
        if(entity > maxEntityCount) {
            throw new IllegalStateException("Entity out of range.");
        }

        signatures[entity] = signature;
    }

    public BitSet getSignature(int entity) {
        if(entity > maxEntityCount) {
            throw new IllegalStateException("Entity out of range.");
        }

        return signatures[entity];
    }

    public int getLivingEntityCount() {
        return this.livingEntityCount;
    }

    public int getMaxEntityCount() {
        return this.maxEntityCount;
    }

}
