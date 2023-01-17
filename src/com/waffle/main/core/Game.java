package com.waffle.main.core;

import com.waffle.main.components.TransformComponent;
import com.waffle.main.ecs.World;
import com.waffle.main.render.Window;
import com.waffle.main.systems.PhysicsSystem;
import com.waffle.main.systems.RenderSystem;

import java.util.BitSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Game implements Runnable {
    public static final int DEFAULT_MAX_ENTITIES = 5000;
    private static final int UPDATE_DELAY_MS = 16;
    private final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
    protected Window window;
    protected World world;
    protected RenderSystem renderSystem;
    protected PhysicsSystem physicsSystem;

    public Game() {
        this(DEFAULT_MAX_ENTITIES);
    }

    public Game(int maxEntities) {
        world = new World(maxEntities);
        registerComponents();
        registerSystems();
        timer.scheduleAtFixedRate(this, 0, UPDATE_DELAY_MS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        if(window != null) {
            this.update(UPDATE_DELAY_MS / 1000.0f);
        }
    }

    public void update(float dt) {
        // Update other systems here
        System.out.println("dt: " + dt);
        physicsSystem.update(dt);
        window.repaint();
    }

    private void registerSystems() {
        renderSystem = world.registerSystem(RenderSystem.class);
        {
            renderSystem.setWorld(world);
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            world.setSystemSignature(sig, RenderSystem.class);
        }

        physicsSystem = world.registerSystem(PhysicsSystem.class);
        {
            physicsSystem.setWorld(world);
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            world.setSystemSignature(sig, PhysicsSystem.class);
        }
    }

    private void registerComponents() {
        world.registerComponent(TransformComponent.class);
    }

    /**
     * @param width the width of the window (in pixels)
     * @param height the height of the window (in pixels)
     * @param title the title of the window
     * @return A JPanel representing the main game window
     */
    protected Window createWindow(int width, int height, String title) {
        return new Window(width, height, title, renderSystem);
    }
}
