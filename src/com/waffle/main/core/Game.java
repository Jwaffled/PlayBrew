package com.waffle.main.core;

import com.waffle.main.components.FontRenderComponent;
import com.waffle.main.components.SpriteRenderComponent;
import com.waffle.main.components.TransformComponent;
import com.waffle.main.ecs.World;
import com.waffle.main.input.Input;
import com.waffle.main.render.Window;
import com.waffle.main.systems.FontRenderSystem;
import com.waffle.main.systems.PhysicsSystem;
import com.waffle.main.systems.RenderSystem;

import java.awt.*;
import java.util.BitSet;
import java.util.concurrent.*;

public abstract class Game implements Runnable {
    public static final int DEFAULT_MAX_ENTITIES = 100000;
    private final int fpsCap;
    private final ExecutorService executinator = Executors.newSingleThreadExecutor();
    protected Window window;
    protected World world;
    protected RenderSystem renderSystem;
    protected PhysicsSystem physicsSystem;
    protected FontRenderSystem fontRenderSystem;

    public Game() {
        this(DEFAULT_MAX_ENTITIES, 60);
    }

    public Game(int maxEntities, int fpsCap) {
        world = new World(maxEntities);
        this.fpsCap = fpsCap;
        init();
    }

    public void init() {
        registerComponents();
        registerSystems();
        executinator.execute(this);
    }

    @Override
    public void run() {
        long currentTime = System.nanoTime();
        double accum = 0;
        float dt = 1.0f / fpsCap;
        while(!Thread.interrupted()) {
            if(window != null) {
                long newTime = System.nanoTime();
                long frameTime = newTime - currentTime;
                if(frameTime > 250 * 1e9) {
                    frameTime = (long)(250 * 1e9);
                }
                currentTime = newTime;
                accum += frameTime / 1e9;
                while(accum >= dt) {
                    this.update(dt);
                    accum -= dt;
                }
                try {
                    Thread.sleep(Math.round(dt));
                } catch(InterruptedException ignored) {}
            }
        }
    }

    public void update(float dt) {
        // Update other systems here
        updateInput();
        physicsSystem.update(dt);
        world.update(dt);
        window.canvas.render();
    }

    private void updateInput() {
        Input.getInstance().mousePosition = MouseInfo.getPointerInfo().getLocation();
    }

    private void registerSystems() {
        renderSystem = world.registerSystem(RenderSystem.class);
        {
            renderSystem.setWorld(world);
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            sig.set(world.getComponentType(SpriteRenderComponent.class));
            world.setSystemSignature(sig, RenderSystem.class);
        }

        physicsSystem = world.registerSystem(PhysicsSystem.class);
        {
            physicsSystem.setWorld(world);
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            world.setSystemSignature(sig, PhysicsSystem.class);
        }

        fontRenderSystem = world.registerSystem(FontRenderSystem.class);
        {
            fontRenderSystem.setWorld(world);
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            sig.set(world.getComponentType(FontRenderComponent.class));
            world.setSystemSignature(sig, FontRenderSystem.class);
        }
    }

    private void registerComponents() {
        world.registerComponent(TransformComponent.class);
        world.registerComponent(SpriteRenderComponent.class);
        world.registerComponent(FontRenderComponent.class);
    }

    /**
     * @param width the width of the window (in pixels)
     * @param height the height of the window (in pixels)
     * @param title the title of the window
     * @return A JPanel representing the main game window
     */
    protected Window createWindow(int width, int height, String title) {
        return new Window(width, height, title, renderSystem, fontRenderSystem);
    }
}
