package com.waffle.ecs;

public abstract class GameObject {
    protected World world;
    public int ID;
    public int layer;
    protected boolean isActive = true;

    public final void setWorld(World world) {
        this.world = world;
    }

    public abstract void start();
    public abstract void update(float dt);

    public final <T extends IComponent> void addComponent(T component) {
        world.addComponent(this.ID, component);
    }

    public final <T extends IComponent> void removeComponent(Class<T> tClass) {
        world.removeComponent(this.ID, tClass);
    }

    public final <T extends IComponent> T getComponent(Class<T> tClass) {
        return world.getComponent(this.ID, tClass);
    }

    public final boolean isActive() {
        return isActive;
    }

    public final void setActive(boolean active) {
        isActive = active;
    }
}
