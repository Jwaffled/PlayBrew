package com.waffle;

import com.waffle.audio.SoundEffect;
import com.waffle.core.Game;
import com.waffle.input.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameTest extends Game {
    private Player player;
    private SoundEffect effect;

    private FrameCounter frameCount;
    private KeybindManager keybinds;
    public static GameTest INSTANCE = null;

    public GameTest() {
        INSTANCE = this;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(keybinds.triggered("Fire")) {
            player.shoot();
            effect.restart();
            effect.start();
        }
        if(keybinds.triggered("MoveLeft")) {
            player.moveLeft();
        } else if(keybinds.triggered("MoveRight")) {
            player.moveRight();
        }
    }

    public void start() {
        window = createWindow(800, 600, "Testing");
        window.addMouseListener(Input.getInstance());
        window.addKeyListener(Input.getInstance());

        player = new Player();
        frameCount = new FrameCounter();
        try {
            effect = new SoundEffect("soundEffectTest.wav");
            //effect.setOverlap(true);
        } catch(Exception e) {
            System.out.println("Skill issue: " + e.getMessage());
        }


        world.createGameObject(player);
        world.createGameObject(frameCount);

        keybinds = new KeybindManager();
        addBindings();
        window.setVisible(true);
    }

    private void addBindings()
    {
        keybinds.addKeybind("MoveLeft", KeyEvent.VK_LEFT);
        keybinds.addKeybind("MoveRight", KeyEvent.VK_RIGHT);
        keybinds.addMouseBind("Fire", MouseEvent.BUTTON1, KeyEvent.VK_SHIFT);
    }
}
