package com.waffle.dredes;

import com.waffle.core.Game;
import com.waffle.dredes.scenes.*;
import com.waffle.input.Input;
import com.waffle.input.KeybindManager;
import com.waffle.render.Camera;

import java.awt.event.KeyEvent;

/**
 * The main game loop and manager for the entire game
 */
public class MainGame extends Game {
    public static MainGame INSTANCE = null;
    public KeybindManager keybindManager;

    /**
     * Constructs a new game with default camera settings and video settings
     */
    public MainGame() {
        super(10, 60, 960, 540, "Dreams & Deserts", new Camera(960, 540));
    }

    /**
     * Updates all of the state within the game
     * @param dt the time between frames
     */
    @Override
    public void update(float dt) {
        super.update(dt);
    }

    /**
     * Frees resources associated with this game
     */
    @Override
    public void free() {

    }

    /**
     * Called before the main game loop starts
     */
    @Override
    public void start() {
        INSTANCE = this;
        // Change this to make it so that each scene has its own ECS
        // And gameobjects
        gameCamera.setZoomScale(1);
        keybindManager = new KeybindManager();


        addScene("MapScene", new MapScene());
        addScene("TitleScene", new TitleScene());
        addScene("GameplayScene", new GameplayScene());
        addScene("PauseScene", new PauseScene());
        addScene("TutorialScene", new TutorialScene());
        setCurrentScene("TitleScene");

        window.addMouseListener(Input.getInstance());
        window.addKeyListener(Input.getInstance());

        addKeybinds();


        window.setVisible(true);
    }

    private void addKeybinds() {
        keybindManager.addKeybind("Pause", KeyEvent.VK_ESCAPE);
    }
}
