package com.waffle.dredes.scenes;

import com.waffle.core.DefaultScene;
import com.waffle.dredes.MainGame;
import com.waffle.dredes.gameobject.DebugMenu;
import com.waffle.dredes.gameobject.player.Player;

public class GameplayScene extends DefaultScene {

    public static GameplayScene INSTANCE = null;
    public Player player;
    private DebugMenu debug;
    public GameplayScene() {
        super(10000);
        INSTANCE = this;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void start() {
        world.createLayers(3);

        player = new Player();

        debug = new DebugMenu();

        world.createGameObject(debug);
        world.createGameObject(player);
    }
}
