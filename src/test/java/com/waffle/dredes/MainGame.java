package com.waffle.dredes;

import com.waffle.core.Game;
import com.waffle.dredes.scenes.GameplayScene;
import com.waffle.dredes.scenes.PauseScene;
import com.waffle.dredes.scenes.TitleScene;
import com.waffle.input.Input;
import com.waffle.input.KeybindManager;
import com.waffle.render.Camera;

import java.awt.event.KeyEvent;

public class MainGame extends Game {
    public static MainGame INSTANCE = null;
    public KeybindManager keybindManager;

    public MainGame() {
        super(10, 60, 960, 540, "Dreams & Deserts", new Camera(960, 540));
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void free() {

    }

    @Override
    public void start() {
        INSTANCE = this;
        // Change this to make it so that each scene has its own ECS
        // And gameobjects
        gameCamera.setZoomScale(5);
        keybindManager = new KeybindManager();


        addScene("TitleScene", new TitleScene());
        addScene("GameplayScene", new GameplayScene());
        addScene("PauseScene", new PauseScene());
        setCurrentScene("TitleScene");

        window.addMouseListener(Input.getInstance());
        window.addKeyListener(Input.getInstance());

        addKeybinds();


        window.setVisible(true);
    }

    private void addKeybinds() {
//        keybindManager.addKeybind("PlayerLeft", KeyEvent.VK_A);
//        keybindManager.addKeybind("PlayerRight", KeyEvent.VK_D);
//        keybindManager.addKeybind("PlayerJump", KeyEvent.VK_W);
//        keybindManager.addKeybind("PlayerShoot", KeyEvent.VK_SPACE);
        keybindManager.addKeybind("Pause", KeyEvent.VK_ESCAPE);
    }
}
