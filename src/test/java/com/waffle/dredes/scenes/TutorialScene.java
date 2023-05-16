package com.waffle.dredes.scenes;

import com.waffle.core.DefaultScene;
import com.waffle.core.Utils;
import com.waffle.dredes.MainGame;
import com.waffle.dredes.gameobject.TextBox;
import com.waffle.dredes.gameobject.player.Player;
import com.waffle.dredes.level.*;
import com.waffle.input.KeybindManager;
import com.waffle.render.Camera;
import com.waffle.struct.Vec2f;

import java.awt.event.KeyEvent;

/**
 * A tutorial scene
 */
public class TutorialScene extends DefaultScene {
    private KeybindManager keybindManager;
    private Player player;
    private SourceEntity source;
    private TextBox textBox;

    /**
     * Constructs a tutorial scene
     */
    public TutorialScene() {
        super(10000);
    }

    /**
     * Updates the tutorial scene's state; called each frame
     * @param dt the time between frames
     */
    @Override
    public void update(float dt) {
        super.update(dt);
        Camera cam = MainGame.INSTANCE.gameCamera;
        cam.getPosition().set(player.transform.position.x - cam.getSize().x / 2, player.transform.position.y - cam.getSize().y / 2);
        if(keybindManager.triggered("Pause") && getFramesActive() >= 20) {
            MainGame.INSTANCE.setCurrentScene("PauseScene");
        }
        if(source.health <= 0) {
            MainGame.INSTANCE.setCurrentScene("TitleScene");
        }

        if(between(player.transform.position.x, 80, 150) && textBox.isFinished()) {
            textBox.setNewMessage("Welcome to Dreams and Deserts!     \nUse W, A, S, and D to move.     ");
        } else if(between(player.transform.position.x, 150, 300) && textBox.isFinished()) {
            textBox.setNewMessage("Use the spacebar to jump.     ");
        } else if(between(player.transform.position.x, 400, 700) && textBox.isFinished()) {
            textBox.setNewMessage("If you get stuck below, don't fear!     \nYou can jump on walls too!     ");
        } else if(between(player.transform.position.x, 1700, 2800) && textBox.isFinished()) {
            textBox.setNewMessage("Your goal is to reach the 'source'.     \nBy touching the 'source', you can move on!     ");
        }
    }

    private boolean between(float pos, float min, float max) {
        return pos >= min && pos <= max;
    }

    /**
     * Initializes the level scene, called automatically
     */
    @Override
    public void start() {
        world.createLayers(3);
        LevelGen l = LevelGen.INSTANCE;
        Tile[] tiles = new Tile[7];
        tiles[0] = null;
        tiles[3] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Water.png"), -1, -1, true, true, 1f, new Vec2f(1.5f, 2f));
        tiles[1] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Grass.png"), -1, -1, false, false);
        tiles[2] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Dirt.png"), -1, -1, false, false);
        tiles[4] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Gravel.png"), -1, -1, false, false, 1.25f, new Vec2f(1.1f,1.5f));
        tiles[5] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Bricks.png"), -1, -1, false, false);
        tiles[6] = new Tile(Utils.loadImageFromPath("DreDes/Tiles/Tiles.png"), -1, -2, false, false);

        RoomLoader rm = new RoomLoader();

        loadGrass(rm);
        rm.addRoomPath("DreDes/Rooms/Universal/OpenAir.txt");
        rm.addRoomPath("DreDes/Rooms/Universal/SolidGround.txt");
        rm.addRoomPath("DreDes/Rooms/Universal/OpenWater.txt");
        rm.addRoomPath("DreDes/Rooms/Special/Camp.txt");
        rm.addRoomPath("DreDes/Rooms/Special/Source.txt");

        Room[][] level = {
                {rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir")},
                {rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir")},
                {rm.getRoom("Camp"), rm.getRoom("SmallHill"), rm.getRoom("PondStart"), rm.getRoom("PondMiddle"), rm.getRoom("PondStart").flip(), rm.getRoom("SmallHill").flip(), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("OpenAir"), rm.getRoom("Source")},
                {rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("DeepPond"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("BigHill").flip(), rm.getRoom("OpenAir"), rm.getRoom("BigHill"), rm.getRoom("SolidGround")},
                {rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround")},
                {rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround"), rm.getRoom("SolidGround")}
        };

        Tile[][] arr = roomsToTiles(level, tiles);
        for(Tile[] a : arr) {
            for(Tile t : a) {
                if(t != null) {
                    world.createGameObject(t);
                }
            }
        }

        player = new Player();
        textBox = new TextBox("");

        source = new SourceEntity();

        keybindManager = new KeybindManager();
        addBindings();

        world.createGameObject(textBox);
        world.createGameObject(source);
        world.createGameObject(player);
    }

    /**
     * Called when the scene is set as the current displayed scene
     */
    @Override
    public void focus() {
        super.focus();
    }

    private void loadGrass(RoomLoader rm) {
        loadHillsFolder(rm);
        loadPondFolder(rm);
    }

    private void loadPondFolder(RoomLoader roomLoader) {
        String[] pondFolder = {
                "DeepPond.txt",
                "PondMiddle.txt",
                "PondStart.txt",
                "SmallPond.txt"
        };

        for(String s : pondFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/Pond/" + s);
        }
    }

    private void loadHillsFolder(RoomLoader roomLoader) {
        String[] hillsFolder = {
                "BigHill.txt",
                "SmallHill.txt"
        };

        for(String s : hillsFolder) {
            roomLoader.addRoomPath("DreDes/Rooms/Hills/" + s);
        }
    }

    private Tile[][] roomsToTiles(Room[][] level, Tile[] tiles) {
        Tile[][] ret = new Tile[36][80];
        for(int i = 0; i < level.length; i++) {
            for(int j = 0; j < level[0].length; j++) {
                if(level[i][j] != null) {
                    int[][] blueprint = level[i][j].configuration;
                    for(int k = 0; k < blueprint.length; k++) {
                        for(int l = 0; l < blueprint[k].length; l++) {
                            if(tiles[blueprint[k][l]] != null) {
                                ret[(i * 6)+ k][(j * 8) + l] = tiles[blueprint[k][l]].copy((i * 6) + k,(j * 8) + l);
                            }
                        }
                    }
                }

            }
        }

        return ret;
    }

    private void addBindings() {
        keybindManager.addKeybind("Pause", KeyEvent.VK_ESCAPE);
    }
}