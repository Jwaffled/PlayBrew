package com.waffle.ecs;

import com.waffle.core.Constants;
import com.waffle.core.LogLevel;

import java.lang.reflect.Field;
import java.util.*;

public class World {
    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final SystemManager systemManager;
//    private final Set<GameObject> gameObjects;
    private final Set<Integer> toRemove;
    private final Map<Integer, GameObject> gameObjects;
    private final Set<GameObject> toAdd;
    private int validLayers = 0;

    /**
     * Creates a new World (Entity Component System).
     * @param maxEntities the maximum amount of entities in the World<br>
     *                    Must be greater than 0
     */
    public World(int maxEntities) {
        if(maxEntities < 1) {
            throw new IllegalArgumentException("Value of maxEntities must be greater than 0.");
        }
        entityManager = new EntityManager(maxEntities);
        componentManager = new ComponentManager(maxEntities);
        systemManager = new SystemManager();
        gameObjects = new HashMap<>();
        toRemove = new HashSet<>();
        toAdd = new HashSet<>();
    }

    /**
     * Adds a GameObject (and all of its components) to the world on layer 0
     * @param gameObj the instance of the GameObject to add
     * @param <T> Type parameter
     */
    public <T extends GameObject> void createGameObject(T gameObj) {
        createGameObject(gameObj, 0);
    }

    /**
     * Adds a GameObject (and all of its components) to the world on a specified layer
     * @param gameObj the instance of the GameObject to add
     * @param layer the layer to add the GameObject to
     * @param <T> Type parameter
     */
    public <T extends GameObject> void createGameObject(T gameObj, int layer) {
        // Instantiate all fields that inherit from IComponent
        gameObj.start();
        int objID = createEntity(layer);
        gameObj.ID = objID;
        gameObj.layer = layer;
        gameObj.setWorld(this);

        try {
            for(Field f : gameObj.getClass().getDeclaredFields()) {
                Class<?> c = f.getType();
                if(IComponent.class.isAssignableFrom(c)) {
                    if(!f.canAccess(gameObj)) {
                        f.setAccessible(true);
                    }
                    Object a = f.get(gameObj);
                    if(a instanceof IComponent) {
                        addComponent(objID, (IComponent)a);
                    }
                }
            }
        } catch(IllegalAccessException e) {
            throw new IllegalStateException("Something went wrong with creating GameObject " + gameObj.getClass().getTypeName() + ": " + e.getMessage());
        }

        toAdd.add(gameObj);

    }

    /**
     * Returns the GameObject with the specified entity ID
     * @param id the ID to look for
     * @return the GameObject with the specified entity ID
     */
    public GameObject getGameObject(int id) {
        return gameObjects.get(id);
    }

    /**
     * Marks a GameObject for deletion<br>
     * This method does NOT guarantee the instant removal of a GameObject
     * @param gameObj the GameObject to remove
     * @param <T> Type parameter
     */
    public <T extends GameObject> void removeGameObject(T gameObj) {
        toRemove.add(gameObj.ID);
    }

    private void sweepMarked() {
        for(int gameObjID : toRemove) {
            GameObject o = gameObjects.get(gameObjID);
            destroyEntity(o.ID, o.layer);
        }
        gameObjects.keySet().removeAll(toRemove);
        toRemove.clear();
    }

    private void addQueued() {
        for(GameObject o : toAdd) {
            gameObjects.put(o.ID, o);
        }
        toAdd.clear();
    }

    /**
     * Updates all GameObjects in the ECS.<br>
     * Sweeps up all "marked" GameObjects for deletion
     * @param dt the time in between frames
     */
    public void update(float dt) {
        addQueued();
        for(GameObject obj : gameObjects.values()) {
            if(obj.isActive()) {
                obj.update(dt);
            }
        }
        sweepMarked();
    }

    /**
     * Creates valid layers from [0, upper) for entities<br>
     * <i><b>MUST</b></i> be called before adding entities
     * @param upper the upper limit (NOT INCLUSIVE) of layers to create
     */
    public void createLayers(int upper) {
        validLayers = upper;
        systemManager.layersCreated(upper);
    }

    /**
     * Creates an entity on layer 0
     * @return the ID of the entity
     */
    public int createEntity() {
        if(validLayers == 0) {
            throw new IllegalArgumentException("Entity layer '0' out of range (Did you call createLayers()?).");
        }
        return entityManager.createEntity(0);
    }

    /**
     * Allocates an entity within the world and returns its ID.
     * @param layer the layer the entity should be placed on
     * @return an integer representing a unique ID for an entity
     */
    public int createEntity(int layer) {
        if(layer > validLayers) {
            throw new IllegalArgumentException("Entity layer '" + layer + "' out of range (Did you call createLayers()?).");
        }
        return entityManager.createEntity(layer);
    }

    /**
     * Removes an entity from the world and deallocates it from existence
     * @param entity the unique ID of an entity
     */
    public void destroyEntity(int entity, int layer) {
        entityManager.removeEntity(entity);
        componentManager.entityDestroyed(entity);
        systemManager.entityDestroyed(entity, layer);
    }

    /**
     * Registers a component for use within the World.<br>
     * <b>MUST</b> be called before using a user-defined component
     * @param tClass The class of the component type to register (I.e. ComponentType.class)
     * @param <T> Component class type
     */
    public <T extends IComponent> void registerComponent(Class<T> tClass) {
        componentManager.registerComponent(tClass);
    }

    /**
     * Adds a component to a specified entity in the World.
     * @param entity the unique ID of an entity
     * @param component an instance of the component to be added
     * @param <T> Component class type
     */
    public <T extends IComponent> void addComponent(int entity, T component) {
        componentManager.addComponent(entity, component);

        BitSet entitySig = entityManager.getSignature(entity);
        if(entitySig == null) {
            entitySig = new BitSet();
        }

        entitySig.set(componentManager.getComponentType(component.getClass()), true);
        entityManager.setSignature(entity, entitySig);

        systemManager.entitySignatureChanged(entity, entityManager.getEntityLayer(entity), entitySig);
    }

    /**
     * Removes a component from a specified entity in the World.
     * @param entity the unique ID of an entity
     * @param tClass The class of the component type to remove (I.e. ComponentType.class)
     * @param <T> Component class type
     */
    public <T extends IComponent> void removeComponent(int entity, Class<T> tClass) {
        componentManager.removeComponent(entity, tClass);

        BitSet entitySig = entityManager.getSignature(entity);
        if(entitySig == null) {
            entitySig = new BitSet();
        }
        entitySig.set(componentManager.getComponentType(tClass), false);
        entityManager.setSignature(entity, entitySig);

        systemManager.entitySignatureChanged(entity, entityManager.getEntityLayer(entity), entitySig);
    }

    /**
     * Retrieves a component on the specified entity.<br>
     * Will throw an error if the component does not exist on the entity.
     * @param entity the unique ID of an entity
     * @param tClass The class of the component type to fetch (I.e. ComponentType.class)
     * @param <T> Component class type
     * @return the instance of the desired component on the specified entity (If it exists)
     */
    public <T extends IComponent> T getComponent(int entity, Class<T> tClass) {
        return componentManager.getComponent(entity, tClass);
    }

    /**
     * Retrieves the ID associated with the actual type of a component.<br>
     * Commonly used when setting signatures for systems:<br>
     * <pre>{@code
     *     World world = ...;
     *     SystemType sys = world.registerSystem(SystemType.class);
     *     BitSet sig = new BitSet();
     *     sig.set(world.getComponentType(ComponentType.class));
     *     world.setSystemSignature(sig, SystemType.class);
     * }</pre>
     *
     * @param tClass The class of the component type to fetch (I.e. ComponentType.class).
     * @param <T> Component class type
     * @return the ID associated with a component type
     */
    public <T extends IComponent> int getComponentType(Class<T> tClass) {
        return componentManager.getComponentType(tClass);
    }

    /**
     * Registers a system for use within the World.<br>
     * <b>The registered class MUST have a parameterless constructor.</b>
     * @param tClass The class of the system type to register (I.e. SystemType.class)
     * @param <T> System class type
     * @return an instance of the registered system
     */
    public <T extends ECSSystem> T registerSystem(Class<T> tClass) {
        T sys = systemManager.registerSystem(tClass);
        sys.setWorld(this);
        return sys;
    }

    /**
     * Sets the bits for the signature of the specified system.<br>
     * Use in combination with <World>.getComponentType() to set proper signatures.
     * <pre>{@code
     *     // Register system before use
     *     BitSet sig = new BitSet();
     *     sig.set(world.getComponentType(ComponentType.class));
     *     world.setSystemSignature(sig, SystemType.class);
     *     // <SystemType>.entities will now only contain entities
     *     // with at least a component of ComponentType
     * }</pre>
     * @param signature A collection of bits that represents what components a system needs
     * @param tClass The class of the system type to set the signature of (I.e. SystemType.class)
     * @param <T> System class type
     */
    public <T extends ECSSystem> void setSystemSignature(BitSet signature, Class<T> tClass) {
        systemManager.setSignature(signature, tClass);
    }

    /**
     * Returns the amount of "living" (total) entities in the ECS
     * @return the amount of living entities in the ECS
     */
    public int getLivingEntityCount() {
        return entityManager.getLivingEntityCount();
    }

    /**
     * Returns the maximum amount of entities allowed in this ECS
     * @return the maximum amount of entities  allowed in this ECS
     */
    public int getMaxEntities() {
        return entityManager.getMaxEntityCount();
    }

    public void clearAllExcept(int... protect) {
        Set<Integer> protectedObjects = new HashSet<>();
        for(int a : protect) {
            protectedObjects.add(a);
        }

        Set<GameObject> toRemove = new HashSet<>();

        for(GameObject o : gameObjects.values()) {
            if(!protectedObjects.contains(o.ID)) {
                toRemove.add(o);
            }
        }

        for(GameObject o : toRemove) {
            removeGameObject(o);
        }
    }
}
