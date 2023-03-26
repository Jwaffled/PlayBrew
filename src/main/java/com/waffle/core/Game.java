package com.waffle.core;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.ecs.World;
import com.waffle.input.Input;
import com.waffle.render.Window;
import com.waffle.systems.FontRenderSystem;
import com.waffle.systems.PhysicsSystem;
import com.waffle.systems.SpriteRenderSystem;

import java.awt.*;
import java.util.BitSet;
import java.util.concurrent.*;

public abstract class Game implements Runnable, FreeableResource {
    public static final int DEFAULT_MAX_ENTITIES = 100000;
    private final int fpsCap;
    private final ExecutorService executinator = Executors.newSingleThreadExecutor();
    protected Window window;
    protected final World world;
    protected SpriteRenderSystem spriteRenderSystem;
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
        this.start();
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
        this.free();
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
        spriteRenderSystem = world.registerSystem(SpriteRenderSystem.class);
        {
            spriteRenderSystem.setWorld(world);
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            sig.set(world.getComponentType(SpriteRenderComponent.class));
            world.setSystemSignature(sig, SpriteRenderSystem.class);
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
        return new Window(width, height, title, spriteRenderSystem, fontRenderSystem);
    }

    public abstract void start();
}
