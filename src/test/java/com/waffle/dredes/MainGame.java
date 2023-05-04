package com.waffle.dredes;

import com.waffle.core.Game;
import com.waffle.dredes.scenes.MenuScene;
import com.waffle.dredes.scenes.PhysicsScene;
import com.waffle.dredes.scenes.TitleScene;
import com.waffle.input.Input;


public class MainGame extends Game {
    public static MainGame INSTANCE = null;

    public MainGame() {
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
//        sceneManager.addScene("TitleScene", new TitleScene());
//        setCurrentScene("TitleScene");
        sceneManager.addScene("Physics Test", new PhysicsScene());
        window.addMouseListener(Input.getInstance());
        window.addKeyListener(Input.getInstance());
        window.setVisible(true);
    }
}
