package com.waffle.dredes.scenes;

import com.waffle.core.DefaultScene;
import com.waffle.dredes.MainGame;
import com.waffle.dredes.gameobject.Background;

import com.waffle.dredes.gameobject.DebugMenu;
import com.waffle.dredes.gameobject.player.Player;
import com.waffle.dredes.level.*;
import com.waffle.input.KeybindManager;
import com.waffle.render.Camera;
import com.waffle.struct.Vec2f;

import java.awt.event.KeyEvent;

/**
 * The main gameplay scene
 */
public class GameplayScene extends DefaultScene {

    public static GameplayScene INSTANCE = null;
    public KeybindManager keybindManager;
    public Player player;
    public Background background;
    private RoomLoader roomLoader;
    public SourceEntity source;
    private Room room;
    private Tile[][] tiles;
    //private DebugMenu debug;

    /**
     * Creates a new gameplay scene with a maximum of 10000 entities
     */
    public GameplayScene() {
        super(10000);
        INSTANCE = this;
    }

    /**
     * Called every frame, updates systems and state within this scene
     * @param dt the time between frames
     */
    @Override
    public void update(float dt) {
        super.update(dt);
        //System.out.println(source.health);
        Camera cam = MainGame.INSTANCE.gameCamera;
        cam.getPosition().set(player.transform.position.x - cam.getSize().x / 2, player.transform.position.y - cam.getSize().y / 2);
        if(keybindManager.triggered("Pause") && getFramesActive() >= 20) {
            MainGame.INSTANCE.setCurrentScene("PauseScene");
        }

        if(source.health < 1) {
            world.removeGameObject(source);
            if(MainGame.INSTANCE.getPreviousScene() instanceof MapScene mapScene) {
                Vec2f temp = new Vec2f(mapScene.pos.x - 37.5f, mapScene.pos.y - 37.5f);
                mapScene.icon.transformComponent.position = new Vec2f(temp);
                for(Tile[] arr : tiles) {
                    for(Tile t : arr) {
                        if(t != null) {
                            world.removeGameObject(t);
                        }
                    }
                }
                MainGame.INSTANCE.setCurrentScene("MapScene");
            }
        }
    }

    /**
     * Called when the Scene is added to the world
     */
    @Override
    public void start() {
        world.createLayers(3);


        Camera camera = MainGame.INSTANCE.gameCamera;
        background = new Background("DreDes/DreDes-BG-Nighttime.png", (int)camera.getSize().x, (int)camera.getSize().y, camera);
        player = new Player();

        //debug = new DebugMenu();

        keybindManager = new KeybindManager();
        addBindings();

        //world.createGameObject(collisionObject);
        //world.createGameObject(debug, 1);
        world.createGameObject(player, 1);
        world.createGameObject(background, 0);
    }

    /**
     * Called when the scene is set as the current displayed scene
     */
    @Override
    public void focus() {
        super.focus();
        if(MainGame.INSTANCE.getPreviousScene() instanceof MapScene mapScene) {
            LevelGen l = LevelGen.INSTANCE;
            Vec2f temp = new Vec2f(mapScene.pos.x, mapScene.pos.y);
            if(tiles != null) {
                for(Tile[] a : tiles) {
                    for(Tile tile : a) {
                        if(tile != null) {
                            world.removeGameObject(tile);
                        }
                    }
                }
            }

            tiles = l.generate(mapScene.biome, (int)temp.x, (int)temp.y, false);

            for(Tile[] a : tiles) {
                for(Tile tile : a) {
                    if(tile != null) {
                        world.createGameObject(tile, 1);
                    }
                }
            }
            player.transform.position.x = 64;
            player.transform.position.y = 448;
            if(source != null)
            {
                
            }
            source = new SourceEntity();
            source.health = 3;
            world.createGameObject(source, 1);
        }

    }

    private void addBindings() {
        keybindManager.addKeybind("Pause", KeyEvent.VK_ESCAPE);
    }
}