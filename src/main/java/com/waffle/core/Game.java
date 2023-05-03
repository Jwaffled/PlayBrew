package com.waffle.core;

import com.waffle.components.*;
import com.waffle.ecs.World;
import com.waffle.input.Input;
import com.waffle.render.Camera;
import com.waffle.render.Window;
import com.waffle.struct.Vec2f;
import com.waffle.systems.*;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.BitSet;
import java.util.concurrent.*;

public abstract class Game implements Runnable, FreeableResource {
    public static final int DEFAULT_MAX_ENTITIES = 100000;
    public static final String DEFAULT_SCENE = "DefaultScene";
    private final int fpsCap;
    private final ExecutorService executinator = Executors.newSingleThreadExecutor();
    public float renderTime;
    protected Window window;
    protected World world;
    protected SceneManager sceneManager;

    /**
     * Constructs a game with default parameters
     */
    public Game() {
        this(DEFAULT_MAX_ENTITIES, 60, 960, 540, "Game", new Camera(960, 540));
    }

    /**
     * Constructs a new game
     * @param maxEntities the maximum amount of entities allowed in the default scene
     * @param fpsCap the maximum allowed frames per second
     * @param width the width of the window
     * @param height the height of the window
     * @param title the title of the application
     * @param cam the camera for viewing
     */
    public Game(int maxEntities, int fpsCap, int width, int height, String title, Camera cam) {
        sceneManager = new SceneManager();
        sceneManager.addScene(DEFAULT_SCENE, new DefaultScene(maxEntities));
        window = new Window(width, height, title, cam);
        setCurrentScene(DEFAULT_SCENE);
        world = sceneManager.getCurrentScene().getWorld();

        this.fpsCap = fpsCap;
        init();
    }

    private void init() {
        this.start();
        executinator.execute(this);
    }

    /**
     * Runs the main game loop of the Game.<br>
     * Called by a separate thread; no need to call this.
     */
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

    /**
     * Sets the current scene to be updated and displayed in the Game
     * @param name the name of the scene
     */
    public void setCurrentScene(String name) {
        sceneManager.setCurrentScene(name);
        Scene s = sceneManager.getCurrentScene();
        world = s.getWorld();
        window.canvas.setFontRenderSystem(s.getFontRenderSystem());
        window.canvas.setGeometryRenderSystem(s.getGeometryRenderSystem());
        window.canvas.setSpriteRenderSystem(s.getSpriteRenderSystem());
        window.canvas.setUiRenderSystem(s.getUIRenderSystem());
    }

    /**
     * Updates all systems within a scene, then displays the rendered frame.
     * @param dt the time between frames
     */
    public void update(float dt) {
        // Update other systems here
        updateInput();
        sceneManager.getCurrentScene().update(dt);
        long start = System.nanoTime();
        window.canvas.render();
        renderTime = (System.nanoTime() - start) / 1e3f;
    }

    private void updateInput() {
        Input.getInstance().setMousePosition(MouseInfo.getPointerInfo().getLocation());
    }

    /**
     * Called before the game loop starts
     */
    public abstract void start();
}
