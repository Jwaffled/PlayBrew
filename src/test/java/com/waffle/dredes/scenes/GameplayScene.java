package com.waffle.dredes.scenes;

import com.waffle.core.DefaultScene;
import com.waffle.core.UpdateCounter;
import com.waffle.core.Utils;
import com.waffle.dredes.MainGame;
import com.waffle.dredes.gameobject.CollisionObject;
import com.waffle.dredes.gameobject.DebugMenu;
import com.waffle.dredes.gameobject.player.Player;
import com.waffle.dredes.level.Room;
import com.waffle.dredes.level.RoomLoader;
import com.waffle.dredes.level.Tile;
import com.waffle.input.KeybindManager;
import com.waffle.render.Camera;
import com.waffle.struct.Vec2f;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameplayScene extends DefaultScene {

    public static GameplayScene INSTANCE = null;
    public KeybindManager keybindManager;
    public Player player;
    public CollisionObject collisionObject;
    private RoomLoader roomLoader;
    private Room room;
    private DebugMenu debug;
    public GameplayScene() {
        super(10000);
        INSTANCE = this;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        Camera cam = MainGame.INSTANCE.gameCamera;
        cam.setPosition(new Vec2f(player.transform.position.x - cam.getSize().x / 2, player.transform.position.y - cam.getSize().y / 2));
        if(keybindManager.triggered("Pause") && getFramesActive() >= 20) {
            MainGame.INSTANCE.setCurrentScene("PauseScene");
        }
    }

    @Override
    public void start() {
        world.createLayers(3);

        roomLoader = new RoomLoader();
        roomLoader.addDirectory("DreDes/Rooms/Cave");
        room = roomLoader.getRoom("CaveEntrance");

        BufferedImage[] arr = new BufferedImage[5];
        arr[0] = Utils.loadImageFromPath("DreDes/Tiles/TileGrass.png");
        arr[1] = Utils.loadImageFromPath("DreDes/Tiles/TileIce.png");
        arr[2] = Utils.loadImageFromPath("DreDes/Tiles/TileRock.png");
        arr[3] = Utils.loadImageFromPath("DreDes/Tiles/TileSand.png");
        arr[4] = Utils.loadImageFromPath("DreDes/Tiles/TileWater.png");

        for(int i = 0; i < room.configuration.length; i++) {
            for(int j = 0; j < room.configuration[i].length; j++) {
                Tile t = new Tile(arr[room.configuration[i][j]], i + 3, j + 3, false, false);
                world.createGameObject(t);
            }
        }


        player = new Player();

        collisionObject = new CollisionObject();

        debug = new DebugMenu();

        keybindManager = new KeybindManager();
        addBindings();

        world.createGameObject(collisionObject);
        world.createGameObject(debug);
        world.createGameObject(player);
    }

    @Override
    public void focus() {
        super.focus();
    }

    private void addBindings() {
        keybindManager.addKeybind("Pause", KeyEvent.VK_ESCAPE);
    }
}
