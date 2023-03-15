package com.waffle.main.ecs;

public abstract class GameObject {
    protected World world;
    public int ID;
    protected boolean isActive = true;

    public final void setWorld(World world) {
        this.world = world;
    }

    public abstract void update(float dt);

    public abstract void start();

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
}
