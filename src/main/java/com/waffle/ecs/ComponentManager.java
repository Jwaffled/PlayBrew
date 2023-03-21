package com.waffle.ecs;

import java.util.HashMap;
import java.util.Map;

public class ComponentManager {
    private final Map<String, Integer> componentTypes;
    private final Map<String, IComponentArray> componentArrays;
    private int nextComponentType;
    private final int maxEntities;

    public ComponentManager(int maxEntitiesCount) {
        maxEntities = maxEntitiesCount;
        componentTypes = new HashMap<>();
        componentArrays = new HashMap<>();
    }

    private <T extends IComponent> ComponentArray<T> getComponentArray(Class<T> tClass) {
        String name = tClass.getTypeName();

        if(!componentTypes.containsKey(name)) {
            throw new IllegalStateException("Component not registered before use.");
        }

        return (ComponentArray<T>)componentArrays.get(name);
    }

    public <T extends IComponent> void registerComponent(Class<T> tClass) {
        String name = tClass.getTypeName();

        if(componentTypes.containsKey(name)) {
            throw new IllegalStateException("Registering component type more than once.");
        }

        componentTypes.put(name, nextComponentType);
        componentArrays.put(name, new ComponentArray<T>(maxEntities));

        nextComponentType++;
    }

    public <T extends IComponent> int getComponentType(Class<T> tClass) {
        String name = tClass.getTypeName();

        if(!componentTypes.containsKey(name)) {
            throw new IllegalStateException("Component not registered before use.");
        }

        return componentTypes.get(name);
    }

    public <T extends IComponent> void addComponent(int entity, T component) {
        getComponentArray(component.getClass()).insertData(entity, component);
    }

    public <T extends IComponent> void removeComponent(int entity, Class<T> tClass) {
        getComponentArray(tClass).removeData(entity);
    }

    public <T extends IComponent> T getComponent(int entity, Class<T> tClass) {
        return getComponentArray(tClass).getData(entity);
    }

    public void entityDestroyed(int entity) {
        for(IComponentArray comp : componentArrays.values()) {
            comp.entityDestroyed(entity);
        }
    }
}
