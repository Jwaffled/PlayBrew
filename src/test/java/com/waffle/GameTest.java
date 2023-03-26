package com.waffle;

import com.waffle.audio.StereoSoundEffect;
import com.waffle.core.Game;
import com.waffle.input.*;
import com.waffle.render.Camera;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameTest extends Game {
    private Player player;
    private StereoSoundEffect effect;
    private StereoSoundEffect bgm;

    private FrameCounter frameCount;
    private AudioMenu audioMenu;
    private KeybindManager keybinds;
    private Camera camera;
    private float currentVolume;
    public static GameTest INSTANCE = null;

    public GameTest() {
        INSTANCE = this;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if(keybinds.triggered("VolUp")) {
            currentVolume = effect.getVolumeControl().getValue() + 0.1f;
            effect.getVolumeControl().setValue(currentVolume);
            bgm.getVolumeControl().setValue(currentVolume);
            audioMenu.setCurrentVolume(currentVolume);
        }

        if(keybinds.triggered("VolDown")) {
            currentVolume = effect.getVolumeControl().getValue() - 0.1f;
            effect.getVolumeControl().setValue(currentVolume);
            bgm.getVolumeControl().setValue(currentVolume);
            audioMenu.setCurrentVolume(currentVolume);
        }

        if(keybinds.triggered("PanUp")) {
            camera.getPosition().addY(-10);
        }

        if(keybinds.triggered("PanDown")) {
            camera.getPosition().addY(10);
        }

        if(keybinds.triggered("PanLeft")) {
            camera.getPosition().addX(-10);
        }

        if(keybinds.triggered("PanRight")) {
            camera.getPosition().addX(10);
        }

        if(keybinds.triggered("Fire")) {
            player.shoot();
            effect.restart();
            effect.start();
        }

        if(keybinds.triggered("MoveLeft")) {
            player.moveLeft();
        }

        if(keybinds.triggered("MoveRight")) {
            player.moveRight();
        }

        if(keybinds.triggered("MoveUp")) {
            player.moveUp();
        }

        if(keybinds.triggered("MoveDown")) {
            player.moveDown();
        }
    }

    public void start() {
        currentVolume = -20;
        camera = new Camera(960, 540);
        window = createWindow("Testing", camera);
        window.addMouseListener(Input.getInstance());
        window.addKeyListener(Input.getInstance());

        player = new Player();
        frameCount = new FrameCounter();
        audioMenu = new AudioMenu();

        try {
            effect = new StereoSoundEffect("eightbit.wav");
            effect.getVolumeControl().setValue(currentVolume);
            audioMenu.setCurrentVolume(currentVolume);
            bgm = new StereoSoundEffect("subwoofer.wav");
            bgm.getVolumeControl().setValue(currentVolume);
        } catch(Exception e) {
            System.out.println("Skill issue: " + e.getMessage());
        }

        bgm.start();


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
        keybinds.addKeybind("MoveUp", KeyEvent.VK_UP);
        keybinds.addKeybind("MoveDown", KeyEvent.VK_DOWN);

        keybinds.addKeybind("VolUp", KeyEvent.VK_PAGE_UP);
        keybinds.addKeybind("VolDown", KeyEvent.VK_PAGE_DOWN);

        keybinds.addKeybind("PanLeft", KeyEvent.VK_A);
        keybinds.addKeybind("PanRight", KeyEvent.VK_D);
        keybinds.addKeybind("PanDown", KeyEvent.VK_S);
        keybinds.addKeybind("PanUp", KeyEvent.VK_W);

        keybinds.addMouseBind("Fire", MouseEvent.BUTTON1);
    }
}
