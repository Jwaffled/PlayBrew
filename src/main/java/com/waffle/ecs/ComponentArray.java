package com.waffle.ecs;

import java.util.HashMap;
import java.util.Map;

public class ComponentArray<T extends IComponent> implements IComponentArray {
    private final T[] componentArray;
    private final Map<Integer, Integer> entityToIndexMap;
    private final Map<Integer, Integer> indexToEntityMap;
    private final int maxEntities;
    private int size;

    public ComponentArray(int maxEntityCount) {
        maxEntities = maxEntityCount;
        size = 0;
        entityToIndexMap = new HashMap<>();
        indexToEntityMap = new HashMap<>();
        componentArray = (T[]) new IComponent[maxEntities];
    }

    public <C extends IComponent> void insertData(int entity, C component) {
        if(entityToIndexMap.containsKey(entity)) {
            throw new IllegalStateException("Component added to entity more than once: " + component.getClass());
        }

        int newIndex = size;
        entityToIndexMap.put(entity, newIndex);
        indexToEntityMap.put(newIndex, entity);

        componentArray[newIndex] = (T) component;
        size++;
    }

    public void removeData(int entity) {
        if(!entityToIndexMap.containsKey(entity)) {
            throw new IllegalStateException("Attempted to remove non-existent component on entity");
        }

        int indexOfRemoved = entityToIndexMap.get(entity);
        int indexOfLast = size - 1;

        componentArray[indexOfRemoved] = componentArray[indexOfLast];

        int entityOfLast = indexToEntityMap.get(indexOfLast);
        entityToIndexMap.put(entityOfLast, indexOfRemoved);
        indexToEntityMap.put(indexOfRemoved, entityOfLast);

        entityToIndexMap.remove(entity);
        indexToEntityMap.remove(indexOfLast);

        size--;
    }

    public T getData(int entity) {
        if(!entityToIndexMap.containsKey(entity)) {
            throw new IllegalStateException("Attempted to retrieve non-existent component");
        }

        return componentArray[entityToIndexMap.get(entity)];
    }

    public void entityDestroyed(int entity) {
        if(!entityToIndexMap.containsKey(entity)) {
            removeData(entity);
        }
    }
}
