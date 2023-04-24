package com.waffle.ecs;

import java.lang.reflect.InvocationTargetException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class SystemManager {
    private final Map<String, BitSet> signatures;
    private final Map<String, ECSSystem> systems;

    public SystemManager() {
        signatures = new HashMap<>();
        systems = new HashMap<>();
    }

    public <T extends ECSSystem> T registerSystem(Class<T> tClass) {
        String name = tClass.getTypeName();

        if(systems.containsKey(name)) {
            throw new IllegalStateException("System registered more than once.");
        }

        T system;

        try {
            system = tClass.getDeclaredConstructor().newInstance();
        } catch(NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        systems.put(name, system);
        return system;
    }

    public <T extends ECSSystem> T getSystem(Class<T> tClass) {
        String name = tClass.getTypeName();

        if(!systems.containsKey(name)) {
            throw new IllegalStateException("System not registered before use.");
        }

        return (T) systems.get(name);
    }


    public <T extends ECSSystem> void setSignature(BitSet signature, Class<T> tClass) {
        String name = tClass.getTypeName();

        if(!systems.containsKey(name)) {
            throw new IllegalStateException("System has not been registered.");
        }

        signatures.put(name, signature);
    }

    public void entityDestroyed(int entity, int layer) {
        for(ECSSystem system : systems.values()) {
            system.entities.get(layer).remove(entity);
        }
    }

    public void entitySignatureChanged(int entity, int layer, BitSet entitySignature) {
        for(Map.Entry<String, ECSSystem> entry : systems.entrySet()) {
            String type = entry.getKey();
            ECSSystem system = entry.getValue();
            BitSet systemSignature = signatures.get(type);

            BitSet cloned = (BitSet) entitySignature.clone();
            cloned.and(systemSignature);
            if(cloned.equals(systemSignature)) {
                system.entityAdded(layer, entity);
            } else {
                system.entityRemoved(layer, entity);
            }
        }
    }

    public void layersCreated(int layerAmount) {
        for(ECSSystem system : systems.values()) {
            system.layersCreated(layerAmount);
        }
    }

}
