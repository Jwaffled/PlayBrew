package com.waffle.dredes.scenes;

import com.waffle.core.DefaultScene;
import com.waffle.core.UpdateCounter;
import com.waffle.core.Utils;
import com.waffle.dredes.MainGame;
import com.waffle.dredes.gameobject.CollisionObject;
import com.waffle.dredes.gameobject.DebugMenu;
import com.waffle.dredes.gameobject.player.Player;
import com.waffle.dredes.level.LevelGen;
import com.waffle.dredes.level.Room;
import com.waffle.dredes.level.RoomLoader;
import com.waffle.dredes.level.Tile;
import com.waffle.input.KeybindManager;
import com.waffle.render.Camera;
import com.waffle.struct.Vec2f;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * The main gameplay scene
 */
public class GameplayScene extends DefaultScene {

    public static GameplayScene INSTANCE = null;
    public KeybindManager keybindManager;
    public Player player;
    public CollisionObject collisionObject;
    private RoomLoader roomLoader;
    private Room room;
    private DebugMenu debug;

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
        Camera cam = MainGame.INSTANCE.gameCamera;
        cam.setPosition(new Vec2f(player.transform.position.x - cam.getSize().x / 2, player.transform.position.y - cam.getSize().y / 2));
        if(keybindManager.triggered("Pause") && getFramesActive() >= 20) {
            MainGame.INSTANCE.setCurrentScene("PauseScene");
        }
    }

    /**
     * Called when the Scene is added to the world
     */
    @Override
    public void start() {
        world.createLayers(3);
        LevelGen l = LevelGen.INSTANCE;
        Tile[][] arr = l.generate(LevelGen.Biome.Stoneland, 0, 0, false);

        for(Tile[] a : arr) {
            for(Tile tile : a) {
                if(tile != null)
                {
                    world.createGameObject(tile);
                }
            }
        }



        player = new Player();

        collisionObject = new CollisionObject();

        debug = new DebugMenu();

        keybindManager = new KeybindManager();
        addBindings();

        //world.createGameObject(collisionObject);
        world.createGameObject(debug);
        world.createGameObject(player);
    }

    /**
     * Called when the scene is set as the current displayed scene
     */
    @Override
    public void focus() {
        super.focus();
    }

    private void addBindings() {
        keybindManager.addKeybind("Pause", KeyEvent.VK_ESCAPE);
    }
}
