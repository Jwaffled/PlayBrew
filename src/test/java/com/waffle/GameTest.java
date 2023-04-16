package com.waffle;

import com.waffle.audio.StereoSoundEffect;
import com.waffle.core.Game;
import com.waffle.core.Utils;
import com.waffle.core.Vec2f;
import com.waffle.input.*;
import com.waffle.render.Camera;
import com.waffle.tilemap.Tilemap;
import com.waffle.tilemap.TilemapBuilder;
import com.waffle.ui.ButtonEventListener;
import com.waffle.ui.TexturedButton;
import com.waffle.ui.TexturedSlider;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class GameTest extends Game {
    public Player player;
    public Tilemap tilemap;
    private StereoSoundEffect effect;
    private StereoSoundEffect bgm;
    private DebugMenu debug;
    private TexturedButton button;
    public TexturedSlider slider;
    public Camera camera;
    public KeybindManager keybinds;
    public float currentVolume;
    public FrameCounter frameCounter;
    public static GameTest INSTANCE = null;

    public GameTest() {
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        frameCounter.update(dt);

        currentVolume = slider.getValue();
        effect.getVolumeControl().setValue(currentVolume);
        bgm.getVolumeControl().setValue(currentVolume);


        if(keybinds.triggered("Exit")) {
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        }

        if(keybinds.triggered("Clear")) {
            world.clearAllExcept(player.ID, debug.ID);
        }

        if(keybinds.triggered("VolUp")) {
            currentVolume = effect.getVolumeControl().getValue() + 0.1f;
            slider.setValue(currentVolume);
        }

        if(keybinds.triggered("VolDown")) {
            currentVolume = effect.getVolumeControl().getValue() - 0.1f;
            slider.setValue(currentVolume);
        }

        if(keybinds.triggered("PanUp")) {
            camera.position.addY(-10 * camera.zoomScale);
        }

        if(keybinds.triggered("PanDown")) {
            camera.position.addY(10 * camera.zoomScale);
        }

        if(keybinds.triggered("PanLeft")) {
            camera.position.addX(-10 * camera.zoomScale);
        }

        if(keybinds.triggered("PanRight")) {
            camera.position.addX(10 * camera.zoomScale);
        }

        if(keybinds.triggered("Fire") && player.canShoot()) {
            player.shoot();
            effect.restart();
            effect.start();
        }
    }

    public void start() {
        world.createLayers(4);
        INSTANCE = this;
        currentVolume = -20;
        camera = new Camera(960, 540);
        window = createWindow(960, 540, "Testing", camera);
        window.addMouseListener(Input.getInstance());
        window.addKeyListener(Input.getInstance());
        window.addMouseWheelListener(e -> camera.zoomScale += e.getWheelRotation() * 0.1f);

        BufferedImage texture = Utils.loadImageFromPath("TestButtonPlayBrew_2.png");

        BufferedImage tint = Utils.applyTint(texture, new Color(90, 90, 90, 100));

        BufferedImage grassTex = Utils.loadImageFromPath("Grass.png");
        BufferedImage dirtTex = Utils.loadImageFromPath("Dirt.png");

        player = new Player();
        frameCounter = new FrameCounter();
        debug = new DebugMenu();


        tilemap = Tilemap.newBuilder()
                .addTilemapping(1, grassTex)
                .addTilemapping(2, dirtTex)
                .setRow(0, 1)
                .setRows(0, TilemapBuilder.TO_END, 2)
                .buildTilemap();

        button = TexturedButton.newBuilder()
                .setX(750)
                .setY(95)
                .setWidth(100)
                .setHeight(50)
                .setButtonMessage("Player can shoot: true")
                .setMessageOffset(new Vec2f(-10, -30))
                .setButtonTexture(texture)
                .addButtonListener(new ButtonEventListener() {
                    @Override
                    public void buttonClicked() {
                        player.setCanShoot(!player.canShoot());
                        button.text.message = "Player can shoot: " + player.canShoot();
                    }

                    @Override
                    public void buttonPressed() {
                        button.setCurrentTexture(tint);
                    }

                    @Override
                    public void buttonReleased() {
                        button.setCurrentTexture(texture);
                    }

                    @Override
                    public void mouseEntered() {
                        System.out.println("Mouse entered button");
                        //button.geometryComponent.color = Color.GRAY;
                    }

                    @Override
                    public void mouseExited() {
                        System.out.println("Mouse exited button");
                        //button.geometryComponent.color = Color.LIGHT_GRAY;
                    }
                })
                .buildTexturedButton();

        BufferedImage sliderRect = Utils.loadImageFromPath("SliderRect.png");
        BufferedImage sliderTrack = Utils.loadImageFromPath("SliderTrack.png");

        slider = TexturedSlider.newBuilder()
                .setX(750)
                .setY(15)
                .setWidth(200)
                .setHeight(50)
                .setMinValue(-60)
                .setMaxValue(0)
                .setSliderWidth(10)
                .setStartingValue(-20)
                .setSliderTexture(sliderRect)
                .setTrackTexture(sliderTrack)
                .buildTexturedSlider();


        try {
            effect = new StereoSoundEffect("eightbit.wav");
            effect.getVolumeControl().setValue(currentVolume);
            bgm = new StereoSoundEffect("subwoofer.wav");
            bgm.getVolumeControl().setValue(currentVolume);
        } catch(Exception e) {
            System.out.println("Skill issue: " + e.getMessage());
        }

        bgm.start();

        world.createGameObject(slider);
        world.createGameObject(tilemap, 1);
        world.createGameObject(player, 2);
        world.createGameObject(debug);
        world.createGameObject(button);

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

        keybinds.addKeybind("Clear", KeyEvent.VK_SPACE);

        keybinds.addKeybind("Exit", KeyEvent.VK_ESCAPE);
    }
}
