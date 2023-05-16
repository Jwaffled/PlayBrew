package com.waffle.dredes.scenes;

import com.waffle.core.DefaultScene;
import com.waffle.dredes.MainGame;
import com.waffle.dredes.gameobject.CollisionObject;
import com.waffle.dredes.gameobject.DebugMenu;
import com.waffle.dredes.gameobject.player.Player;
import com.waffle.dredes.level.*;
import com.waffle.input.KeybindManager;
import com.waffle.render.Camera;
import com.waffle.struct.Vec2f;

import java.awt.event.KeyEvent;
import java.util.Map;

public class TutorialScene extends DefaultScene {

    public static TutorialScene INSTANCE = null;
    public KeybindManager keybindManager;
    public Player player;
    public SourceEntity source;
    public Map<Vec2f, String> guide;
    public CollisionObject collisionObject;
    //private DebugMenu debug;
    public TutorialScene() {
        super(10000);
        INSTANCE = this;
    }

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
    }

    @Override
    public void start() {
        world.createLayers(3);
        LevelGen l = LevelGen.INSTANCE;
        Tile[][] arr = l.generate(LevelGen.Biome.Grassland, 0, 0, false);
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

        source = new SourceEntity();

        keybindManager = new KeybindManager();
        addBindings();


        world.createGameObject(source);
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