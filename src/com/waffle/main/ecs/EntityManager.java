package com.waffle.main.ecs;

import java.util.BitSet;
import java.util.Deque;
import java.util.LinkedList;

public class EntityManager {
    private final Deque<Integer> availableEntities = new LinkedList<>();
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

    public int createEntity() {
        if(livingEntityCount >= maxEntityCount) {
            throw new IllegalStateException("Too many entities in existence.");
        }

        int id = availableEntities.pop();
        livingEntityCount++;
        return id;
    }

    public void removeEntity(int entity) {
        if(entity > maxEntityCount) {
            throw new IllegalStateException("Entity out of range.");
        }

        signatures[entity].clear();
        availableEntities.push(entity);
        livingEntityCount--;
    }

    void setSignature(int entity, BitSet signature) {
        if(entity > maxEntityCount) {
            throw new IllegalStateException("Entity out of range.");
        }

        signatures[entity] = signature;
    }

    BitSet getSignature(int entity) {
        if(entity > maxEntityCount) {
            throw new IllegalStateException("Entity out of range.");
        }

        return signatures[entity];
    }

}
