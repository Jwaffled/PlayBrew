package com.waffle.core;

import com.waffle.components.*;
import com.waffle.ecs.World;
import com.waffle.input.Input;
import com.waffle.render.Camera;
import com.waffle.render.Window;
import com.waffle.systems.*;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.BitSet;
import java.util.concurrent.*;

public abstract class Game implements Runnable, FreeableResource {
    public static final int DEFAULT_MAX_ENTITIES = 100000;
    private final int fpsCap;
    private final ExecutorService executinator = Executors.newSingleThreadExecutor();
    public float renderTime;
    protected Window window;
    protected final World world;
    protected SpriteRenderSystem spriteRenderSystem;
    protected PhysicsSystem physicsSystem;
    protected CollisionSystem collisionSystem;
    protected FontRenderSystem fontRenderSystem;
    protected GeometryRenderSystem geometryRenderSystem;
    protected UIRenderSystem uiRenderSystem;

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
        try {
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
        } catch(Exception e) {
            Constants.LOGGER.logException(e, LogLevel.FATAL);
        } finally {
            if(window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }
            this.free();
        }
    }

    public void update(float dt) {
        // Update other systems here
        updateInput();
        physicsSystem.update(dt);
        collisionSystem.update(dt);
        world.update(dt);
        long start = System.nanoTime();
        window.canvas.render();
        renderTime = (System.nanoTime() - start) / 1e3f;
    }

    private void updateInput() {
        Input.getInstance().setMousePosition(MouseInfo.getPointerInfo().getLocation());
    }

    private void registerSystems() {
        spriteRenderSystem = world.registerSystem(SpriteRenderSystem.class);
        {
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            sig.set(world.getComponentType(SpriteRenderComponent.class));
            world.setSystemSignature(sig, SpriteRenderSystem.class);
        }

        physicsSystem = world.registerSystem(PhysicsSystem.class);
        {
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            sig.set(world.getComponentType(KinematicComponent.class));
            world.setSystemSignature(sig, PhysicsSystem.class);
        }

        fontRenderSystem = world.registerSystem(FontRenderSystem.class);
        {
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            sig.set(world.getComponentType(FontRenderComponent.class));
            world.setSystemSignature(sig, FontRenderSystem.class);
        }

        geometryRenderSystem = world.registerSystem(GeometryRenderSystem.class);
        {
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(GeometryComponent.class));
            sig.set(world.getComponentType(TransformComponent.class));
            world.setSystemSignature(sig, GeometryRenderSystem.class);
        }

        uiRenderSystem = world.registerSystem(UIRenderSystem.class);
        {
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(UITextureComponent.class));
            sig.set(world.getComponentType(TransformComponent.class));
            world.setSystemSignature(sig, UIRenderSystem.class);
        }
        collisionSystem = world.registerSystem(CollisionSystem.class);
        {
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            sig.set(world.getComponentType(ColliderComponent.class));
            sig.set(world.getComponentType(KinematicComponent.class));
            world.setSystemSignature(sig, CollisionSystem.class);
        }
    }

    private void registerComponents() {
        world.registerComponent(TransformComponent.class);
        world.registerComponent(SpriteRenderComponent.class);
        world.registerComponent(FontRenderComponent.class);
        world.registerComponent(GeometryComponent.class);
        world.registerComponent(UITextureComponent.class);
        world.registerComponent(KinematicComponent.class);
        world.registerComponent(ColliderComponent.class);
    }

    /**
     * @param width the width of the window (in pixels)
     * @param height the height of the window (in pixels)
     * @param title the title of the window
     * @return A JPanel representing the main game window
     */
    protected Window createWindow(int width, int height, String title, Camera cam) {
        return new Window(width, height, title, spriteRenderSystem, fontRenderSystem, geometryRenderSystem, uiRenderSystem, cam);
    }

    protected Window createWindow(String title, Camera cam) {
        return new Window(title, spriteRenderSystem, fontRenderSystem, geometryRenderSystem, uiRenderSystem, cam);
    }

    public abstract void start();
}
