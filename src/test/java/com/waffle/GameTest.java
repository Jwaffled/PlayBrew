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
    private AudioMenu audioMenu;
    private KeybindManager keybinds;
    private float currentVolume;
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

        if(keybinds.triggered("VolUp")) {
            currentVolume = effect.getVolumeControl().getValue() + 0.1f;
            effect.getVolumeControl().setValue(currentVolume);
            audioMenu.setCurrentVolume(currentVolume);
        }

        if(keybinds.triggered("VolDown")) {
            currentVolume = effect.getVolumeControl().getValue() - 0.1f;
            effect.getVolumeControl().setValue(currentVolume);
            audioMenu.setCurrentVolume(currentVolume);
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
        audioMenu = new AudioMenu();
        try {
            effect = new SoundEffect("soundEffectTest.wav");
        } catch(Exception e) {
            System.out.println("Skill issue: " + e.getMessage());
        }


        world.createGameObject(player);
        world.createGameObject(frameCount);
        world.createGameObject(audioMenu);

        keybinds = new KeybindManager();
        addBindings();
        window.setVisible(true);
    }

    @Override
    public void free() {
        effect.free();
    }

    private void addBindings() {
        keybinds.addKeybind("MoveLeft", KeyEvent.VK_LEFT);
        keybinds.addKeybind("MoveRight", KeyEvent.VK_RIGHT);
        keybinds.addMouseBind("Fire", MouseEvent.BUTTON1, KeyEvent.VK_SHIFT);
        keybinds.addKeybind("VolUp", KeyEvent.VK_UP);
        keybinds.addKeybind("VolDown", KeyEvent.VK_DOWN);
    }
}
